package pers.agitator.mybatis.demo;

import pers.agitator.mybatis.demo.pojo.User;
import pers.agitator.mybatis.session.SqlSession;
import pers.agitator.mybatis.session.SqlSessionFactory;
import pers.agitator.mybatis.session.SqlSessionFactoryBuilder;
import pers.agitator.mybatis.util.ResourceUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(ResourceUtil.getResources("configuration.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("pers.agitator.mybatis.demo.mapper.UserMapper.selectAll");
        System.out.println(list);

    }
}
