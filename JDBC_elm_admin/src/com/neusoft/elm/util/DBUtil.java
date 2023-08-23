package com.neusoft.elm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

	//JDBC工具类的封装，链接数据库
	private static final String URL = "jdbc:mysql://localhost:3306/elm_admin?characterEncoding=utf-8";
	//jdbc:mysql，是jdbc链接mysql的协议
	//记得设置为utf-8的编码

	//jdbc:mysql://localhost:3306/?user=root
	//jdbc:mysql://localhost:3306/?user=root
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	//jar包的内路径，在包的文件夹里面就可以找到
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123";
	
	//获取Connection，实现和数据库的链接
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//关闭资源，关闭和数据库的链接
	public static void close(ResultSet rs,PreparedStatement pst,Connection con) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(pst!=null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pst = null;
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con = null;
		}
	}
}
