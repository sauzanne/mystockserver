CREATE TABLE `news_flow` (
  `id` int unsigned NOT NULL auto_increment,
  `name` varchar(255) NOT NULL,
  `url` varchar(300) NOT NULL,
  `keyword` text NOT NULL,
  `notification` tinyint unsigned NOT NULL,
  `account_id` int unsigned NOT NULL,
  `first_input` datetime NOT NULL,
  `last_modified` datetime NULL,
  PRIMARY KEY(id)
) ENGINE=InnoDB ROW_FORMAT = DEFAULT;

create index news_flow_ix_account_id on news_flow(account_id);

alter table news_flow add CONSTRAINT news_flow_fk_account_id foreign key news_flow_ix_account_id(account_id) references account(id);

INSERT INTO `news_flow` (`name`, `url`, `keyword`, `notification`, `account_id`, `first_input`, `last_modified`) VALUES
('boursier.com', 'http://www.boursier.com/(S(tluffdbozlfm3okioeqgux5u))/syndication/atom/news', 'plastivaloire, abc arbitrage, albioma, aurea, bigben, orange, eo2, velcan, sword, bouygues, renault, quadient, maurel et prom, engie, cnim, pizzorno, fnac, iliad, it link, umanis, itlink, samsung, pétrole, automobile, voltalia, graines voltz, sqli, ose immuno, ose immunotherapeutics, erytech,eramet, prodways, deutsche bank, aures, société générale, telefonica, spotify, canadian solar, chili,veolia, derichebourg, tesla, precia, infotel, sopra steria, actia, first solar, sanofi, atos, claranova, worldline, solocal, guillemot, envea, seche, innate, ipsen, lna santé, casino, mediawan, lvmh, EssilorLuxottica, ubisoft, solutions 30, rallye, moulinvest, altice, luxe, parrot, marimekko, banque, banques, worldline, seb, michelin, maisons du monde, netflix, uber, hermes, maison du monde', 1, 80, '2015-10-27 12:01:29', '2018-07-10 07:24:23');