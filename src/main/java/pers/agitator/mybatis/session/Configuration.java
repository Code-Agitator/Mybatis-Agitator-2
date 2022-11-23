package pers.agitator.mybatis.session;

import pers.agitator.mybatis.config.MapperBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取与解析配置信息，并返回处理后的Environment
 */
public class Configuration {
    Map<String, MapperBean> mapperMap = new HashMap<>();

    public DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Map<String, MapperBean> getMapperMap() {
        return mapperMap;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
