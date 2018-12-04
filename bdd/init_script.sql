/*CREATE DATABASE mystocks
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;*/
  
  CREATE TABLE mystocks.stock_type (
   id INT UNSIGNED NOT NULL auto_increment,
   code VARCHAR(50) NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(code)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;



CREATE TABLE mystocks.account_type (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   code VARCHAR(20) NOT NULL,
   description VARCHAR(100) NOT NULL,
   PRIMARY KEY(id),
	UNIQUE(code)

) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


  
  
  CREATE TABLE mystocks.account (
   id INT UNSIGNED NOT NULL auto_increment,
   last_name VARCHAR(50),
   first_name VARCHAR(50),
   account_type_id INT UNSIGNED NOT NULL,
   password VARCHAR(512),
   login VARCHAR(50),
   mail VARCHAR(255),
   first_input datetime not null,
   last_modified DATETIME,
   PRIMARY KEY(id),
   UNIQUE(login)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index account_ix_account_type_id on account(account_type_id);

alter table account add CONSTRAINT account_fk_account_type_id foreign key account_ix_account_type_id(account_type_id) references account_type(id);

  
  CREATE TABLE mystocks.stock (
   id INT unsigned NOT NULL auto_increment,
   isin VARCHAR(12) NOT NULL,
   name VARCHAR(255) NOT NULL,
   stock_type_id INT unsigned NOT NULL,
   account_id INT unsigned NOT NULL,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   greenrj_listed TINYINT(1) UNSIGNED NOT NULL default 0,
   mystocks_listed TINYINT(1) UNSIGNED NOT NULL default 0,
   PRIMARY KEY (id),
   UNIQUE (isin)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;



create index stock_ix_stock_type_id on stock(stock_type_id);

alter table stock add CONSTRAINT stock_fk_stock_type_id foreign key stock_ix_stock_type_id(stock_type_id) references stock_type(id);

create index stock_ix_account_id on stock(account_id);

alter table stock add CONSTRAINT stock_fk_account_id foreign key stock_ix_account_id(account_id) references account(id);


/* pas utilisée pour le moment */
/*CREATE TABLE mystocks.language (
   id INT NOT NULL,
   alpha_2 VARCHAR(2),
   alpha_3 VARCHAR(3),
   name VARCHAR(100) NOT NULL
) ENGINE = InnoDB ROW_FORMAT = DEFAULT;*/

CREATE TABLE mystocks.currency (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   name VARCHAR(100) NOT NULL,
   alpha_3 VARCHAR(20) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (alpha_3)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT;



CREATE TABLE mystocks.place (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   code VARCHAR(2) NOT NULL,
   place_name VARCHAR(50) NOT NULL,
   currency_id INT UNSIGNED NOT NULL,
   country_name VARCHAR(50) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (code)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


create index place_ix_currency_id on place(currency_id);

alter table place add CONSTRAINT place_fk_currency_id foreign key place_ix_currency_id(currency_id) references currency(id);


CREATE TABLE mystocks.stock_ticker (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   code VARCHAR(5) NOT NULL,
   stock_id INT UNSIGNED NOT NULL,
   place_id INT UNSIGNED NOT NULL,
   main_place TINYINT(1) UNSIGNED NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (code) 
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index stock_ticker_ix_stock_id on stock_ticker(stock_id);

alter table stock_ticker add CONSTRAINT stock_ticker_fk_stock_id foreign key stock_ticker_ix_stock_id(stock_id) references stock(id);

create index stock_ticker_ix_place_id on stock_ticker(place_id);

alter table stock_ticker add CONSTRAINT stock_ticker_fk_place_id foreign key stock_ticker_ix_place_id(place_id) references place(id);

ALTER TABLE `mystocks`.`stock_ticker` 
ADD COLUMN `server_user_id` INT UNSIGNED NULL AFTER `main_place`,
ADD COLUMN `first_input` DATETIME NULL AFTER `server_user_id`,
ADD COLUMN `last_modified` DATETIME NULL AFTER `first_input`;

update stock_ticker set server_user_id = 1, first_input = current_timestamp() where id > 1

ALTER TABLE `mystocks`.`stock_ticker` 
CHANGE COLUMN `server_user_id` `server_user_id` INT(10) UNSIGNED NOT NULL ,
CHANGE COLUMN `first_input` `first_input` DATETIME NOT NULL ;

CREATE TABLE mystocks.dividend (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   payment DATETIME not null,
   detachment DATETIME not null,
   amount DECIMAL(9,3) NOT NULL,
   start_year YEAR NOT NULL,
   end_year YEAR NOT NULL,
   period ENUM('Y','Q1','Q2','Q3','Q4','HY1','HY2') NOT NULL,
   stock_id INT UNSIGNED NOT NULL,
   account_id INT UNSIGNED NOT NULL,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   	PRIMARY KEY (id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index dividend_ix_stock_id on dividend(stock_id);

alter table dividend add CONSTRAINT dividend_fk_stock_id foreign key dividend_ix_stock_id(stock_id) references stock(id);

create index dividend_ix_account_id on dividend(account_id);

alter table dividend add CONSTRAINT dividend_fk_account_id foreign key d_ix_account_id(account_id) references account(id);

CREATE TABLE mystocks.news_type (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   code VARCHAR(3) NOT NULL,
   title VARCHAR(50) NOT NULL,
   language_ind ENUM('EN','FR') NOT NULL,
  	PRIMARY KEY (id),
	UNIQUE (code)  
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;



CREATE TABLE mystocks.news (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   title VARCHAR(255) NOT NULL,
   stock_id INT UNSIGNED NOT NULL,
   news_type_id INT UNSIGNED NOT NULL,
   period ENUM('Y','Q1','Q2','Q3','Q4','HY1','HY2') NOT NULL,
   start_year YEAR NOT NULL,
   end_year YEAR NOT NULL,
   link TEXT ASCII,
   data BLOB,
   input_date DATETIME NOT NULL,
   article TEXT ASCII,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   language_ind ENUM('EN','FR') NOT NULL,
   PRIMARY KEY (id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


create index news_ix_stock_id on news(stock_id);

alter table news add CONSTRAINT news_fk_stock_id foreign key news_ix_stock_id(stock_id) references stock(id);

create index news_ix_news_type_id on news(news_type_id);

alter table news add CONSTRAINT news_fk_news_type_id foreign key news_ix_news_type_id(news_type_id) references news_type(id);


CREATE TABLE mystocks.operations (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   revenues DECIMAL(18,2) UNSIGNED,
   ebit DECIMAL(18,2),
   current_ebit DECIMAL(18,2),
   ebitda DECIMAL(18,2),
   cost_of_revenues DECIMAL(18,2) UNSIGNED,
   financial_expenses DECIMAL(18,2),
   shareowners_earnings DECIMAL(18,2),
   operational_cash_flow DECIMAL(18,2),
   free_cash_flow DECIMAL(18,2),
   exceptional_items DECIMAL(18,2),
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY (id)   
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

ALTER TABLE `mystocks`.`operations` 
ADD COLUMN `adjusted_earnings` DECIMAL(18,2) UNSIGNED NULL AFTER shareowners_earnings;


CREATE TABLE mystocks.liabilities (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   current_liabilities DECIMAL(18,2),
   short_term_borrowings DECIMAL(18,2),
   long_term_borrowings DECIMAL(18,2),
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY (id)   
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

CREATE TABLE mystocks.assets (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   cash DECIMAL(18,2),
   inventories DECIMAL(18,2) UNSIGNED,
   current_assets DECIMAL(18,2),
   goodwill DECIMAL(18,2),
   trade_accounts DECIMAL(18,2),
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
  PRIMARY KEY (id)   
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

CREATE TABLE mystocks.equities (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   shareholder_equity DECIMAL(18,2),
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY (id)   
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

CREATE TABLE mystocks.balance_sheets (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   equities_id INT UNSIGNED NOT NULL,
   liabilities_id INT UNSIGNED NOT NULL,
   assets_id INT UNSIGNED NOT NULL,
	account_id INT UNSIGNED NOT NULL,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
     PRIMARY KEY (id)    
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


create index balance_sheets_ix_equities_id on balance_sheets(equities_id);

alter table balance_sheets add CONSTRAINT balance_sheets_fk_equities_id foreign key balance_sheets_ix_equities_id(equities_id) references equities(id);

create index balance_sheets_ix_liabilities_id on balance_sheets(liabilities_id);

alter table balance_sheets add CONSTRAINT balance_sheets_fk_liabilities_id foreign key balance_sheets_ix_liabilities_id(liabilities_id) references liabilities(id);

create index balance_sheets_ix_assets_id on balance_sheets(assets_id);

alter table balance_sheets add CONSTRAINT balance_sheets_fk_assets_id foreign key balance_sheets_ix_assets_id(assets_id) references assets(id);

create index balance_sheets_ix_account_id on balance_sheets(account_id);

alter table balance_sheets add CONSTRAINT balance_sheets_fk_account_id foreign key balance_sheets_ix_account_id(account_id) references account(id);



CREATE TABLE mystocks.review (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   stock_id INT UNSIGNED NOT NULL,
   period ENUM('Y','Q1','Q2','Q3','Q4','HY1','HY2') NOT NULL,
   start_year YEAR NOT NULL,
   end_year YEAR NOT NULL,
   operations_id INT UNSIGNED NULL,
   balance_sheets_id INT UNSIGNED NULL,
   nb_shares_end_period BIGINT UNSIGNED,
   account_id INT UNSIGNED NOT NULL,
   currency_id INT UNSIGNED NOT NULL,
   last_modified DATETIME,
   first_input DATETIME NOT NULL,
   PRIMARY KEY (id),
	UNIQUE(stock_id, period, start_year, end_year, account_id) 
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index review_ix_stock_id on review(stock_id);

alter table review add CONSTRAINT review_fk_stock_id foreign key review_ix_stock_id(stock_id) references stock(id);

create index review_ix_account_id on review(account_id);

alter table review add CONSTRAINT review_fk_account_id foreign key review_ix_account_id(account_id) references account(id);

create index review_ix_currency_id on review(currency_id);

alter table review add CONSTRAINT review_fk_currency_id foreign key review_ix_currency_id(currency_id) references currency(id);

create index review_ix_balance_sheets_id on review(balance_sheets_id);

alter table review add CONSTRAINT review_fk_balance_sheets_id foreign key review_ix_balance_sheets_id(balance_sheets_id) references balance_sheets(id);

ALTER TABLE mystocks.review 
ADD COLUMN start_date date NULL AFTER `end_year`

ALTER TABLE mystocks.review 
ADD COLUMN publication_date date NULL AFTER `start_date`

ALTER TABLE mystocks.review  
ADD COLUMN free_float DECIMAL(4,2) UNSIGNED NULL AFTER nb_shares_end_period;






CREATE TABLE mystocks.stock_price (
	stock_ticker_id int unsigned not null,
	input_date date not null,
	price decimal(10,3) not null,
	close tinyint(1) unsigned not null,
	PRIMARY KEY (stock_ticker_id, input_date)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index stock_price_ix_stock_ticker_id on stock_price(stock_ticker_id);

alter table stock_price add CONSTRAINT stock_price_fk_stock_ticker_id foreign key stock_price_ix_stock_ticker_id(stock_ticker_id) references stock_ticker(id);


ALTER TABLE `mystocks`.`operations` 
MODIFY `adjusted_earnings` DECIMAL(18,2) NULL;