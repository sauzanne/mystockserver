CREATE TABLE mystocks.t_stock_ticker (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   code VARCHAR(5) NOT NULL,
   stock_id INT UNSIGNED NOT NULL,
   place_name varchar(5) NOT NULL,
   main_place TINYINT(1) UNSIGNED NOT NULL,
PRIMARY KEY (id),
UNIQUE (code) 
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(5,'RIA',23,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(6,'ORA',24,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(7,'VIV',25,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(8,'TIT',26,'MI',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(9,'ATO',27,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(10,'ABIO',28,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(11,'PVL',29,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(12,'vie',30,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(13,'NOA3',31,'F',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(14,'aure',32,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(15,'ug',33,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(16,'ENGI',34,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(17,'SEV',35,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(18,'alvel',39,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(19,'big',40,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(20,'ubi',41,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(21,'aurs',42,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(22,'osa',43,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(23,'mt',44,'AS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(24,'rx',45,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(25,'GF8',46,'F',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(26,'FTRN',47,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(27,'CCN',48,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(28,'BND',49,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(29,'RIM',50,'TO',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(30,'ote',51,'F',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(31,'UMS',52,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(32,'sqi',53,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(33,'INF',54,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(34,'prec',55,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(35,'pic',56,'BR',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(36,'fp',57,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(37,'xom',58,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(38,'gea',59,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(39,'com',60,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(40,'civ',61,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(41,'aleo2',62,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(42,'ecp',63,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(43,'gam',64,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(44,'era',65,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(45,'abca',66,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(46,'mgic',67,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(47,'EO',68,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(48,'ALO',69,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(49,'PARRO',71,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(50,'bvd',72,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(51,'swp',73,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(52,'ALPRO',74,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(53,'twtr',75,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(54,'znga',76,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(55,'P',77,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(56,'yelp',78,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(57,'fb',79,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(58,'amzn',80,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(59,'grpn',81,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(60,'pcln',82,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(61,'TFA',83,'AS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(62,'tsla',84,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(63,'mpi',85,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(64,'MRB',86,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(65,'pbr',87,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(66,'OGZPY',88,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(67,'LEY',89,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(68,'vip',90,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(69,'yndx',91,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(70,'mbt',92,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(71,'gpro',93,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(72,'AREVA',94,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(73,'NUM',95,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(74,'MLRYY',96,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(75,'SFTBY',97,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(76,'LUKOY',98,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(77,'ES',99,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(78,'cgg',100,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(79,'ATI',101,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(80,'RUI',102,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(81,'ATC',103,'AS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(82,'LOCAL',104,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(83,'TEC',105,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(84,'nti',106,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(85,'CVRR',107,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(86,'ILD',108,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(87,'IRC',109,'MI',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(88,'ALMIC',110,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(89,'GIRO',111,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(90,'TKTT',112,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(91,'PGN',113,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(92,'SDRL',114,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(93,'RIG',115,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(94,'esv',116,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(95,'VK',117,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(96,'STO',118,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(97,'JXR',119,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(98,'alver',120,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(99,'ALTUT',121,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(100,'SQM',122,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(101,'saft',123,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(102,'csiq',125,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(103,'QIWI',126,'GS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(104,'ALPLA',127,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(105,'him',128,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(106,'TSL',130,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(107,'EN',131,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(108,'ASP',132,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(109,'rco',133,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(110,'RNO',134,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(111,'ALBLD',135,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(112,'TFI',136,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(113,'sk',137,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(114,'MAU',138,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(115,'VLKAY',139,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(116,'NEO',140,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(117,'NTG',141,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(118,'PCA',142,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(119,'VOW',143,'F',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(120,'NXTV',124,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(122,'EMBD',932,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(123,'RSXJ',931,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(930,'PHPT',930,'AS',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(931,'ECH',939,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(932,'PBR-A',935,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(933,'GRE',940,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(934,'BRZ',941,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(935,'ALBRS',942,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(936,'HBW',936,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(937,'RIO',944,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(938,'LEM',945,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(939,'BQRE',946,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(940,'ERR',953,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(941,'ALHOM',951,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(942,'PAH3',955,'F',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(943,'DBG',956,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(944,'STL',957,'OL',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(945,'RE',958,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(946,'PARDS',959,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(947,'GFI',961,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(948,'NEX',962,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(949,'enel',963,'MI',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(950,'FSLR',964,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(951,'SOLB',965,'BR',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(952,'GLE',967,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(953,'gpe',968,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(954,'GUI',969,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(955,'ALUMS',971,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(956,'sft',972,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(957,'ITL',973,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(958,'EUCAR',974,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(959,'CO',975,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(960,'cbd',976,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(961,'ML',977,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(962,'SCHP',979,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(963,'UHRN',981,'SW',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(964,'CFR',982,'VX',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(965,'ALS30',984,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(966,'fem',985,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(967,'OLG',986,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(968,'BNP',987,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(969,'G',988,'MI',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(970,'LUX',989,'MI',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(971,'YMAU',990,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(972,'NOKIA',993,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(973,'smsn',994,'L',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(974,'jks',995,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(975,'LAT',996,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(976,'GRVO',997,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(977,'ALAST',998,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(978,'OSE',999,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(979,'IPH',1000,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(980,'2017S',933,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(981,'OIH',934,'US',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(982,'EXP1',937,'DE',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(983,'EXP2',938,'DE',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(984,'RAN',947,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(985,'LAC',948,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(986,'ALEUP',949,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(987,'ALGUI',950,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(988,'ALPRV',952,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(989,'CEC',954,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(990,'ZC',1001,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(991,'FNAC',1002,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(992,'ERYP',1003,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(993,'ALD',1004,'PA',1);
insert into t_stock_ticker(id,code,stock_id, place_name,main_place) values(994,'GTO',1005,'PA',1);

insert into mystocks.stock_ticker
select ts.id, ts.code, stock_id, p.id, main_place
from t_stock_ticker ts
inner join place p on ts.place_name = p.code
inner join stock s on s.id = ts.stock_id;

drop table t_stock_ticker;

ALTER TABLE `mystocks`.`stock_ticker` 
ADD COLUMN `disabled` DATE NULL AFTER `server_user_id`;

update stock_ticker set disabled = '2019-01-02' where code = 'RIA';
update stock_ticker set disabled = '2019-01-02' where code = 'OSA';
update stock_ticker set disabled = '2019-01-02' where code = 'RIM';
update stock_ticker set disabled = '2019-01-02' where code = 'PIC';
update stock_ticker set disabled = '2019-01-02' where code = 'MGIC';
update stock_ticker set disabled = '2019-01-02' where code = 'BVD';

update stock_ticker set disabled = '2019-01-02' where code = 'PCLN';

update stock_ticker set disabled = '2019-01-02' where code = 'MPI';
update stock_ticker set disabled = '2019-01-02' where code = 'LEY';
update stock_ticker set disabled = '2019-01-02' where code = 'VIP';

update stock_ticker set disabled = '2019-01-02' where code = 'AREVA';
update stock_ticker set disabled = '2019-01-02' where code = 'NUM';
update stock_ticker set disabled = '2019-01-02' where code = 'TEC';

update stock_ticker set disabled = '2019-01-02' where code = 'NTI';
update stock_ticker set disabled = '2019-01-02' where code = 'PGN';
update stock_ticker set disabled = '2019-01-02' where code = 'STO';
update stock_ticker set disabled = '2019-01-02' where code = 'ALTUT';
update stock_ticker set disabled = '2019-01-02' where code = 'SAFT';
update stock_ticker set disabled = '2019-01-02' where code = 'HIM';
update stock_ticker set disabled = '2019-01-02' where code = 'TSL';
update stock_ticker set disabled = '2019-01-02' where code = 'VLKAY';
update stock_ticker set disabled = '2019-01-02' where code = 'NXTV';

update stock_ticker set disabled = '2019-01-02' where code = 'EMBD';
update stock_ticker set disabled = '2019-01-02' where code = 'PHPT';

update stock_ticker set disabled = '2019-01-02' where code = 'ALBRS';
update stock_ticker set disabled = '2019-01-02' where code = 'BQRE';
update stock_ticker set disabled = '2019-01-02' where code = 'ERR';
update stock_ticker set disabled = '2019-01-02' where code = 'ALHOM';
update stock_ticker set disabled = '2019-01-02' where code = 'STL';
update stock_ticker set disabled = '2019-01-02' where code = 'PARDS';
update stock_ticker set disabled = '2019-01-02' where code = 'GFI';
update stock_ticker set disabled = '2019-01-02' where code = 'SOLB';
update stock_ticker set disabled = '2019-01-02' where code = 'fem';
update stock_ticker set disabled = '2019-01-02' where code = 'YMAU';
update stock_ticker set disabled = '2019-01-02' where code = 'ALAST';
update stock_ticker set disabled = '2019-01-02' where code = '2017s';
update stock_ticker set disabled = '2019-01-02' where code = 'EXP1';
update stock_ticker set disabled = '2019-01-02' where code = 'EXP2';
update stock_ticker set disabled = '2019-01-02' where code = 'RAN';
update stock_ticker set disabled = '2019-01-02' where code = 'LAC';
update stock_ticker set disabled = '2019-01-02' where code = 'ALGUI';
update stock_ticker set disabled = '2019-01-02' where code = 'ALPRV';
update stock_ticker set disabled = '2019-01-02' where code = 'ZC';
update stock_ticker set disabled = '2019-01-02' where code = 'ETE';

update stock_ticker set place_id = 1 where code = 'GTO';
update stock_ticker set place_id = 4 where code = 'UHRN';
update stock_ticker set place_id = 4 where code = 'VWS';
insert into stock_ticker(code,stock_id, place_id,main_place,server_user_id,disabled,first_input) values('NAGF',1024,4,1,1,null,now());


ALTER TABLE `mystocks`.`stock_ticker` 
MODIFY `code` varchar(8) not NULL;