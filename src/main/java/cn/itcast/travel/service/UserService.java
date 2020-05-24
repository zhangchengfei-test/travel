package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserService {

    public int regist(User user);

    int active(String code);

    User login(User user);

    List<Category> findAll();
}
