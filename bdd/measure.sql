drop table measure;

CREATE TABLE mystocks.measure (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   code VARCHAR(50) NOT NULL,
      first_input DATETIME NOT NULL,
   PRIMARY KEY (id),
	UNIQUE(code)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


INSERT INTO `measure`(code, first_input) VALUES
('financial.roe', now());
INSERT INTO `measure`(code, first_input) VALUES ('financial.gpr', now());
INSERT INTO `measure`(code, first_input) VALUES ('financial.operatingmargin', now());
INSERT INTO `measure`(code, first_input) VALUES ('financial.operatingmargin.current', now());
INSERT INTO `measure`(code, first_input) VALUES ('financial.netmargin', now());
INSERT INTO `measure`(code, first_input) VALUES ('financial.revenuesgrowth', now());
INSERT INTO `measure`(code, first_input) VALUES ('financial.profitgrowth', now());
INSERT INTO `measure`(code, first_input) VALUES ('financial.ebitgrowth', now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.pe', now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.pe.wei', now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.peg', now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.evr', now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.pb', now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.pegh', now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.evebitda', now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.evebit', now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.yield',now());
INSERT INTO `measure`(code, first_input) VALUES ('valuation.yield.last',now());
INSERT INTO `measure`(code, first_input) VALUES ('risks.netgearing',now());
INSERT INTO `measure`(code, first_input) VALUES ('risks.quickratio',now());
INSERT INTO `measure`(code, first_input) VALUES ('risks.netdebt',now());
INSERT INTO `measure`(code, first_input) VALUES ('risks.dso',now());
INSERT INTO `measure`(code, first_input) VALUES('risks.inventory.turnover',now());

insert into measure(code, first_input) values ('technical.analysis.mm10',now());
insert into measure(code, first_input) values ('technical.analysis.mm200',now());
insert into measure(code, first_input) values ('technical.analysis.mm150',now());
insert into measure(code, first_input) values ('technical.analysis.mm50',now());
insert into measure(code, first_input) values ('technical.analysis.mm100',now());

insert into measure(code, first_input) values ('common.price.last',now());





