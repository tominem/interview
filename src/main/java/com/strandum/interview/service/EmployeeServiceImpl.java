package com.strandum.interview.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.strandum.interview.client.EmployeeRestClient;
import com.strandum.interview.domain.Employee;
import com.strandum.interview.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRestClient client;
	private EmployeeRepository employeeRepository;
	
	private List<Employee> employeesCash = null;
	private int attempts = 1;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRestClient client, EmployeeRepository employeeRepository) {
		this.client = client;
		this.employeeRepository = employeeRepository;
	}

	@Override
	@Transactional
	public List<Employee> consumeAndPersist() {
		try {	
			
			log.info("consuming employees ...");
			
			// making cash to avoid unnecessary queries
			if (employeesCash == null) {
				employeesCash = findAll();
			}
			
			ResponseEntity<List<Employee>> response = client.consume();
			List<Employee> employees = response.getBody();
			
			if(employees!= null && !employeesCash.equals(employees)) {
				
				System.out.println(String.format("different data from rest-api: %s inserting %d elements ...", client.getBaseUrl(), employees.size()));
				
				employeeRepository.persistAllInBatch(employees);
				employeesCash = employees;
				
			} else {
				System.out.println(String.format("the same data coming from rest-api: %s response just ignored!", client.getBaseUrl()));
			}
			
			return employees;
		} catch (Exception e) {
			
			System.out.println("Error when trying to sinc " + client.getBaseUrl() + ", attempt number: " + attempts);
			
			attempts++;
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAllFetch();
	}

}
