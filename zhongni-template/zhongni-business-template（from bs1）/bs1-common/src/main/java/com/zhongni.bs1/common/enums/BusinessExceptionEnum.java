package com.zhongni.bs1.common.enums;

/**
 *
 * 错误码含义
 * 前三位代表系统模块，如100是squid系统 101是gateway系统以此类推
 * 第四位代表异常信息种类如 如1-参数异常 2-远程调用异常 3-系统异常...
 * 后几位是流水号，依次递增即可
 */
public enum BusinessExceptionEnum {
    UNKNOWN_EXCEPTION("-1","未知的异常，请查看日志", "unknown exceptions, check the log"),
    PARAMS_EXCEPTION_INAVLID_MONEY_NUMBER_PATTERN("100-10002",
            "参数异常-入参中存在非法的金钱格式",
            "Parameter exception-An illegal money format exists in the input parameter"),
    PARAMS_EXCEPTION_NOT_ALLOW_NULL_VALUE("100-10005",
            "参数异常-入参中的必传属性存在空值",
            "Parameter Exception-Can not be null attributes in the input parameter"),
    PARAMS_EXCEPTION_ONCE_BUY_MAX_LIMIT("100-10006",
            "参数异常-超过一次性购买上限",
            "Parameter Exception-The number of one-time purchases exceeds the upper limit"),

    PARAMS_EXCEPTION_EMAIL_NOT_RIGHT("100-10009",
            "参数异常-入参中的邮箱地址格式不正确",
            "Parameter Exception-The format of the email address entered is incorrect"),
    PARAMS_EXCEPTION_EMAIL_HAS_BEEN_REGISTERED("100-10010",
            "参数异常-当前邮箱已经被注册过",
            "Parameter Exception-The current mailbox has been registered"),

    PARAMS_EXCEPTION_VERIFICATION_CODE_HAS_EXPIRED("100-10011",
            "参数异常-验证码已过期",
            "Parameter Exception-The verification code has expired"),

    PARAMS_EXCEPTION_VERIFICATION_CODE_NOT_RIGHT("100-10012",
            "参数异常-验证码错误",
            "Parameter Exception-Incorrect verification code"),

    PARAMS_EXCEPTION_INVITATION_CODE_NOT_EXIST("100-10013",
            "参数异常-无此邀请码",
            "Parameter Exception-Invitation code not exist"),
    PARAMS_EXCEPTION_EMAIL_ADDRESS_EXIST("100-10014",
            "参数异常-邮箱地址已存在",
            "Parameter Exception-The email address already exists"),
    PARAMS_EXCEPTION_EMAIL_ADDRESS_IS_NULL("100-10015",
            "参数异常-邮箱地址为空",
            "Parameter Exception-The email address is null"),
    PARAMS_EXCEPTION_WALLET_ADDRESS_IS_NULL("100-10016",
            "参数异常-钱包地址为空",
            "Parameter Exception-The wallet address is null"),
    PARAMS_EXCEPTION_LOGIN_PASSWORD_IS_NULL("100-10017",
            "参数异常-登录密码为空",
            "Parameter Exception-The login password is null"),
    PARAMS_EXCEPTION_LOGIN_PASSWORD_ERROR("100-10018",
            "参数异常-登录密码错误",
            "Parameter Exception-Login password error"),
    PARAMS_EXCEPTION_LOGIN_DOMAIN_ID_IS_NULL("100-10019",
            "参数异常-区块链ID不能为空",
            "Parameter Exception-The blockchain ID cannot be null"),
    PARAMS_EXCEPTION_EMAIL_NOT_REGISTERED("100-10020",
            "参数异常-该邮箱未注册",
            "Parameter Exception-The email address is not registered"),

    PARAMS_EXCEPTION_EMAIL_NOT_CHECKED_BY_RESET("100-10021",
            "参数异常-密码重置不存在有效的验证信息，可能信息已经失效，请重新获取验证码",
            "Parameter Exception-There is no valid verification information for password reset, the information may have expired, please obtain the verification code again"),
    PARAMS_EXCEPTION_EMAIL_NOT_EQUALS("100-10022",
            "参数异常-重置密码验证的邮箱和当前入参中的邮箱不一致",
            "Parameter Exception-The email address for reset password verification is inconsistent with the email address in the current entry"),

