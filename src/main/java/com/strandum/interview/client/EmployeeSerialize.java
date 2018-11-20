package com.strandum.interview.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.strandum.interview.domain.Employee;
import com.strandum.interview.domain.JobInfo;
import com.strandum.interview.domain.Salary;

public class EmployeeSerialize extends JsonSerializer<Employee>{

	@Override
	public void serialize(Employee employee, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		JobInfo jobInfo = employee.getJobinfo();
		Salary salary = employee.getSalary();
		
		gen.writeStartObject();
		gen.writeStringField("staffNumber", employee.getStaffNumber());
		gen.writeStringField("firstName", employee.getFirstName());
		gen.writeStringField("lastName", employee.getLastName());
		gen.writeNumberField("salary", salary.getSalary());
		gen.writeNumberField("hourlyRate", salary.getHourlyRate());
		gen.writeStringField("contractType", jobInfo.getContractType());
		gen.writeStringField("jobTitle", jobInfo.getJobTitle());
		gen.writeEndObject();
		
	}

}
