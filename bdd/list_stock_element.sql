   --obligatoire pour la suppression en cascade de mettre list_stock_id à null
  CREATE TABLE mystocks.list_stock_element (
   id INT unsigned NOT NULL auto_increment,
   stock_id INT unsigned NOT NULL,
   list_stock_id INT unsigned NULL,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY (id),
   UNIQUE (stock_id, list_stock_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


create index list_stock_element_ix_list_stock_id on list_stock_element(list_stock_id);

alter table list_stock_element add CONSTRAINT list_stock_element_fk_list_stock_id foreign key list_stock_element_ix_list_stock_id(list_stock_id) references list_stock(id);

create index list_stock_element_ix_stock_id on list_stock_element(stock_id);

alter table list_stock_element add CONSTRAINT list_stock_element_fk_stock_id foreign key list_stock_element_ix_stock_id(stock_id) references stock(id);