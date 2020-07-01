package cn.bupt.sse.nmp.exception;

import cn.bupt.sse.nmp.result.CodeMsg;
import cn.bupt.sse.nmp.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局处理Springboot的抛出异常
 */
@RestControllerAdvice
@Slf4j
public class MyGlobalExceptionHandler {

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result<String> handleShiroException(ShiroException e) {
        return Result.error(new CodeMsg(401, e.getMessage()));
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public Result<String> handleUnAuthorizedException() {
        return Result.error(CodeMsg.UNAUTHORIZED);
    }

    // 捕捉其他所有异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Result<String> handleGlobalException(HttpServletRequest request, Throwable ex) {
        return Result.error(new CodeMsg(getHttpStatus(request).value(), ex.getMessage()));
    }


    private HttpStatus getHttpStatus(HttpServletRequest request) {

        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");

        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.valueOf(statusCode);
    }

}
