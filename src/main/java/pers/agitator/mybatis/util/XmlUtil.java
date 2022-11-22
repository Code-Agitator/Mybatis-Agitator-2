package pers.agitator.mybatis.util;

import org.dom4j.Attribute;
import org.dom4j.Element;
import pers.agitator.mybatis.annotation.NotBlank;
import pers.agitator.mybatis.constant.XmlConstant;
import pers.agitator.mybatis.exception.XmlParseException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class XmlUtil {
    /**
     * 将xml的element下的property 中按照 name  和 value 包装成 {@link java.util.Properties}
     *
     * @param element parent element of property
     * @return Properties
     */
    public static Properties coverElementPropertyToProperties(Element element) {
        List<Element> elements = element.elements(XmlConstant.PROPERTY);
        Properties properties = new Properties();
        if (Objects.isNull(elements) || elements.size() == 0) {
            return properties;
        }
        for (Element propertyElement : elements) {
            Attribute nameAttribute = propertyElement.attribute(XmlConstant.PROPERTY_NAME);
            String value = propertyElement.getStringValue();
            String fieldName = nameAttribute.getValue();
            if (StrUtil.isBlank(fieldName)) {
                throw new XmlParseException("the attribute '{}' of element <{}> not be blank,path:[{}]", XmlConstant.PROPERTY_NAME, XmlConstant.PROPERTY, nameAttribute.getPath());
            }
            properties.setProperty(fieldName, value);
        }
        return properties;
    }

    /**
     * 将xml的element下的property 中按照 name  和 value 包装到对象 clazz 中
     *
     * @param element parent element of property
     * @param clazz   the clazz to cover
     * @param <T>     clazz
     * @return clazz
     */
    public static <T> T coverElementPropertyToObject(Element element, Class<T> clazz) {
        List<Element> elements = element.elements(XmlConstant.PROPERTY);
        try {
            T instance = clazz.newInstance();
            if (Objects.isNull(elements) || elements.size() == 0) {
                return instance;
            }
            for (Element propertyElement : elements) {
                Attribute nameAttribute = propertyElement.attribute(XmlConstant.PROPERTY_NAME);
                String value = propertyElement.getStringValue();
                String fieldName = nameAttribute.getValue();
                if (StrUtil.isBlank(fieldName)) {
                    throw new XmlParseException("the attribute '{}' of element <{}> not be blank,path:[{}]", XmlConstant.PROPERTY_NAME, XmlConstant.PROPERTY, nameAttribute.getPath());
                }
                String setterMethodName = getSetterMethodName(fieldName);

                try {
                    Field declaredField = clazz.getDeclaredField(fieldName);
                    Method method = clazz.getDeclaredMethod(setterMethodName, declaredField.getType());
                    NotBlank notBlank = declaredField.getAnnotation(NotBlank.class);
                    if (notBlank != null && StrUtil.isBlank(value)) {
                        throw new XmlParseException("field [()] require not blank", fieldName);
                    }
                    if (!declaredField.getType().equals(String.class)) {
                        throw new XmlParseException("only support type string while parse xml to object,case:[class:{} field:{}]", clazz.getSimpleName(), declaredField.getName());
                    }
                    method.invoke(instance, value);
                } catch (NoSuchMethodException e) {
                    throw new XmlParseException("setter of field can not be found", e);
                } catch (NoSuchFieldException ignore) {
                } catch (InvocationTargetException e) {
                    throw new XmlParseException("error in invoke setter", e);
                }

            }
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new XmlParseException(e);
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
