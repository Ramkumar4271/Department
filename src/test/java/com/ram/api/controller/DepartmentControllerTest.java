package com.ram.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ram.api.entity.Department;
import com.ram.api.service.DepartmentService;
import com.ram.api.util.Utils;

@WebMvcTest
public class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService departmentService;
	
	private Department iDepartment;
	private Department oDepartment;
	private List<Department> departmentList;

	@BeforeEach
	void setup() throws Exception {
		iDepartment = new ObjectMapper().readValue(new File("src/test/resources/data/validDepartmentRequest.json"), Department.class);
		oDepartment = new ObjectMapper().readValue(new File("src/test/resources/data/validDepartmentResponse.json"), Department.class);
		departmentList = new ArrayList(Arrays.asList(new ObjectMapper().readValue(new File("src/test/resources/data/validDepartmentListResponse.json"), Department[].class)));
	}

	@Test
	public void test_createDepartment() throws Exception {
		String department_json = Utils.convertJsonFileToString("src/test/resources/data/validDepartmentRequest.json");
		ResponseEntity<Department> departmentRE = ResponseEntity.status(HttpStatus.CREATED).body(oDepartment);
		Mockito.when(departmentService.createDepartment(iDepartment)).thenReturn(departmentRE);
		MvcResult result = mockMvc.perform(post("/department").contentType(MediaType.APPLICATION_JSON).content(department_json)).andExpect(status().isCreated()).andReturn();
		Department departmentActual = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Department.class);
		assertNotNull(departmentActual);
		assertEquals(oDepartment.getName(), departmentActual.getName());
		
		department_json = Utils.convertJsonFileToString("src/test/resources/data/DepartmentRequestWithoutName.json");
		result = mockMvc.perform(post("/department").contentType(MediaType.APPLICATION_JSON).content(department_json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Department Name should be provided!!!", result.getResponse().getContentAsString());
		
		department_json = Utils.convertJsonFileToString("src/test/resources/data/DepartmentRequestWithInvalidPincode.json");
		result = mockMvc.perform(post("/department").contentType(MediaType.APPLICATION_JSON).content(department_json)).andExpect(status().isBadRequest()).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains("Cannot deserialize value of type `int` from String"));
	}
	
	@Test
	public void test_getDepartmentList() throws Exception {
		ResponseEntity<List<Department>> departmentListRE = ResponseEntity.status(HttpStatus.OK).body(departmentList);
		Mockito.when(departmentService.getDepartmentList()).thenReturn(departmentListRE);
		MvcResult result = mockMvc.perform(get("/department").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		List<Department> oDepartmentList = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
		assertNotNull(oDepartmentList);
		assertEquals(2, oDepartmentList.size());
	}
	
	@Test
	public void test_getDepartmentById() throws Exception {
		ResponseEntity<Department> departmentRE = ResponseEntity.status(HttpStatus.OK).body(oDepartment);
		Mockito.when(departmentService.getDepartmentById(1L)).thenReturn(departmentRE);
		MvcResult result = mockMvc.perform(get("/department/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		Department departmentActual = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Department.class);
		assertNotNull(departmentActual);
		assertEquals(oDepartment.getName(), departmentActual.getName());
		
		departmentRE = ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		Mockito.when(departmentService.getDepartmentById(2L)).thenReturn(departmentRE);
		mockMvc.perform(get("/department/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
		
		result = mockMvc.perform(get("/department/a").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains("Failed to convert value of type 'java.lang.String' to required type 'long'"));
	}
	
	@Test
	public void test_deleteDepartmentById() throws Exception {
		long id = 1L;
		ResponseEntity<String> re = ResponseEntity.status(HttpStatus.ACCEPTED).body("Department of ID " + id + "is deleted Successfully!");
		Mockito.when(departmentService.deleteDepartmentById(id)).thenReturn(re);
		MvcResult result = mockMvc.perform(delete("/department/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
		assertEquals("Department of ID " + id + "is deleted Successfully!", result.getResponse().getContentAsString());
		
		id = 2L;
		re = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Requested Department(ID: " + id + ") is not exist!!!");
		Mockito.when(departmentService.deleteDepartmentById(id)).thenReturn(re);
		result = mockMvc.perform(delete("/department/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
		assertEquals("Requested Department(ID: " + id + ") is not exist!!!", result.getResponse().getContentAsString());
	}
	
	@Test
	public void test_updateDepartmentById() throws Exception {
		iDepartment = new ObjectMapper().readValue(new File("src/test/resources/data/validUpdateDepartmentRequest.json"), Department.class);
		oDepartment = new ObjectMapper().readValue(new File("src/test/resources/data/validUpdateDepartmentResponse.json"), Department.class);
		long id = 1L;
		String department_json = Utils.convertJsonFileToString("src/test/resources/data/validUpdateDepartmentRequest.json");
		ResponseEntity<Department> oDepartmentRE = ResponseEntity.status(HttpStatus.CREATED).body(oDepartment);
		Mockito.when(departmentService.updateDepartmentById(id, iDepartment)).thenReturn(oDepartmentRE);
		MvcResult result = mockMvc.perform(put("/department/1").contentType(MediaType.APPLICATION_JSON).content(department_json)).andExpect(status().isCreated()).andReturn();
		Department departmentActual = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Department.class);
		assertNotNull(departmentActual);
		assertEquals(oDepartment.getName(),departmentActual.getName());
	}
	
}

















