  CREATE TABLE mystocks.portfolio_history (
   id INT unsigned NOT NULL auto_increment,
   portfolio_id INT unsigned NOT NULL,
   nb int not null,
   value DECIMAL(9,3) NOT NULL,
   fx_spot DECIMAL(9,3) NOT NULL,
   type_operation_id INT unsigned NOT NULL,
   reason_id INT unsigned NOT NULL,
   account_id int unsigned not null,
   stock_id int unsigned not null,
   portfolio_history_id_father int unsigned not null,
   date_operation date not null,
   description text null,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY (id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;



create index portfolio_history_ix_portfolio_id on portfolio_history(portfolio_id);

alter table portfolio_history add CONSTRAINT portfolio_history_fk_portfolio_id foreign key portfolio_history_ix_portfolio_id(portfolio_id) references portfolio(id);

create index portfolio_history_ix_account_id on portfolio_history(account_id);

alter table portfolio_history add CONSTRAINT portfolio_history_fk_account_id foreign key portfolio_history_ix_account_id(account_id) references account(id);

create index portfolio_history_ix_reason_id on portfolio_history(reason_id);

alter table portfolio_history add CONSTRAINT portfolio_history_fk_reason_id foreign key portfolio_history_ix_reason_id(reason_id) references reason(id);

create index portfolio_history_ix_type_operation_id on portfolio_history(type_operation_id);

alter table portfolio_history add CONSTRAINT portfolio_history_fk_type_operation_id foreign key portfolio_history_ix_type_operation_id(type_operation_id) references type_operation(id);

create index portfolio_history_ix_portfolio_history_id_father on portfolio_history(portfolio_history_id_father);

alter table portfolio_history add CONSTRAINT portfolio_history_fk_portfolio_history_id_father foreign key portfolio_history_ix_portfolio_history_id_father(portfolio_history_id_father) references portfolio_history(id);


