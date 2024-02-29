CREATE DATABASE repair_system1;

CREATE TABLE Customer(
	Cust_ID VARCHAR(6) NOT NULL,
    Title VARCHAR(10),
    FirstName VARCHAR(30),
    LastName VARCHAR(30),
    HouseNo VARCHAR(10),
    Road VARCHAR(30),
    City VARCHAR(30),
    Cust_regDate DATE,
	PRIMARY KEY (Cust_ID)
);
/*DROP TABLE customer;
DROP TABLE Customer_TelNO;
DROP TABLE Add_New_Repair;
DROP TABLE add_new_emp;
DROP tABLE EMP_TelNO;
DROP TABLE Repair_DropOff;
DROP TABLE Repair_Cost; */

CREATE TABLE Customer_TelNO(
	Cust_ID  VARCHAR(6) NOT NULL,
    Tel_NO VARCHAR(50),
    PRIMARY KEY(Cust_ID,Tel_NO),
    FOREIGN KEY(Cust_ID) REFERENCES Customer(Cust_ID)
);

CREATE TABLE Add_New_EMP(
	EMP_ID VARCHAR(4) NOT NULL,
    EMP_Title  VARCHAR(10),
    EMP_FirstName VARCHAR(30),
    EMP_LastName VARCHAR(30),
    Address VARCHAR(100),
    DOB DATE,
    PRIMARY KEY(EMP_ID)
);

CREATE TABLE EMP_TelNO(
	EMP_ID VARCHAR(4),
    E_Tel_NO VARCHAR(50),
    PRIMARY KEY(EMP_ID,E_Tel_NO),
    FOREIGN KEY(EMP_ID) REFERENCES Add_New_EMP(EMP_ID)
);

CREATE TABLE Add_New_Repair(
	Job_NO VARCHAR(6) NOT NULL,
    Brand VARCHAR(15),
    Domain VARCHAR(15),
    Cust_ID VARCHAR(6),
    EMP_ID VARCHAR(4),
    Intake_Date DATE,
    Status_R VARCHAR (10) NOT NULL,
    PRIMARY KEY(Job_NO),
    FOREIGN KEY(Cust_ID) REFERENCES Customer(Cust_ID),
    FOREIGN KEY(EMP_ID) REFERENCES Add_New_EMP(EMP_ID)
);

CREATE TABLE Repair_DropOff(
  Job_NO VARCHAR(6) NOT NULL PRIMARY KEY,
  Drop_Off_Date Date,
  FOREIGN KEY (Job_NO) REFERENCES Add_New_Repair(Job_NO) 
  );
  
  CREATE TABLE Repair_Cost(
      Job_NO VARCHAR(6) NOT NULL PRIMARY KEY,
      Cost DECIMAL (10,2),
      FOREIGN KEY (Job_NO) REFERENCES Add_New_Repair(Job_NO) 
  );
  

INSERT INTO Customer
VALUES ('B1006','Mr.' , 'Rajitha' , 'Silva' , '253' , 'St.Mary Lane' , 'Mahara','20240228'),
('B1007' , 'Mr.' , 'Shaan' , 'Perera' , '20' , 'Second Cross St' , 'Pettah','20230621' );

INSERT INTO Customer_TelNO
VALUES ('B1006' , '0714589634,0714568755,0772145876'),
('B1007' , '077451236,0778951240');

INSERT INTO Add_New_EMP
VALUES ('A100' ,'Mr.', 'Sujith',  'Perera' , 'No.5, Samagi mawatha, Kandy' , '19890225'),
('A101' ,'Mr.',  'Nuwan' ,  'Pathum', 'No.45/b, Kotahena', '19900420');

INSERT INTO EMP_TelNO
VALUES ('A100', '077895421,0741258584'),
('A101' , '0745682427,0772457841');

INSERT INTO Add_New_Repair
VALUES ('AB105', 'Samsung', 'TV','B1006','A100', '20240220','Pending'),
('AC102', 'LG', 'AC', 'B1007', 'A101', '20240125','Pending');

INSERT INTO  Repair_Cost
VALUES ('AB105', 10000.50),
('AC102',5000.25);

/* Employee Performance*/
SELECT 
    e.EMP_ID AS Employee_ID,
    CONCAT(e.EMP_FirstName, ' ', e.EMP_LastName) AS Employee_Name,
    r.Job_NO AS Job_ID,
    rc.Cost
FROM 
    Add_New_EMP e
JOIN 
    Add_New_Repair r ON e.EMP_ID = r.EMP_ID
JOIN 
    Repair_Cost rc ON r.Job_NO = rc.Job_NO;


/* Repair History */    
SELECT
    CONCAT(e.EMP_FirstName, ' ', e.EMP_LastName) AS Employee_Name,
    rc.Cost,
    r.Intake_Date
FROM
    Add_New_Repair r
JOIN
    Add_New_EMP e ON r.EMP_ID = e.EMP_ID
JOIN
    Repair_Cost rc ON r.Job_NO = rc.Job_NO
WHERE
    r.Job_NO = 'AB105';
 
 
/* Billing  */ 
SELECT
    r.Job_NO,
    r.Domain,
    r.Brand,
    rc.Cost
FROM
    Add_New_Repair r
JOIN
    Repair_Cost rc ON r.Job_NO = rc.Job_NO
WHERE
    r.Job_NO = 'AB105';


SELECT * FROM customer;
SELECT * FROM emp_telno;

Drop Database repair_system;