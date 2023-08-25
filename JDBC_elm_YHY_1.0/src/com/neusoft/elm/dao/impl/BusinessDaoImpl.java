package com.neusoft.elm.dao.impl;

import com.neusoft.elm.dao.BusinessDao;
import com.neusoft.elm.po.Business;
import com.neusoft.elm.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessDaoImpl implements BusinessDao{

    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet resultSet = null;
    public List<Business> listBusiness(String businessName,String businessAddress){
        List<Business> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from business where 1=1");
        //这里很重要，方便后面查询条件的插入
        if(businessName!=null&&!businessName.equals("")) {
            sql.append(" and businessName like '%"+businessName+"%' ");
        }
        if(businessAddress!=null&&!businessAddress.equals("")) {
            sql.append(" and businessAddress like '%"+businessAddress+"%' ");
        }

        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql.toString());
            resultSet = pst.executeQuery();
            while(resultSet.next()) {
                Business business = new Business();
                business.setBusinessId(resultSet.getInt("businessId"));
                business.setPassword(resultSet.getString("password"));
                business.setBusinessName(resultSet.getString("businessName"));
                business.setBusinessAddress(resultSet.getString("businessAddress"));
                business.setBusinessExplain(resultSet.getString("businessExplain"));
                business.setStarPrice(resultSet.getDouble("starPrice"));
                business.setDeliveryPrice(resultSet.getDouble("deliveryPrice"));
                list.add(business);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,pst,resultSet);
        }
        return list;
    }

    public int saveBusiness(String businessName){
        int businessId = 0;
        String sql ="insert into business(businessName,password) values(?,'123')";
        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            //此处PreparedStatement.RETURN_GENERATED_KEYS字段，表明预处理sql语句时自动生成主键元素
            //（因为下面需要使用）
            pst.setString(1,businessName);
            pst.executeUpdate();
            resultSet = pst.getGeneratedKeys();
            if(resultSet.next()){
                businessId = resultSet.getInt(1);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,resultSet);
        }


        return businessId;
    }

    public int removeBusiness(int businessId){
        int result = 0;
        String delFoodSql = "delete from food where businessId = ?";
        String delBusinessSql = "delete from Business where businessId = ?";
        try{
            connection = DBUtil.connect();
            connection.setAutoCommit(false);//将数据库设置为手动提交方式

            pst = connection.prepareStatement(delFoodSql);
            pst.setInt(1,businessId);
            pst.executeUpdate();

            pst = connection.prepareStatement(delBusinessSql);
            pst.setInt(1,businessId);
            result = pst.executeUpdate();

            connection.commit();
        }catch(SQLException e){
            result = 0;
            try{
                connection.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,resultSet);
        }
        return result;
    }

    public Business getBusinessByIdByPass(int businessId, String password){
        Business business = null;
        String sql = "select * from business where businessId = ? and password = ?";
        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setInt(1,businessId);
            pst.setString(2,password);
            resultSet = pst.executeQuery();
            while(resultSet.next()){
                business = new Business();
                business.setBusinessId(resultSet.getInt("businessId"));
                business.setPassword(resultSet.getString("password"));
                business.setBusinessName(resultSet.getString("businessName"));
                business.setBusinessAddress(resultSet.getString("businessAddress"));
                business.setBusinessExplain(resultSet.getString("businessExplain"));
                business.setStarPrice(resultSet.getDouble("starPrice"));
                business.setDeliveryPrice(resultSet.getDouble("deliveryPrice"));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,resultSet);
        }
        return business;
    }

    public Business getBusinessById(Integer businessId){
        Business business = null;
        String sql = "select * from business where businessId = ?";
        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setInt(1,businessId);
            resultSet = pst.executeQuery();
            while(resultSet.next()){
                business = new Business();
                business.setBusinessId(resultSet.getInt("businessId"));
                business.setPassword(resultSet.getString("password"));
                business.setBusinessName(resultSet.getString("businessName"));
                business.setBusinessAddress(resultSet.getString("businessAddress"));
                business.setBusinessExplain(resultSet.getString("businessExplain"));
                business.setStarPrice(resultSet.getDouble("starPrice"));
                business.setDeliveryPrice(resultSet.getDouble("deliveryPrice"));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,resultSet);
        }
        return business;
    }

    public int updateBusiness(Business business){
        int result = 0;
        String sql = "update business set businessName = ?, businessAddress = ?, businessExplain=?,starPrice=?,deliveryPrice=? where businessId=?";
        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setString(1,business.getBusinessName());
            pst.setString(2,business.getBusinessAddress());
            pst.setString(3,business.getBusinessExplain());
            pst.setDouble(4,business.getStarPrice());
            pst.setDouble(5,business.getDeliveryPrice());
            pst.setInt(6, business.getBusinessId());
            result = pst.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,resultSet);
        }
        return result;
    }

    public int updateBusinessByPassword(Integer businessId, String newPass){
        int result = 0;
        String sql = "update business set password = ? where businessId = ?";
        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setString(1,newPass);
            pst.setInt(2,businessId);
            result = pst.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,resultSet);
        }
        return result;
    }




}
