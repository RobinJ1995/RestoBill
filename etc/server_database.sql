create table `table` (id integer, name varchar);
create table `product` (id integer, name varchar, price decimal(8,2), description varchar, available tinyint(1));
create table `order` (id integer, bill_id integer, product_id integer, amount integer);
create table `bill` (id integer, table_id integer, closed tinyint(1));
