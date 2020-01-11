package com.hqyj.demo.modules.test.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.demo.config.dataSource.aopImpl.DataSourceAnnotation;
import com.hqyj.demo.modules.test.dao.TestDao;
import com.hqyj.demo.modules.test.entity.City;
import com.hqyj.demo.modules.test.entity.Country;
import com.hqyj.demo.modules.test.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;
	
	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		return Optional.ofNullable(testDao.getCitiesByCountryId2(countryId))
				.orElse(Collections.emptyList());
	}

	@Override
	@DataSourceAnnotation("testDb")
	public Country getcountryByCountryId(int countryId) {
		return testDao.getcountryByCountryId(countryId);
	}

	@Override
	public Country getCountryByCountryName(String countryName) {
		return testDao.getCountryByCountryName(countryName);
	}

	@Override
	public PageInfo<City> getCitiesByPage(int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<City> cities = Optional.ofNullable(testDao.getCitiesByPage())
				.orElse(Collections.emptyList());
		return new PageInfo<>(cities);
	}

	@Override
	public City insertCity(City city) {
		testDao.insertCity(city);
		return city;
	}

	/* 
	 * 添加事务，并设置其属性
	 */
	@Override
	@Transactional(noRollbackFor=ArithmeticException.class,propagation=Propagation.REQUIRED)
	public City updateCity(City city) {
		testDao.updateCity(city);
//		int i = 1 / 0;
		return city;
	}

	@Override
	public void deleteCity(int cityId) {
		testDao.deleteCity(cityId);
	}

}
