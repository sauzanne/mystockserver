  CREATE TABLE mystocks.list_stock (
   id INT unsigned NOT NULL auto_increment,
   name VARCHAR(255) NOT NULL,
   account_id INT unsigned NOT NULL,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY (id),
   UNIQUE (name, account_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;



create index list_stock_ix_account_id on list_stock(account_id);

alter table list_stock add CONSTRAINT list_stock_fk_account_id foreign key list_stock_ix_account_id(account_id) references account(id);