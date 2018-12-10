  CREATE TABLE mystocks.type_operation (
   id INT UNSIGNED NOT NULL auto_increment,
 	code varchar(50) not null,
   PRIMARY KEY(id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

INSERT INTO `type_operation`(id, code) VALUES
(1,'portfolio.operation.type.buy'),
(2,'portfolio.operation.type.sell'),
(3,'portfolio.operation.type.dividend.cash'),
(4,'portfolio.operation.type.dividend.stock'),
(5,'portfolio.operation.type.income'),
(6,'portfolio.operation.type.cost'),
(7,'portfolio.operation.type.outside');