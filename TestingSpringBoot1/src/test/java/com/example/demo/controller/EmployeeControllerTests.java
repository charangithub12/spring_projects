package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTests {
@Autowired
MockMvc mockMvc;
@MockBean
EmployeeService employeeService;
@Autowired 
ObjectMapper objectMapper;

@Test
public void test1() throws Exception
{
	//given
	Employee employee = Employee.builder().email("abc@gmail.com").firstName("goutham")
			.build();
	BDDMockito.given(employeeService.saveEmployee(any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0) );
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

@Test
public void test2() throws Exception
{
	//given
	Employee employee = Employee.builder().email("abc@gmail.com").firstName("charan")
			.build();
	List<Employee> list1 = List.of(employee);
	BDDMockito.given(employeeService.findAllEmployee()).willReturn(list1);
	//when
	  ResultActions response = mockMvc.perform(get("/api/employees"));
	            //.contentType(MediaType.APPLICATION_JSON)
	            //.content(objectMapper.writeValueAsString(employee)));
	//then
	  response.andDo(print()).
      andExpect(status().isOk())
      .andExpect(jsonPath("$.size()",
              is(list1.size())));
      
//      .andExpect(jsonPath("$.email",
//              is(employee.getEmail())));
	  
}

@Test
public void test3() throws Exception
{
	//given
	Employee employee = Employee.builder().email("abc@gmail.com").firstName("charan")
			.build();
	BDDMockito.given(employeeService.getEmployeeById(anyLong())).willReturn(Optional.empty());
	//when
	  ResultActions response = mockMvc.perform(get("/api/employees/{id}",1L));
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .content(objectMapper.writeValueAsString(employee)));
	//then
	  response.andDo(print()).
      andExpect(status().isNotFound());
//      .andExpect(jsonPath("$.firstName",
//              is(employee.getFirstName())))
//      
//      .andExpect(jsonPath("$.email",
//              is(employee.getEmail())));
	  
}
}
