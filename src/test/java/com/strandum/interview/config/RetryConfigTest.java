//package com.strandum.interview.config;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.retry.annotation.EnableRetry;
//
//import com.strandum.interview.service.EmployeeService;
//
//@Configuration
//@EnableRetry
//public class RetryConfigTest {
//
//	@Bean
//	public EmployeeService employeeService() {
//		EmployeeService employeeService = mock(EmployeeService.class);
//		when(employeeService.consumeAndPersist()).thenThrow(new RuntimeException("Remote Exception 1"))
//				.thenThrow(new RuntimeException("Remote Exception 2")).thenReturn(null);
//		return employeeService;
//	}
//}