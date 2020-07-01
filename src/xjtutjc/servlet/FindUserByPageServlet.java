package xjtutjc.servlet;

import xjtutjc.domain.PageBean;
import xjtutjc.domain.User;
import xjtutjc.service.UserService;
import xjtutjc.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        //获取条件查询的参数
        Map<String, String[]> condition = request.getParameterMap();
        if (currentPage == null || "".equals(currentPage) || Integer.parseInt(currentPage)<1){
            currentPage = "1";
        }
        if (rows == null || "".equals(rows)){
            rows = "5";
        }
        UserService service = new UserServiceImpl();
        PageBean<User> pageBean =service.findUserByPage(currentPage,rows,condition);
        request.setAttribute("map",condition);
        request.setAttribute("pb",pageBean);
        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
