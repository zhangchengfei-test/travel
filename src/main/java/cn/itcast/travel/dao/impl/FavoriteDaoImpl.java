package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid){

        Favorite favorite = null;

        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (Exception e){
            e.printStackTrace();
        }

        return favorite;
    }

    @Override
    public int findCountByRid(String rid) {

        int ridInt = 0;

        if (rid != null && rid.length() > 0){
            ridInt = Integer.parseInt(rid);
        }

        int count = 0;
        try {
            String sql = "select count(*) from tab_favorite where rid = ?";
            count = template.queryForObject(sql,Integer.class,ridInt);
        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }
}
