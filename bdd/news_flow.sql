CREATE TABLE `news_flow` (
  `id` int unsigned NOT NULL,
  `name` nvarchar(255) NOT NULL,
  `url` nvarchar(300) NOT NULL,
  `keyword` text NOT NULL,
  `notification` tinyint(1) unsigned NOT NULL,
  `account_id` int unsigned NOT NULL,
  `first_input` datetime NOT NULL,
  `last_modified` datetime NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 ROW_FORMAT = DEFAULT;

create index news_flow_ix_account_id on news_flow(account_id);

alter table news_flow add CONSTRAINT news_flow_fk_account_id foreign key news_flow_ix_account_id(account_id) references account(id);

INSERT INTO `news_flow` (`id`, `name`, `url`, `keyword`, `notification`, `account_id`, `first_input`, `last_modified`) VALUES
(18, 'boursier.com', 'http://www.boursier.com/(S(tluffdbozlfm3okioeqgux5u))/syndication/atom/news', 'plastivaloire, abc arbitrage, opr, velcan energy, grèce, albioma, aurea, bigben,faiveley, actia,\n bigben, platine, platinum, orange, business et decision, bnd, futuren, parrot, eo2, mpi, brésil,\n amérique latine, alstom, systran, amazon, vergnet, velcan, mr bricolage, sfr, tesla, yandex, derichebourg, porsche, gaumont, GIROD, microwave, sword, vergnet, qiwi, bouygues, peugeot, renault, ubi,volkswagen, maurel, neopost, maurel et prom, ubisoft, engie,\n cnim, engie, pizzorno, fnac, umanis, michelin, brexit, signaux girod,\n iliad, Business et Decision, Aufeminin.com, automobile, archos, it link, umanis, trump, itlink,\n samsung, pétrole, voltalia, coface, latecoere, graines voltz, solutions 30, sqli, ile et vilaine, innate pharma, ose immuno, ose immunotherapeutics, astellia, opa, zodiac, sanofi, lg display, median,\nsolocal, erytech, peugeot, psa, maurel,eramet, free, pétrole, environnement sa, prodways, ald, deutsche bank, micropole, aures, gemalto,technipfmc, cgg, carrefour, sqli, arcelormittal, société générale,1000mercis, telefonica, spotify, dnxcorp, navya, michelin', 1, 80, '2015-10-27 12:01:29', '2018-07-10 07:24:23');