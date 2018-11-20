package com.strandum.interview.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strandum.interview.domain.Employee;
import com.strandum.interview.domain.JobInfo;
import com.strandum.interview.domain.Salary;

public class EmployeesGenerator {

	public List<Employee> generate() {

		List<Employee> employees = new ArrayList<>();

		for (int i = 0; i < 90000; i++) {

			Salary salary = Salary.builder().id(Long.valueOf(i)).salary(new BigDecimal("10" + i))
					.hourlyRate(new BigDecimal("50" + i)).build();

			JobInfo jobInfo = JobInfo.builder().id(Long.valueOf(i)).contractType("contract" + i).jobTitle("Admin" + i)
					.build();

			Employee employee = Employee.builder().id(Long.valueOf(i)).firstName("TOM" + i).lastName("CoSTA" + i)
					.jobinfo(jobInfo).salary(salary).build();

			employees.add(employee);
		}

		return employees;
	}

	public String toJson() {
		try {
			return serialize(generate());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private String serialize(List<Employee> employees) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String value = mapper.writeValueAsString(employees);
		return value;
	}

	public void whenWriteStringUsingBufferedWritter_thenCorrect() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("json.txt"));
		writer.write(toJson());

		writer.close();
	}

	public static void main(String[] args) throws IOException {
		new EmployeesGenerator().whenWriteStringUsingBufferedWritter_thenCorrect();
	}

}
