package pers.agitator.mybatis.builder;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import pers.agitator.mybatis.constant.XmlConstant;
import pers.agitator.mybatis.exception.ConfigurationException;
import pers.agitator.mybatis.session.Configuration;
import pers.agitator.mybatis.util.ResourceUtil;
import pers.agitator.mybatis.util.XmlUtil;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class XmlConfigurationBuilder {
    private Configuration configuration;
    private final InputStream in;

    public XmlConfigurationBuilder(InputStream in) {
        newConfig();
        this.in = in;

    }

    private void newConfig() {
        this.configuration = new Configuration();
    }

    public Configuration parse() {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            Element root = document.getRootElement();

            if (!Objects.equals(root.getName(), XmlConstant.ROOT_ELEMENT_NAME)) {
                throw new ConfigurationException("root must be <{}>", XmlConstant.ROOT_ELEMENT_NAME);
            }
            //获取属性节点
            Properties properties = XmlUtil.coverElementPropertyToProperties(root.element(XmlConstant.DATA_SOURCE));
            // 加载连接池
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            this.configuration.setDataSource(dataSource);

            // 加载mapper
            List<Node> nodes = root.selectNodes("//mapper");
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            for (Node node : nodes) {
                Element mapperElement = (Element) node;
                String mapperResource = mapperElement.attributeValue(XmlConstant.RESOURCE);
                xmlMapperBuilder.parse(ResourceUtil.getResources(mapperResource));
            }
            return configuration;
        } catch (Exception e) {
            throw new ConfigurationException("加载配置出现错误", e);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
