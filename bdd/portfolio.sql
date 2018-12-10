--portfolio
  CREATE TABLE mystocks.portfolio (
   id INT unsigned NOT NULL auto_increment,
   name VARCHAR(255) NOT NULL,
   currency_id INT unsigned NOT NULL,
   default_fees DECIMAL(9,3) NOT NULL,
   percent_fees DECIMAL(9,3) NOT NULL,
   description text null,
   account_id int unsigned not null,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY (id),
   UNIQUE (name, account_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;



create index portfolio_ix_currency_id on portfolio(currency_id);

alter table portfolio add CONSTRAINT portfolio_fk_currency_id foreign key portfolio_ix_currency_id(currency_id) references currency(id);

create index portfolio_ix_account_id on portfolio(account_id);

alter table portfolio add CONSTRAINT portfolio_fk_account_id foreign key portfolio_ix_account_id(account_id) references account(id);



INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(16,'Binck PEA',978,2.50,0,'Portefeuille PEA binck',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(17,'BOURSE DIRECT COMPTE TITRE',978,0.99,0,'Compte titre ordinaire de bourse direct',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(18,'bourse direct PEA-PME',978,0.99,0,'Compte PEA-PME de bourse direct',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(19,'BINCK COMPTE TITRE',978,5.00,0,'NULL',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(20,'de giro',978,0.85,0,'NULL',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(21,'nabilla compte titre',978,0.00,0,'compte titre nabilla',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(22,'Nabilla PEA',978,2.00,0,'NULL',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(23,'Nabila PEA-PME',978,0.99,0,'PEA-PME bourse direct de Nabila',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(24,'Nabila De Giro',978,0.50,0,'Compte degiro Nabila',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(25,'Claire de giro',978,0.35,0,'compte titre de giro de claire auzanne',80,now(),null);
INSERT INTO portfolio(id, name,currency_id,default_fees,percent_fees, description, account_id, first_input,last_modified) values(26,'Claire PEA',978,2.50,0,'PEA de claire',80,now(),null);