    PARAMS_EXCEPTION_EMAIL_NOT_CHECKED_BY_REGISTER("100-10023",
            "参数异常-用户注册不存在有效的验证信息，可能信息已经失效，请重新获取验证码",
            "Parameter Exception-There is no valid verification information for user registration, the information may have expired, please obtain the verification code again"),
    PARAMS_EXCEPTION_UNSUPPORTED_IMAGE_TYPE("100-10024",
            "参数异常-不支持的图片类型",
            "Parameter Exception-Unsupported image types"),
    PARAMS_EXCEPTION_IMAGE_TOO_LARGE("100-10025",
            "参数异常-图片过大",
            "Parameter Exception-Image is too large"),
    PARAMS_EXCEPTION_FILE_NAME_EMPTY("100-10026",
            "参数异常-上传的文件名称为空",
            "Parameter Exception-Uploaded file name is empty"),
    PARAMS_EXCEPTION_FILE_SUFFIX_EMPTY("100-10027",
            "参数异常-上传的文件名称中不存在后缀名",
            "Parameter Exception-Unable to get the suffix of the file"),
    PARAMS_EXCEPTION_UNSUPPORTED_MAIL_OPER_TYPE("100-10028",
            "参数异常-不支持的邮件内容类型",
            "Parameter Exception-Unsupported mail message content type"),

    PARAMS_EXCEPTION_ONLY_ONE_TYPE_CAN_UPLOAD("100-10029",
            "参数异常-只允许上传一种头像类型",
            "Parameter Exception-Only one profile picture type can be uploaded"),
    PARAMS_EXCEPTION_ILLEGAL_LOGIN_TYPE("100-10030",
            "参数异常-登录类型非法",
            "Parameter Exception-Illegal login type"),

    PARAMS_EXCEPTION_LEAGUE_QUERY_TOO_MANY("100-10031",
            "参数异常-过多的联赛查询",
            "Parameter Exception-Support queries of up to three game types"),
    REMOTE_EXCEPTION("100-20001", "远程请求失败", "remote request fail"),
    SYSTEM_EXCEPTION_CLOCK_MOVED_BACKWORDS("100-30001",
            "系统异常-系统时钟存在回拨，当前时间片内拒绝生成ID",
            "System exception-The system clock is dialed back, and no ID is generated in the current time slice"),
    SYSTEM_EXCEPTION_TICKET_CODE_GENERATE_REPEAT("100-30002",
            "系统异常-券码生成重复",
            "System exception-Duplicate ticket code generation"),
    SYSTEM_EXCEPTION_EMAIL_TEMPLATE_GET_FAIL("100-30003",
            "系统异常-无法获取邮件模板信息",
            "System exception-Unable to get email template information"),
    SYSTEM_EXCEPTION_UPLOAD_IMG_FAIL("100-30004",
            "系统异常-头像文件上传失败",
            "System exception-Avatar file upload failed"),
    SYSTEM_EXCEPTION_UPLOAD_PATH_CREATE_FAIL("100-30005",
            "系统异常-上传路径创建失败",
            "System exception-Failed to create upload path"),

    SYSTEM_EXCEPTION_BACKUP_IMAGE_FAIL("100-30006",
            "系统异常-头像文件备份失败",
            "System exception-The avatar file backup failed"),
    SYSTEM_EXCEPTION_IMAGE_FILE_DELETE_FAIL("100-30007",
            "系统异常-头像文件删除失败",
            "System exception-The redundancy avatar file delete failed"),
    SYSTEM_EXCEPTION_WAIT_LOCK_TIMEOUT("100-30008",
            "系统异常-锁等待超时",
            "System exception-Wait Lock timeout"),
    SYSTEM_EXCEPTION_UNKNOWN_LOCK_TYPE("100-30009",
            "系统异常-获取不到锁类型",
            "System exception-Unable to get lock type"),
    SYSTEM_EXCEPTION_UNKNOWN_CACHE_TYPE("100-30010",
            "系统异常-获取不到缓存类型",
            "System exception-Unable to get cache type"),
    USER_EXCEPTION_ILLEGAL_LOGIN_INFO("100-40001",
            "用户异常-无法获取合法的登录信息，请确认当前用户是否已经登录",
            "User Exception-Valid login information cannot be obtained. Please check whether the current person has logged in"),
    USER_EXCEPTION_PERSON_HAS_NOT_EMAIL_ADDRESS("100-40002",
            "用户异常-用户不存在邮箱地址信息",
            "User Exception-The user does not have an email address"),

