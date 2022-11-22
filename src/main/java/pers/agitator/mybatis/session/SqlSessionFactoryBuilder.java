package pers.agitator.mybatis.session;

import pers.agitator.mybatis.builder.XmlConfigurationBuilder;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public static SqlSessionFactory build(InputStream inputStream) {
        XmlConfigurationBuilder xmlConfigurationBuilder = new XmlConfigurationBuilder();
        Configuration configuration = xmlConfigurationBuilder.loadXmlConfiguration(inputStream);
        return new DefaultSqlSessionFactory(configuration);

    }
}
