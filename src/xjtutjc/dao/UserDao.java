package xjtutjc.dao;
/*
用户操作的dao
 */

import xjtutjc.domain.Root;
import xjtutjc.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //查询所有用户
    public List<User> findAll();
    //实现登录
    Root login(String username, String password);

    void addUser(User user);

    void deleteUser(int id);

    User findUser(int parseInt);

    void updateUser(User user);

    List<User> findByPage(int currentRow, int rownum, Map<String, String[]> condition);

    int findTotalCount(Map<String, String[]> condition);
}
