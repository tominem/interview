package com.strandum.interview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.strandum.interview.config.PersistenceJPAConfig;
import com.strandum.interview.config.RestConfig;

/**
 * @author interview
 *
 *         Class with Main method in order to start the spring context and calls
 *         a run method on the InterviewMain class.
 *
 *         JPA Persistence is configured here.
 */
public class App {
	static Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(PersistenceJPAConfig.class, RestConfig.class);
		InterviewMain main = ctx.getBean(InterviewMain.class);
		main.run();
	}
}
