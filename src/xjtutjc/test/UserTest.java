package xjtutjc.test;

import org.junit.Test;
import xjtutjc.dao.UserDao;
import xjtutjc.dao.impl.UserDaoImpl;
import xjtutjc.domain.PageBean;
import xjtutjc.domain.Root;
import xjtutjc.domain.User;
import xjtutjc.service.UserService;
import xjtutjc.service.impl.UserServiceImpl;

public class UserTest {
    @Test
    public void testLogin(){
        UserDao dao = new UserDaoImpl();
        Root user = dao.login("18642167701", "tjc970518");
        System.out.println(user);
    }
    @Test
    public void testfind(){
        UserDao dao = new UserDaoImpl();
        User user = dao.findUser(1);
        System.out.println(user);
    }
//    @Test
//    public void testPage(){
//        UserService service = new UserServiceImpl();
//        PageBean<User> userByPage = service.("1", "5", condition);
//        System.out.println(userByPage);
//    }
}
