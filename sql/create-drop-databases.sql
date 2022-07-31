drop table if exists orders;
drop table if exists products;
drop table if exists users;
drop table if exists orders_products;
drop table if exists users_orders;


create table products (
                          id INT PRIMARY KEY auto_increment,
                          name varchar(255),
                          price double precision (8, 2),
                          date datetime,
                          color varchar(255),
                          total_amount INT
);


create table users_orders (
      user_id INT REFERENCES users(id) on delete cascade,
      order_id INT REFERENCES orders(id) on delete cascade,
      UNIQUE (user_id, order_id)
);


create table users (
                       id INT PRIMARY KEY auto_increment,
                       email varchar(255),
                       first_name varchar(255),
                       last_name varchar(255),
                       password varchar(255),
                       role varchar(255),
                       blocked boolean DEFAULT true
);

create table orders (
                        id INT PRIMARY KEY auto_increment,
                        date datetime,
                        status varchar(255),
                        user_id INT REFERENCES users(id) on delete cascade
);