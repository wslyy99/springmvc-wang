package com.wang.web.exception;

import com.wang.common.exception.BizException;



/**
 * 用户业务异常类
 */
public class UserBizException extends BizException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8402819840773628289L;

	/** 用户不存在 **/
    public static final int USER_IS_NULL = 101;

    /** 用户操作有误 **/
    public static final int USER_OPER_ERRPR = 102;
    
    public static final UserBizException USER_ACCOUNT_IS_NULL = new UserBizException(10010002, "用户未设置账户信息!");

    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(UserBizException.class);

    public UserBizException() {
    }

    public UserBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public UserBizException(int code, String msg) {
        super(code, msg);
    }

    public UserBizException print() {
    	logger.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }
}
