CREATE TABLE mystocks.amf_notification (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   account_id INT UNSIGNED not null,
   stock_id INT UNSIGNED not null,
PRIMARY KEY (id),
unique(account_id, stock_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index amf_notification_ix_account_id on amf_notification(account_id);

alter table amf_notification add CONSTRAINT amf_notification_fk_account_id foreign key amf_notification_ix_account_id(account_id) references account(id);


create index amf_notification_type_ix_stock_id on amf_notification(stock_id);

alter table amf_notification add CONSTRAINT amf_notification_fk_stock_id foreign key amf_notification_type_ix_stock_id(stock_id) references stock(id);


-- script d'initialisation
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ERA';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'IPN';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'VLA';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ALMOU';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ALDEL';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'CLA';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'HEXA';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ATO';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'NRO';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'DBG';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'TE';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ALTUV';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'AI';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ITL';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'OSE';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ALERS';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'SO';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'STLA';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'BIG';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'EN';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'SCHP';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'FP';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ABCA';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ALPDX';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'RNO';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'SGO';
insert into amf_notification(account_id, stock_id)
select 1000, s.id from stock s
inner join stock_ticker st on st.stock_id = s.id
 where code = 'ALFOC';

