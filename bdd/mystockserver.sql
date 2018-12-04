/* sécurisation du serveur mystocks */  



 drop table mystockserver.server_user;
 drop table mystockserver.application;
 drop table mystockserver.application_user; 
 drop table mystockserver.session;
 
 use mystockserver;
  
CREATE TABLE mystockserver.server_user (
   id INT UNSIGNED NOT NULL auto_increment,
   login VARCHAR(50) NOT NULL,
   password varchar(150) not null,
   role enum('superadmin','admin','user','readonlyuser') not null,
   PRIMARY KEY(id),
   UNIQUE(login)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

  CREATE TABLE mystockserver.application (
   id INT UNSIGNED NOT NULL auto_increment,
   name VARCHAR(50) NOT NULL,
   app_type enum('app','web','software') not null,
   os enum('win','android','ios') null,
   PRIMARY KEY(id),
   UNIQUE(name, app_type)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


  CREATE TABLE mystockserver.application_user (
   server_user_id INT UNSIGNED NOT NULL,
   application_id INT UNSIGNED NOT NULL,
   PRIMARY KEY(server_user_id,application_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


create index application_user_ix_server_user_id on application_user(server_user_id);
create index application_user_ix_application_id on application_user(application_id);


alter table mystockserver.application_user add CONSTRAINT application_user_fk_server_user_id foreign key application_user_ix_server_user_id(server_user_id) references mystockserver.server_user(id);
alter table mystockserver.application_user add CONSTRAINT application_user_fk_application_id foreign key application_user_ix_application_id(application_id) references mystockserver.application(id);

  CREATE TABLE mystockserver.session (
  	token varchar(20) not null,
   server_user_id INT UNSIGNED NOT NULL,
   expiry datetime not null,
   PRIMARY KEY(token)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index session_ix_server_user_id on session(server_user_id);
alter table mystockserver.session add CONSTRAINT session_fk_server_user_id foreign key session_ix_server_user_id(server_user_id) references mystockserver.server_user(id);
 
insert into application(name,app_type,os) values ('mystocks','software','win')

insert into server_user(login, password, role) values ('sauzanne',sha2('S0dGqk9dBRUz4CY2STIp',512),'admin')

insert into application_user (server_user_id, application_id)
select su.id, app.id FROM server_user su, application app 
where su.login = 'sauzanne' and app.name='mystocks';




