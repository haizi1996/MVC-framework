package framework.dataSource;

import javax.sql.DataSource;

/**
 * 数据源工厂
 * @author hailin
 */
public interface DataSourceFactory {

    /**
     * 获取数据源
     *
     * @return 数据源
     */
    DataSource getDataSource();
}
