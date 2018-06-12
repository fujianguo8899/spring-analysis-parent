package com.viewhigh.analysis.eureka.consumer.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.viewhigh.analysis.domain.ApiResult;
import com.viewhigh.analysis.domain.ErrorInfo;
import com.viewhigh.analysis.exception.CheckedException;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class ApiExceptionHandler {

	  /**
     * 默认异常处理方法,返回异常请求路径和异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResult<Void> defaulErrorHandler(Exception e) throws  Exception{
        return ApiResult.errorResult(ErrorInfo.SYSTEM_ERROR);
    }
    
	@ExceptionHandler(CheckedException.class)
	@ResponseBody
	public ApiResult<Void> handleCheckedException(CheckedException ex) {
		return ApiResult.errorResult(ex.getErrorCode(), ex.getErrorMessage());
	}
    
    
}
