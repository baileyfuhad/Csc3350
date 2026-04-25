-- Company Z employee management schema
DROP DATABASE IF EXISTS employee_management;
CREATE DATABASE employee_management;
USE employee_management;

CREATE TABLE states (
    stateID INT NOT NULL AUTO_INCREMENT,
    state_abbrev CHAR(2) NOT NULL,
    PRIMARY KEY (stateID),
    UNIQUE KEY uq_state_abbrev (state_abbrev)
) ENGINE=InnoDB;

CREATE TABLE cities (
    cityID INT NOT NULL AUTO_INCREMENT,
    city_name VARCHAR(25) NOT NULL,
    PRIMARY KEY (cityID)
) ENGINE=InnoDB;

CREATE TABLE addresses (
    addressID INT NOT NULL AUTO_INCREMENT,
    street VARCHAR(100) NOT NULL,
    cityID INT NOT NULL,
    stateID INT NOT NULL,
    zip VARCHAR(10) NOT NULL,
    DOB DATE NULL,
    phone VARCHAR(15) NULL,
    emergency_name VARCHAR(100) NULL,
    emergency_phone VARCHAR(15) NULL,
    PRIMARY KEY (addressID),
    CONSTRAINT fk_address_city  FOREIGN KEY (cityID)  REFERENCES cities(cityID),
    CONSTRAINT fk_address_state FOREIGN KEY (stateID) REFERENCES states(stateID)
) ENGINE=InnoDB;

CREATE TABLE employees (
    empID INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(65) NOT NULL,
    last_name VARCHAR(65) NOT NULL,
    email VARCHAR(65) NULL,
    hire_date DATE NULL,
    salary DECIMAL(10,2) NOT NULL,
    SSN VARCHAR(12) NULL,
    addressID INT NULL,
    PRIMARY KEY (empID),
    CONSTRAINT fk_employee_address FOREIGN KEY (addressID) REFERENCES addresses(addressID)
) ENGINE=InnoDB;

CREATE INDEX idx_employees_last_name ON employees(last_name);
CREATE INDEX idx_employees_hire_date ON employees(hire_date);

CREATE TABLE division (
    divID INT NOT NULL AUTO_INCREMENT,
    division_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NULL,
    addressLine1 VARCHAR(100) NULL,
    addressLine2 VARCHAR(100) NULL,
    state VARCHAR(50) NULL,
    country VARCHAR(50) NULL,
    postalCode VARCHAR(15) NULL,
    PRIMARY KEY (divID)
) ENGINE=InnoDB;

CREATE TABLE job_titles (
    job_titleID INT NOT NULL AUTO_INCREMENT,
    job_title VARCHAR(125) NOT NULL,
    PRIMARY KEY (job_titleID)
) ENGINE=InnoDB;

CREATE TABLE employee_division (
    empID INT NOT NULL,
    divID INT NOT NULL,
    PRIMARY KEY (empID, divID),
    CONSTRAINT fk_emp_div_employee FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE,
    CONSTRAINT fk_emp_div_division FOREIGN KEY (divID) REFERENCES division(divID)
) ENGINE=InnoDB;

CREATE TABLE employee_job_titles (
    empID INT NOT NULL,
    job_titleID INT NOT NULL,
    PRIMARY KEY (empID, job_titleID),
    CONSTRAINT fk_emp_job_employee FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE,
    CONSTRAINT fk_emp_job_title    FOREIGN KEY (job_titleID) REFERENCES job_titles(job_titleID)
) ENGINE=InnoDB;

CREATE TABLE payroll (
    payID INT NOT NULL AUTO_INCREMENT,
    empID INT NOT NULL,
    pay_date DATE NOT NULL,
    gross_pay DECIMAL(10,2) NOT NULL,
    fed_tax DECIMAL(8,2) DEFAULT 0,
    fed_med DECIMAL(8,2) DEFAULT 0,
    fed_SS DECIMAL(8,2) DEFAULT 0,
    state_tax DECIMAL(8,2) DEFAULT 0,
    retire_401k DECIMAL(8,2) DEFAULT 0,
    health_care DECIMAL(8,2) DEFAULT 0,
    net_pay DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (payID),
    CONSTRAINT fk_payroll_employee FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_payroll_pay_date ON payroll(pay_date);
CREATE INDEX idx_payroll_emp ON payroll(empID);

CREATE TABLE app_user (
    empID INT NOT NULL,
    username VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY (empID),
    UNIQUE KEY uq_username (username),
    CONSTRAINT fk_app_user_employee FOREIGN KEY (empID) REFERENCES employees(empID) ON DELETE CASCADE
) ENGINE=InnoDB;
