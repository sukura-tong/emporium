package com.swust.emporium.interceptor.shopadmin;

import com.swust.emporium.pojo.UserLogin;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店家管理系统拦截器
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 主要做事前拦截，即用户操作发生前，改写preHadndle里的逻辑进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
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
