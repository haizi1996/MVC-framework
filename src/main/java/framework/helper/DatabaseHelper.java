package framework.helper;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import framework.core.ObjectFactory;
import framework.dataSource.ConnectionPool;
import framework.dataSource.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库辅助类
 * @author hailin
 */
public class DatabaseHelper {


    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    /**
     * 定义一个局部线程变量（使每个线程都拥有自己的连接）
     */
	private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();

    /**
     * 获取数据源工厂
     */
    private static final DataSourceFactory dataSourceFactory = ObjectFactory.getDataSourceFactory();


    private static DataSource dataSource;

	static {
		dataSource = ObjectFactory.getDataSource();
		if(dataSource == null) {
			dataSource = ConnectionPool.getDataSource();
		}
	}

	public static Connection getConnection() {
		Connection conn = connContainer.get();
		if (conn == null) {
			try {
				conn = dataSource.getConnection();
				connContainer.set(conn);
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
			connContainer.set(conn);
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
				connContainer.set(conn);
			}
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connContainer.remove();
		}
	}

	/**
	 * 执行回滚
	 */
	public static void rollbackTransaction() {
		Connection conn = connContainer.get();
		if (conn != null) {
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				connContainer.remove();
			}
		}
	}
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	
	
}