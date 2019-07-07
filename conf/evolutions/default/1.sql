# --- !Ups

create table product_inventory (
  id                bigint not null,
  qty               bigint,
  constraint pk_product_inventory primary key (id))
;


# --- !Downs

drop table if exists product_inventory;
