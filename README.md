# 一、工程简介
## 1、前言：
**'仲尼项目'，仲尼是取自中国古代著名圣人孔子的字，寓意此项目可以像孔圣人一样，
给人以思想上的启发（哈哈开个玩笑的，里面都是些浅尝辄止的东西，但这并不能妨碍我有这样一个梦想 ^_^），
此项目其实主要是一些代码基础模板，方便日后我快速搭建开发环境，以及对Java相关技术的尝试和练习**

## 2、技术栈：
- **简单业务：Spring Boot、Mybatis / Mybatis-Plus**
- **分布式相关：Spring Gateway、Nacos、Dubbo3**
- **系统鉴权：Spring Security**
- **缓存：Redis、Caffeine**
- **数据库：MySQL8.0**
- **消息中间件（MQ）：待引入**
- **定时任务：待引入**
- **搜索引擎：待引入**
- **容器化部署：待适配**
- **代码管理：MAVEN、GIT**
- **其他技术：持续引入中**

## 3、层级结构

### zhongni-base（基础模块）
__________________________

- **zhongni-gateway - 网关**
    > 待补充
__________________________

- **zhongni-oauth - 鉴权**
  > 待补充


### zhongni-business（业务子系统）
__________________________

- **zhongni-bs1 - 业务系统1** <br/>
1) ***bs1-start（引导模块）*** <br/>
   > ZhonniApplication.java - 系统启动类 <br/>
   > resources - 包含系统、日志配置文件、数据库脚本、启停脚本、项目版本信息、邮件模板静态文件等 <br/>

2) ***bs1-common（公共依赖模块）*** <br/>
    > - advice - 全局异常处理 <br/>
    > - annotation - 自定义注解 <br/>
    > - conf - 配置类 <br/>
    > - constants - 常量 <br/>
    > - enums - 枚举 <br/>
    > - exception - 自定义异常 <br/>
    > - interceptor - 拦截器 <br/>
    > - resp - 统一返回对象 <br/>
    > - spring - 框架相关 <br/>
    > - util - 工具类 <br/>
3) ***bs1-controller（控制模块）*** <br/>
   > - controller - 定义开放接口 <br/>
4) ***bs1-service（业务模块）*** <br/>
   > - aspect - 业务切面 <br/>
   > - init - 系统的初始化 <br/>
   > - remote - 远程调用 <br/>
   > - service - 各类服务接口的具体实现、其他工具接口实现 <br/>

5) ***bs1-entity（实体对象模块）*** <br/>
   > -db - 数据库实体对象 <br/>
   > -dto - 前后端交互实体对象 <br/>
   > -other - 其他实体对象 <br/>

6) ***bs1-mapper（关系映射模块）*** <br/>
   > mapper - 映射接口 <br/>
   > resources - Mybatis SQL XML <br/>

7) ***bs1-task（定时任务模块）*** <br/>
   > task - 基于Spring  @Scheduled注解的定时任务 <br/>
__________________________

- **zhongni-bs2 - 业务系统2** <br/>
> 待补充

### zhongni-parent（公共父模块）
__________________________

- **pom.xml - 版本管理**
    > 所有项目的parent,用于三方Jar包的统一管理
	
### zhongni-template（模板模块）
__________________________

- **zhongni-business-template（from bs1） - 业务子系统的简单模板**
    > 脱胎于bs1子系统，一个更加干净的子系统模板，只保留最原始的结构和最基础的依赖
	> 日后如果需要，可以快速引入新的东西，成为一个全新的子系统模块
__________________________

- **zhongni-simple-template（from gateway） - 基础模块的简单模板**
  > 脱胎于zhongni-gateway基础模块，一个更加干净的基础模块模板，只保留最原始的结构和最基础的依赖
  > 日后如果需要，可以快速引入新的东西，成为一个全新的基础模块
  


# 二、开发指引

