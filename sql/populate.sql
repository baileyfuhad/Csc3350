USE joshi_data;

INSERT INTO employees (empid, Fname, Lname, email, HireDate, Salary, SSN, addressID) VALUES 
(1,'Snoopy', 'Beagle', 'Snoopy@example.com', '2022-08-01', 45000.00, '111-11-1111', 1),
(2,'Charlie', 'Brown', 'Charlie@example.com', '2022-07-01', 48000.00, '111-22-1111', 1),
(11,'Bugs', 'Bunny', 'Bugs@example.com', '1934-07-01', 18000.00, '222-11-1111', 5);

INSERT INTO job_titles (job_title_id, job_title) VALUES 
(100,'software manager'), (900,'Chief Exec. Officer'), (902,'Chief Info. Officer');

INSERT INTO division (div_ID, Name, city, addressLine1, addressLine2, state, country, postalCode) 
VALUES(1,'Technology Engineering', 'Atlanta', '200 17th Street NW', '', 'GA', 'USA', '30363'),
      (999,'HQ','New York', '45 West 57th Street', '', 'NY', 'USA', '00034');

INSERT INTO employee_division (empid, div_ID) VALUES(1,999), (2,999), (11,1);