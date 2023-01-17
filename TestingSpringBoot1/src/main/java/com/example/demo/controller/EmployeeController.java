package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
@Autowired
EmployeeService employeeService;


	
//public EmployeeController(EmployeeService employeeService) {
//	super();
//	this.employeeService = employeeService;
//}



@PostMapping
@ResponseStatus(code = HttpStatus.CREATED)
public Employee createEmployee(@RequestBody Employee employee)
{
	return employeeService.saveEmployee(employee);
}

@GetMapping
@ResponseStatus(code = HttpStatus.OK)
public List<Employee> getAll()
{
	return employeeService.findAllEmployee();
}

@GetMapping("{id}")
public ResponseEntity<Employee> getById(@PathVariable("id") long id)
{
	return employeeService.getEmployeeById(id)
			.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());

			
}

	

}
