package com.strandum.interview.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.strandum.interview.domain.Employee;

@Component
public class EmployeeRestClient {
	
	private String baseUrl;
	private RestTemplate restTemplate;

	@Autowired
	public EmployeeRestClient(@Value("${rest.url}") String baseUrl, RestTemplate restTemplate) {
		this.baseUrl = baseUrl;
		this.restTemplate = restTemplate;
	}
	
	public ResponseEntity<List<Employee>> consume() throws JsonProcessingException {
		
		ResponseEntity<List<Employee>> response = restTemplate.exchange(
		  baseUrl,
		  HttpMethod.GET,
		  null,
		  new ParameterizedTypeReference<List<Employee>>(){});
		
		return response;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

//	private String serialize(List<Employee> employees) throws JsonProcessingException {
//		ObjectMapper mapper = new ObjectMapper();
//		String value = mapper.writeValueAsString(employees);
//		return value;
//	}

}
