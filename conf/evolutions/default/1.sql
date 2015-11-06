# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ANUNCIANTE (
  id                        integer not null,
  cidade                    varchar(255),
  bairro                    varchar(255),
  ocasional                 boolean,
  constraint pk_ANUNCIANTE primary key (id))
;

create table ANUNCIO (
  id                        integer not null,
  descricao                 varchar(255),
  titulo                    varchar(255),
  criador                   integer,
  data                      timestamp,
  codigo                    varchar(255),
  constraint pk_ANUNCIO primary key (id))
;

create table CONVERSA (
  id                        integer not null,
  conversas                 integer not null,
  pergunta                  varchar(255),
  resposta                  varchar(255),
  constraint pk_CONVERSA primary key (id))
;

create sequence ANUNCIANTE_seq;

create sequence ANUNCIO_seq;

create sequence CONVERSA_seq;

alter table ANUNCIO add constraint fk_ANUNCIO_criador_1 foreign key (criador) references ANUNCIANTE (id) on delete restrict on update restrict;
create index ix_ANUNCIO_criador_1 on ANUNCIO (criador);
alter table CONVERSA add constraint fk_CONVERSA_ANUNCIO_2 foreign key (conversas) references ANUNCIO (id) on delete restrict on update restrict;
create index ix_CONVERSA_ANUNCIO_2 on CONVERSA (conversas);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists ANUNCIANTE;

drop table if exists ANUNCIO;

drop table if exists CONVERSA;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists ANUNCIANTE_seq;

drop sequence if exists ANUNCIO_seq;

drop sequence if exists CONVERSA_seq;

