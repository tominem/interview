CREATE DATABASE interview;

CREATE TABLE IF NOT EXISTS Employee (
	id BIGINT NOT NULL,
    staff_number VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    id_Salary BIGINT,
    id_JobInfo BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_Salary) REFERENCES Salary(id),
    FOREIGN KEY (id_JobInfo) REFERENCES JobInfo(id)
);

CREATE TABLE IF NOT EXISTS JobInfo (
    id BIGINT NOT NULL,
    contract_type VARCHAR(255),
    job_title VARCHAR(255),
	id_Employee BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Salary (
    id BIGINT NOT NULL,
    salary DECIMAL(10,2),
    hourly_rate VARCHAR(255),
    id_Employee BIGINT,
    PRIMARY KEY (id)
);