package com.hqyj.demo.config.dataSource.aopImpl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Source Holder ---- 采用 ThreadLocal工具类，设置数据源、获取数据源、清除数据源
 * @author: HymanHu
 * @date: 2020年1月10日
 */
public class DataSourceHolder {

	private final static Logger LOGGER = LoggerFactory.getLogger(DataSourceHolder.class);
	// 使用ThreadLocal实现线程安全，创建副本各不干扰
	private final static ThreadLocal<String> DATA_SOURCE_HOLDER = new ThreadLocal<>();
	
	// 配置不同的数据源名称
	public enum DataSource {
		mainDb, testDb
	}
	
	// 设置数据源
	public static void setDataBase(String dataSource) {
		LOGGER.debug("Set data source: " + dataSource);
		DATA_SOURCE_HOLDER.set(dataSource);
	}
	
	// 获取数据源
	public static String getDataSource() {
		return StringUtils.isBlank(DATA_SOURCE_HOLDER.get()) ? 
				DataSource.mainDb.toString() : DATA_SOURCE_HOLDER.get();
	}
	
	// 清除数据源
	public static void cleanDataSource() {
		DATA_SOURCE_HOLDER.remove();
	}
}
