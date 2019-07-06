# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table product_inventory (
  id                bigint not null,
  qty               bigint,
  constraint pk_product_inventory primary key (id))
;



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists product_inventory;

SET REFERENTIAL_INTEGRITY TRUE;
