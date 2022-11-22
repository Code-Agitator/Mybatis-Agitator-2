package pers.agitator.mybatis.util;

import java.lang.reflect.Field;

public class ReflectUtil {

    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field declaredField = getDeclaredField(obj, fieldName);
            if (declaredField == null) {
                return null;
            }
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private static Field getDeclaredField(Object obj, String fieldName) {
        try {
            Class<?> clazz = obj.getClass();
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

}
