package pers.agitator.mybatis.exception;

public class ExecutorException extends MybatisAgitatorException {
    public ExecutorException() {
    }

    public ExecutorException(String template, Object... arg) {
        super(template, arg);
    }

    public ExecutorException(String message) {
        super(message);
    }

    public ExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutorException(Throwable cause, String template, Object... arg) {
        super(cause, template, arg);
    }

    public ExecutorException(Throwable cause) {
        super(cause);
    }

    public ExecutorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExecutorException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String template, Object... arg) {
        super(cause, enableSuppression, writableStackTrace, template, arg);
    }
}
