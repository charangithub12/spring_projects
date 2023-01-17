package com.example.demo.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.exception.ResourceNotFound;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepo;
import com.example.demo.service.EmployeeService;
@Component
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepo employeeRepo;
	public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
		// TODO Auto-generated constructor stub
		this.employeeRepo = employeeRepo;
		
	}
	@Override
	public Employee saveEmployee(Employee employee) {
		
		Optional<Employee> savedEmployee  = employeeRepo.findByEmail(employee.getEmail());
		if(savedEmployee.isPresent())
			throw new ResourceNotFound("employee is present");
		
		return employeeRepo.save(employee);
	}
	@Override
	public List<Employee> findAllEmployee() {
		// TODO Auto-generated method stub
		return employeeRepo.findAll();
	}
	@Override
	public void deleteEmployee(Long empId) {
		// TODO Auto-generated method stub
		
		employeeRepo.deleteById(empId);
		
	}
	@Override
	public Optional<Employee> getEmployeeById(long id) {
		// TODO Auto-generated method stub
		
		
		return employeeRepo.findById(id);
	}
	

	
	
}
