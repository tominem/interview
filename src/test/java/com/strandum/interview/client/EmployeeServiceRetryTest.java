//package com.strandum.interview.client;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.strandum.interview.config.RetryConfigTest;
//import com.strandum.interview.service.EmployeeService;
//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes=RetryConfigTest.class)
//public class EmployeeServiceRetryTest {
//
//	@Autowired
//    private EmployeeService employeeService;
//
//    @Test
//    public void testRetry3Times() throws Exception {
//        employeeService.consumeAndPersist();
//        verify(employeeService, times(3)).consumeAndPersist();
//    }
//
//}
