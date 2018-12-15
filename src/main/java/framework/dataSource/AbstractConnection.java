package framework.dataSource;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;


public abstract class AbstractConnection implements Connection{
	
	protected Connection connection ;
	protected ConnectionPool dataSource;

	public AbstractConnection(Connection connection, ConnectionPool dataSource) {
		this.connection = connection;
		this.dataSource = dataSource;
		
	}

	public Connection getconnection() {
		return connection;
	}


	@Override
	public void clearWarnings() throws SQLException {
		this.connection.clearWarnings();
		
	}

	@Override
	public void close() throws SQLException {
		this.connection.close();
		
	}

	@Override
	public void commit() throws SQLException {
		this.connection.commit();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		return this.connection.createArrayOf(typeName, elements);
	}

	@Override
	public Blob createBlob() throws SQLException {
		return this.createBlob();
	}

	@Override
	public Clob createClob() throws SQLException {
		return this.connection.createClob();
	}

	@Override
	public NClob createNClob() throws SQLException {
		return this.connection.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return this.connection.createSQLXML();
	}

	@Override
	public Statement createStatement() throws SQLException {
		return this.connection.createStatement();
	}

	@Override
	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return this.connection.createStatement(resultSetType, resultSetConcurrency);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		return this.connection.createStruct(typeName, attributes);
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return this.connection.getAutoCommit();
	}

	@Override
	public String getCatalog() throws SQLException {
		return this.connection.getCatalog();
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return this.connection.getClientInfo();
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		return this.connection.getClientInfo(name);
	}

	@Override
	public int getHoldability() throws SQLException {
		return this.connection.getHoldability();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return this.connection.getMetaData();
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return this.connection.getTransactionIsolation();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return this.connection.getTypeMap();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.connection.getWarnings();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return this.connection.isClosed();
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return this.connection.isReadOnly();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		return this.connection.isValid(timeout);
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		return this.connection.nativeSQL(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return this.connection.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return this.connection.prepareCall(sql);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		return this.connection.prepareStatement(sql,autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		return this.connection.prepareStatement(sql, columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		return this.connection.prepareStatement(sql, columnNames);
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return this.connection.prepareStatement(sql);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		this.connection.releaseSavepoint(savepoint);
	}

	@Override
	public void rollback() throws SQLException {
		this.connection.rollback();
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		this.connection.rollback(savepoint);
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.connection.setAutoCommit(autoCommit);
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		this.connection.setCatalog(catalog);
	}

	@Override
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		this.connection.setClientInfo(properties);
	}

	@Override
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		this.connection.setClientInfo(name, value);
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		this.connection.setHoldability(holdability);
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		this.connection.setReadOnly(readOnly);
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return this.connection.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return this.connection.setSavepoint(name);
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		this.connection.setTransactionIsolation(level);
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		this.connection.setTypeMap(map);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.connection.isWrapperFor(iface);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.connection.unwrap(iface);
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		this.connection.setSchema(schema);
	}

	@Override
	public String getSchema() throws SQLException {
		return this.connection.getSchema();
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		this.connection.abort(executor);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		this.connection.setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return connection.getNetworkTimeout();
	}
	

}
