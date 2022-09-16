package com.ram.api.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.ram.api.entity.Department;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentIntegrationTest {

	@LocalServerPort
	private int port;

	private String baseURL = "http://localhost";

	private static RestTemplate restTemplate = null;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setup() {
		baseURL = baseURL.concat(":").concat(port + "").concat("/department");
	}

	@Test
	@Sql(statements = "INSERT INTO Department (id, name, address, pincode) VALUES (1, 'ABC', '1, XYZ', 123456);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM Department where id = 1;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getDepartments() {
		Department[] departments = restTemplate.getForObject(baseURL, Department[].class);
		assertAll(
			() -> assertNotNull(departments),
			() -> assertEquals(1, departments.length),
			() -> assertEquals(1L, departments[0].getId()),
			() -> assertEquals("ABC", departments[0].getName()),
			() -> assertEquals("1, XYZ", departments[0].getAddress()),
			() -> assertEquals(123456, departments[0].getPincode())
		);
		
	}
	
	@Test
	@Sql(statements = "INSERT INTO Department (id, name, address, pincode) VALUES (1, 'ABC', '1, XYZ', 123456);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM Department where id = 1;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getDepartmentById() {
		Department department = restTemplate.getForObject(baseURL.concat("/1"), Department.class);
		assertAll(
			() -> assertNotNull(department),
			() -> assertEquals(1L, department.getId()),
			() -> assertEquals("ABC", department.getName()),
			() -> assertEquals("1, XYZ", department.getAddress()),
			() -> assertEquals(123456, department.getPincode())
		);
		
	}
	
	@Test
	@Sql(statements = "DELETE FROM Department where id = 1;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void createDepartment() {
		Department iDepartment = Department.builder().name("AAA").address("2, BBB").pincode(33333).build();
		Department oDepartment = restTemplate.postForObject(baseURL, iDepartment, Department.class);
		assertAll(
			() -> assertNotNull(oDepartment),
			() -> assertEquals(1L, oDepartment.getId()),
			() -> assertEquals(iDepartment.getName(), oDepartment.getName()),
			() -> assertEquals(iDepartment.getAddress(), oDepartment.getAddress()),
			() -> assertEquals(iDepartment.getPincode(), oDepartment.getPincode())
		);
		
	}
}
