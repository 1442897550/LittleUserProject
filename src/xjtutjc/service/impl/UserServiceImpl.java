package xjtutjc.service.impl;

import xjtutjc.dao.UserDao;
import xjtutjc.dao.impl.UserDaoImpl;
import xjtutjc.domain.PageBean;
import xjtutjc.domain.Root;
import xjtutjc.domain.User;
import xjtutjc.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        //调用dao完成查询
        return dao.findAll();
    }

    @Override
    public Root login(String username, String password) {
        return dao.login(username,password);
    }

    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.deleteUser(Integer.parseInt(id));
    }

    @Override
    public User findUser(String id) {
        return dao.findUser(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    public void delSelected(String[] uids) {
        if (uids!=null&&uids.length>0){
            for (String uid : uids) {
                dao.deleteUser(Integer.parseInt(uid));
            }
        }

    }

    @Override
    public PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition) {
        PageBean<User> pageBean = new PageBean<>();
        int currentpage =  Integer.parseInt(currentPage);
        int rownum = Integer.parseInt(rows);
        int totals = dao.findTotalCount(condition);
        int totalpage = totals%rownum==0? totals/rownum:totals/rownum+1;
        if (currentpage > totalpage){
            currentpage = totalpage;
        }
        int currentRow = (currentpage-1)*rownum;
        List<User> list =  dao.findByPage(currentRow,rownum,condition);


//        System.out.println(currentpage);
        pageBean.setCurrentPage(currentpage);
        pageBean.setRows(rownum);
        pageBean.setTotalCount(totals);
        pageBean.setList(list);
        pageBean.setTotalPage(totalpage);
        return pageBean;

    }
}
