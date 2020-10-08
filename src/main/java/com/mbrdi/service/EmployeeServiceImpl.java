package com.mbrdi.service;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.protobuf.Struct.Builder;
import com.google.protobuf.TextFormat.ParseException;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import com.mbrdi.EncryptDecrypt.AESEncryptDecryptUtil;
import com.mbrdi.domain.Employee;
import com.mbrdi.rabbitmq.Producer;

@Component
public class EmployeeServiceImpl implements EmployeeService{

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${ms2.url}")
	private String ms2url;

	@Autowired
	Environment environment;

	@Autowired
	Producer producer;

	@Autowired
	AESEncryptDecryptUtil encryptUtil;

	@Override
	public ResponseEntity<String> storeEmployeeDetails(Employee employee, String fileType) {

		if(fileType.equalsIgnoreCase("CSV"))
		{
			Builder structBuilder = JsonToProtobufConverter(employee);
			String encryptedProtoData = encryptUtil.encrypt(structBuilder.toString());
			producer.produceStoreCsvMsg(encryptedProtoData);
			return new ResponseEntity<>("Status : Storing employee details in csv format",HttpStatus.CREATED);
		}
		else if(fileType.equalsIgnoreCase("XML"))
		{
			Builder structBuilder = JsonToProtobufConverter(employee);
			String encryptedProtoData = encryptUtil.encrypt(structBuilder.toString());
			producer.produceStoreXmlMsg(encryptedProtoData);
			return new ResponseEntity<>("Status : Storing employee details in xml format",HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>("Error : Invalid File Format",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> updateEmployeeDetails(Employee employee,String fileType) {

		if(fileType.equalsIgnoreCase("CSV"))
		{
			Builder structBuilder = JsonToProtobufConverter(employee);
			String encryptedProtoData = encryptUtil.encrypt(structBuilder.toString());
			producer.produceUpdateCsvMsg(encryptedProtoData);
			return new ResponseEntity<>("Status : Updating the Employee details of csv file",HttpStatus.OK);
		}
		else if(fileType.equalsIgnoreCase("XML"))
		{
			Builder structBuilder = JsonToProtobufConverter(employee);
			String encryptedProtoData = encryptUtil.encrypt(structBuilder.toString());
			producer.produceUpdateXmlMsg(encryptedProtoData);
			return new ResponseEntity<>("Status : Updating the Employee details of xml file",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Error : Couldn't find the file to update the details",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<Employee> readEmployeeDetails(String fileName) {

		String file = findTheEmployeeFile(fileName);
		if(file!=null && !file.isEmpty())
		{
			ObjectMapper mapper = new ObjectMapper();
			Employee employee = new Employee();

			ResponseEntity<String> response = restTemplate.exchange(ms2url+fileName, HttpMethod.GET, null , String.class);

			try {
				String decryptedProtoData = encryptUtil.decrypt(response.getBody());
				String convertedJsonData = ProtobufToJsonConverter(decryptedProtoData);
				employee = mapper.readValue(convertedJsonData, Employee.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<>(employee,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public String findTheEmployeeFile(String FileName) {
		String folder = environment.getProperty("folder.location");
		File file = new File(folder+"\\"+FileName);
		if(file.exists()) {
			return file.getName();
		}
		else {
			return null;
		}
	}

	/*
	 * Method which converts the json format data to protobuf format data
	 */
	private Builder JsonToProtobufConverter(Object obj)
	{
		JSONObject parameters = new JSONObject(obj);
		Builder structBuilder = Struct.newBuilder();
		try {
			JsonFormat.parser().merge(parameters.toString(), structBuilder);

			//System.out.println("from protobuf to json format -> "+JsonFormat.printer().print(structBuilder));

			return structBuilder;
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Method which converts the protobuf format data to json format data
	 */
	private String ProtobufToJsonConverter(String protoBufReceivedMsg) {
		try {
			CharSequence data = protoBufReceivedMsg;
			Builder structBuilder = Struct.newBuilder();
			com.google.protobuf.TextFormat.getParser().merge(data, structBuilder);
			return JsonFormat.printer().print(structBuilder);
		} catch (InvalidProtocolBufferException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
