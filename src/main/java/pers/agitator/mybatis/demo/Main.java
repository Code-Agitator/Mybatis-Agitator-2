package pers.agitator.mybatis.demo;

import pers.agitator.mybatis.demo.mapper.UserMapper;
import pers.agitator.mybatis.demo.pojo.User;
import pers.agitator.mybatis.session.SqlSession;
import pers.agitator.mybatis.session.SqlSessionFactory;
import pers.agitator.mybatis.session.SqlSessionFactoryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder("configuration.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("pers.agitator.mybatis.demo.mapper.UserMapper.selectAll");
        System.out.println(list);
        User user = new User();
        user.setUsername("用户名");
        List<User> list2 = sqlSession.selectList("pers.agitator.mybatis.demo.mapper.UserMapper.selectByUsername", user);
        System.out.println(list2);
        System.out.println("===================");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.selectAll());
        System.out.println(mapper.selectByUsername(user));
    }
}
