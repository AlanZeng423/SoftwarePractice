package com.neusoft.elm.util;

import java.sql.*;

public class DBUtil {
    private static String jdbcUrl = "jdbc:mysql://localhost:3306/your_database_name";
    private static String username = "root";
    private static String password = "123";

    public static Connection connect(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");//注册JDBC驱动程序
            connection = DriverManager.getConnection(jdbcUrl,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void close(Connection connection, PreparedStatement pst, ResultSet resultSet){
        if(connection != null){
            try{
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(pst != null){
            try{
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(resultSet != null){
            try{
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
