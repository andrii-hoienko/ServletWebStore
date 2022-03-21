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


# create table users_orders (
#       user_id INT REFERENCES users(id) on delete cascade,
#       order_id INT REFERENCES orders(id) on delete cascade,
#       UNIQUE (user_id, order_id)
# );


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

create table orders_products (
        order_id INT REFERENCES orders(id) on delete cascade,
        product_id INT REFERENCES products(id) on delete cascade,
        amount INT,
        UNIQUE (order_id, product_id)
);

insert into users (id, email, first_name, last_name, password, role, blocked)
values
    (default, 'useremail1@gmail.com', 'name1', 'surname1', 'user1', 'USER', false),
    (default, 'useremail2@gmail.com', 'name2', 'surname2', 'user2', 'USER', false),
    (default, 'useremail3@gmail.com', 'name3', 'surname3', 'user3', 'USER', false),
    (default, 'useremail4@gmail.com', 'name4', 'surname4', 'user4', 'USER', false),
    (default, 'useremail4@gmail.com', 'name4', 'surname4', 'user4', 'USER', false),
    (default, 'admin@gmail.com', 'a', 'h', 'admin', 'ADMIN', false);

insert into products (id, name, price, date, color, total_amount)
values
    (default, 'prod1', 31, CURRENT_TIMESTAMP, 'red', 100),
    (default, 'prod2', 43, CURRENT_TIMESTAMP, 'black', 150),
    (default, 'prod3', 26, CURRENT_TIMESTAMP, 'green', 200),
    (default, 'prod4', 38, CURRENT_TIMESTAMP, 'yellow', 310),
    (default, 'prod5', 12, CURRENT_TIMESTAMP, 'pink', 90);

insert into orders (id, date, status)
values
    (default, CURRENT_TIMESTAMP, 'PAID'),
    (default, CURRENT_TIMESTAMP, 'CANCELED'),
    (default, CURRENT_TIMESTAMP, 'REGISTRATIONS');

insert into orders_products (order_id, product_id, amount)
VALUES
    (1, 1, 3),
    (1, 2, 1),
    (1, 3, 2),
    (2, 4, 2),
    (2, 1, 1),
    (2, 2, 4),
    (3, 4, 8),
    (3, 1, 2);