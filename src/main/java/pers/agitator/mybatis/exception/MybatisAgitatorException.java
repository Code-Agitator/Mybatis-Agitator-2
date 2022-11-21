package pers.agitator.mybatis.exception;

import pers.agitator.mybatis.util.StrUtil;

/**
 * base exception
 */
public class MybatisAgitatorException extends RuntimeException {
    public MybatisAgitatorException() {
    }

    public MybatisAgitatorException(String template, Object... arg) {
        super(StrUtil.format(template, arg));
    }

    public MybatisAgitatorException(String message) {
        super(message);
    }

    public MybatisAgitatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public MybatisAgitatorException(Throwable cause, String template, Object... arg) {
        super(StrUtil.format(template, arg), cause);
    }

    public MybatisAgitatorException(Throwable cause) {
        super(cause);
    }

    public MybatisAgitatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MybatisAgitatorException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String template, Object... arg) {
        super(StrUtil.format(template, arg), cause, enableSuppression, writableStackTrace);
    }

}
