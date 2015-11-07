create table `table` (name varchar, syncId integer);
create table `product` (name varchar, price decimal(8,2), description varchar, available tinyint(1), syncId integer);
create table `order` (bill_entity integer, product_entity integer, amount integer, syncId integer);
create table `bill` (table_entity integer, closed tinyint(1), syncId integer);
