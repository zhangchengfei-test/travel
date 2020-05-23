package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        response.setContentType("application/json;charset=utf-8");
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            ObjectMapper objectMapper = new ObjectMapper();
            String err_json = objectMapper.writeValueAsString(resultInfo);
            response.getWriter().write(err_json);
            return;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        int regist = userService.regist(user);
        ResultInfo resultInfo = new ResultInfo();

        if (regist == 0 ){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }else {
            resultInfo.setFlag(true);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String err_json = objectMapper.writeValueAsString(resultInfo);
        response.getWriter().write(err_json);
    }

    /**
     * 登陆功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        response.setContentType("application/json;charset=utf-8");

        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            ObjectMapper objectMapper = new ObjectMapper();
            String err_json = objectMapper.writeValueAsString(resultInfo);
            response.getWriter().write(err_json);
            return;
        }

        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        User user1 = userService.login(user);

        ResultInfo info = new ResultInfo();

        if (user1 == null){
            info.setFlag(false);
            info.setErrorMsg("登陆失败");
        }
        if (user1 !=null && !"Y".equals(user1.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("登陆失败");
        }

        if (user1 !=null && "Y".equals(user1.getStatus())){
            request.getSession().setAttribute("user",user1);
            info.setFlag(true);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(info);
        response.getWriter().write(json);
    }

    /**
     * 查询单个用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        Object user = request.getSession().getAttribute("user");

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),user);
    }

    /**
     * 退出登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        //销毁session
        request.getSession().invalidate();
        //重定向到登陆页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 激活用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        String code = request.getParameter("code");
        response.setContentType("text/html;charset=utf-8");
        String msg = null;
        if (code != null){
            int active = userService.active(code);
            if (active == 0){
                msg = "激活失败，请重新注册激活！";
            }else if (active == -1){
                msg = "此账号已经激活，不要重复激活啦！请<a href='login.html'>登陆</a>";
            }else {
                msg = "激活成功，请<a href='login.html'>登陆</a>";
            }
            response.getWriter().write(msg);
        }
    }

}
