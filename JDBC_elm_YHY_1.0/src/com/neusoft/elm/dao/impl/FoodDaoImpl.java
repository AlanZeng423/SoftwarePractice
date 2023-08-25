package com.neusoft.elm.dao.impl;

import com.neusoft.elm.dao.FoodDao;
import com.neusoft.elm.po.Food;
import com.neusoft.elm.util.DBUtil;

import javax.swing.*;
import java.awt.image.DataBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoImpl implements FoodDao {
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet resultSet = null;

    public List<Food> listFoodByBusinessId(int businessId){
        List<Food> list = new ArrayList<>();
        String sql = "select * from food where businessId = ?";
        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setInt(1,businessId);
            resultSet = pst.executeQuery();
            while(resultSet.next()){
                Food food = new Food();
                food.setFoodId(resultSet.getInt("foodId"));
                food.setFoodName(resultSet.getString("foodName"));
                food.setFoodExplain(resultSet.getString("foodExplain"));
                food.setFoodPrice(resultSet.getDouble("foodPrice"));
                food.setBusinessId(resultSet.getInt("businessId"));
                list.add(food);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,resultSet);
        }
        return list;
    }

    public int saveFood(Food food){
        int result = 0;
        String sql = "insert into food values(null,?,?,?,?)";
        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setString(1, food.getFoodName());
            pst.setString(2, food.getFoodExplain());
            pst.setDouble(3, food.getFoodPrice());
            pst.setInt(4, food.getBusinessId());
            result = pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,resultSet);
        }
        return result;
    }

    public Food getFoodById(int foodId){
        Food food = null;
        String sql = "select * from food where foodId=?";
        try {
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setInt(1, foodId);
            resultSet = pst.executeQuery();
            while(resultSet.next()) {
                food = new Food();
                food.setFoodId(resultSet.getInt("foodId"));
                food.setFoodName(resultSet.getString("foodName"));
                food.setFoodExplain(resultSet.getString("foodExplain"));
                food.setFoodPrice(resultSet.getDouble("foodPrice"));
                food.setBusinessId(resultSet.getInt("businessId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, pst, resultSet);
        }
        return food;
    }

    public int updateFood(Food food){
        int result = 0;
        String sql = "update food set foodName=?,foodExplain=?,foodPrice=? where foodId=?";
        try {
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setString(1, food.getFoodName());
            pst.setString(2, food.getFoodExplain());
            pst.setDouble(3, food.getFoodPrice());
            pst.setInt(4, food.getFoodId());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, pst, null);
        }
        return result;
    }

    public int removeFood(int foodId){
        int result = 0;
        String sql = "delete from food where foodId=?";
        try{
            connection = DBUtil.connect();
            pst = connection.prepareStatement(sql);
            pst.setInt(1,foodId);
            result = pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pst,null);
        }
        return result;
    }
}
