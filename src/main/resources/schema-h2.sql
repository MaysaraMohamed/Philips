create table users
(
   --id integer PRIMARY KEY AUTO_INCREMENT,
   name varchar(150),
   user_name varchar (50) PRIMARY KEY, 
   password varchar (50), 
   birth_date DATE,
   title varchar(50),
   mail varchar(60),
   phone varchar(20),
   address varchar(255), 
   user_type varchar(20), 
   profile_image TEXT, 
   extras TEXT
);


create table location
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   latitude double, 
   longitude double, 
   user_name varchar(50),
   extras TEXT, 
   FOREIGN KEY (user_name) REFERENCES users(user_name) ON DELETE CASCADE ON UPDATE CASCADE
);

create table philips_invoice
(
   --id integer PRIMARY KEY AUTO_INCREMENT,
   sales_id varchar(50) PRIMARY KEY, 
   invoice_date DATE, 
   user_name varchar(50), 
   extras TEXT, 
   FOREIGN KEY (user_name) REFERENCES users(user_name) ON DELETE CASCADE ON UPDATE CASCADE
);

create table submited_invoice
(
   --id integer PRIMARY KEY AUTO_INCREMENT,
   sales_id varchar(50) PRIMARY KEY, 
   status varchar(50), 
   invoice_date DATE, 
   user_name varchar(50),
   extras TEXT, 
   FOREIGN KEY (user_name) REFERENCES users(user_name) ON DELETE CASCADE ON UPDATE CASCADE
);

create table category
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   category_name varchar(50),
   extras TEXT, 
);

create table submited_invoice_categories
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   sales_id varchar(50),
   category_id int, 
   net_sale double, 
   extras TEXT, 
   --PRIMARY KEY (sales_id, category_id), 
   FOREIGN KEY (sales_id) REFERENCES submited_invoice(sales_id) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
);


create table philips_invoice_categories
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   sales_id varchar(50),
   category_id int, 
   net_sale double, 
   extras TEXT, 
   --PRIMARY KEY (sales_id, category_id), 
   FOREIGN KEY (sales_id) REFERENCES philips_invoice(sales_id) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
);
