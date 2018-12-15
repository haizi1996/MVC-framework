package framework.dataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PooledConnection extends AbstractConnection {
	public PooledConnection(Connection connection, ConnectionPool dataSource) {
		super(connection, dataSource);
	}
	 // 返回此对象中的连接
    public Connection getConnection() {
        return connection;
    }

    // 设置此对象的，连接
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		try {
			super.dataSource.closeConnectionPool(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    
	
}