package pers.agitator.mybatis.session;

import pers.agitator.mybatis.config.MapperBean;
import pers.agitator.mybatis.exception.ExecutorException;
import pers.agitator.mybatis.pasrsing.BoundSql;
import pers.agitator.mybatis.pasrsing.GenericTokenParser;
import pers.agitator.mybatis.pasrsing.ParameterMapping;
import pers.agitator.mybatis.pasrsing.ParameterMappingTokenHandler;
import pers.agitator.mybatis.util.ReflectUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    private final Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MapperBean mapper, Object... params) {
        try {
            Connection connection = configuration.getDataSource().getConnection();
            BoundSql boundSql = getBoundSql(mapper);
            PreparedStatement preparedStatement = getPreparedStatement(connection, boundSql, params);
            ResultSet resultSet = preparedStatement.executeQuery();
            return handleResultSet(mapper, resultSet);
        } catch (Exception e) {
            throw new ExecutorException(e);
        }
    }

    private static PreparedStatement getPreparedStatement(Connection connection, BoundSql boundSql, Object... params) throws SQLException {
        String sqlText = boundSql.getSqlText();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        //获取preparedStatement，并传递参数值
        PreparedStatement preparedStatement = connection.prepareStatement(sqlText);

        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            Object o = ReflectUtil.getFieldValue(params[0], content);
            preparedStatement.setObject(i + 1, o);
        }
        return preparedStatement;
    }

    private <E> List<E> handleResultSet(MapperBean mapper, ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        List<E> result = new ArrayList<>();
        //封装结果集
        Class<?> resultType = mapper.getResultType();
        while (resultSet.next()) {
            Object obj = resultType.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(obj, value);
            }
            result.add((E) obj);
        }
        return result;
    }

    private static BoundSql getBoundSql(MapperBean mapper) {
        String sql = mapper.getSql();
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        return new BoundSql(parseSql, parameterMappingTokenHandler.getParameterMappings());
    }
}
