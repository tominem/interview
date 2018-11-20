package com.strandum.interview.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name="JobInfo")
public class JobInfo {
	
	@Id
	private Long id;
	@Column(name="contract_type")
	private String contractType;
	@Column(name="job_title")
	private String jobTitle;
	
	@OneToMany(mappedBy="jobinfo")
	private List<Employee> employees;
	
	public JobInfo() {}
	
	public void addEmployee(Employee employee) {
		if (this.employees == null) {
			this.employees = new ArrayList<>();
		}
		this.employees.add(employee);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contractType == null) ? 0 : contractType.hashCode());
		result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
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
		JobInfo other = (JobInfo) obj;
		if (contractType == null) {
			if (other.contractType != null)
				return false;
		} else if (contractType.compareTo(other.contractType) != 0)
			return false;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (jobTitle.compareTo(other.jobTitle) != 0)
			return false;
		return true;
	}
	
}
