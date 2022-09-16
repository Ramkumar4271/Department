package com.ram.api.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ram.api.entity.Department;
import com.ram.api.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@PostMapping("/department")
	public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) {
		return this.departmentService.createDepartment(department);
	}

	@GetMapping("/department")
	public ResponseEntity<List<Department>> getDepartmentList() {
		return this.departmentService.getDepartmentList();
	}

	@GetMapping("/department/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable(name="id") long id) {
		return this.departmentService.getDepartmentById(id);
	}
	
	@DeleteMapping("/department/{id}")
	public ResponseEntity<String> deleteDepartmentById(@PathVariable(name="id", required = true) long id) {
		return this.departmentService.deleteDepartmentById(id);
	}
	
	@PutMapping("/department/{id}")
	public ResponseEntity<Department> updateDepartmentById(@PathVariable(name="id") long id, @Valid @RequestBody Department department) {
		return this.departmentService.updateDepartmentById(id, department);
	}
}
