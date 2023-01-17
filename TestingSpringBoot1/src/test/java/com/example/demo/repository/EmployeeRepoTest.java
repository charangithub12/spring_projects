package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepo;

@DataJpaTest
public class EmployeeRepoTest {
	@Autowired
	private EmployeeRepo employeeRepo;
	
	
//	@Test
//	public void givenEmployeeObject_whensave_thenreturnSavedEmp()
//	{
//		//given
//		Employee employee  = new Employee();
//		employee.setEmail("abc@gmail.com");
//		employee.setFirstName("charan");
//		employee.setLastName("gadam");
//		
//		Employee savedEmployee = employeeRepo.save(employee);
//		//when
//		Optional<Employee> se = employeeRepo.findByEmail(employee.getEmail());
//		
//		//then
//		Assertions.assertThat(se.get()).isNotNull();
//		Assertions.assertThat(savedEmployee.getId()).isGreaterThan(1);
//		
//		
//	}

	
	@Test
	public void givenEmployeeObject_whensave_thenreturnSavedEmp()
	{
		//given
		Employee employee  = new Employee();
		employee.setEmail("abc@gmail.com");
		employee.setFirstName("charan");
		employee.setLastName("gadam");
		
		Employee savedEmployee = employeeRepo.save(employee);
//		//when
//		Optional<Employee> se = employeeRepo.findByEmail(employee.getEmail());
//		
//		//then
//		Assertions.assertThat(se.get()).isNotNull();
//		Assertions.assertThat(savedEmployee.getId()).isGreaterThan(1);
		
		Employee e = employeeRepo.findByJPQL("charan", "gadam"); 
		
		Assertions.assertThat(e).isNotNull();
		
		
	}
}
