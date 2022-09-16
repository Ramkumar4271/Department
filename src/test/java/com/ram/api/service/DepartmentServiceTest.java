package com.ram.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ram.api.entity.Department;
import com.ram.api.repository.DepartmentRepository;

@SpringBootTest
public class DepartmentServiceTest {

	@Autowired
	private DepartmentService departmentService;
	
	@MockBean
	private DepartmentRepository departmentRepository;
	
	@BeforeEach
	void init() {
	}
	
	@Test
	public void testCreateDepartment() {
		Department department = Department.builder().id(1).name("ABC").address("123, XYZ").pincode(12345).build();	
		Mockito.when(this.departmentRepository.save(department)).thenReturn(department);
		
		ResponseEntity<Department> departmentRE = this.departmentService.createDepartment(department);
		assertEquals(HttpStatus.CREATED, departmentRE.getStatusCode());
		assertNotNull(departmentRE.getBody());
		assertEquals(1, departmentRE.getBody().getId());
	}
	
	@Test
	public void testGetDepartmentList() {
		List<Department> departments = new ArrayList<>();
		departments.add(Department.builder().id(1).name("ABC").address("123, XYZ").pincode(12345).build());
		departments.add(Department.builder().id(2).name("XYZ").address("456, UVW").pincode(67890).build());
		Mockito.when(this.departmentRepository.findAll()).thenReturn(departments);
		
		ResponseEntity<List<Department>> departmentListRE = this.departmentService.getDepartmentList();
		assertEquals(HttpStatus.OK, departmentListRE.getStatusCode());
		assertNotNull(departmentListRE.getBody());
		assertEquals(2, departmentListRE.getBody().size());
	}
	
	@Test
	public void testGetDepartmentById() {
		Department department = Department.builder().id(1).name("ABC").address("123, XYZ").pincode(12345).build();
		Optional<Department> departmentOptional = Optional.of(department);
		Mockito.when(this.departmentRepository.findById(1L)).thenReturn(departmentOptional);
		ResponseEntity<Department> departmentByIdRE = this.departmentService.getDepartmentById(1L);
		assertEquals(HttpStatus.OK, departmentByIdRE.getStatusCode());
		assertNotNull(departmentByIdRE.getBody());
		assertEquals(department, departmentByIdRE.getBody());
		
		Mockito.when(this.departmentRepository.findById(1L)).thenReturn(departmentOptional.empty());
		departmentByIdRE = this.departmentService.getDepartmentById(1L);
		assertEquals(HttpStatus.NO_CONTENT, departmentByIdRE.getStatusCode());
		assertNull(departmentByIdRE.getBody());
	}
	
	@Test
	public void testDeleteDepartmentById() {
		Mockito.when(this.departmentRepository.existsById(1L)).thenReturn(true);
		ResponseEntity<String> deleteDepartmentById = this.departmentService.deleteDepartmentById(1L);
		assertEquals(HttpStatus.ACCEPTED, deleteDepartmentById.getStatusCode());
		assertEquals("Department of ID " + 1L + "is deleted Successfully!", deleteDepartmentById.getBody());
		
		Mockito.when(this.departmentRepository.existsById(1L)).thenReturn(false);
		deleteDepartmentById = this.departmentService.deleteDepartmentById(1L);
		assertEquals(HttpStatus.NO_CONTENT, deleteDepartmentById.getStatusCode());
		assertEquals("Requested Department(ID: " + 1L + ") is not exist!!!", deleteDepartmentById.getBody());
	}
	
	@Test
	public void testUpdateDepartmentById() {
		Department department = Department.builder().id(1).name("ABC").address("123, XYZ").pincode(12345).build();	
		Mockito.when(this.departmentRepository.existsById(1L)).thenReturn(true);
		Mockito.when(this.departmentRepository.save(department)).thenReturn(department);
		ResponseEntity<Department> departmentRE = this.departmentService.updateDepartmentById(1L, department);
		assertEquals(HttpStatus.CREATED, departmentRE.getStatusCode());
		assertNotNull(departmentRE.getBody());
		assertEquals(department, departmentRE.getBody());
		
		Mockito.when(this.departmentRepository.existsById(1L)).thenReturn(false);
		departmentRE = this.departmentService.updateDepartmentById(1L, department);
		assertEquals(HttpStatus.NO_CONTENT, departmentRE.getStatusCode());
		assertNull(departmentRE.getBody());
	}
	
	
	
	
	
	
}
