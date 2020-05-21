package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    public User findUserByName(String username);

    public int addUser(User user);
}
