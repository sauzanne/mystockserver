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

--jour de fermeture à New york
insert into place_closing(place_id, date_closing) values (13, '2018-01-01');

insert into place_closing(place_id, date_closing) values (13, '2018-01-15');

insert into place_closing(place_id, date_closing) values (13, '2018-02-19');

insert into place_closing(place_id, date_closing) values (13, '2018-03-30');
insert into place_closing(place_id, date_closing) values (13, '2018-05-28');

insert into place_closing(place_id, date_closing) values (13, '2018-07-04');

insert into place_closing(place_id, date_closing) values (13, '2018-09-03');

insert into place_closing(place_id, date_closing) values (13, '2018-11-22');

insert into place_closing(place_id, date_closing) values (13, '2018-12-25');

--Nasdaq

insert into place_closing(place_id, date_closing) values (5, '2018-01-01');

insert into place_closing(place_id, date_closing) values (5, '2018-01-15');

insert into place_closing(place_id, date_closing) values (5, '2018-02-19');

insert into place_closing(place_id, date_closing) values (5, '2018-03-30');
insert into place_closing(place_id, date_closing) values (5, '2018-05-28');

insert into place_closing(place_id, date_closing) values (5, '2018-07-04');

insert into place_closing(place_id, date_closing) values (5, '2018-09-03');

insert into place_closing(place_id, date_closing) values (5, '2018-11-22');

insert into place_closing(place_id, date_closing) values (5, '2018-12-25');

--London

insert into place_closing(place_id, date_closing) values (6, '2018-01-01');

insert into place_closing(place_id, date_closing) values (6, '2018-03-30');
insert into place_closing(place_id, date_closing) values (6, '2018-02-04');

insert into place_closing(place_id, date_closing) values (6, '2018-05-07');

insert into place_closing(place_id, date_closing) values (6, '2018-05-28');

insert into place_closing(place_id, date_closing) values (6, '2018-08-27');

insert into place_closing(place_id, date_closing) values (6, '2018-12-25');

insert into place_closing(place_id, date_closing) values (6, '2018-12-26');

--London second place
insert into place_closing(place_id, date_closing) values (17, '2018-01-01');

insert into place_closing(place_id, date_closing) values (17, '2018-03-30');
insert into place_closing(place_id, date_closing) values (17, '2018-02-04');

insert into place_closing(place_id, date_closing) values (17, '2018-05-07');

insert into place_closing(place_id, date_closing) values (17, '2018-05-28');

insert into place_closing(place_id, date_closing) values (17, '2018-08-27');

insert into place_closing(place_id, date_closing) values (17, '2018-12-25');

insert into place_closing(place_id, date_closing) values (17, '2018-12-26');

--Greece
insert into place_closing(place_id, date_closing) values (16, '2018-01-01');

insert into place_closing(place_id, date_closing) values (16, '2018-02-19');
insert into place_closing(place_id, date_closing) values (16, '2018-03-30');
insert into place_closing(place_id, date_closing) values (16, '2018-04-02');
insert into place_closing(place_id, date_closing) values (16, '2018-04-06');
insert into place_closing(place_id, date_closing) values (16, '2018-04-09');

insert into place_closing(place_id, date_closing) values (16, '2018-05-01');

insert into place_closing(place_id, date_closing) values (16, '2018-05-28');

insert into place_closing(place_id, date_closing) values (16, '2018-08-15');

insert into place_closing(place_id, date_closing) values (16, '2018-12-24');

insert into place_closing(place_id, date_closing) values (16, '2018-12-25');

insert into place_closing(place_id, date_closing) values (16, '2018-12-26');



