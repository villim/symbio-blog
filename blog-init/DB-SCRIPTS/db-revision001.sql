create table USER (ID long, NAME varchar2, EMAIL varchar2, PASSWORD varchar2, ROLES varchar2, MODIFIED_DATE date );
create table POST (ID long, USER_ID long, TITLE varchar2, BODY varchar2, MODIFIED_DATE date);

CREATE SEQUENCE SEQ_USER;
CREATE SEQUENCE SEQ_POST;

insert into USER values (SEQ_USER.nextval, 'admin', '','admin','ROLE_ADMIN', now());
insert into USER values (SEQ_USER.nextval, 'anonymous', '','anonymous','ROLE_ANONYMOUS', now());

commit;