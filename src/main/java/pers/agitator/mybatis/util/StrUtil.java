package pers.agitator.mybatis.util;

public class StrUtil {
    public static boolean isBlank(CharSequence str) {
        return str == null || str.length() == 0 || str.chars().allMatch(Character::isWhitespace);
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static String format(String template, Object... args) {
        return String.format(template.replaceAll("\\{}", "%s"), args);
    }

}
