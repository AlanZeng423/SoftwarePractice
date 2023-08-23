package com.neusoft.elm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.neusoft.elm.dao.AdminDao;
import com.neusoft.elm.po.Admin;
import com.neusoft.elm.util.DBUtil;

public class AdminDaoImpl implements AdminDao{
	
	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;

	@Override
	public Admin getAdminByNameByPass(String adminName, String password) {
		Admin admin = null;
		//不要new，需要赋值为null
		//便于调用者查看是否有查询出数据，若为null则没有查询出数据
		String sql = "select * from admin where adminName=? and password=?";
		//sql语句
		try {
			con = DBUtil.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1, adminName);
			pst.setString(2, password);
			rs = pst.executeQuery();
			while(rs.next()) {
				//循环要确保查询的一致性
				admin = new Admin();
				admin.setAdminId(rs.getInt("adminId"));
				admin.setAdminName(rs.getString("adminName"));
				admin.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pst, con);
		}
		return admin;
	}
}
