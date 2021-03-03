--update de mars 2021
update stock set name = 'Orange' where name = 'Orange (ex-France Telecom)';
update stock set name = 'PLASTIQUES DU VAL DE LOIRE' where name = 'Plastivaloire';

update stock set name = 'GEA' where name = 'G.E.A.';
update stock set name = 'TELEVISION FRANCAISE 1' where name = 'TF1';

delete st.* from stock_ticker st inner join stock s on s.id = st.stock_id where s.name = 'Parrot DS 2015';

delete s.* from stock s where s.name = 'Parrot DS 2015';

delete r.* from review r inner join stock s on s.id = r.stock_id where s.name = 'UMANIS RGPT';

delete s.* from stock s where s.name = 'UMANIS RGPT';

update stock set name = 'OLYMPIQUE LYONNAIS GROUPE' where name = 'OL GROUPE';

update stock set name = 'LNA SANTE' where name = 'LNA SANTÉ';


/* euronext paris : id = 10 */
insert into place_closing(place_id, date_closing) values (10, '2021-01-01');
insert into place_closing(place_id, date_closing) values (10, '2021-04-02');
insert into place_closing(place_id, date_closing) values (10, '2021-04-05');

/* new york : id = 5, 13 */
insert into place_closing(place_id, date_closing) values (5, '2021-01-01');
insert into place_closing(place_id, date_closing) values (13, '2021-01-01');

insert into place_closing(place_id, date_closing) values (5, '2021-01-18');
insert into place_closing(place_id, date_closing) values (13, '2021-01-18');

insert into place_closing(place_id, date_closing) values (5, '2021-02-15');
insert into place_closing(place_id, date_closing) values (13, '2021-02-15');

insert into place_closing(place_id, date_closing) values (5, '2021-04-02');
insert into place_closing(place_id, date_closing) values (13, '2021-04-02');


insert into place_closing(place_id, date_closing) values (5, '2021-05-31');
insert into place_closing(place_id, date_closing) values (13, '2021-05-31');

insert into place_closing(place_id, date_closing) values (5, '2021-07-05');
insert into place_closing(place_id, date_closing) values (13, '2021-07-05');


insert into place_closing(place_id, date_closing) values (5, '2021-09-06');
insert into place_closing(place_id, date_closing) values (13, '2021-09-06');

insert into place_closing(place_id, date_closing) values (5, '2021-11-25');
insert into place_closing(place_id, date_closing) values (13, '2021-11-25');

insert into place_closing(place_id, date_closing) values (5, '2021-12-24');
insert into place_closing(place_id, date_closing) values (13, '2021-12-24');

/* london, id = 6, 17 */
insert into place_closing(place_id, date_closing) values (6, '2021-01-01');
insert into place_closing(place_id, date_closing) values (17, '2021-01-01');

insert into place_closing(place_id, date_closing) values (6, '2021-04-02');
insert into place_closing(place_id, date_closing) values (17, '2021-04-02');

insert into place_closing(place_id, date_closing) values (6, '2021-04-05');
insert into place_closing(place_id, date_closing) values (17, '2021-04-05');

insert into place_closing(place_id, date_closing) values (6, '2021-05-03');
insert into place_closing(place_id, date_closing) values (17, '2021-05-03');

insert into place_closing(place_id, date_closing) values (6, '2021-05-31');
insert into place_closing(place_id, date_closing) values (17, '2021-05-31');

insert into place_closing(place_id, date_closing) values (6, '2021-08-30');
insert into place_closing(place_id, date_closing) values (17, '2021-08-30');

insert into place_closing(place_id, date_closing) values (6, '2021-12-27');
insert into place_closing(place_id, date_closing) values (17, '2021-12-27');


insert into place_closing(place_id, date_closing) values (6, '2021-12-28');
insert into place_closing(place_id, date_closing) values (17, '2021-12-28');

/* germany, id = 3,4 */
insert into place_closing(place_id, date_closing) values (3, '2021-01-01');
insert into place_closing(place_id, date_closing) values (4, '2021-01-01');


