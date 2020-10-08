package com.mbrdi.service;

import org.springframework.http.ResponseEntity;

import com.mbrdi.domain.Employee;

public interface EmployeeService {

	ResponseEntity<String> storeEmployeeDetails(Employee employee, String fileType);

	String findTheEmployeeFile(String FileName);

	ResponseEntity<String> updateEmployeeDetails(Employee employee, String fileType);

	ResponseEntity<Employee> readEmployeeDetails(String fileName);

}
