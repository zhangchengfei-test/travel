package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class UserDaoImpl implements UserDao {

    private DataSource dataSource = JDBCUtils.getDataSource();
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    @Override
    public int findUserByName(String username) {
        String sql = "select * from tab_user where username = ?";

        return 0;
    }

    @Override
    public int addUser(User user) {
        return 0;
    }
}
