create table User
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   name varchar(150),
   user_name varchar (255), 
   password varchar (255), 
   birth_date DATE,
   title varchar(50),
   mail varchar(60),
   phone varchar(20),
   role varchar(255) 
);