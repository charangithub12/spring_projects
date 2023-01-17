package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.example.demo.exception.ResourceNotFound;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepo;
import com.example.demo.service.Impl.EmployeeServiceImpl;

public class EmployeeServiceTests {
EmployeeRepo employeeRepo;
EmployeeService employeeService;
Employee employee;

@BeforeEach
public void beforeMethod()
{
	employeeRepo  = Mockito.mock(EmployeeRepo.class);
	employeeService = new EmployeeServiceImpl(employeeRepo);
	employee = Employee.builder().email("abc@gmail.com").firstName("charan")
			.build();
	
}

@Test
public void test1()
{
	//given
	given(employeeRepo.findByEmail(employee.getEmail())).willReturn(Optional.empty());
	given(employeeRepo.save(employee)).willReturn(employee);
	System.out.println(employeeRepo);
	System.out.println(employeeService);
	//when
	Employee savedEmployee = employeeService.saveEmployee(employee);
	System.out.println(savedEmployee);
	
	//then
	Assertions.assertThat(savedEmployee).isEqualTo(employee);
	
}

@Test
public void test2()
{
	//given
	given(employeeRepo.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
	//given(employeeRepo.save(employee)).willReturn(employee);
	System.out.println(employeeRepo);
	System.out.println(employeeService);
	//when
//	Employee savedEmployee = employeeService.saveEmployee(employee);
//	System.out.println(savedEmployee);
	assertThrows(ResourceNotFound.class,() ->{employeeService.saveEmployee(employee);} );
	
	//then
	//Assertions.assertThat(savedEmployee).isEqualTo(employee);
	
	verify(employeeRepo,never()).save(any(Employee.class));
	
}

@Test
public void test3()
{
	//given
	given(employeeRepo.findAll()).willReturn(List.of(employee));
	//given(employeeRepo.save(employee)).willReturn(employee);
	System.out.println(employeeRepo);
	System.out.println(employeeService);
	//when
//	Employee savedEmployee = employeeService.saveEmployee(employee);
//	System.out.println(savedEmployee);
	//assertThrows(ResourceNotFound.class,() ->{employeeService.saveEmployee(employee);} );
	List<Employee> savedEmployees = employeeService.findAllEmployee();
	
	
	//then
	//Assertions.assertThat(savedEmployee).isEqualTo(employee);
	
	//verify(employeeRepo,never()).save(any(Employee.class));
	assertThat(savedEmployees.size()).isEqualTo(1);
	
}
@Test
public void test4()
{
	//given
	//BDDMockito.willDoNothing().given(employeeRepo).deleteById(1L);
	//when

	//List<Employee> savedEmployees = employeeService.findAllEmployee();
	employeeService.deleteEmployee(1l);
	
	//then
	//Assertions.assertThat(savedEmployee).isEqualTo(employee);
	
	//verify(employeeRepo,never()).save(any(Employee.class));
	//assertThat(savedEmployees.size()).isEqualTo(1);
	verify(employeeRepo,times(1)).deleteById(1l);
	
}

}
