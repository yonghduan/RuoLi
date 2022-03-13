
drop table if exists record_login_info
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