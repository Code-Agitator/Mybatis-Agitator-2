package pers.agitator.mybatis.exception;

public class XmlParseException extends MybatisAgitatorException {
    public XmlParseException() {
    }

    public XmlParseException(String template, Object... arg) {
        super(template, arg);
    }

    public XmlParseException(String message) {
        super(message);
    }

    public XmlParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParseException(Throwable cause, String template, Object... arg) {
        super(cause, template, arg);
    }

    public XmlParseException(Throwable cause) {
        super(cause);
    }

    public XmlParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public XmlParseException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String template, Object... arg) {
        super(cause, enableSuppression, writableStackTrace, template, arg);
    }
}
