package com.rongsm.myhomework;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rongsimin
 * @date 2020/11/19 0:21
 */
public class JDBCdemo {

	private String url = "jdbc:mysql://localhost:3306/my_learn?characterEncoding=utf-8";
	private String userName = "root";
	private String password = "root";

	/**
	 * 1）使用JDBC 原生接口，实现数据库的增删改查操作。
	 *
	 * @return
	 */
	public List<User> query() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> list = new ArrayList<>();
		String sql;
		try {
			//1.加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			//2.获得数据库链接
			conn = DriverManager.getConnection(url, userName, password);
			sql = "select * from t_user";
			//3.通过数据库的连接操作数据库，实现增删改查
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
				list.add(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
	 */
	public void useBatch() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			//1.加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			//2.获得数据库链接
			conn = DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(false);

			ps = conn.prepareStatement("insert into t_user (name) values (?)");
			for (int a = 0; a < 1000000; a++) {
				String name = "tommy" + a;
				ps.setString(1, name);
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
