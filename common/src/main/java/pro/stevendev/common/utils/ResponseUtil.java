package pro.stevendev.common.utils;

import pro.stevendev.common.dto.ResponseDTO;

public class ResponseUtil {

    /**
     * 成功
     */
    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(0, data, "ok");
    }

    public static <T> ResponseDTO<T> success( Integer code, String message,T data) {
        return new ResponseDTO<>(code, data, message);
    }

    public static <T> ResponseDTO<T> success( Integer code, String message) {
        return new ResponseDTO<>(code, message);
    }

    /**
     * 成功
     */
    public static ResponseDTO success(int data) {
        return new ResponseDTO(0, data, "ok");
    }

    /**
     * 失败
     */
    public static ResponseDTO error(ErrorCode errorCode) {
        return new ResponseDTO<>(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    /**
     * 失败
     */
    public static ResponseDTO error(ErrorCode errorCode, String message, String description) {
        return new ResponseDTO<>(errorCode.getCode(), description);
    }

    /**
     * 失败
     */
    public static ResponseDTO error(int code, String message, String description) {
        return new ResponseDTO<>(code, null, message, description);
    }

    /**
     * 失败
     */
    public static ResponseDTO error(ErrorCode errorCode, String description) {
        return new ResponseDTO<>(errorCode.getCode(), null, errorCode.getMessage(), description);
    }

}