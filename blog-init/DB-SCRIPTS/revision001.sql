create table USER (ID long, NAME varchar2, EMAIL varchar2, PASSWORD varchar2, ROLES varchar2, MODIFIED_DATE date );
create table POST (ID long, TITLE varchar2, BODY blob, MODIFIED_DATE date);

CREATE SEQUENCE SEQ_USER;
CREATE SEQUENCE SEQ_POST;

insert into USER values (1, 'admin', '','admin','ROLE_ADMIN', now());
insert into USER values (1, 'anonymous', '','anonymous','ROLE_ANONYMOUS', now());

commit;