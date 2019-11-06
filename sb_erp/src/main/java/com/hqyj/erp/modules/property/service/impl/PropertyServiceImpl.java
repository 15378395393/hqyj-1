package com.hqyj.erp.modules.property.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.erp.modules.common.vo.Result;
import com.hqyj.erp.modules.common.vo.SearchVo;
import com.hqyj.erp.modules.property.dao.PropertyDao;
import com.hqyj.erp.modules.property.entity.GrantProperty;
import com.hqyj.erp.modules.property.entity.Property;
import com.hqyj.erp.modules.property.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyDao propertyDao;
	
	@Override
	public Result addProperty(Property property) {
		Property existProperty = propertyDao.getPropertyByAttribute(property);
		property.initProperty(existProperty);
		
		return null;
	}
	
	@Override
	public PageInfo<GrantProperty> getGrantProperties(SearchVo searchVo) {
		try {
			SearchVo.initSearchVo(searchVo);
			PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
			
			List<GrantProperty> grantProperties = 
					Optional.ofNullable(propertyDao.getGrantProperties(searchVo))
					.orElse(Collections.emptyList());
			return new PageInfo<>(grantProperties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new PageInfo<>();
	}

}
