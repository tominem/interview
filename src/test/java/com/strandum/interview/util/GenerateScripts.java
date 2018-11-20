package com.strandum.interview.util;

public class GenerateScripts {

	public static void main(String[] args) {
		
		String format = null;
		
		for (int i = 0; i < 50; i++) {
			format = String.format("INSERT INTO Salary(id, salary, hourly_rate) VALUES (%d, '%s', '%s');", i, "10"+i, "50"+i);
			System.out.println(format);
		}	

		System.out.println();

		for (int i = 0; i < 50; i++) {
			format = String.format("INSERT INTO JobInfo(id, contract_type, job_title) VALUES (%d, '%s', '%s');", i, "contract"+i, "Admin"+i, i);
			System.out.println(format);
		}

		System.out.println();
		
		for (int i = 0; i < 50; i++) {
			format = String.format("INSERT INTO Employee(id, staff_number, first_name, last_name, id_Salary, id_JobInfo) VALUES (%d, %d, '%s', '%s', %d, %d);", 
					i, Integer.valueOf("1"+i),"tom"+i, "costa"+i, i, i);
			
			System.out.println(format);
		}	
		
	}
	
}
