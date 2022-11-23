package pers.agitator.mybatis.session;

import pers.agitator.mybatis.config.MapperBean;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;

    private final Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor(configuration);
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) {
        MapperBean mapperBean = configuration.getMapperMap().get(statementId);
        return executor.queryList(mapperBean, params);
    }

    @Override
    public <E> E getMapper(Class<E> clazz) {

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{clazz}, ((proxy, method, args) -> {
            String methodName = method.getName();
            String clazzName = method.getDeclaringClass().getName();
            String statementId = clazzName + "." + methodName;
            return selectList(statementId, args);
        }));
        return (E) proxyInstance;
    }
}
