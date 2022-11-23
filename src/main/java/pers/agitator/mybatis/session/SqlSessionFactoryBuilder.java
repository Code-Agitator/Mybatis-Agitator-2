package pers.agitator.mybatis.session;

import pers.agitator.mybatis.builder.XmlConfigurationBuilder;
import pers.agitator.mybatis.util.ResourceUtil;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    private final InputStream in;

    public SqlSessionFactoryBuilder(String classPath) {
        this(ResourceUtil.getResources(classPath));
    }
    public SqlSessionFactoryBuilder(InputStream in) {
        this.in = in;
    }
    public SqlSessionFactory build() {
        XmlConfigurationBuilder xmlConfigurationBuilder = new XmlConfigurationBuilder(in);
        Configuration configuration = xmlConfigurationBuilder.parse();
        return new DefaultSqlSessionFactory(configuration);
    }

}
