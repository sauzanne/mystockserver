CREATE TABLE mystocks.amf_publication_type (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   publication_type nvarchar(255) NOT NULL,
PRIMARY KEY (id),
UNIQUE(publication_type)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;



CREATE TABLE mystocks.amf_publication (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   date_publication date NOT NULL,
   doc_bdif nvarchar(255) null,
   amf_publication_type_id INT UNSIGNED not null,
   stock_id INT UNSIGNED not null,
   link varchar(2048) not null,
PRIMARY KEY (id),
unique(doc_bdif)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index amf_publication_type_ix_amf_publication_type_id on amf_publication(amf_publication_type_id);

alter table amf_publication add CONSTRAINT amf_publication_fk_amf_publication_type_id foreign key amf_publication_type_ix_amf_publication_type_id(amf_publication_type_id) references amf_publication_type(id);


create index amf_publication_type_ix_stock_id on amf_publication(stock_id);

alter table amf_publication add CONSTRAINT amf_publication_fk_stock_id foreign key amf_publication_type_ix_stock_id(stock_id) references stock(id);

