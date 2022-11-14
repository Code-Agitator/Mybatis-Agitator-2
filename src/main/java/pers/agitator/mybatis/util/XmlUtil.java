package pers.agitator.mybatis.util;

import org.dom4j.Attribute;
import org.dom4j.Element;
import pers.agitator.mybatis.constant.XmlConstant;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class XmlUtil {
    /**
     * 将xml的element下的property 中按照 name  和 value 包装到对象 clazz 中
     *
     * @param element parent element of property
     * @param clazz   the clazz to cover
     * @param <T>     clazz
     * @return clazz
     */
    public static <T> T coverElementPropertyToObject(Element element, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<Element> elements = element.elements(XmlConstant.PROPERTY);
        if (Objects.isNull(elements) || elements.size() == 0) {
            return clazz.newInstance();
        }
        for (Element propertyElement : elements) {
            Attribute nameAttribute = propertyElement.attribute(XmlConstant.PROPERTY_NAME);
            Attribute valueAttribute = propertyElement.attribute(XmlConstant.PROPERTY_VALUE);
            String fieldName = nameAttribute.getValue();
            if (StrUtil.isBlank(fieldName)) {
                throw new RuntimeException("the attribute '" + XmlConstant.PROPERTY_NAME + "' of element <" + XmlConstant.PROPERTY + "> not be blank,path:[" + nameAttribute.getPath() + "]");
            }
            String setterMethodName = getSetterMethodName(fieldName);

            try {
                Field declaredField = clazz.getDeclaredField(fieldName);
                Method method = clazz.getDeclaredMethod(setterMethodName, declaredField.getType());

                if (Number.class.isAssignableFrom(declaredField.getType())) {
                    // 处理数字类型

                } else {
                    // 处理字符串类型

                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }


        }
    }

    private static String getSetterMethodName(String str) {
        // Capitalize first letter
        String firstLetter = str.substring(0, 1);
        // Get remaining letter
        String remainingLetters = str.substring(1);
        return "set" + firstLetter.toUpperCase() + remainingLetters;
    }
}