    USER_EXCEPTION_IVNITATION_CODE_CANNOT_EDIT_IF_EXIST("100-40003",
            "用户异常-已填写过邀请码的用户不允许修改邀请码",
            "User Exception-Users who have filled in the invitation code are not allowed to modify the invitation code"),
    DATA_EXCEPTION_GET_TICKET_INFO_FAIL("100-50001",
            "数据异常-获取库存商券失败",
            "DataSearch exception-failed to obtain inventory tickets"),
    DATA_EXCEPTION_NOT_FIND_ORDER_BY_TICKET("100-50002",
            "数据异常-无法获取当前商券对应的订单信息",
            "DataSearch exception-Unable to obtain the order information corresponding to the current ticket"),
    DATA_EXCEPTION_NOT_FIND_TICKET_BY_ORDER("100-50003",
            "数据异常-无法获取当前订单对应的商券信息",
            "DataSearch exception-Unable to obtain the ticket information corresponding to the current order"),
    DATA_EXCEPTION_NOT_FIND_INDEX_INFO_BY_TICKET("100-50004",
            "数据异常-无法获取当前商券的开奖信息",
            "DataSearch exception-Unable to obtain the lottery information of the current ticket"),
    DATA_EXCEPTION_CAN_NOT_GET_USER("100-50005",
            "数据异常-查询不到有效的用户信息",
            "DataSearch exception-Unable to query valid user information"),

    DATA_EXCEPTION_USER_CACHE_IS_NULL("100-50006",
            "数据异常-无法获取当前token对应的用户信息",
            "DataSearch exception-The user information corresponding to the current token cannot be obtained"),
    DATA_EXCEPTION_LEAGUE_INFO_IS_NULL("100-50007",
            "数据异常-无法获取联赛信息",
            "DataSearch exception-league info is null"),
    DATA_EXCEPTION_MARKET_INFO_IS_NULL("100-50008",
            "数据异常-无法获取玩法信息",
            "DataSearch exception-market info is null"),
    DATA_EXCEPTION_EVENTS_INFO_IS_NULL("100-50009",
            "数据异常-无法获取比赛信息",
            "DataSearch exception-event info is null"),

    DATA_EXCEPTION_PLATE_INFO_IS_NULL("100-50010",
            "数据异常-无法获取比赛信息",
            "DataSearch exception-plate info is null"),

    OPERATE_EXCEPTION_INVALID_OPERATION_TICKET("100-60001",
            "操作异常-商券对应的订单状态不允许进行此操作",
            "Operation exception-the order status corresponding to the ticket does not allow this operation"),
    OPERATE_EXCEPTION_FREQUENT_SEND_EMAIL_REQUEST("100-60002",
            "操作异常-验证码尚在有效期，三分钟内同一个邮箱不需要重复请求验证码",
            "Operation exception-The verification code is still valid. The same mailbox does not need to request the verification code repeatedly within three minutes"),
    OPERATE_EXCEPTION_LOGIN_NOT_CHECK_IN_HERE("100-60003",
            "操作异常-登录验证码不需要通过此接口进行验证",
            "Operation exception-The login verification code does not need to be authenticated through this interface"),

    OPERATE_EXCEPTION_ORDER_NOT_BELOW_CURRENT_PERSON("100-60004",
            "操作异常-订单不属于当前登录人员，不允许此操作",
            "Operation exception-This operation is not allowed because the order does not belong to the current logged-in person");


    private String code;
    private String msg;

    private String msgEn;
    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    public String getMsgEn() {
        return msgEn;
    }



    BusinessExceptionEnum(String code, String msg, String msgEn) {
        this.code = code;
        this.msg = msg;
        this.msgEn = msgEn;
    }
}
