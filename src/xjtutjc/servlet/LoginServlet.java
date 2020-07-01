package xjtutjc.servlet;

import org.apache.commons.beanutils.BeanUtils;
import xjtutjc.domain.Root;
import xjtutjc.domain.User;
import xjtutjc.service.UserService;
import xjtutjc.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request);
        request.setCharacterEncoding("utf-8");
        //获取数据
        String verifycode = request.getParameter("verifycode");//验证码
//校验验证码
        HttpSession session = request.getSession();
        String checkCode = (String)session.getAttribute("checkcode");
        session.removeAttribute("checkcode");
        if (!checkCode.equalsIgnoreCase(verifycode)){
            //验证码不正确
            //提示信息
            //跳转登录页面
            request.setAttribute("login_msg","验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
//        User user = new User();
        Root root = new Root();
        try {
            BeanUtils.populate(root,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        Root loginUser = service.login(root.getUsername(), root.getPassword());
        if (loginUser!=null){
            HttpSession session1 = request.getSession();
            session1.setAttribute("user",loginUser);
            response.sendRedirect(request.getContextPath()+"/index.jsp");

        }else {
            request.setAttribute("login_msg","用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }



        //调用service查询

        //判断是否登录成功
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
