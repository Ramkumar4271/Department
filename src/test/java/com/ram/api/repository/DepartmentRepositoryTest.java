package com.ram.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.AssertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ram.api.entity.Department;

@DataJpaTest
public class DepartmentRepositoryTest {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private Department department;
	
	private static long d_id;
	
	@BeforeEach()
	void init() {
		department = Department.builder().name("AAA").address("123, AAA").pincode(111).build();
		d_id++;
	}
	
	@Test
	public void testSave() {
		assertEquals(0, department.getId());
		Department savedDepartment = departmentRepository.save(department);
		
		assertNotNull(savedDepartment);
		assertEquals(d_id, savedDepartment.getId());
	}
	
	@Test
	public void testFindAll() {
		entityManager.persist(department);
		List<Department> findAll = departmentRepository.findAll();
		assertEquals(1, findAll.size());
	}
	
	@Test
	public void testFindById() {
		entityManager.persist(department);
		Optional<Department> departmentOptional = departmentRepository.findById(d_id);
		assertTrue(departmentOptional.isPresent());
		
		entityManager.detach(department);
		departmentOptional = departmentRepository.findById(d_id);
		assertTrue(departmentOptional.isEmpty());
	}
	
	@Test
	public void testExistsById() {
		boolean existsById = departmentRepository.existsById(d_id);
		assertFalse(existsById);
		entityManager.persist(department);
		existsById = departmentRepository.existsById(d_id);
		assertTrue(existsById);
	}
	
	@Test
	public void testDeleteById() {
		assertFalse(departmentRepository.existsById(d_id));
		departmentRepository.save(department);
		
		assertTrue(departmentRepository.existsById(d_id));
		departmentRepository.deleteById(d_id);
		assertFalse(departmentRepository.existsById(d_id));
	}
}
