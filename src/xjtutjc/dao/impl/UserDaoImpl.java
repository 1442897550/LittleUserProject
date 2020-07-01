package xjtutjc.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import xjtutjc.dao.UserDao;
import xjtutjc.domain.Root;
import xjtutjc.domain.User;
import xjtutjc.util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<User> findAll() {
        //jdbc操作数据库

        String sql = "Select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));

        return users;
    }

    @Override
    public Root login(String username,String password) {
        try {
            String sql ="Select * from root where username = ? and password = ?";
            Root root = template.queryForObject(sql, new BeanPropertyRowMapper<Root>(Root.class), username,password);
            return root;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void addUser(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?)";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());

    }

    @Override
    public void deleteUser(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql,id);
    }

    @Override
    public User findUser(int parseInt) {
        String sql = "select * from user where id = ?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), parseInt);
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user set name = ?,gender=?,age=?,address=?,qq=?,email=? where id = ?";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public List<User> findByPage(int currentRow, int rownum, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1";
//        String sql ="select * from user limit ? , ?";
        //定义模板sql
        StringBuilder sb = new StringBuilder(sql);
        //定义参数集合
        List<Object> params = new ArrayList<Object>();
        //遍历map
        Set<String> strings = condition.keySet();
        for (String string : strings) {
            //排除分页条件
            if ("currentPage".equals(string)||"rows".equals(string)){
                continue;
            }
            String value = condition.get(string)[0];
            if (value!=null && !"".equals(value)){
                sb.append(" and "+ string+" like ? ");
                params.add("%"+value+"%");//加问号条件的值
            }
        }
        sb.append(" limit ? , ? ");
        params.add(currentRow);
        params.add(rownum);


            List<User> query = template.query(sb.toString(), new BeanPropertyRowMapper<User>(User.class), params.toArray());
            return query;







    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        String sql = "select count(*) from user where 1 = 1";
        //定义模板sql
        StringBuilder sb = new StringBuilder(sql);
        //定义参数集合
        List<Object> params = new ArrayList<Object>();
        //遍历map
        Set<String> strings = condition.keySet();

        for (String string : strings) {
            //排除分页条件
            if ("currentPage".equals(string)||"rows".equals(string)){
                continue;
            }
            String value = condition.get(string)[0];
            if (value!=null && !"".equals(value)){
                sb.append(" and "+ string+" like ? ");
                params.add("%"+value+"%");//加问号条件的值
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);
        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }
}
