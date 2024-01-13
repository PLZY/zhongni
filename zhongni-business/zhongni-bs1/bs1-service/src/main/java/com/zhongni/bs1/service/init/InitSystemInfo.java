package com.zhongni.bs1.service.init;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.service.service.local.systeminfo.SystemInfoService;
import com.zhongni.bs1.entity.db.systeminfo.SystemInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class InitSystemInfo implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SystemInfoService systemInfoService;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void run(ApplicationArguments args)
    {
        log.info("execute init system info after springboot start...");
        //initTableAndData();
        log.info("execute end...");
    }

    /**
     * 初始化数据库表信息
     */
    public void initTableAndData()
    {
        // todo 极端场景：如果是集群部署并且所有节点使用的是同一个库，同时启动所有节点可能会出现脚本覆盖执行的问题，必须要保证其中一个节点启动成功并且脚本执行完毕之后，再启动其他节点
        String dbFileDirPath = Objects.requireNonNull(getClass().getResource("/")).getPath() + "db" + File.separator;
        File dbFileDir = new File(dbFileDirPath);
        log.info("dbFileDirPath is {}, dbFileDirPath exists flag is {}", dbFileDirPath, dbFileDir.exists());
        // true if the new current row is valid; false if there are no more rows.
        if(!jdbcTemplate.queryForRowSet("SHOW TABLES LIKE 'squid_system_info'").first())
        {
            // 将文件夹按照文件名从小到大依次排序
            List<File> allDBFileDirList = Stream.of(Objects.requireNonNull(dbFileDir.listFiles())).
                    sorted(Comparator.comparingLong(file -> Long.parseLong(file.getName()))).collect(Collectors.toList());
            // 不存在squid_system_info说明是第一次部署，执行所有的SQL文件
            log.info("first deployment: will execute all sql script file where in resources/db dir ");
            initTableAndDataByFile(allDBFileDirList);
            return;
        }

        SystemInfo initSystemInfo = systemInfoService.getOne(new LambdaQueryWrapper<SystemInfo>().eq(SystemInfo::getApplicationName, applicationName));
        // 获取不到系统的初始化信息直接默认执行db文件夹下全部的sql文件
        if(null == initSystemInfo || StringUtils.isBlank(initSystemInfo.getCurrentDBVersion()))
        {
            List<File> allDBFileDirList = Stream.of(Objects.requireNonNull(dbFileDir.listFiles())).
                    sorted(Comparator.comparingLong(file -> Long.parseLong(file.getName()))).collect(Collectors.toList());
            log.info("can not get system db version info will execute all sql script file where in resources/db dir ");
            initTableAndDataByFile(allDBFileDirList);
            return;
        }

        long currentDBVersion = Long.parseLong(initSystemInfo.getCurrentDBVersion());
        List<File> allDBFileDirList = Stream.of(Objects.requireNonNull(dbFileDir.listFiles())).
                sorted(Comparator.comparingLong(file -> Long.parseLong(file.getName()))).collect(Collectors.toList());
        long lastDBFileVersion = Long.parseLong(allDBFileDirList.get(allDBFileDirList.size() - 1).getName());
        // 当前系统的数据库脚本与db文件夹下包含的最新脚本版本一致或者高，则不进行任何的脚本执行
        if(currentDBVersion >= lastDBFileVersion)
        {
            log.info("no sql file need execute. current system db version is [{}], db dir last version is [{}]", currentDBVersion, lastDBFileVersion);
            return;
        }

        List<File> needExecuteFileDirList = allDBFileDirList.stream().filter(file -> Long.parseLong(file.getName()) > currentDBVersion).collect(Collectors.toList());
        log.info("need execute db file list is {} ", JSON.toJSONString(needExecuteFileDirList));
        initTableAndDataByFile(needExecuteFileDirList);
    }

    private void initTableAndDataByFile(List<File> allDBFileDirList)
    {
        try(Connection connection = dataSource.getConnection())
        {
            ScriptRunner scriptRunner = getScriptRunner(connection);
            for (File fileDir : allDBFileDirList)
            {
                if(!fileDir.isDirectory())
                {
                    log.warn("ignore file in root path, file name is {}", fileDir.getName());
                    continue;
                }

                // 将文件按照文件长度从小短到长依次排序，保证限制性建表的table.sql文件再执行添加数据的table-data.sql文件
                List<File> realDBFileList = Stream.of(Objects.requireNonNull(fileDir.listFiles())).sorted(Comparator.comparingLong(realFile -> realFile.getName().length())).collect(Collectors.toList());
                for (File realDBFile : realDBFileList)
                {
                    if(-1 == realDBFile.getName().lastIndexOf(".sql"))
                    {
                        log.warn("ignore no .sql file in path, file name is {}", realDBFile.getPath());
                        continue;
                    }

                    log.info("sql file [{}] will execute...", realDBFile.getPath());
                    scriptRunner.runScript(new InputStreamReader(Files.newInputStream(realDBFile.toPath()), StandardCharsets.UTF_8));
                    log.info("sql file [{}] execute finished ...", realDBFile.getPath());
                }

                log.info("all sql file in [{}] has execute success...", fileDir.getPath());
                addOrUpdateSystemInfo(fileDir);
            }
        }
        catch (Exception e)
        {
            log.error("init db script is Exception : {}", e.getMessage(), e);
            throw new BusinessException(BusinessExceptionEnum.UNKNOWN_EXCEPTION);
        }
    }

    private void addOrUpdateSystemInfo(File fileDir)
    {
        SystemInfo initSystemInfo = systemInfoService.getOne(new LambdaQueryWrapper<SystemInfo>().eq(SystemInfo::getApplicationName, applicationName));
        if(null == initSystemInfo)
        {
            SystemInfo systemInfo = new SystemInfo();
            systemInfo.setCurrentDBVersion(fileDir.getName());
            systemInfo.setApplicationName(applicationName);
            systemInfo.setDeployHost(System.getProperty("local-ip"));
            systemInfo.setLastUpdateDatetime(new Date());
            systemInfoService.save(systemInfo);
            return;
        }

        initSystemInfo.setCurrentDBVersion(fileDir.getName());
        initSystemInfo.setDeployHost(System.getProperty("local-ip"));
        initSystemInfo.setLastUpdateDatetime(new Date());
        systemInfoService.updateById(initSystemInfo);
    }

    private ScriptRunner getScriptRunner(Connection connection)
    {
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setAutoCommit(true);
        runner.setFullLineDelimiter(false);
        //日志数据输出，这样就不会输出过程
        runner.setLogWriter(null);
        // 一次性执行整个脚本，而不是根据分隔符一行一行执行
        runner.setSendFullScript(true);
        // 语句结束符号设置 其实sendFullScript是true的话这个设置实际是无效的
        runner.setDelimiter(";");
        runner.setStopOnError(true);
        return runner;
    }
}