## 1、简单HTTP接口开发
1. **在Controller模块下controller包中定义自己的业务包名，之后在包中新建Controller结尾的类**
2. **在Service层级下service.local包中定义自己的业务包名，之后在包中新建Service结尾的接口和对应的Impl实现类**
3. **在Entity层级下db包中新增自己的数据库映射实体类，如果需要返回给前端则数据库映射类必须集成BaseDO.java并实现toDTO()方法，还需在dto包中新增自己的前后端交互对象实体类并继承BaseOutDTO对象。具体可参考Goods.java、GoodsOutDTO.java**
4. **如果不需要返回给前端，只是后端内部的查询和使用则自由编写即可，具体可参考Lottery.java类**
5. **如果涉及数据库操作，在Mapper层级下定义自己的业务包名，之后在包中新建Mapper结尾的接口, 最后在resources目录下新增对应的SQL xml文件**
6. **步骤三、四建议使用代码生成插件，防止映射关系有问题导致操作数据库无效**

### 注意事项：
- 每个Controller类头上都需要使用@RequestMapping注解来指定统一的一个请求映射
- 方法返回需要使用统一的**CommonResponse**对象并添加泛型，**CommonResponse**提供了一些静态的返回方法，
  可直接调用来快速构建一个成功或者失败的消息对象。示例如下
```java
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 注册
     * @param useRregisterLocalInDTO 注册入参对象
     */
    @PostMapping("/register")
    public CommonResponse<List<String>> registerLocal(UseRregisterLocalInDTO useRregisterLocalInDTO) {
        List<String> strings = userService.registerLocal(useRregisterLocalInDTO);
        return CommonResponse.success(strings);
    }
}
```

- 如果涉及到分页，可使用**PageResponse**对象, 使用套路与**CommonResponse**，示例如下
```java
@RestController
@RequestMapping("/ticket")
public class TicketController {
    @PostMapping("/scratch/page")
    public PageResponse<PageInfo<OrderTicketOutDTO>, OrderTicketOutDTO> scratchPage(@RequestHeader("token") String token, @RequestBody ScratchPageInDTO scratchPageInDTO) {
        return PageResponse.pageSuccess(orderTicketService.scratchPage(token, scratchPageInDTO));
    }
}
```

## 2、定时任务开发
1. **在Task层级下定义自己的业务包名，之后在包中新建Task结尾的定时任务类**
2. **使用@Component注解修饰该类，使用@Scheduled注解修饰方法即可**

### 注意事项： 
- 定时任务的实际执行请使用线程池来异步执行，防止阻塞。示例如下
```java
@Component
public class OrderTask {
    @Scheduled(cron = "${schedule.ordercheck.cron}")
    public void orderSyncTask() {
        log.debug("orderSyncTask start execute...");
        // 直接使用线程池异步执行，防止阻塞导致后续的定时任务均无法执行
        asyncTaskThreadPool.execute(this::checkAndUpdateOrderStatus);
        log.debug("orderSyncTask end execute...");
    }
}
```

## 3、远程访问其他服务开发
**待补充...**

## 4、其他开发指引
1. **切面的逻辑均放置在BusinessAspect.java中,目前切面的逻辑有参数校验，方法执行耗时统计，日志打印...**
2. **登录鉴权的拦截器在LoginInterceptor.java中,主要用于对相关url的登录校验，其中application-business.properties配置文件中的exclude.check.url参数是配置不需要校验的URL的，
即配置之后用户不需要登录也可以访问该接口**
3. **GlobalInterceptor.java拦截器主要用于记录发送请求的客户端信息如IP，类型，时间等等**
4. **重要的工具类：CacheUtil、LockUtil。因为当前项目支持集群和单机两种部署方式，这两个工具类会根据部署方式的不同切换不同的缓存类型和锁类型，
因此涉及到加锁和缓存的地方请直接使用这两个工具类。使用方式如下：**
```txt
    CacheUtil.put(key, value, noOperationTimeOut, TimeUnit.SECONDS);
    LockUtil.syncExecute(tokenCacheKey, loginInDTO, item -> {...})
```

