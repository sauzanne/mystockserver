  CREATE TABLE mystocks.reason (
   id INT UNSIGNED NOT NULL auto_increment,
 	code varchar(50) not null,
   PRIMARY KEY(id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

INSERT INTO `reason`(id, code) VALUES
(1,'portfolio.reason.ipo'),
(2,'portfolio.reason.po'),
(3,'portfolio.reason.tax'),
(4,'portfolio.reason.broker.commission'),
(5,'portfolio.reason.delisted'),
(6,'portfolio.reason.other');