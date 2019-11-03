package com.hqyj.erp.modules.organization.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.hqyj.erp.modules.common.vo.Result;
import com.hqyj.erp.modules.common.vo.SearchVo;
import com.hqyj.erp.modules.organization.entity.Department;
import com.hqyj.erp.modules.organization.entity.Position;

public interface OrganizationService {

	List<Department> getDepartments();
	
	Department getDepartmentById(int departId);
	
	Department getDepartmentByName(String departName);
	
	Result insertOrUpdateDepartment(Department department);
	
	Result deleteDepartment(int departId);
	
	List<Position> getPositions();
	
	Result insertOrUpdatePosition(Position position);
	
	PageInfo<Position> getPositionsByPage(SearchVo searchVo);
	
	Result deletePosition(int positionId);
	
	List<Position> getPositionsByDepartName(@RequestParam String departName);
}
