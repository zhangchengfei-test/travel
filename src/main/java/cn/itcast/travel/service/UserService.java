package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {

    public int regist(User user);

    int active(String code);

    User login(User user);
}
