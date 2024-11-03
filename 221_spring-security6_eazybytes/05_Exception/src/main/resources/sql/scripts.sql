CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

-- test123!
insert into customer(email, pwd, role) values("user@test.com", "$2a$10$tkorgRjoY6XyN8k7uP/thuQWhp2LhNaSvcwbUhrtsiD8Hp3qNAp6q", "read");
insert into customer(email, pwd, role) values("admin@test.com", "$2a$10$tkorgRjoY6XyN8k7uP/thuQWhp2LhNaSvcwbUhrtsiD8Hp3qNAp6q", "admin");

SELECT * FROM `easybytes_spring-security`.customer;