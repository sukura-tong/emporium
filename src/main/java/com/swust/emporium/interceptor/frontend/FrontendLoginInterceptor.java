package com.swust.emporium.interceptor.frontend;

import com.swust.emporium.pojo.UserLogin;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class FrontendLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从session中取出用户信息
        Object userObj = request.getSession().getAttribute("userLogin");
        if (userObj != null){
            //若用户信息不为空则将session里的用户信息转化成PersonInfo实体类对象
            UserLogin user = (UserLogin) userObj;
            //空值判断
            if (user != null && !(user.getUid() < 0)){
                return true;
            }
        }
        // 若不满足登陆验证，跳转到账号登陆界面
        PrintWriter out = response.getWriter();
//        System.out.println(request.getContextPath());
        out.println("<html>");
        out.println("<script>");
        out.println("window.open(" + "'" + request.getContextPath() + "/frontend/login'" + ")");
//        System.out.println("'" + request.getContextPath() + "/frontend/login'");
        out.println("</script>");
        out.println("</html>");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
