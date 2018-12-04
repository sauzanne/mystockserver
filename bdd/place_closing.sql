--jour de fermeture de la bourse


CREATE TABLE mystocks.place_closing (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   place_id INT UNSIGNED NOT NULL,
   date_closing date not NULL,
	PRIMARY KEY (id),
	UNIQUE (place_id, date_closing)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


create index place_closing_ix_place_id on place_closing(place_id);

alter table place_closing add CONSTRAINT place_closing_fk_place_id foreign key place_closing_ix_place_id(place_id) references place(id);

insert into place_closing(place_id, date_closing) values (10, '2017-04-14');

insert into place_closing(place_id, date_closing) values (10, '2017-04-17');

insert into place_closing(place_id, date_closing) values (10, '2017-05-01');

insert into place_closing(place_id, date_closing) values (10, '2017-12-25');
insert into place_closing(place_id, date_closing) values (10, '2017-12-26');

insert into place_closing(place_id, date_closing) values (10, '2018-01-01');

insert into place_closing(place_id, date_closing) values (10, '2018-03-30');

insert into place_closing(place_id, date_closing) values (10, '2018-04-02');

insert into place_closing(place_id, date_closing) values (10, '2018-05-01');

insert into place_closing(place_id, date_closing) values (10, '2018-12-25');
insert into place_closing(place_id, date_closing) values (10, '2018-12-26');
