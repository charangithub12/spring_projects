package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
	public Optional<Employee> findByEmail(String email);
	
	
	@Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
	public Employee findByJPQL(String firstName,String lastName);

}
