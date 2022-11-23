package pers.agitator.mybatis.session;

import java.io.Closeable;
import java.util.List;

public interface SqlSession extends Closeable {
    <E> List<E> selectList(String statementId, Object... params);

    <E> E getMapper(Class<E> clazz);
}
