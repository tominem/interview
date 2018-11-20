package com.strandum.interview.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@Entity
@Table(name="Salary")
public class Salary {
	
	@Id
	private Long id;
	private BigDecimal salary;
	@Column(name="hourly_rate")
	private BigDecimal hourlyRate;
	
	@OneToMany(mappedBy="salary")
	private List<Employee> employees;
	
	Salary(){
	}

	public Salary(BigDecimal salary, BigDecimal hourlyRate) {
		this.salary = salary;
		this.hourlyRate = hourlyRate;
	}
	
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
		result = prime * result + ((hourlyRate == null) ? 0 : hourlyRate.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
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
		Salary other = (Salary) obj;
		if (hourlyRate == null) {
			if (other.hourlyRate != null)
				return false;
		} else if (hourlyRate.compareTo(other.hourlyRate) != 0)
			return false;	
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (salary.compareTo(other.salary) != 0)
			return false;
		return true;
	}
	
}
