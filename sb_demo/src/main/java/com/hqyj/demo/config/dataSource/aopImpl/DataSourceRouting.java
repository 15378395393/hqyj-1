package com.hqyj.demo.config.dataSource.aopImpl;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Data Source Routing ---- 数据源开关，在程序运行时，把数据源通过 AbstractRoutingDataSource 
 * -动态织入到程序中，灵活的进行数据源切换
 * @author: HymanHu
 * @date: 2020年1月10日
 */
public class DataSourceRouting extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHolder.getDataSource();
	}

}
