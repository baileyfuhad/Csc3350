-- 1. Create a brand new, unique database
CREATE DATABASE IF NOT EXISTS joshi_data;
USE joshi_data;

SET FOREIGN_KEY_CHECKS=0;

-- 2. Create Tables (Fresh and Clean)
CREATE TABLE `division` (
  `div_ID` int NOT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `city` varchar(50) NOT NULL,
  `addressLine1` varchar(50) NOT NULL,
  `addressLine2` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `country` varchar(50) NOT NULL,
  `postalCode` varchar(15) NOT NULL,
  PRIMARY KEY (`div_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `employees` (
  `empid` int NOT NULL,
  `Fname` varchar(65) NOT NULL,
  `Lname` varchar(65) NOT NULL,
  `email` varchar(65) NOT NULL,
  `HireDate` date DEFAULT NULL,
  `Salary` decimal(10,2) NOT NULL,
  `SSN` varchar(12) DEFAULT NULL,
  `addressID` int NOT NULL,
  PRIMARY KEY (`empid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `job_titles` (
  `job_title_id` int NOT NULL,
  `job_title` varchar(125) NOT NULL,
  PRIMARY KEY (`job_title_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `employee_division` (
  `empid` int NOT NULL,
  `div_ID` int NOT NULL,
  PRIMARY KEY (`empid`),
  CONSTRAINT `fk_emp_div_division` FOREIGN KEY (`div_ID`) REFERENCES `division` (`div_ID`),
  CONSTRAINT `fk_emp_div_employee` FOREIGN KEY (`empid`) REFERENCES `employees` (`empid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `employee_job_titles` (
  `empid` int NOT NULL,
  `job_title_id` int NOT NULL,
  CONSTRAINT `fk_emp_job_employee` FOREIGN KEY (`empid`) REFERENCES `employees` (`empid`),
  CONSTRAINT `fk_emp_job_title` FOREIGN KEY (`job_title_id`) REFERENCES `job_titles` (`job_title_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `addresses` (
  `addressID` int NOT NULL AUTO_INCREMENT,
  `street` varchar(100) NOT NULL,
  `cityID` int NOT NULL,
  `StateID` int NOT NULL,
  `zip` varchar(10) NOT NULL,
  `DOB` date DEFAULT NULL,
  `Phone_num` varchar(15) DEFAULT NULL,
  `Emergency_name` varchar(100) DEFAULT NULL,
  `Emergency_phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`addressID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `payroll` (
  `payID` int NOT NULL,
  `pay_date` date DEFAULT NULL,
  `earnings` decimal(8,2) DEFAULT NULL,
  `fed_tax` decimal(7,2) DEFAULT NULL,
  `fed_med` decimal(7,2) DEFAULT NULL,
  `fed_SS` decimal(7,2) DEFAULT NULL,
  `state_tax` decimal(7,2) DEFAULT NULL,
  `retire_401k` decimal(7,2) DEFAULT NULL,
  `health_care` decimal(7,2) DEFAULT NULL,
  `empid` int DEFAULT NULL,
  PRIMARY KEY (`payID`),
  CONSTRAINT `fk_payroll_employee` FOREIGN KEY (`empid`) REFERENCES `employees` (`empid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS=1;