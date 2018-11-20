package com.strandum.interview.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.strandum.interview.config.RestConfig;
import com.strandum.interview.config.TestJPAConfig;
import com.strandum.interview.domain.Employee;
import com.strandum.interview.repository.EmployeeRepository;
import com.strandum.interview.service.EmployeeService;
import com.strandum.interview.service.EmployeeServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TestJPAConfig.class, RestConfig.class})
@EnableJpaRepositories(basePackageClasses=EmployeeRepository.class)
@ActiveProfiles("test")
public class EmployeeServiceTest {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String baseURL = "/employees";
	
	@Autowired
	private String restResponse1;
	
	@Autowired
	private String restResponse2;
	
	private EmployeeRestClient employeeClient;
	
	private MockRestServiceServer server;
	private EmployeeService employeeService;
	
	@Before
	public void setup() {
		server = MockRestServiceServer.bindTo(restTemplate).build();
		employeeClient = new EmployeeRestClient(baseURL, restTemplate);
		employeeService = new EmployeeServiceImpl(employeeClient, employeeRepository);
	}
	
	@Test
	public void firstConsume() throws JsonProcessingException {
		// given
		long sizeBefore = employeeService.findAll().size();
		server.expect(once(), requestTo("/employees")).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(restResponse1, MediaType.APPLICATION_JSON));
		
		// when
		employeeService.consumeAndPersist();
		List<Employee> employeesFromDB = employeeService.findAll();
		long sizeAfter = employeesFromDB.size();
		
		// then
		server.verify();
		assertThat(sizeBefore).isNotEqualTo(sizeAfter);
		assertThat(sizeAfter).isGreaterThan(0);
		
		// check capitalizedNames according to requirements
		assertCaptalizedNames(employeesFromDB);
	}

	public void assertCaptalizedNames(List<Employee> employeesFromDB) {
		employeesFromDB.forEach( emp -> {
			
			String firstname = emp.getFirstName();
			String lastNname = emp.getLastName();
			
			assertThat(firstname).isEqualTo(StringUtils.capitalize(firstname));
			assertThat(lastNname).isEqualTo(StringUtils.capitalize(lastNname));
			
		});
	}

	@Test
	public void calling3Times_theLastOneWithDiffResponse() throws JsonProcessingException {

		// first-time => database empty (0 elements)
		//     must insert all elements from response (50 elements)
		long sizeBefore = employeeService.findAll().size();
		server.expect(times(2), requestTo("/employees")).andExpect(method(HttpMethod.GET))
			.andRespond(withSuccess(restResponse1, MediaType.APPLICATION_JSON));

		employeeService.consumeAndPersist();
		long sizeAfter = employeeService.findAll().size();
		
		assertThat(sizeBefore).isNotEqualTo(sizeAfter);
		assertThat(sizeBefore).isEqualTo(0);
		assertThat(sizeAfter).isEqualTo(50);

		// second-time => database with elements (50 elements)
		//     must insert ignore response, because is the same (50 elements)
		sizeBefore = sizeAfter;
		employeeService.consumeAndPersist();
		sizeAfter = employeeService.findAll().size();
		
		server.verify();
		assertThat(sizeBefore).isEqualTo(sizeAfter);
		assertThat(sizeAfter).isEqualTo(50);
		
		server.reset();

		// third-time database with elements (50 elements)
		// 		must delete previous data and persist new data from different response (48 elements)

		sizeBefore = employeeService.findAll().size();
		server.expect(once(), requestTo("/employees")).andExpect(method(HttpMethod.GET))
			.andRespond(withSuccess(restResponse2, MediaType.APPLICATION_JSON));
		
		employeeService.consumeAndPersist();
		List<Employee> employeesFromDB = employeeService.findAll();
		sizeAfter = employeesFromDB.size();
		
		server.verify();
		assertThat(sizeBefore).isNotEqualTo(sizeAfter);
		assertThat(sizeAfter).isEqualTo(48);
		
		// check capitalizedNames according to requirements
		assertCaptalizedNames(employeesFromDB);
		
	}

}