insert into place_closing(place_id, date_closing) values (3, '2021-04-02');
insert into place_closing(place_id, date_closing) values (4, '2021-04-02');


insert into place_closing(place_id, date_closing) values (3, '2021-04-05');
insert into place_closing(place_id, date_closing) values (4, '2021-04-05');

insert into place_closing(place_id, date_closing) values (3, '2021-05-24');
insert into place_closing(place_id, date_closing) values (4, '2021-05-24');

insert into place_closing(place_id, date_closing) values (3, '2021-12-24');
insert into place_closing(place_id, date_closing) values (4, '2021-12-24');

insert into place_closing(place_id, date_closing) values (3, '2021-12-31');
insert into place_closing(place_id, date_closing) values (4, '2021-12-31');

/* madrid, id = 7 */
insert into place_closing(place_id, date_closing) values (7, '2021-01-01');

insert into place_closing(place_id, date_closing) values (7, '2021-04-02');

insert into place_closing(place_id, date_closing) values (7, '2021-04-05');

insert into place_closing(place_id, date_closing) values (7, '2021-05-01');

insert into place_closing(place_id, date_closing) values (7, '2021-12-24');

insert into place_closing(place_id, date_closing) values (7, '2021-12-25');

insert into place_closing(place_id, date_closing) values (7, '2021-12-31');

/* amsterdam, id = 1 */
insert into place_closing(place_id, date_closing) values (1, '2021-01-01');

insert into place_closing(place_id, date_closing) values (1, '2021-04-02');

insert into place_closing(place_id, date_closing) values (1, '2021-04-05');

/* milan, id = 8 */
insert into place_closing(place_id, date_closing) values (8, '2021-01-01');

insert into place_closing(place_id, date_closing) values (8, '2021-04-02');

insert into place_closing(place_id, date_closing) values (8, '2021-04-05');

insert into place_closing(place_id, date_closing) values (8, '2021-12-24');

insert into place_closing(place_id, date_closing) values (8, '2021-12-31');

/* bruxelles, id = 2 */
insert into place_closing(place_id, date_closing) values (2, '2021-01-01');

insert into place_closing(place_id, date_closing) values (2, '2021-04-02');

insert into place_closing(place_id, date_closing) values (2, '2021-04-05');

insert into place_closing(place_id, date_closing) values (2, '2021-12-24');

insert into place_closing(place_id, date_closing) values (2, '2021-12-31');

/* oslo, id = 9 */
insert into place_closing(place_id, date_closing) values (9, '2021-01-01');

insert into place_closing(place_id, date_closing) values (9, '2021-04-01');

insert into place_closing(place_id, date_closing) values (9, '2021-04-02');

insert into place_closing(place_id, date_closing) values (9, '2021-04-05');

insert into place_closing(place_id, date_closing) values (9, '2021-05-13');

insert into place_closing(place_id, date_closing) values (9, '2021-05-17');

insert into place_closing(place_id, date_closing) values (9, '2021-05-24');

insert into place_closing(place_id, date_closing) values (9, '2021-12-24');

insert into place_closing(place_id, date_closing) values (9, '2021-12-31');

/* Copenhagen, id = 15 */
insert into place_closing(place_id, date_closing) values (15, '2021-01-01');

insert into place_closing(place_id, date_closing) values (15, '2021-04-01');

insert into place_closing(place_id, date_closing) values (15, '2021-04-02');

insert into place_closing(place_id, date_closing) values (15, '2021-04-05');

insert into place_closing(place_id, date_closing) values (15, '2021-04-30');


insert into place_closing(place_id, date_closing) values (15, '2021-05-13');

insert into place_closing(place_id, date_closing) values (15, '2021-05-14');


insert into place_closing(place_id, date_closing) values (15, '2021-05-24');

insert into place_closing(place_id, date_closing) values (15, '2021-12-24');

insert into place_closing(place_id, date_closing) values (15, '2021-12-31');









