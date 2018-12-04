CREATE DATABASE fleexy
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
  
  
  CREATE TABLE fleexy.make (
   id INT UNSIGNED NOT NULL auto_increment,
   code VARCHAR(5) NOT NULL,
   name VARCHAR(50) not null,
   PRIMARY KEY(id),
   UNIQUE(code)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

insert into make (code,name) values ('RNO', 'Renault');

  CREATE TABLE fleexy.range (
   id INT UNSIGNED NOT NULL auto_increment,
   name_key VARCHAR(150) NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(name_key)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

insert into fleexy.range (name_key) values ('range.estate');
insert into fleexy.range (name_key) values ('range.family');


  CREATE TABLE fleexy.model (
   id INT UNSIGNED NOT NULL auto_increment,
   name VARCHAR(150) NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(name)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;
insert into model (name) values ('Laguna');


  CREATE TABLE fleexy.model_generation (
   id INT UNSIGNED NOT NULL auto_increment,
   model_id INT UNSIGNED NOT NULL,
   name VARCHAR(150) not null,
   year_start YEAR(4) not null,
   year_end year(4) null,
   model_generation_id_previous_generation INT UNSIGNED NULL,
   model_generation_id2_next_generation INT UNSIGNED NULL,
   PRIMARY KEY(id),
   UNIQUE(name)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index model_generation_ix_model_id on model_generation(model_id);
create index model_generation_ix_model_generation_id_previous_generation on model_generation(id);

alter table model_generation add CONSTRAINT model_generation_fk_model_id foreign key model_generation_ix_model_id(model_id) references model(id);
alter table model_generation add CONSTRAINT model_generation_fk_model_generation_id_previous_generation foreign key model_generation_ix_model_generation_id_previous_generation(model_generation_id_previous_generation) references model_generation(id);
alter table model_generation add CONSTRAINT model_generation_fk_model_generation_id2_next_generation foreign key model_generation_ix_model_generation_id_previous_generation(model_generation_id2_next_generation) references model_generation(id);

insert into model_generation (model_id,name,year_start,year_end,model_generation_id_previous_generation,model_generation_id2_next_generation) values (1,'Laguna II',2001,2007,null,null);

  CREATE TABLE fleexy.model_facelift (
     id INT UNSIGNED NOT NULL auto_increment,
  model_generation_id INT UNSIGNED NOT NULL,
   name VARCHAR(150) not null,
   year_start YEAR(4) not null,
   year_end YEAR(4) null,
   PRIMARY KEY(id),
   UNIQUE(name)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index model_facelift_ix_model_generation_id on model_facelift(model_generation_id);

alter table model_facelift add CONSTRAINT model_facelift_fk_model_generation_id foreign key model_facelift_ix_model_generation_id(model_generation_id) references model_generation(id);

insert into model_facelift (model_generation_id,name,year_start,year_end) values (1,'Laguna II Phase 2',2005,2007);


  CREATE TABLE fleexy.variant (
   id INT UNSIGNED NOT NULL auto_increment,
   name_key VARCHAR(150) not null,
   number_seat INT UNSIGNED NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(name_key)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;
insert into variant (name_key, number_seat) values ('variant.estate',5);

--associe une phase de model avec sa variante (break, berline, roadster...)
  CREATE TABLE fleexy.model_facelift_variant (
   model_facelift_id INT UNSIGNED NOT NULL,
      variant_id INT UNSIGNED NOT NULL,
      PRIMARY KEY(model_facelift_id,variant_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index model_facelift_variant_ix_model_facelift_id on model_facelift_variant(model_facelift_id);
create index model_facelift_variant_ix_variant_id on model_facelift_variant(variant_id);

alter table model_facelift_variant add CONSTRAINT model_facelift_variant_fk_model_facelift_id foreign key model_facelift_variant_ix_model_facelift_id(model_facelift_id) references model_facelift(id);
alter table model_facelift_variant add CONSTRAINT model_facelift_variant_fk_variant_id foreign key model_facelift_variant_ix_variant_id(variant_id) references variant(id);

insert into model_facelift_variant (model_facelift_id,variant_id) values (1,1);


--finition (forcément associée à un facelift). Exemple 'Luxe privilège' chez Renault
  CREATE TABLE fleexy.model_finition (
    id INT UNSIGNED NOT NULL auto_increment,
       name VARCHAR(150) NOT NULL,
       model_facelift_id INT UNSIGNED NOT NULL,
      PRIMARY KEY(id),
      UNIQUE(name, model_facelift_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index model_finition_ix_model_facelift_id on model_finition(model_facelift_id);

alter table model_finition add CONSTRAINT model_finition_fk_model_facelift_id foreign key model_finition_ix_model_facelift_id(model_facelift_id) references make(id);

insert into model_finition (name,model_facelift_id) values ('Carminat',1);

--option, peut être associée à une marque, à une génération de modèle mais aussi être universelle (ex : 'régulateur de vitesse', 'climatisation automatique')
-- une association précise permet de mieux filtrer cette option
  CREATE TABLE fleexy.model_option (
    id INT UNSIGNED NOT NULL auto_increment,
   name VARCHAR(150) not null,
      make_id INT UNSIGNED NULL,
      model_generation_id INT UNSIGNED NULL,
       model_facelift_id INT UNSIGNED NULL,
       description VARCHAR(500) null,
       language ENUM('EN','FR') NOT NULL,
      PRIMARY KEY(id),
      UNIQUE(name, make_id,model_generation_id,model_facelift_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index model_option_ix_make_id on model_option(make_id);
create index model_option_ix_model_generation_id on model_option(model_generation_id);
create index model_option_ix_model_model_facelift_id on model_option(model_facelift_id);

alter table model_option add CONSTRAINT model_option_fk_make_id foreign key model_option_ix_make_id(make_id) references make(id);
alter table model_option add CONSTRAINT model_option_fk_model_generation_id foreign key model_option_ix_model_generation_id(model_generation_id) references model_generation(id);
alter table model_option add CONSTRAINT model_option_fk_model_facelift_id foreign key model_option_ix_model_model_facelift_id(model_facelift_id) references model_facelift(id);

insert into model_option(name,make_id,model_generation_id,model_facelift_id,description, language) values ('Régulateur de vitesse',null,null,null,null,'FR');
insert into model_option(name,make_id,model_generation_id,model_facelift_id,description, language) values ('Limitateur de vitesse',null,null,null,null,'FR');
insert into model_option(name,make_id,model_generation_id,model_facelift_id,description, language) values ('Carminat GPS',1,1,1,'Système de navigation avec vue 3D','FR');
insert into model_option(name,make_id,model_generation_id,model_facelift_id,description, language) values ('Autoradio Cabasse avec chargeur 6 CD',1,1,1,'Système audio compatible avec format MP3','FR');
insert into model_option(name,make_id,model_generation_id,model_facelift_id,description, language) values ('Frein de parking électrique',null,null,null,null,'FR');
insert into model_option(name,make_id,model_generation_id,model_facelift_id,description, language) values ('Direction assistée',null,null,null,null,'FR');
insert into model_option(name,make_id,model_generation_id,model_facelift_id,description, language) values ('FAP avec regénération automatique',null,null,null,null,'FR');


--permet d'accorder une finition avec des options

CREATE TABLE fleexy.model_finition_model_option (
   model_finition_id INT UNSIGNED NOT NULL,
      model_option_id INT UNSIGNED NOT NULL,
      PRIMARY KEY(model_finition_id,model_option_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index model_finition_model_option_ix_model_finition_id on model_finition_model_option(model_finition_id);
create index model_finition_model_option_ix_model_option_id on model_finition_model_option(model_option_id);

alter table model_finition_model_option add CONSTRAINT model_finition_model_option_fk_model_finition_id foreign key model_finition_model_option_ix_model_finition_id(model_finition_id) references model_finition(id);
alter table model_finition_model_option add CONSTRAINT model_finition_model_option_fk_model_option_id foreign key model_finition_model_option_ix_model_option_id(model_option_id) references model_option(id);

insert into model_finition_model_option (model_finition_id,model_option_id) values (1,1);
insert into model_finition_model_option (model_finition_id,model_option_id) values (1,2);
insert into model_finition_model_option (model_finition_id,model_option_id) values (1,3);
insert into model_finition_model_option (model_finition_id,model_option_id) values (1,4);
insert into model_finition_model_option (model_finition_id,model_option_id) values (1,6);


  CREATE TABLE fleexy.fuel_type (
   id INT UNSIGNED NOT NULL auto_increment,
   name_key VARCHAR(150) not null,
   PRIMARY KEY(id),
   UNIQUE(name_key)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

insert into fuel_type (name_key) values ('fuel.type.bifuel');
insert into fuel_type (name_key) values ('fuel.type.diesel');
insert into fuel_type (name_key) values ('fuel.type.electric');
insert into fuel_type (name_key) values ('fuel.type.hybrid');
insert into fuel_type (name_key) values ('fuel.type.petrol');
insert into fuel_type (name_key) values ('fuel.type.petrol.ethanol');
insert into fuel_type (name_key) values ('fuel.type.gaz');



--permet d'enregistrer un moteur
-- la puissance doit être donnée en watt
--la taille du moteur est en cm3
--les émissions de co2 en g/km

  CREATE TABLE fleexy.engine (
    id INT UNSIGNED NOT NULL auto_increment,
   name VARCHAR(150) not null,
    number_cylinder int unsigned null,
    number_valve int unsigned null,
    turbo tinyint(1),
    fuel_type_id int unsigned null,
    size int unsigned null,
    power int unsigned null,
    torque int unsigned null,
    torque_at int unsigned null,
    co2_emissions decimal(5,2) null,
    consumption decimal(4,2) null,
    consumption_unity ENUM('liter','kWh') NULL,
    speed_max int unsigned null,
    zerocent decimal(4,2) unsigned null,
      make_id INT UNSIGNED NULL,
       details VARCHAR(500) null,
      PRIMARY KEY(id),
      UNIQUE(name, size,fuel_type_id,make_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index engine_ix_make_id on engine(make_id);

alter table engine add CONSTRAINT engine_fk_make_id foreign key engine_ix_make_id(make_id) references make(id);

insert into engine(name, number_cylinder, number_valve, fuel_type_id, size, power, torque, torque_at, co2_emissions, consumption, consumption_unity, speed_max,zerocent, make_id, details) values ("dCi",4, 8, 2, 1870, 95.6, 300, 2000, 154, 6,"liter", 200,10.8,1,null);



--permet d'accorder un moteur avec des options spécifiques (FAP)

CREATE TABLE fleexy.engine_model_option (
   engine_id INT UNSIGNED NOT NULL,
      model_option_id INT UNSIGNED NOT NULL,
      PRIMARY KEY(engine_id,model_option_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index engine_model_option_ix_model_engine_id on engine_model_option(engine_id);
create index engine_model_option_ix_model_option_id on engine_model_option(model_option_id);

alter table engine_model_option add CONSTRAINT engine_model_option_fk_engine_id foreign key engine_model_option_ix_model_engine_id(engine_id) references engine(id);
alter table engine_model_option add CONSTRAINT engine_model_option_fk_model_option_id foreign key engine_model_option_ix_model_option_id(model_option_id) references model_option(id);

insert into engine_model_option(engine_id, model_option_id) values (1, 7);

--permet de spécifier les opérations d'entretien subie par un modèle ou un moteur
-- le délai d'entretien est donné en nombre de mois
-- le temps estimé pour l'opération doit être donné en heures
  CREATE TABLE fleexy.service (
   id INT UNSIGNED NOT NULL auto_increment,
   name_key VARCHAR(150) not null,
   delay INT UNSIGNED NULL,
   mileage int unsigned null,
   details varchar(500) null,
   estimated_duration int unsigned null,
   engine_id int unsigned null,
   make_id int unsigned null,
   model_facelift_id int unsigned null,
   PRIMARY KEY(id),
   UNIQUE(name_key,engine_id,make_id,model_facelift_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index service_ix_make_id on service(make_id);
create index service_ix_engine_id on service(engine_id);
create index service_ix_model_facelift_id on service(model_facelift_id);


alter table service add CONSTRAINT service_fk_engine_id foreign key service_ix_engine_id(engine_id) references engine(id);
alter table service add CONSTRAINT service_fk_make_id foreign key service_ix_make_id(make_id) references make(id);
alter table service add CONSTRAINT service_fk_model_facelift_id foreign key service_ix_model_facelift_id(model_facelift_id) references model_facelift(id);
--ALTER TABLE service
  --DROP FOREIGN KEY service_fk_model_facelift_id;


insert into service (name_key, delay, mileage, details,estimated_duration,engine_id,make_id,model_facelift_id) values ('service.oil',12,15000,'Vidange du moteur',null,null,1, null);


--TO DO execute next

  CREATE TABLE fleexy.consummable_familly (
   id INT UNSIGNED NOT NULL auto_increment,
   name VARCHAR(150) not null,
   name_extended varchar(150) not null,
   detail varchar(500) not null,
   PRIMARY KEY(id),
   UNIQUE(name, name_extended)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

insert into consummable_familly (name,name_extended, detail) values ('Huile RN0720 / ACEA C4','5W30','Huile pour moteur diesel renault fappés');


create table fleexy.consummable(
   id INT UNSIGNED NOT NULL auto_increment,
   name VARCHAR(250) not null,
   consummable_familly_id int unsigned not null,
   volume decimal(5,2) unsigned null,
   PRIMARY KEY(id),
   UNIQUE(name, consummable_familly_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index consummable_ix_consummable_familly_id on consummable(consummable_familly_id);

alter table consummable add CONSTRAINT consummable_fk_consummable_familly_id foreign key consummable_ix_consummable_familly_id(consummable_familly_id) references consummable_familly(id);

insert into consummable (name, consummable_familly_id, volume) values ('Elf evolution Full-Tech 5W30',1,5.0);

--associe une opération de service avec les familles de consommable nécessaire
CREATE TABLE fleexy.service_consummable_familly (
   service_id INT UNSIGNED NOT NULL,
  consummable_familly_id INT UNSIGNED NOT NULL,
   volume decimal(5,2) unsigned null,
   quantity INT UNSIGNED NULL,
      PRIMARY KEY(service_id,consummable_familly_id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index service_consummable_familly_ix_service_id on service_consummable_familly(service_id);
create index service_consummable_familly_ix_consummable_familly_id on service_consummable_familly(consummable_familly_id);

alter table service_consummable_familly add CONSTRAINT service_consummable_familly_fk_service_id foreign key service_consummable_familly_ix_service_id(service_id) references service(id);
alter table service_consummable_familly add CONSTRAINT service_consummable_familly_fk_consummable_familly_id foreign key service_consummable_familly_ix_consummable_familly_id(consummable_familly_id) references consummable_familly(id);

insert into service_consummable_familly(service_id,consummable_familly_id,volume,quantity) values (1, 1,8.0, 1);

--la table car permet d'enregistrer une voiture
-- national identification type correspond à la case D2.1 de la carte grise
-- identification correspond à la case E de la carte grise
 
create table fleexy.car(
   id INT UNSIGNED NOT NULL auto_increment,
   model_facelift_id int unsigned null,
   mileage_buy int unsigned not null,
   national_identification_type varchar(12) not null, 
   identification varchar(20) not null,
   registration_number varchar(8) not null, 
   registration_date date not null,
   first_registration date not null,
   mileage_sell int unsigned null,
   detail varchar(500),   
    server_user_id int unsigned not null,
   creation datetime not null,
   modification datetime null,
   PRIMARY KEY(id),
   UNIQUE(registration_number), unique(identification)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;


create index car_ix_model_facelift_id on car(model_facelift_id);

alter table car add CONSTRAINT car_fk_model_facelift_id foreign key car_ix_model_facelift_id(model_facelift_id) references model_facelift(id);

insert into car(model_facelift_id, mileage_buy, national_identification_type, identification, registration_number, registration_date, first_registration, mileage_sell, detail, server_user_id, creation)
values (1, 128500, "MRE5514CB947","VF1KG4VB637395715","DG907NG", '2014-05-27', '2007-02-27',null,null,1,now());


create table fleexy.operation(
   id INT UNSIGNED NOT NULL auto_increment,
   service_id int unsigned null,
   car_id int unsigned not null,
   mileage int unsigned not null,
   date_operation datetime not null,
   duration decimal (5,2) unsigned not null,
   detail varchar(500),
   PRIMARY KEY(id),
   UNIQUE(service_id, car_id, mileage)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index operation_ix_car_id on operation(car_id);
create index operation_ix_service_id on operation(service_id);

alter table operation add CONSTRAINT operation_fk_car_id foreign key operation_ix_car_id(car_id) references car(id);
alter table operation add CONSTRAINT operation_fk_service_id foreign key operation_ix_service_id(service_id) references service(id);

create table fleexy.customer_provider(
   id INT UNSIGNED NOT NULL auto_increment,
   name varchar(150) not null,
   url varchar(300) null,
   mail1 varchar(300) null,
   mail2 varchar(300) null,
   mobile varchar(14) null,
   phone_number1 varchar(14) null,
   phone_number2 varchar(14) null,
   customer tinyint(1) not null,
   provider tinyint(1) not null,
    creation datetime not null,
    modification datetime null,  
   PRIMARY KEY(id),
   UNIQUE(mail1),unique(mail2),unique(url), unique(mobile),unique(phone_number1), unique(phone_number2)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;



create table fleexy.invoice(
   id INT UNSIGNED NOT NULL auto_increment,
   price decimal(5,2) not null,
   date_operation date not null,
   payment_date date null,
   customer_provider_id_buyer INT UNSIGNED NOT NULL,
   customer_provider_id2_seller INT UNSIGNED NOT NULL,
   PRIMARY KEY(id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index invoice_ix_customer_provider_id_buyer on invoice(customer_provider_id_buyer);
create index invoice_ix_customer_provider_id2_seller on invoice(customer_provider_id2_seller);


alter table invoice add CONSTRAINT invoice_fk_customer_provider_id_buyer foreign key invoice_ix_customer_provider_id_buyer(customer_provider_id_buyer) references customer_provider(id);
alter table invoice add CONSTRAINT invoice_fk_customer_provider_id2_seller foreign key invoice_ix_customer_provider_id2_seller(customer_provider_id2_seller) references customer_provider(id);

create table fleexy.invoice_line(
   id INT UNSIGNED NOT NULL auto_increment,
   unit_price decimal(5,2) not null,
   quantity int unsigned not null,
   invoice_id int unsigned not null,
   consummable_id int unsigned null,
   car_id int unsigned null,
   PRIMARY KEY(id)
) ENGINE = InnoDB CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index invoice_line_ix_invoice_id on invoice_line(invoice_id);
create index invoice_line_ix_consummable_id on invoice_line(consummable_id);
create index invoice_line_ix_car_id on invoice_line(car_id);

alter table invoice_line add CONSTRAINT invoice_line_fk_invoice_id foreign key invoice_line_ix_invoice_id(invoice_id) references invoice(id);
alter table invoice_line add CONSTRAINT invoice_line_fk_consummable_id foreign key invoice_line_ix_consummable_id(consummable_id) references consummable(id);
alter table invoice_line add CONSTRAINT invoice_line_fk_car_id foreign key invoice_line_ix_car_id(car_id) references car(id);










 


