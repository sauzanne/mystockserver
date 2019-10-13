  
drop table measure_alert;

  CREATE TABLE mystocks.measure_alert (
   id INT UNSIGNED NOT NULL auto_increment,
   account_id INT UNSIGNED NOT NULL,
   stock_ticker_id INT UNSIGNED NOT NULL,
   measure_id INT UNSIGNED NOT NULL,
   measure_id2_compared INT UNSIGNED NULL,
   value DECIMAL(18,2) UNSIGNED NULL,
   binary_operator ENUM('GE','LE') NOT NULL,
   triggered tinyint(1) unsigned not null,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY(id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index measure_alert_ix_account_id on measure_alert(account_id);

alter table measure_alert add CONSTRAINT measure_alert_fk_account_id foreign key measure_alert_ix_account_id(account_id) references account(id);

create index measure_alert_ix_stock_ticker_id on measure_alert(stock_ticker_id);

alter table measure_alert add CONSTRAINT measure_alert_fk_stock_ticker_id foreign key measure_alert_ix_stock_ticker_id(stock_ticker_id) references stock_ticker(id);

create index measure_alert_ix_measure_id on measure_alert(measure_id);

alter table measure_alert add CONSTRAINT measure_alert_fk_measure_id foreign key measure_alert_ix_measure_id(measure_id) references measure(id);

create index measure_alert_ix_measure_id2_compared on measure_alert(measure_id2_compared);

alter table measure_alert add CONSTRAINT measure_alert_fk_measure_id2_compared foreign key measure_alert_ix_measure_id2_compared(measure_id2_compared) references measure(id);

ALTER TABLE mystocks.measure_alert  
ADD COLUMN comment TEXT NULL AFTER binary_operator;

