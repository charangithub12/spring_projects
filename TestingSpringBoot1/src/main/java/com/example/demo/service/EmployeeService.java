package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Employee;

public interface EmployeeService {
	public Employee saveEmployee(Employee employee);
	public List<Employee>  findAllEmployee();
	public void deleteEmployee(Long empId);
	public Optional<Employee> getEmployeeById(long id);
}
