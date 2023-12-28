package cn.soldat.jutils.web.response

enum class ResponseCode(val code: Int, val msg: String){
    SUCCESS(200, "成功"),
    ERROR(500, "未知错误"),

    NOT_AUTHORIZED(401, "Not Authorized"),
    FORBIDDEN(403, "Forbidden"),
    PAGE_NOT_FOUND(404, "Page Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    SERVER_ERROR(5001, "服务器错误"),

    TOKEN_ERROR(5002, "Token错误"),
    TOKEN_INVALIDATE(5003, "Token无效"),
    TOKEN_EXPIRED(5004, "Token已过期"),

    USER_NOT_EXIST(5005, "用户不存在或已被禁用"),
    USER_ALREADY_EXIST(5006, "用户已存在"),
    USER_EMAIL_NOT_EXIST(5007, "该邮箱不存在"),
    USER_EMAIL_ALREADY_EXIST(5008, "该邮箱已存在"),
    USER_PHONE_NOT_EXIST(5009, "手机号不存在"),
    USER_PHONE_ALREADY_EXIST(5010, "该手机号已存在"),
    USERNAME_ERROR(5011, "用户名或密码错误"),
    USER_PWD_ERROR(5012, "用户名或密码错误"),
    USERNAME_PWD_ERROR(5013, "用户名或密码错误"),
    USER_NOT_AUTHORIZED(5014, "用户未认证，请登录"),
    USER_FORBIDDEN(5015, "没有权限"),
    USER_NOT_ALLOWED_OP(5016, "没有操作权限，请联系管理员"),
    USER_HAS_BEEN_DISABLED(5017, "用户不存在或已被禁用"),
    USERNAME_NOT_ALLOW_EMPTY(5018, "用户名不能为空"),
    PASSWORD_NOT_ALLOW_EMPTY(5019, "密码不能为空"),
    PASSWORD_NOT_MATCH_PATTERN(5020, "密码不符合要求"),
    USER_OPENID_ALREADY_EXIST(5021, "用户已存在"),

    SQL_ERROR(51001, "数据库错误"),
    SQL_DATA_ERROR(51002, "数据库数据错误"),

    JSON_SERIALIZED_ERROR(51101, "Json反序列化失败"),
    JSON_DESERIALIZED_ERROR(51102, "Json序列化失败"),

    // 微信内容检查 label 值
    WX_MSG_SEC_CHECK_AD(10001, ""),
    WX_MSG_SEC_CHECK_POLICY(20001, ""),
    WX_MSG_SEC_CHECK_PORN(20002, "色情"),
    WX_MSG_SEC_CHECK_SHIT(20003, "辱骂"),
    WX_MSG_SEC_CHECK_CRIME(20006, "违法犯罪"),
    WX_MSG_SEC_CHECK_FAKE(20008, "欺诈"),
    WX_MSG_SEC_CHECK_VULGAR(20012, "低俗"),
    WX_MSG_SEC_CHECK_COPYRIGHT(20013, "版权"),
    WX_MSG_SEC_CHECK_OTHER(21000, "其他")
}
