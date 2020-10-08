package com.mbrdi.test.controller;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.mbrdi.controller.EmployeeController;
import com.mbrdi.domain.Employee;
import com.mbrdi.service.EmployeeService;

import org.junit.platform.runner.JUnitPlatform;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@WebMvcTest(controllers = EmployeeController.class)
@ContextConfiguration(classes = EmployeeController.class)
public class EmployeeControllerTest {
	
	@Mock
	private EmployeeController employeeController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;

	@Test
	public void testStoreEmployeeInCSV()
	{
	    String employeeDataInJson = "{\r\n"
	    		+ "    \"employeeName\":\"Mary\",\r\n"
	    		+ "	\"dateOfBirth\":\"2200-03-20\",\r\n"
	    		+ "	\"salary\":\"3675\",\r\n"
	    		+ "	\"age\":50\r\n"
	    		+ "}";
	    
	    Mockito.when(employeeService.storeEmployeeDetails(Mockito.any(Employee.class),Mockito.anyString())).thenReturn(new ResponseEntity<>("Status : Storing employee details in csv format",HttpStatus.CREATED));
	    
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/storeEmployees").accept(MediaType.APPLICATION_JSON).header("file-Type", "CSV").content(employeeDataInJson).contentType(MediaType.APPLICATION_JSON);

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStoreEmployeeInXML()
	{
	    String employeeDataInJson = "{\r\n"
	    		+ "    \"employeeName\":\"Mary\",\r\n"
	    		+ "	\"dateOfBirth\":\"2200-03-20\",\r\n"
	    		+ "	\"salary\":\"3675\",\r\n"
	    		+ "	\"age\":50\r\n"
	    		+ "}";
	    
	    Mockito.when(employeeService.storeEmployeeDetails(Mockito.any(Employee.class),Mockito.anyString())).thenReturn(new ResponseEntity<>("Status : Storing employee details in xml format",HttpStatus.CREATED));
	    
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/storeEmployees").accept(MediaType.APPLICATION_JSON).header("file-Type", "XML").content(employeeDataInJson).contentType(MediaType.APPLICATION_JSON);

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStoreEmployeeForUnsupportedFileFormat()
	{
	    String employeeDataInJson = "{\r\n"
	    		+ "    \"employeeName\":\"Mary\",\r\n"
	    		+ "	\"dateOfBirth\":\"2200-03-20\",\r\n"
	    		+ "	\"salary\":\"3675\",\r\n"
	    		+ "	\"age\":50\r\n"
	    		+ "}";
	    
	    Mockito.when(employeeService.storeEmployeeDetails(Mockito.any(Employee.class),Mockito.anyString())).thenReturn(new ResponseEntity<>("Error : Invalid File Format",HttpStatus.BAD_REQUEST));
	    
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/storeEmployees").accept(MediaType.APPLICATION_JSON).header("file-Type", "Text").content(employeeDataInJson).contentType(MediaType.APPLICATION_JSON);

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateEmployeeDetailsInCSV() {
		String employeeDataInJson = "{\r\n"
	    		+ "    \"employeeName\":\"Mary\",\r\n"
	    		+ "	\"dateOfBirth\":\"2200-03-20\",\r\n"
	    		+ "	\"salary\":\"3675\",\r\n"
	    		+ "	\"age\":50\r\n"
	    		+ "}";
	    
	    Mockito.when(employeeService.updateEmployeeDetails(Mockito.any(Employee.class),Mockito.anyString())).thenReturn(new ResponseEntity<>("Status : Updating the Employee details of csv file",HttpStatus.OK));
	    
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateEmployees").accept(MediaType.APPLICATION_JSON).header("file-Type", "CSV").content(employeeDataInJson).contentType(MediaType.APPLICATION_JSON);

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateEmployeeDetailsInXML() {
		String employeeDataInJson = "{\r\n"
	    		+ "    \"employeeName\":\"Mary\",\r\n"
	    		+ "	\"dateOfBirth\":\"2200-03-20\",\r\n"
	    		+ "	\"salary\":\"3675\",\r\n"
	    		+ "	\"age\":50\r\n"
	    		+ "}";
	    
	    Mockito.when(employeeService.updateEmployeeDetails(Mockito.any(Employee.class),Mockito.anyString())).thenReturn(new ResponseEntity<>("Status : Updating the Employee details of xml file",HttpStatus.OK));
	    
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateEmployees").accept(MediaType.APPLICATION_JSON).header("file-Type", "XML").content(employeeDataInJson).contentType(MediaType.APPLICATION_JSON);

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateEmployeeDetailsForFailedCondition() {
		String employeeDataInJson = "{\r\n"
	    		+ "    \"employeeName\":\"Mary\",\r\n"
	    		+ "	\"dateOfBirth\":\"2200-03-20\",\r\n"
	    		+ "	\"salary\":\"3675\",\r\n"
	    		+ "	\"age\":50\r\n"
	    		+ "}";
	    
	    Mockito.when(employeeService.updateEmployeeDetails(Mockito.any(Employee.class),Mockito.anyString())).thenReturn(new ResponseEntity<>("Error : Couldn't find the file to update the details",HttpStatus.BAD_REQUEST));
	    
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateEmployees").accept(MediaType.APPLICATION_JSON).header("file-Type", "text").content(employeeDataInJson).contentType(MediaType.APPLICATION_JSON);

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadEmployeesDetails() {
	    
	    Mockito.when(employeeService.readEmployeeDetails(Mockito.anyString())).thenReturn(new ResponseEntity<>(Mockito.any(Employee.class),HttpStatus.OK));
	    
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/readEmployeesDetails/Mary_2200-03-20.CSV").accept(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadEmployeesDetailsFailedCondition() {
	    
	    Mockito.when(employeeService.readEmployeeDetails(Mockito.anyString())).thenReturn(new ResponseEntity<>(Mockito.any(Employee.class),HttpStatus.BAD_REQUEST));
	    
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/readEmployeesDetails/Mary_2200-03-20.CSV").accept(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
