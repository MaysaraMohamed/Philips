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
   total_points double, 
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
   submission_date DATE, 
   user_name varchar(50),
   invoice_points double, 
   extras TEXT, 
   FOREIGN KEY (user_name) REFERENCES users(user_name) ON DELETE CASCADE ON UPDATE CASCADE
);

create table category
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   category_name varchar(50),
   ar_category_name varchar(50),
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

create table sub_category
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   sub_category_name varchar(50),
   ar_sub_category_name varchar(50),
   category_id int, 
   extras TEXT,
   FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
);


create table points_history
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   points double, 
   used_points double, 
   user_name varchar(50),
   points_date DATE,
   extras TEXT,
   FOREIGN KEY (user_name) REFERENCES users(user_name) ON DELETE CASCADE ON UPDATE CASCADE
);


create table gifts
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   points double, 
   gift_options TEXT, 
   ar_gift_options TEXT,
   user_type varchar(20), 
   extras TEXT,
);

create table points_mapping
(
   id integer PRIMARY KEY AUTO_INCREMENT,
   net_sale integer,
   points integer
);