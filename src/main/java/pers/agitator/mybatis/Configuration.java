package pers.agitator.mybatis;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.agitator.mybatis.constant.XmlConstant;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Objects;

/**
 * 读取与解析配置信息，并返回处理后的Environment
 */
public class Configuration {
    private static final ClassLoader LOADER = ClassLoader.getSystemClassLoader();

    /**
     * 读取xml信息并处理
     */
    public Connection build(String resource) {
        try (InputStream stream = LOADER.getResourceAsStream(resource)) {
            if (resource.endsWith("xml")) {
                return parseXmlConfiguration(stream);
            } else if (resource.endsWith("yaml") || resource.endsWith("yml")) {
                return parseYamlConfiguration(stream);
            } else {
                throw new RuntimeException("error occurred while eval Configuration ,cause: file type not support,only support yml/yaml/xml");
            }
        } catch (Exception e) {
            throw new RuntimeException("error occurred while eval Configuration file[path:" + resource + "]" + "cause:" + e.getMessage());
        }
    }

    private Connection parseYamlConfiguration(InputStream stream) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(stream);
        Element root = document.getRootElement();
        return evalDataSource(root);
    }

    private Connection evalDataSource(Element root) {
        if (!Objects.equals(root.getName(), XmlConstant.ROOT_ELEMENT_NAME)) {
            throw new RuntimeException("root must be <" + XmlConstant.ROOT_ELEMENT_NAME + ">");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        //获取属性节点


        return null;
    }

    private Connection parseXmlConfiguration(InputStream stream) {
        return null;
    }
}
