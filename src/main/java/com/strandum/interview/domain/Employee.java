package com.strandum.interview.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.strandum.interview.client.EmployeeDeserealize;
import com.strandum.interview.client.EmployeeSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@JsonDeserialize(using=EmployeeDeserealize.class)
@JsonSerialize(using=EmployeeSerialize.class)
@Entity
@Table(name="Employee")
public class Employee {
	
	@Id
	private Long   id;
	@Column(name="staff_number")
	private String staffNumber;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_Salary")
	private Salary salary;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_JobInfo")
	private JobInfo jobinfo;
	
	public Employee() {}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((jobinfo == null) ? 0 : jobinfo.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		result = prime * result + ((staffNumber == null) ? 0 : staffNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (jobinfo == null) {
			if (other.jobinfo != null)
				return false;
		} else if (!jobinfo.equals(other.jobinfo))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		if (staffNumber == null) {
			if (other.staffNumber != null)
				return false;
		} else if (!staffNumber.equals(other.staffNumber))
			return false;
		return true;
	}

}
