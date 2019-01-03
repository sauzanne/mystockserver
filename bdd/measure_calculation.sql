CREATE TABLE mystocks.measure_calculation (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   review_id INT UNSIGNED NULL,
   stock_ticker_id INT UNSIGNED NULL,
   measure_id INT UNSIGNED NOT NULL,
   calculation_date date not null,
   value DECIMAL(18,2) not null,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY (id),
   UNIQUE(review_id, stock_ticker_id, calculation_date)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index measure_calculation_ix_review_id on measure_calculation(review_id);

alter table measure_calculation add CONSTRAINT measure_calculation_fk_review_id foreign key measure_calculation_ix_review_id(review_id) references review(id);

create index measure_calculation_ix_measure_id on measure_calculation(measure_id);

alter table measure_calculation add CONSTRAINT measure_calculation_fk_measure_id foreign key measure_calculation_ix_measure_id(measure_id) references measure(id);

create index measure_calculation_ix_stock_ticker_id on measure_calculation(stock_ticker_id);

alter table measure_calculation add CONSTRAINT measure_calculation_fk_stock_ticker_id foreign key measure_calculation_ix_stock_ticker_id(stock_ticker_id) references stock_ticker(id);
