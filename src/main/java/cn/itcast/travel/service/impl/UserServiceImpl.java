package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public int regist(User user) {

        User userByName = userDao.findUserByName(user.getUsername());

        if (userByName != null){
            return 0;
        }
        int count = userDao.addUser(user);

        return count;
    }
}
