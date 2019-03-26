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

--Germany
insert into place_closing(place_id, date_closing) values (3, '2018-01-01');

insert into place_closing(place_id, date_closing) values (3, '2018-03-30');
insert into place_closing(place_id, date_closing) values (3, '2018-04-02');
insert into place_closing(place_id, date_closing) values (3, '2018-05-01');
insert into place_closing(place_id, date_closing) values (3, '2018-05-21');
insert into place_closing(place_id, date_closing) values (3, '2018-10-03');
insert into place_closing(place_id, date_closing) values (3, '2018-12-24');

insert into place_closing(place_id, date_closing) values (3, '2018-12-25');

insert into place_closing(place_id, date_closing) values (3, '2018-12-26');

insert into place_closing(place_id, date_closing) values (4, '2018-01-01');

insert into place_closing(place_id, date_closing) values (4, '2018-03-30');
insert into place_closing(place_id, date_closing) values (4, '2018-04-02');
insert into place_closing(place_id, date_closing) values (4, '2018-05-01');
insert into place_closing(place_id, date_closing) values (4, '2018-05-21');
insert into place_closing(place_id, date_closing) values (4, '2018-10-03');
insert into place_closing(place_id, date_closing) values (4, '2018-12-24');

insert into place_closing(place_id, date_closing) values (4, '2018-12-25');

insert into place_closing(place_id, date_closing) values (4, '2018-12-26');

--Amsterdam


insert into place_closing(place_id, date_closing) values (1, '2018-03-30');
insert into place_closing(place_id, date_closing) values (1, '2018-04-02');
insert into place_closing(place_id, date_closing) values (1, '2018-05-01');

insert into place_closing(place_id, date_closing) values (1, '2018-12-25');

insert into place_closing(place_id, date_closing) values (1, '2018-12-26');

insert into place_closing(place_id, date_closing) values (1, '2019-01-01');

insert into place_closing(place_id, date_closing) values (1, '2019-04-19');
insert into place_closing(place_id, date_closing) values (1, '2019-04-22');
insert into place_closing(place_id, date_closing) values (1, '2019-05-01');
insert into place_closing(place_id, date_closing) values (1, '2019-12-25');
insert into place_closing(place_id, date_closing) values (1, '2019-12-26');

--Milan
insert into place_closing(place_id, date_closing) values (8, '2018-01-01');

insert into place_closing(place_id, date_closing) values (8, '2018-03-30');
insert into place_closing(place_id, date_closing) values (8, '2018-04-02');
insert into place_closing(place_id, date_closing) values (8, '2018-05-01');
insert into place_closing(place_id, date_closing) values (8, '2018-08-15');

insert into place_closing(place_id, date_closing) values (8, '2018-12-24');

insert into place_closing(place_id, date_closing) values (8, '2018-12-25');

insert into place_closing(place_id, date_closing) values (8, '2018-12-26');

insert into place_closing(place_id, date_closing) values (8, '2018-12-31');

insert into place_closing(place_id, date_closing) values (8, '2019-01-01');

insert into place_closing(place_id, date_closing) values (8, '2019-04-19');
insert into place_closing(place_id, date_closing) values (8, '2019-04-22');
insert into place_closing(place_id, date_closing) values (8, '2019-05-01');
insert into place_closing(place_id, date_closing) values (8, '2019-08-15');

insert into place_closing(place_id, date_closing) values (8, '2019-12-24');

insert into place_closing(place_id, date_closing) values (8, '2019-12-25');
insert into place_closing(place_id, date_closing) values (8, '2019-12-26');
insert into place_closing(place_id, date_closing) values (8, '2019-12-31');


--Espana
insert into place_closing(place_id, date_closing) values (7, '2018-01-01');

insert into place_closing(place_id, date_closing) values (7, '2018-03-30');
insert into place_closing(place_id, date_closing) values (7, '2018-04-02');
insert into place_closing(place_id, date_closing) values (7, '2018-05-01');


insert into place_closing(place_id, date_closing) values (7, '2018-12-25');

insert into place_closing(place_id, date_closing) values (7, '2018-12-26');


insert into place_closing(place_id, date_closing) values (7, '2019-01-01');

insert into place_closing(place_id, date_closing) values (7, '2019-04-19');
insert into place_closing(place_id, date_closing) values (7, '2019-04-22');
insert into place_closing(place_id, date_closing) values (7, '2019-05-01');


insert into place_closing(place_id, date_closing) values (7, '2019-12-25');

insert into place_closing(place_id, date_closing) values (7, '2019-12-26');

delete from place_closing where date_closing = '2019-01-01';

insert into place_closing(place_id, date_closing) select place_id, '2019-01-01' from place_closing group by place_id;


--rajout après le 26/02/2019

--US
insert into place_closing(place_id, date_closing) values (5, '2019-01-21');

insert into place_closing(place_id, date_closing) values (5, '2019-02-18');

insert into place_closing(place_id, date_closing) values (5, '2019-04-19');

