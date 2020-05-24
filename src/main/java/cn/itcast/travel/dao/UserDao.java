package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserDao {

    public User findUserByName(String username);

    public User findUserByCode(String code);

    public int addUser(User user);

    int updateUserStatus(String code);


    User findUserByUsernameAndPassword(User user);

    List<Category> findAll();
}