5. **重要的工具类：MoneyUtil。MoneyUtil主要是用于金钱单位的转换，目前数据库中存储的相关金额为防止精度问题都是以分为单位，用int进行存储的
，与前端交互时可能会涉及到分转元和元转分的操作，可以使用该工具类进行安全的转换。使用方式如下：**
```txt
    MoneyUtil.longFen2Yuan(this.getTicketWinMoney();
    MoneyUtil.yuan2LongFen(String.valueOf(lottery.getWinPrice()));
```

# 三、使用规范

## 1、异常规范
- 为了能够进行统一异常的管理，抛出的业务异常也需要使用系统内封装的**BusinessException**异常对象，该**BusinessException**类的其中一个构造方法
  只接受**BusinessExceptionEnum**下的枚举值，因此每抛出一个特定的错误必须要在**BusinessExceptionEnum**增加新的枚举， 然后将该枚举值传入
  **BusinessException**的构造方法中。抛出的**BusinessException**异常后会被统一处理，把**BusinessExceptionEnum**枚举中的错误详细信息返回到前端。另外一个构造方法在传BusinessExceptionEnum基础上还可以传String类型的字符串，用以进一步描述发生这个错误的原因。
  如果不使用BusinessException而是直接抛出一些JDK自带异常，出于安全因素考虑会将该异常的具体信息转换成一句无意义的提示给前端，不利于错误的分析和定位，切记！！！

    异常使用示例如下：
```java
public class LoginInterceptor {
    
    @Override
    public boolean preHandle() {
        if (null == o) {
            // 直接抛出异常
            throw new BusinessException(BusinessExceptionEnum.USER_EXCEPTION_ILLEGAL_LOGIN_INFO);
        }

        
        if (timestamp < lastTimestamp) {
            // 抛出异常的同时可以另外的传一个参数，用于进一步描述发生错误的原因
            throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_CLOCK_MOVED_BACKWORDS,
                    String.format("Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        return true;
    }
}
```
## 2、maven依赖规范
- 所有新增的maven依赖必须统一声明到父级目录的pom.xml中进行统一的版本管理，然后哪个层级需要使用，则再自行引入。具体可参考bs1-common层级中引入的pagehelper-spring-boot-starter依赖如下
```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
</dependency>
```

# 四、脚本简介
## 1、数据库脚本
1. **在bs1-starter\src\main\resources\db目录下会分列着各个版本的数据库脚本文件夹，文件夹名称代表的就是版本号，
一般就是创建文件夹那天的8位日期+3位序号。如果需要新增数据库脚本那么新增的文件夹版本号必须要比里面最新版本号还要大，
因为系统在执行数据库脚本时会根据文件名从小到大依次执行**
2. **每次项目启动时会自动扫描数据库（如果数据库或者表不存在目前已经支持自动创建，不需要担心）
中squid_system_info表中的信息，来获取当前项目已经执行过的最新的数据库脚本版本，即current_db_version字段的值。如果数据库不存在，数据表不存在，查不到数据或者有数据但是current_db_version字段是空，
则系统认为这是第一次部署会在启动时执行db目录下的所有数据库脚本，进行系统的初始化**
3. **因为系统在进行全量初始化的时候会按照db目录下的文件名的从小到大依次执行。所以00000000000文件夹中包含的是0号脚本，即最先执行的脚本也是最基本的脚本。**
4. **如果查到了squid_system_info表中current_db_version字段的值，则会系统会进行增量更新，只执行文件名大于current_db_version的文件夹下的sql脚本**
5. **一般数据库脚本文件夹下包含table.sql和table-data.sql两个文件，分别代表建表语句和数据操作语句，系统执行时会根据文件名称从短到长依次执行，即保证先执行建表语句再执行插入语句**
6. **数据库的所有脚本必须可以重复执行，方便多次部署**


## 2、打包脚本
1. **在项目的根目录下存在package.bat脚本，双击可以进行Maven的一键打包，其中maven打包的各种配置存在于bs1-starter\pom.xml里**
2. **打包成功后会在bs1-starter\target\下生成一个名为bs1-starter的文件夹。该文件夹下包含的就是整个当前项目的内容，将整个文件夹拷贝出来部署到服务器即可，具体配置修改以及启停请查看部署文档中的相关说明**