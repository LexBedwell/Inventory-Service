# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table inventory_service (
  id                        bigint not null,
  qty                bigint,
  constraint pk_inventory_service primary key (id))
;

create sequence inventory_service_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists inventory_service;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists inventory_service_seq;