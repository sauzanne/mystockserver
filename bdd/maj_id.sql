-- mise à jour des id pour synchroniser les bases

-- liste les clés étrangères
SELECT 
  TABLE_NAME,COLUMN_NAME,CONSTRAINT_NAME, REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME
FROM
  INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE
  REFERENCED_TABLE_SCHEMA = 'mystocks' AND
  REFERENCED_TABLE_NAME = 'stock';
  
  
--mise à jour de la table stock  
SET FOREIGN_KEY_CHECKS=0;
  update stock set id = id + 1000 where first_input >= '2020-03-17';
  
  update review as r 
  left join stock as s on s.id = r.stock_id
  set r.stock_id= r.stock_id + 2000
  where s.id is null;
  
  
  update stock_ticker as st
  left join stock as s on s.id = st.stock_id
  set st.stock_id= st.stock_id + 2000
  where s.id is null;
  
  
  ALTER TABLE stock AUTO_INCREMENT = 3075;
  
  --mise à jour de la table stock_ticker
   delete mc.* from measure_calculation mc inner join stock_ticker st on st.id = mc.stock_ticker_id
 where st.first_input >= '2020-03-17';
 
 delete ma.* from measure_alert ma inner join stock_ticker st on st.id = ma.stock_ticker_id
 where st.first_input >= '2020-03-17';
 
   update stock_ticker set id = id + 2000 where first_input >= '2020-03-17';
   ALTER TABLE stock_ticker AUTO_INCREMENT = 3058;

   
     --mise à jour de la table review
   delete mc.* from measure_calculation mc inner join review r on mc.review_id = r.id
 where r.first_input >= '2020-03-17';
 
    update review set id = id + 2000 where first_input >= '2020-03-17';
       ALTER TABLE review AUTO_INCREMENT = 2751;

    
--mise à jour de la table operations

 update operations set id = id + 2000 where first_input >= '2020-03-17';
 ALTER TABLE operations AUTO_INCREMENT = 2767;
 update review set operations_id = operations_id + 2000 where operations_id >= 680;

alter table mystocks.review  add CONSTRAINT review_fk_operations_id foreign key review_ix_operations_id(operations_id) references operations(id);

--mise à jour de balance_sheets
SET FOREIGN_KEY_CHECKS=0;
update review r
inner join balance_sheets bs on r.balance_sheets_id = bs.id
set r.balance_sheets_id = r.balance_sheets_id + 2000, bs.id = bs.id + 2000
where bs.first_input >= '2020-03-17';

--mise à jour de liabilities
  SET FOREIGN_KEY_CHECKS=0;
update balance_sheets bs
inner join liabilities l on bs.liabilities_id = l.id
set bs.liabilities_id = bs.liabilities_id + 2000, l.id = l.id + 2000
where l.first_input >= '2020-03-17';
ALTER TABLE liabilities AUTO_INCREMENT = 2770;

--mise à jour de equities
update balance_sheets bs
inner join equities e on bs.equities_id = e.id
set bs.equities_id = bs.equities_id + 2000, e.id = e.id + 2000
where e.first_input >= '2020-03-17';
ALTER TABLE equities AUTO_INCREMENT = 2775;

--mise à jour de assets
update balance_sheets bs
inner join assets a on bs.assets_id = a.id
set bs.assets_id = bs.assets_id + 2000, a.id = a.id + 2000
where a.first_input >= '2020-03-17';
ALTER TABLE assets AUTO_INCREMENT = 2841;
