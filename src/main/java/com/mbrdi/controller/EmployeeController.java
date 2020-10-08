package com.mbrdi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbrdi.domain.Employee;
import com.mbrdi.service.EmployeeService;

@RestController
@RequestMapping("/")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	/*
	 * Store the new Employee details 
	 * '201' : description : Stored the Employee details & Created a file. 
	 * '400' : description : Invalid file format
	 */
	@PostMapping("/storeEmployees")
	public ResponseEntity<String> storeEmployeeDetails(@RequestBody Employee employee, @RequestHeader("File-Type") String fileType) {

		return employeeService.storeEmployeeDetails(employee,fileType);
		
	}
	
	/*
	 * Update the data of existing employee details 
	 * '200' : description : Updated the Employee details.
	 * '400' : description : Couldn't find the file for updating the details
	 */
	@PutMapping("/updateEmployees")
	public ResponseEntity<String> updateEmployeeDetails(@RequestBody Employee employee, @RequestHeader("File-Type") String fileType){
		
			return employeeService.updateEmployeeDetails(employee,fileType);
	}
	
	/*
	 * Read the employee details by using the filename
	 * '200' : description : Fetches the Employee details.
	 * '400' : description : Couldn't find the file for fetching the details
	 */
		
	@GetMapping("/readEmployeesDetails/{fileName}")
	public ResponseEntity<Employee> readEmployeeDetails(@PathVariable("fileName") String fileName) {
	
			return employeeService.readEmployeeDetails(fileName);
		
	}
}
