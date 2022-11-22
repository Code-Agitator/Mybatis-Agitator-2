package pers.agitator.mybatis.demo.mapper;

import pers.agitator.mybatis.demo.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    List<User> selectByUsername(String username);
}
