DROP TABLE IF EXISTS Salary;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS JobInfo;

CREATE TABLE Salary (
    id NUMERIC IDENTITY PRIMARY KEY,
    salary DECIMAL(10,2),
    hourly_rate VARCHAR(255)
);

CREATE TABLE JobInfo (
    id NUMERIC IDENTITY PRIMARY KEY,
    contract_type VARCHAR(255),
    job_title VARCHAR(255)
);

CREATE TABLE Employee(
    id NUMERIC IDENTITY PRIMARY KEY, 
	staff_number VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    id_JobInfo NUMERIC,
    id_Salary NUMERIC,
  	FOREIGN KEY (id_JobInfo) references JobInfo(id),
  	FOREIGN KEY (id_Salary) references Salary(id)
);

