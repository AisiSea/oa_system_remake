USE oa_system_remake;

DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS id;
DROP TABLE IF EXISTS `position`;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS dept_head;
DROP TABLE IF EXISTS account;

CREATE TABLE department(
	dept_id 	INT 	PRIMARY KEY 	AUTO_INCREMENT 	COMMENT '部门ID',
	dept_name 	VARCHAR(63) 	NOT NULL	COMMENT '部门名称',
	dept_parent 	INT		COMMENT '父级部门ID',
	dept_phone 	VARCHAR(31)	COMMENT '部门电话',
	dept_local 	VARCHAR(255)	COMMENT '部门地址',
	dept_remarks 	VARCHAR(255)	COMMENT '部门备注'
	#FOREIGN KEY (dept_parent) REFERENCES department (dept_id) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE department AUTO_INCREMENT = 1001;

CREATE TABLE id(
	id_id 		INT 		PRIMARY KEY 	AUTO_INCREMENT 	COMMENT '证件类型ID',
	id_type 	VARCHAR(31) 	NOT NULL 	COMMENT '证件类型名称',
	id_length 	INT 	NOT NULL 	COMMENT '证件号码长度'
);

ALTER TABLE id AUTO_INCREMENT = 101;

CREATE TABLE `position`(
	post_id 	INT 	PRIMARY KEY 	AUTO_INCREMENT 	COMMENT '职位ID',
	post_name 	VARCHAR(63) 	NOT NULL 	COMMENT '职位名称'
);

ALTER TABLE `position` AUTO_INCREMENT = 1001;

CREATE TABLE employee(
	emp_id		INT 		PRIMARY KEY 	AUTO_INCREMENT 	COMMENT '员工ID',
	emp_name	VARCHAR(31)	NOT NULL 	COMMENT '员工姓名',
	emp_sex		VARCHAR(1)	NOT NULL 	COMMENT '员工性别',
	emp_bir        	TIMESTAMP 	NOT NULL 	COMMENT '员工生日',
	id_id 		INT 	 	NOT NULL 	COMMENT '证件类型',
	id_num 		VARCHAR(63) 	NOT NULL 	COMMENT '证件号码',
	emp_phone	VARCHAR(15)	NOT NULL 	COMMENT '员工电话',
	emp_email	VARCHAR(63) 	COMMENT '员工邮箱',
	dept_id		INT 	 	COMMENT '所属部门',
	post_id		INT 		COMMENT '员工职位',
	emp_remarks 	VARCHAR(255) 	COMMENT '员工备注'
	#,FOREIGN KEY (dept_id) REFERENCES department (dept_id) ON DELETE CASCADE ON UPDATE CASCADE,
	#FOREIGN KEY (post_id) REFERENCES `position` (post_id) ON DELETE CASCADE ON UPDATE CASCADE,
	#FOREIGN KEY (id_id) REFERENCES id (id_id) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE employee AUTO_INCREMENT = 100001;

CREATE TABLE dept_head(
	dept_head_id 	INT 	PRIMARY KEY 	AUTO_INCREMENT 	COMMENT '负责ID',
	dept_id 	INT 	NOT NULL 	COMMENT '部门ID',
	emp_id 		INT 	NOT NULL 	COMMENT '员工ID'
	#,FOREIGN KEY (dept_id) REFERENCES department (dept_id) ON DELETE CASCADE ON UPDATE CASCADE,
	#FOREIGN KEY (emp_id) REFERENCES employee (emp_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE account (
	emp_id  	INT 	 	NOT NULL,
	ac_username 	VARCHAR(15) 	PRIMARY KEY,
	ac_password 	VARCHAR(15) 	NOT NULL,
	ac_authority 	VARCHAR(255) 	NOT NULL,
	ac_remarks 	VARCHAR(255)
	#,FOREIGN KEY (emp_id) REFERENCES employee (emp_id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO department(dept_id, dept_name, dept_phone, dept_parent, dept_local, dept_remarks) VALUES(1000, "无", NULL, NULL, NULL, NULL);
INSERT INTO department(dept_name, dept_phone, dept_parent, dept_local, dept_remarks) VALUES
("广州开发部", "30624700", 1000, "广东省广州市番禺区长洲大桥底", NULL),
("广州测试部", "30624700", 1000, "广东省广州市番禺区长洲大桥上", NULL),
("广州研发部", "30624700", 1000, "广东省广州市番禺区长洲大桥墩", NULL),
("广州人力部", "30624700", 1000, "广东省广州市番禺区长洲大桥水底", NULL),
("广州董事会", "30624700", 1000, "广东省广州市番禺区长洲大桥路灯", NULL),
("广州保卫部", "30624700", 1000, "广东省广州市番禺区长洲大桥小船", NULL),
("广州财务部", "30624700", 1000, "广东省广州市番禺区长洲大桥地洞", NULL),
("广州后勤部", "30624700", 1000, "广东省广州市番禺区长洲大桥涵洞", NULL),
("广州法务部", "30624700", 1000, "广东省广州市番禺区长洲大桥横梁", NULL),
("广州安全部", "30624700", 1000, "广东省广州市番禺区长洲大桥牌坊", NULL),
("深圳开发部", "59420235", 1001, "广东省深圳市南山区南山立交底", NULL),
("深圳测试部", "59420235", 1000, "广东省深圳市南山区南山立交上", NULL),
("深圳研发部", "59420235", 1000, "广东省深圳市南山区南山立交墩", NULL),
("深圳人力部", "59420235", 1000, "广东省深圳市南山区南山立交水底", NULL),
("深圳董事会", "59420235", 1000, "广东省深圳市南山区南山立交路灯", NULL),
("深圳保卫部", "59420235", 1000, "广东省深圳市南山区南山立交小船", NULL),
("深圳财务部", "59420235", 1000, "广东省深圳市南山区南山立交地洞", NULL),
("深圳后勤部", "59420235", 1000, "广东省深圳市南山区南山立交涵洞", NULL),
("深圳法务部", "59420235", 1000, "广东省深圳市南山区南山立交横梁", NULL),
("深圳安全部", "59420235", 1000, "广东省深圳市南山区南山立交牌坊", NULL);

INSERT INTO id(id_type, id_length) VALUES("中华人民共和国居民身份证", 18);

INSERT INTO employee(emp_name, emp_sex, emp_bir, id_id, id_num, emp_phone, emp_email, dept_id, post_id, emp_remarks)
 VALUES("卷心菜", "男", "19870123", 101, "112223444455667788", "12334566789", NULL, 1001, 1001, NULL);
 
INSERT INTO account(emp_id, ac_username, ac_password, ac_authority, ac_remarks) VALUES(100001, "admin", "admin", "111111111", "系统管理员");

SELECT * FROM department;

SELECT dept_id, dept_name, dept_phone, dept_parent, dept_local, dept_remarks FROM department LIMIT 3, 3;

SELECT dept_id, dept_name, dept_phone, dept_parent, dept_local, dept_remarks FROM department WHERE dept_local LIKE '%深圳%';

SELECT d1.dept_id, d1.dept_name, d1.dept_phone, d1.dept_local, d1.dept_remarks, d2.dept_name AS dept_parent
FROM department AS d1 INNER JOIN department AS d2
ON d1.dept_parent = d2.dept_id;

UPDATE department SET dept_name = "无" WHERE dept_id = 1000;


