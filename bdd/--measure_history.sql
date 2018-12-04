CREATE TABLE mystocks.measure_history (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   measure_id INT UNSIGNED not null,
   review_id INT UNSIGNED not null,
   start_date date not null,
   end_date date null,
   value DECIMAL(19,3) not null,
   evol DECIMAL(7,3) null,
   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   	PRIMARY KEY (id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


create index measure_history_ix_review_id on measure_history(review_id);

alter table measure_history add CONSTRAINT measure_history_fk_review_id foreign key measure_history_ix_review_id(review_id) references review(id);

create index measure_history_ix_measure_id on measure_history(measure_id);

alter table measure_history add CONSTRAINT measure_history_fk_measure_id foreign key measure_history_ix_measure_id(measure_id) references measure(id);