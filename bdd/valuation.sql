--valuation table  
  
CREATE TABLE mystocks.valuation (
   id INT UNSIGNED NOT NULL auto_increment,
 	expected_growth_rate DECIMAL(4,2) null,
 	   first_input DATETIME NOT NULL,
   last_modified DATETIME,
   PRIMARY KEY(id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;
