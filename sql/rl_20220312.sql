
drop table if exists record_login_info;
create table record_login_info(
    id  bigint not null auto_increment,
    username varchar(50) default '',
    ip_address varchar (128) default '',
    ip_location varchar (255) default '',
    operate_system varchar (50) default '',
    browser varchar (50) default '',
    status char(1) default '0',
    msg varchar (255) default '',
    login_time datetime ,
    primary key(id)
)engine=innodb auto_increment=100

drop table if exists system_user;
create table system_user (
    user_id bigint not null auto_increment,
    username varchar (50) default '',
    password varchar (50) default '',
    is_expired char(1) default '0',
    is_locked char(1) default '0',
    is_credential_expired char (1) default '0',
)

