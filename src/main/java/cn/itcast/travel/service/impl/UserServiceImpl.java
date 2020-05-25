package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.JedisUtil;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public int regist(User user) {
        User userByName = userDao.findUserByName(user.getUsername());
        if (userByName != null){
            return 0;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        int count = userDao.addUser(user);
        String emailContent = "<a href='http://localhost:8080/travel/user/active?code="+user.getCode()+"'>旅游网激活用户</a>";
        MailUtils.sendMail(user.getEmail(),emailContent,"激活邮件");
        return count;
    }

    @Override
    public int active(String code) {
        User userByCode = userDao.findUserByCode(code);

        if (userByCode == null){
            return 0;
        }

        if ("Y".equals(userByCode.getStatus())){
            return -1;
        }

        int count = userDao.updateUserStatus(code);
        return count;
    }

    @Override
    public User login(User user) {
        return userDao.findUserByUsernameAndPassword(user);
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = null;//userDao.findAll();

        Jedis jedis = JedisUtil.getJedis();

        //使用sortedset排序查询
        Set<String> categorys = jedis.zrange("category", 0, -1);

        if (categorys == null || categorys.size() == 0){
            categories = userDao.findAll();

            for (int i = 0; i < categories.size(); i++) {
                jedis.zadd("category",categories.get(i).getCid(),categories.get(i).getCname());
            }

        }else {
            categories = new ArrayList<>();
            for (String name : categorys) {
                Category c = new Category();
                c.setCname(name);
                categories.add(c);
            }
        }


        return categories;
    }
}
