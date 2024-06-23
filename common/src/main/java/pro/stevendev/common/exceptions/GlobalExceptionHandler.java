package pro.stevendev.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.stevendev.common.dto.ResponseDTO;
import pro.stevendev.common.utils.ErrorCode;
import pro.stevendev.common.utils.ResponseUtil;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseDTO businessExceptionHandler(BusinessException e) {
        log.error("BusinessException" + e.getMessage(), e);
        return ResponseUtil.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseDTO runtimeExceptionHandler(RuntimeException e) {
        //集中处理
        log.error("RuntimeException", e);
        return ResponseUtil.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}

