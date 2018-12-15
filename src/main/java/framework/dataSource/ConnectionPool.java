package framework.dataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.sql.DataSource;

import framework.helper.ConfigHelper;
import framework.utils.ClassUtil;

public class ConnectionPool implements DataSource {

	private String jdbcDriver = ""; // 数据库驱动
	private String jdbcUrl = ""; // 数据 URL
	private String username = ""; // 数据库用户名
	private String password = ""; // 数据库用户密码
	private int initialConnections = 20; // 连接池的初始大小
	
	private ExecutorService threadPool = Executors.newFixedThreadPool(1);

	private int maxConnections = 50; // 连接池最大的大小
	private int minConnections = 5; // 连接池最大的大小
	
	
	private AtomicInteger activeConn = new AtomicInteger(0);//活动的连接数
	
	
	private boolean isFirst;
	
	private BlockingQueue<Connection> connectionContainer;
	
	// 它中存放的对象为 PooledConnection 型
	
	private ConnectionPool() {
		jdbcDriver = ConfigHelper.getJdbcDriver();
		jdbcUrl = ConfigHelper.getJdbcUrl();
		username = ConfigHelper.getJdbcUsername();
		password = ConfigHelper.getJdbcPassword();
		isFirst = true;
		ClassUtil.loadClass(jdbcDriver, false);
		try {
			createPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	 public synchronized void createPool() throws Exception {
	        // 确保连接池没有创建
	        // 假如连接池己经创建了，保存连接的向量 connections 不会为空
	        if (connectionContainer != null) {
	            return; // 假如己经创建，则返回
	        }
	        // 创建保存连接的向量 , 初始时有 0 个元素
	        connectionContainer = new LinkedBlockingQueue<>();
	        // 根据 initialConnections 中设置的值，创建连接。
	        createConnections(this.initialConnections);
	        System.out.println(" 数据库连接池创建成功！ ");
	    }
	
	
	private void createConnections(int numConnections) throws InterruptedException {
		 // 循环创建指定数目的数据库连接
        for (int x = 0; x < numConnections; x++) {
            // 是否连接池中的数据库连接的数量己经达到最大？最大值由类成员 maxConnections
            // 指出，假如 maxConnections 为 0 或负数，表示连接数量没有限制。
            // 假如连接数己经达到最大，即退出。
            if (this.maxConnections > 0 &&
                this.connectionContainer.size() + activeConn.get() >= this.maxConnections) {
                break;
            }
            connectionContainer.put(new PooledConnection(newConnection() , this));
            System.out.println(" 数据库连接己创建 ......");
        }
	}

	private Connection newConnection() {
		 // 创建一个数据库连接
        Connection conn;
		try {
			conn = DriverManager.getConnection(jdbcUrl, username, password);
			// 假如这是第一次创建数据库连接，即检查数据库，获得此数据库答应支持的
			// 最大客户连接数目
			//connections.size()==0 表示目前没有连接己被创建
			if (isFirst) {
				DatabaseMetaData metaData = conn.getMetaData();
				int driverMaxConnections = metaData.getMaxConnections();
				// 数据库返回的 driverMaxConnections 若为 0 ，表示此数据库没有最大
				// 连接限制，或数据库的最大连接限制不知道
				//driverMaxConnections 为返回的一个整数，表示此数据库答应客户连接的数目
				// 假如连接池中设置的最大连接数量大于数据库答应的连接数目 , 则置连接池的最大
				// 连接数目为数据库答应的最大数目
				if (driverMaxConnections > 0 &&
						this.maxConnections > driverMaxConnections) {
					this.maxConnections = driverMaxConnections;
				}
				isFirst = false;
			}
			return conn; // 返回创建的新的数据库连接
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("新建连接失败!!");
		}
	}
	public int getInitialConnections() {
        return this.initialConnections;
    }

   
    public void setInitialConnections(int initialConnections) {
        this.initialConnections = initialConnections;
    }

   

   
    public int getMaxConnections() {
        return this.maxConnections;
    }

   
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    private void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(" 关闭数据库连接出错： " + e.getMessage());
        }
    }
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection conn = null; 
		try {
			conn = connectionContainer.take();
			if(connectionContainer.size() < minConnections ) {
				threadPool.submit(new Runnable() {
					int num = maxConnections - activeConn.get() - connectionContainer.size();
					@Override
					public void run() {
						try {
							createConnections(num);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			activeConn.incrementAndGet();
			return conn;
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return getConnection();
	}

	public synchronized void closeConnectionPool(Connection pooledConnection) throws InterruptedException {
		if(connectionContainer.size() < initialConnections) {
			this.connectionContainer.put(pooledConnection);
		}else {
			closeConnection(pooledConnection);
		}
		activeConn.decrementAndGet();
	}
	private static class inner{
		private static ConnectionPool connectionPool = new ConnectionPool();
	}
	
	public static DataSource getDataSource() {
		return inner.connectionPool;
	}
}
