package com.example.demo.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class EmployeeControllerITests {
	
	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:latest");
@Autowired
MockMvc mockMvc;
@Autowired
EmployeeRepo employeeRepo;
@Autowired
ObjectMapper objectMapper;

@BeforeEach
public void beforeMethod()
{
	employeeRepo.deleteAll();
}

@Test
public void test1() throws Exception
{
	//given
	Employee employee = Employee.builder().email("abc@gmail.com").firstName("charan")
			.build();
	
	//when
	  ResultActions response = mockMvc.perform(post("/api/employees")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(employee)));
	//then
	  response.andDo(print()).
      andExpect(status().isCreated())
      .andExpect(jsonPath("$.firstName",
              is(employee.getFirstName())))
      
      .andExpect(jsonPath("$.email",
              is(employee.getEmail())));
	  
}
}
