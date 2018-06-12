package com.viewhigh.analysis.domain;

import com.viewhigh.analysis.exception.CheckedException;

public enum ErrorInfo {

	TEST("1","test"),
    SUCCESS("200",""),
    LOGIN_FAIL("101","登录失败"),
    USER_NOT_FOUND("102","未注册用户"),
    USER_DISABLED("103","无效用户"),
    LOGOUT_SUCCESS("104","登出成功"),
    NO_LOGIN("105","未登录"),
    VERIFY_CODE_ERROR("106","验证码错误"),
    VERIFY_REFRESH_CODE_ERROR("107","验证码刷新失败"),
    SYSTEM_ERROR("100","系统错误");

    private String code;
    private String message;

    public String getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }

    private ErrorInfo(String code, String message){
        this.code = code;
        this.message = message;
    }
    
    /**
     * 检查给定的条件是否满足,如果不满足将抛出{@link CheckedException}
     * @param expression
     * @throws CheckedException
     */
    public void check(boolean expression) {
        if (!expression) {
            throw new CheckedException(this);
        }
    }
    
    /**
     * 直接抛出{@link CheckedException}
     * @throws StatusException
     */
    public void throwOut()  {
        throw new CheckedException(this);
    }
    
	/**
	 * ErrorInfo转换为CheckedException
	 * 
	 * @return
	 */
	public CheckedException exception() {
		return new CheckedException(this);
	}
	
    public static void check(boolean expression,String code, String msg) {
        if (!expression) {
            throw new CheckedException(code, msg);
        }
    }
    
    
}
