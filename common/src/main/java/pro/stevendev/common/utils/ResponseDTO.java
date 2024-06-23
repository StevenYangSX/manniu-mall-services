package pro.stevendev.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pro.stevendev.common.utils.ErrorCode;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseDTO<T> implements Serializable {
    private Integer statusCode;
    private String message;
    private T data;
    private String description;

    public ResponseDTO(int code, T data, String message, String description) {
        this.statusCode = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public ResponseDTO(int code, T data, String message) {
        this(code, data, message, "");
    }

    public ResponseDTO(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

    public ResponseDTO(int code, T data) {
        this(code, data, "", "");
    }

    public ResponseDTO(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    public ResponseDTO(ErrorCode errorCode, String description) {
        this(errorCode.getCode(), null, errorCode.getMessage(), description);
    }

}