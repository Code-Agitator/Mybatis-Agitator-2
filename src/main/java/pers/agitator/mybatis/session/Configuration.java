package pers.agitator.mybatis.session;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.agitator.mybatis.config.MapperBean;
import pers.agitator.mybatis.constant.XmlConstant;
import pers.agitator.mybatis.exception.ConfigurationException;
import pers.agitator.mybatis.util.XmlUtil;

import javax.sql.DataSource;
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

    public DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    private Connection parseXmlConfiguration(InputStream stream) {
        return null;
    }

    public Map<String, MapperBean> getMapperMap() {
        return mapperMap;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
