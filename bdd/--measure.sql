CREATE TABLE mystocks.measure (
   id INT UNSIGNED NOT NULL,
   code varchar(5) not null,
   label varchar(50) not null,
   type ENUM('data','financial_performance','risk','valuation') not null,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   	PRIMARY KEY (id),
    unique(code)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

insert into measure values (1,'e','earnings','data',now(),null);
insert into measure values (2,'r','revenues','data',now(),null);
insert into measure values (3,'et','ebit','data',now(),null);
insert into measure values (4,'eda','EBITDA','data',now(),null);
insert into measure values (5,'ce','CURRENT_EBIT','data',now(),null);
insert into measure values (6,'b','BOOK','data',now(),null);
insert into measure values (7,'d','dividend','data',now(),null);
insert into measure values (8,'pe','price/earnings','valuation',now(),null);


