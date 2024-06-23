package pro.stevendev.common.utils;


import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(0, "ok", ""),
    PARAMS_ERROR(40000, "Request body Error.", ""),
    NULL_ERROR(40001, "请求参数为空", ""),
    NO_LOGIN(40100, "Not Authenticated", ""),
    NO_AUTH(40101, "暂无权限访问", ""),
    SYSTEM_ERROR(50000, "System Internal Error", ""),
    RESOURCE_NOT_FOUND(51000, "No Data Found", ""),
    DUPLICATED_RECORD(52000,"Duplicated Data Found",""),
    INVALID_TOKEN(41000,"Invalid Token","");
    //get方法
    //返回码
    private final int code;
    //操作响应信息
    private final String message;
    //响应信息的详细描述
    private final String description;

    //构造函数
    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}