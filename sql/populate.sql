USE employee_management;

INSERT INTO states (state_abbrev) VALUES
('AL'),('AK'),('AZ'),('AR'),('CA'),('CO'),('CT'),('DE'),('FL'),('GA'),
('HI'),('ID'),('IL'),('IN'),('IA'),('KS'),('KY'),('LA'),('ME'),('MD'),
('MA'),('MI'),('MN'),('MS'),('MO'),('MT'),('NE'),('NV'),('NH'),('NJ'),
('NM'),('NY'),('NC'),('ND'),('OH'),('OK'),('OR'),('PA'),('RI'),('SC'),
('SD'),('TN'),('TX'),('UT'),('VT'),('VA'),('WA'),('WV'),('WI'),('WY');

INSERT INTO cities (city_name) VALUES
('Atlanta'),('New York'),('Los Angeles'),('Chicago'),('Houston'),('Boston');

INSERT INTO addresses (street, cityID, stateID, zip, DOB, phone, emergency_name, emergency_phone) VALUES
('200 17th Street NW',  1, 10, '30363', '1985-04-12', '404-555-0101', 'Pat Doe',   '404-555-0102'),
('45 West 57th Street', 2, 32, '10019', '1990-09-22', '212-555-0201', 'Sam Doe',   '212-555-0202'),
('1 Peachtree St',      1, 10, '30303', '1978-01-30', '404-555-0301', 'Alex Doe',  '404-555-0302');

INSERT INTO division (divID, division_name, city, addressLine1, state, country, postalCode) VALUES
(1,   'Technology Engineering', 'Atlanta',  '200 17th Street NW',  'GA', 'USA', '30363'),
(2,   'Human Resources',        'Atlanta',  '200 17th Street NW',  'GA', 'USA', '30363'),
(999, 'Headquarters',           'New York', '45 West 57th Street', 'NY', 'USA', '10019');

INSERT INTO job_titles (job_titleID, job_title) VALUES
(100, 'Software Engineer'),
(200, 'HR Administrator'),
(900, 'Chief Executive Officer'),
(902, 'Chief Information Officer');

INSERT INTO employees (empID, first_name, last_name, email, hire_date, salary, SSN, addressID) VALUES
(1001, 'Avery',   'Admin',    'avery.admin@companyz.com',    '2020-01-15', 95000.00, '111-11-1111', 1),
(1002, 'Eli',     'Employee', 'eli.employee@companyz.com',   '2022-07-01', 58000.00, '222-22-2222', 2),
(1003, 'Jordan',  'Lee',      'jordan.lee@companyz.com',     '2024-03-12', 72000.00, '333-33-3333', 3),
(1004, 'Morgan',  'Patel',    'morgan.patel@companyz.com',   '2025-11-04', 64000.00, '444-44-4444', 1);

INSERT INTO employee_division (empID, divID) VALUES
(1001, 2), (1002, 1), (1003, 1), (1004, 999);

INSERT INTO employee_job_titles (empID, job_titleID) VALUES
(1001, 200), (1002, 100), (1003, 100), (1004, 902);

INSERT INTO payroll (empID, pay_date, gross_pay, fed_tax, fed_med, fed_SS, state_tax, retire_401k, health_care, net_pay) VALUES
(1002, '2026-01-31', 4833.33, 700.00, 70.00, 300.00, 200.00, 240.00, 150.00, 3173.33),
(1002, '2026-02-28', 4833.33, 700.00, 70.00, 300.00, 200.00, 240.00, 150.00, 3173.33),
(1002, '2026-03-31', 4833.33, 700.00, 70.00, 300.00, 200.00, 240.00, 150.00, 3173.33),
(1003, '2026-01-31', 6000.00, 900.00, 87.00, 372.00, 250.00, 300.00, 150.00, 3941.00),
(1003, '2026-02-28', 6000.00, 900.00, 87.00, 372.00, 250.00, 300.00, 150.00, 3941.00),
(1003, '2026-03-31', 6000.00, 900.00, 87.00, 372.00, 250.00, 300.00, 150.00, 3941.00),
(1004, '2026-03-31', 5333.33, 800.00, 77.00, 330.00, 220.00, 266.00, 150.00, 3490.33);

INSERT INTO app_user (empID, username, password_hash, role) VALUES
(1001, 'admin',    'admin123', 'HR_ADMIN'),
(1002, 'employee', 'user123',  'EMPLOYEE');
