package com.strandum.interview.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.strandum.interview.domain.Employee;
import com.strandum.interview.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Component
@Scope("request")
@Qualifier("employeeBean")
@Slf4j
public class EmployeeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3041976312851721575L;

	@Autowired
	private EmployeeService employeeService;

	public List<Employee> getEmployees() {
		return employeeService.findAll();
	}
	
	public void syncAction(ActionEvent actionEvent) {
		try {
			employeeService.consumeAndPersist();
			getEmployees();
			
			addMessage("Employees synchronized");
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			addErrorMessage(e.getMessage());
		}
	}

	private void addMessage(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	private void addErrorMessage(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
	}
		
}
