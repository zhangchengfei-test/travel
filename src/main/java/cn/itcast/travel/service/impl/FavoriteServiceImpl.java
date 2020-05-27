package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {

        int ridInt = 0;

        if (rid != null && rid.length() > 0){
            ridInt = Integer.parseInt(rid);
        }

        Favorite favorite = favoriteDao.findByRidAndUid(ridInt,uid);

        if (favorite == null){
            return false;
        }
        return true;
    }

}
