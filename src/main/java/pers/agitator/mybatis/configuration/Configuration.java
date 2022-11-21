package pers.agitator.mybatis.configuration;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.agitator.mybatis.config.MapperBean;
import pers.agitator.mybatis.constant.XmlConstant;
import pers.agitator.mybatis.exception.ConfigurationException;
import pers.agitator.mybatis.util.XmlUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 读取与解析配置信息，并返回处理后的Environment
 */
public class Configuration {
    private static final ClassLoader LOADER = ClassLoader.getSystemClassLoader();
    Map<String, MapperBean> mapperMap = new HashMap<>();

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

    private Connection parseYamlConfiguration(InputStream stream) throws DocumentException, ClassNotFoundException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(stream);
        Element root = document.getRootElement();
        return evalDataSource(root);
    }

    private Connection evalDataSource(Element root) throws ClassNotFoundException {
        if (!Objects.equals(root.getName(), XmlConstant.ROOT_ELEMENT_NAME)) {
            throw new ConfigurationException("root must be <{}>", XmlConstant.ROOT_ELEMENT_NAME);
        }
        //获取属性节点
        DataSource dataSource = XmlUtil.coverElementPropertyToObject(root, DataSource.class);
        Class.forName(dataSource.getDriverClassName());
        Connection connection = null;
        try {
            //建立数据库链接
            connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
            return connection;
        } catch (SQLException e) {
            throw new ConfigurationException("error in connecting database server", e);
        }
    }


    private Connection parseXmlConfiguration(InputStream stream) {
        return null;
    }

    public Map<String, MapperBean> getMapperMap() {
        return mapperMap;
    }
}
