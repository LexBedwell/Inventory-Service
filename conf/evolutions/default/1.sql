# --- !Ups

create table product_inventory (
  id                bigint not null,
  qty               bigint not null,
  constraint pk_product_inventory primary key (id))
;


# --- !Downs

drop table if exists product_inventory;
