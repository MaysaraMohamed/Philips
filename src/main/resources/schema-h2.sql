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
   address varchar(255), 
   profile_image TEXT
);


create table Location
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   latitude double, 
   longitude double, 
   user_id int, 
   FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);