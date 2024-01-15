package com.zhongni.bs1.entity.db.systeminfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName squid_system_info
 */
@TableName(value ="squid_system_info")
@Data
public class SystemInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 系统名称
     */
    @TableField(value = "application_name")
    private String applicationName;

    /**
     * 部署主机
     */
    @TableField(value = "deploy_host")
    private String deployHost;

    /**
     * 当前数据库脚本的版本号
     */
    @TableField(value = "current_db_version")
    private String currentDBVersion;

    /**
     * 最后一次脚本更新的时间
     */
    @TableField(value = "last_update_datetime")
    private Date lastUpdateDatetime;
    /**
     */
    @TableField(value = "current_system_version")
    private String currentSystemVersion;
    /**
     */
    @TableField(value = "last_update_status")
    private String lastUpdateStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}