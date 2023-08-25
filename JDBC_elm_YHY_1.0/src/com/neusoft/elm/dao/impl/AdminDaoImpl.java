package com.neusoft.elm.dao.impl;

import com.neusoft.elm.dao.AdminDao;
import com.neusoft.elm.po.Admin;
import com.neusoft.elm.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDaoImpl implements AdminDao {
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet resultSet = null;
    public Admin getAdminByNameByPass(String adminName, String password){
        Admin admin = null;
        //不要new，直接赋值为null
        //便于调用者查看是否有查询出数据，若为null则没有查询出数据

        String sql = "select * from admin where adminName=? and password=?";
        //需要执行的SQL语句直接给出来，然后有几个问号就是缺几个变量（之后要补的）
        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setString(1,adminName);
            pst.setString(2,password);
            resultSet = pst.executeQuery();
            while(resultSet.next()){
                admin = new Admin();
                admin.setAdminId(resultSet.getInt("adminId"));
                admin.setAdminName(resultSet.getString("adminName"));
                admin.setPassword(resultSet.getString("password"));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,resultSet);
        }
        return admin;
    }
}
