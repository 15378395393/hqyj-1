package com.hqyj.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.hqyj.demo.config.dataSource.aopImpl.DataSourceAnnotation;
import com.hqyj.demo.config.dataSource.aopImpl.DataSourceHolder;

@Aspect
@Component
@Order(1)
public class DataSourceAspect {

	@Pointcut("@annotation(com.hqyj.demo.config.dataSource.aopImpl.DataSourceAnnotation)")
	public void annotationPointCut() {}
	
	@Before(value = "annotationPointCut()&&@annotation(dataSourceAnnotation)")
	public void beforeSwitchDs(JoinPoint point, DataSourceAnnotation dataSourceAnnotation) {
		DataSourceHolder.setDataBase(dataSourceAnnotation.value());
	}
	
	@After(value = "annotationPointCut()")
	public void afterSwitchDs() {
		DataSourceHolder.cleanDataSource();
	}
}
