package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);

        int totalCount = routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);

        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);

        pageBean.setList(list);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize)+1;
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        Route route = null;
        if (rid != null && rid.length() > 0){
            route = routeDao.findOne(Integer.parseInt(rid));
        }

        List<RouteImg> list =  routeImgDao.findImgByRid(route.getRid());

        route.setRouteImgList(list);

        Seller seller = sellerDao.findById(route.getSid());

        route.setSeller(seller);

        int count = favoriteDao.findCountByRid(rid);

        route.setCount(count);
        return route;
    }
}
