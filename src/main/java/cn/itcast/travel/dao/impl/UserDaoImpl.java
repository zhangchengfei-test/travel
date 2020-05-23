package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByName(String username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public User findUserByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public int addUser(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";

        int count = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());

        return count;
    }

    public int updateUserStatus(String code){
        String sql = "update tab_user set status = 'Y' where code = ?";

        int count = jdbcTemplate.update(sql, code);

        return count;
    }

    @Override
    public User findUserByUsernameAndPassword(User user) {
        User user1 = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user1 = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(),user.getPassword());
        } catch (Exception e) {

        }
        return user1;
    }
}
