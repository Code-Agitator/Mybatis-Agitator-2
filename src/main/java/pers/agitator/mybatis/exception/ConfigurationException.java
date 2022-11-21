package pers.agitator.mybatis.exception;

public class ConfigurationException extends MybatisAgitatorException {
    public ConfigurationException() {
    }

    public ConfigurationException(String template, Object... arg) {
        super(template, arg);
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause, String template, Object... arg) {
        super(cause, template, arg);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    public ConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConfigurationException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String template, Object... arg) {
        super(cause, enableSuppression, writableStackTrace, template, arg);
    }
}
