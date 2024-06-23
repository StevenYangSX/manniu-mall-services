package pro.stevendev.common.exceptions;

import lombok.Data;
import pro.stevendev.common.utils.ErrorCode;

@Data
public class BusinessException extends RuntimeException {
    private final int code;
    private final String description;

    /**
     * 各种构造函数，供我们灵活的使用
     */
    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

}
