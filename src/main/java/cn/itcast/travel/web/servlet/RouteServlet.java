package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");

        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        int cid = 0;
        if (cidStr !=null && cidStr.length() > 0){
            cid = Integer.parseInt(cidStr);
        }

        int currentPage = 0;
        if (currentPageStr !=null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr !=null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }

        PageBean<Route> pageBean = routeService.pageQuery(cid,currentPage,pageSize,rname);

        writeValue(pageBean,response);

    }

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");

        Route route = routeService.findOne(rid);

        writeValue(route,response);
    }

    /**
     * 是否收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");

        User user = (User) request.getSession().getAttribute("user");

        int uid = 0;
        if (user != null){
            uid = user.getUid();
        }

        boolean flag = favoriteService.isFavorite(rid,uid);

        writeValue(flag,response);
    }
}
