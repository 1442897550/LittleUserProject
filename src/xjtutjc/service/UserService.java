package xjtutjc.service;
/*
用户管理的业务接口
 */

import xjtutjc.domain.PageBean;
import xjtutjc.domain.Root;
import xjtutjc.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /*
    查询所有用户信息
     */
    public List<User> findAll();
    public Root login(String username, String password);

    void addUser(User user);
    //根据id删除
    void deleteUser(String id);

    User findUser(String id);

    void updateUser(User user);

    void delSelected(String[] uids);

    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
