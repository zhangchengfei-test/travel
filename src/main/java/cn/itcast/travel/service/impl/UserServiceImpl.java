package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

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
        String emailContent = "<a href='http://localhost:8080/travel/activeUserServlet?code="+user.getCode()+"'>旅游网激活用户</a>";
        MailUtils.sendMail(user.getEmail(),emailContent,"激活邮件");
        return count;
    }

    @Override
    public int active(String code) {
        User userByCode = userDao.findUserByCode(code);

        if (userByCode == null || "Y".equals(userByCode.getStatus())){
            return 0;
        }

        int count = userDao.updateUserStatus(code);
        return count;
    }
}
