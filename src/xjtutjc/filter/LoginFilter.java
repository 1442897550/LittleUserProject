package xjtutjc.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //判断是否是登录相关的资源
        //资源请求路径
        //强转
        System.out.println(req);
        HttpServletRequest request = (HttpServletRequest) req;
        //获取相关资源请求路径
        String requestURI = request.getRequestURI();
        //注意排除掉css，js，图片，验证码等资源
        if (requestURI.contains("/login.jsp")||requestURI.contains("/loginServlet")||requestURI.contains("/css/")||requestURI.contains("/fonts/")||requestURI.contains("/js/")||requestURI.contains("/checkCode")){
            //包含，证明用户就是登录
            chain.doFilter(req,resp);
        }else {
            //不包含，验证用户是否登录
            //从session中获取user
            Object user = request.getSession().getAttribute("user");
            if (user!=null){
                chain.doFilter(req,resp);
            }else {
                //没有登录，跳转登录页面
                request.setAttribute("login_msg","您尚未登录，请登录");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }

        }

//        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
