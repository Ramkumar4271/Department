package com.ram.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ram.api.entity.Department;
import com.ram.api.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public ResponseEntity<Department> createDepartment(Department department) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.departmentRepository.save(department));
	}

	@Override
	public ResponseEntity<List<Department>> getDepartmentList() {
		return ResponseEntity.status(HttpStatus.OK).body(this.departmentRepository.findAll());
	}

	@Override
	public ResponseEntity<Department> getDepartmentById(long id) {
		Optional<Department> opt = this.departmentRepository.findById(id);
		return opt.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(opt.get()) : ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@Override
	public ResponseEntity<String> deleteDepartmentById(long id) {
		if (this.departmentRepository.existsById(id)) {
			this.departmentRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Department of ID " + id + "is deleted Successfully!");
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Requested Department(ID: " + id + ") is not exist!!!");
	}

	@Override
	public ResponseEntity<Department> updateDepartmentById(long id, Department department) {
		if(this.departmentRepository.existsById(id)) {
			department.setId(id);
			return ResponseEntity.status(HttpStatus.CREATED).body(this.departmentRepository.save(department));
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

}
