package pers.agitator.mybatis.util;

public class StrUtil {
    public static boolean isBlank(CharSequence str) {
        return str == null || str.length() == 0 || str.chars().allMatch(Character::isWhitespace);
    }
}
