package com.strandum.interview.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Function;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.strandum.interview.domain.Employee;
import com.strandum.interview.domain.JobInfo;
import com.strandum.interview.domain.Salary;

public class EmployeeDeserealize extends JsonDeserializer<Employee>{

	@Override
	public Employee deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec codec = parser.getCodec();
		JsonNode node = codec.readTree(parser);
		
		Salary salary = Salary.builder()
				.salary((BigDecimal) parse(node, "salary", BigDecimal::new))
				.hourlyRate((BigDecimal) parse(node, "salary", BigDecimal::new))
				.build();
		
		JobInfo jobInfo = JobInfo.builder()
				.contractType(node.get("contractType").asText().trim())
				.jobTitle(node.get("jobTitle").asText().trim())
				.build();
		
		Employee employee = Employee.builder()
				.staffNumber(node.get("staffNumber").asText().trim())
				.firstName(StringUtils.capitalize(node.get("firstName").asText().trim()))
				.lastName(StringUtils.capitalize(node.get("lastName").asText().trim()))
				.salary(salary)
				.jobinfo(jobInfo)
				.build();
		
		salary.addEmployee(employee);
		jobInfo.addEmployee(employee);
		
		return employee;
	}

	private Object parse(JsonNode node, String field, Function<String, Object> function) {
		String value = node.get(field).asText().trim();
		return value != null && !value.equals("null") ? function.apply(value) : null;
	}
	
}
