package com.ram.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ram.api.entity.Department;

public interface DepartmentService {
	
	ResponseEntity<Department> createDepartment(Department department);
	
	ResponseEntity<List<Department>> getDepartmentList();
	
	ResponseEntity<Department> getDepartmentById(long id);
	
	ResponseEntity<String> deleteDepartmentById(long id);
	
	ResponseEntity<Department> updateDepartmentById(long id, Department department);
}
