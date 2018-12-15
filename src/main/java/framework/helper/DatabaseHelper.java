package framework.helper;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import framework.core.ObjectFactory;
import framework.dataSource.ConnectionPool;

public class DatabaseHelper {

	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	private static DataSource dataSource;

	static {
		dataSource = ObjectFactory.getDataSource();
		if(dataSource == null) {
			dataSource = ConnectionPool.getDataSource();
		}
	}

	public static Connection getConnection() {
		Connection conn = threadLocal.get();
		if (conn == null) {
			try {
				conn = dataSource.getConnection();
				threadLocal.set(conn);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return conn;
	}
	/**
	 * 开启事务
	 */
	public static void startTransaction() {
		Connection conn = getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("开启事务出错");
		} finally {
			threadLocal.set(conn);
		}
	}
	/**
	 * 提交事务
	 */
	public static void commitTransaction() {
		Connection conn = getConnection();
		try {
			if (conn == null) {
				conn = dataSource.getConnection();
				threadLocal.set(conn);
			}
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			threadLocal.remove();
		}
	}

	/**
	 * 执行回滚
	 */
	public static void rollbackTransaction() {
		Connection conn = threadLocal.get();
		if (conn != null) {
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				threadLocal.remove();
			}
		}
	}
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	
	
}