insert into place_closing(place_id, date_closing) values (5, '2019-05-27');
insert into place_closing(place_id, date_closing) values (5, '2019-09-02');


insert into place_closing(place_id, date_closing) values (13, '2019-01-21');

insert into place_closing(place_id, date_closing) values (13, '2019-02-18');

insert into place_closing(place_id, date_closing) values (13, '2019-04-19');

insert into place_closing(place_id, date_closing) values (13, '2019-05-27');
insert into place_closing(place_id, date_closing) values (13, '2019-09-02');

--Paris bruxelles amsterdam (1,2,10)
insert into place_closing(place_id, date_closing) values (1, '2019-04-19');
insert into place_closing(place_id, date_closing) values (1, '2019-04-22');
insert into place_closing(place_id, date_closing) values (1, '2019-05-01');
insert into place_closing(place_id, date_closing) values (1, '2019-12-25');
insert into place_closing(place_id, date_closing) values (1, '2019-12-26');

insert into place_closing(place_id, date_closing) values (2, '2019-04-19');
insert into place_closing(place_id, date_closing) values (2, '2019-04-22');
insert into place_closing(place_id, date_closing) values (2, '2029-05-01');
insert into place_closing(place_id, date_closing) values (2, '2019-12-25');
insert into place_closing(place_id, date_closing) values (2, '2019-12-26');


insert into place_closing(place_id, date_closing) values (10, '2019-04-19');
insert into place_closing(place_id, date_closing) values (10, '2019-04-22');
insert into place_closing(place_id, date_closing) values (10, '2019-05-01');
insert into place_closing(place_id, date_closing) values (10, '2019-12-25');
insert into place_closing(place_id, date_closing) values (10, '2019-12-26');

--fermeture place Tokyo
insert into place_closing(place_id, date_closing) values (19, '2018-01-01');
insert into place_closing(place_id, date_closing) values (19, '2018-01-02');
insert into place_closing(place_id, date_closing) values (19, '2018-01-03');
insert into place_closing(place_id, date_closing) values (19, '2018-01-08');
insert into place_closing(place_id, date_closing) values (19, '2018-02-11');
insert into place_closing(place_id, date_closing) values (19, '2018-02-12');
insert into place_closing(place_id, date_closing) values (19, '2018-03-21');
insert into place_closing(place_id, date_closing) values (19, '2018-04-29');
insert into place_closing(place_id, date_closing) values (19, '2018-04-30');
insert into place_closing(place_id, date_closing) values (19, '2018-05-03');
insert into place_closing(place_id, date_closing) values (19, '2018-05-04');
insert into place_closing(place_id, date_closing) values (19, '2018-07-16');
insert into place_closing(place_id, date_closing) values (19, '2018-08-11');
insert into place_closing(place_id, date_closing) values (19, '2018-09-17');
insert into place_closing(place_id, date_closing) values (19, '2018-09-23');
insert into place_closing(place_id, date_closing) values (19, '2018-09-24');
insert into place_closing(place_id, date_closing) values (19, '2018-10-08');
insert into place_closing(place_id, date_closing) values (19, '2018-11-03');
insert into place_closing(place_id, date_closing) values (19, '2018-11-23');
insert into place_closing(place_id, date_closing) values (19, '2018-12-23');
insert into place_closing(place_id, date_closing) values (19, '2018-12-24');
insert into place_closing(place_id, date_closing) values (19, '2018-12-31');


insert into place_closing(place_id, date_closing) values (19, '2019-01-01');
insert into place_closing(place_id, date_closing) values (19, '2019-01-02');
insert into place_closing(place_id, date_closing) values (19, '2019-01-03');
insert into place_closing(place_id, date_closing) values (19, '2019-01-14');
insert into place_closing(place_id, date_closing) values (19, '2019-02-11');
insert into place_closing(place_id, date_closing) values (19, '2019-03-21');
insert into place_closing(place_id, date_closing) values (19, '2019-04-29');
insert into place_closing(place_id, date_closing) values (19, '2019-04-30');
insert into place_closing(place_id, date_closing) values (19, '2019-05-01');
insert into place_closing(place_id, date_closing) values (19, '2019-05-02');
insert into place_closing(place_id, date_closing) values (19, '2019-05-03');
insert into place_closing(place_id, date_closing) values (19, '2019-05-04');
insert into place_closing(place_id, date_closing) values (19, '2019-05-06');

insert into place_closing(place_id, date_closing) values (19, '2019-07-15');
insert into place_closing(place_id, date_closing) values (19, '2019-08-12');
insert into place_closing(place_id, date_closing) values (19, '2019-09-16');
insert into place_closing(place_id, date_closing) values (19, '2019-09-23');
insert into place_closing(place_id, date_closing) values (19, '2019-10-14');
insert into place_closing(place_id, date_closing) values (19, '2019-10-22');
insert into place_closing(place_id, date_closing) values (19, '2019-11-04');
insert into place_closing(place_id, date_closing) values (19, '2019-11-23');
insert into place_closing(place_id, date_closing) values (19, '2019-12-31');