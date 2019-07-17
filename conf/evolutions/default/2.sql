# --- !Ups

insert into product_inventory (id, qty) values (1, 11);
insert into product_inventory (id, qty) values (2, 22);
insert into product_inventory (id, qty) values (3, 33);
insert into product_inventory (id, qty) values (4, 44);
insert into product_inventory (id, qty) values (5, 55);
insert into product_inventory (id, qty) values (6, 0);


# --- !Downs

delete from product_inventory where id = 1;
delete from product_inventory where id = 2;
delete from product_inventory where id = 3;
delete from product_inventory where id = 4;
delete from product_inventory where id = 5;
delete from product_inventory where id = 6;
