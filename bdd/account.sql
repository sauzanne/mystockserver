
INSERT INTO `account` (`id`, `last_name`, `first_name`, `account_type_id`, `password`, `login`,`mail`,`first_input`,`last_modified`) VALUES
(1000, 'Auzanne', 'Sylvain', 4, 'ef99eeb020b51a56698a244360058cb0', 'admin','sauzanne@gmail.com',curdate(), null);

INSERT INTO `account` (`id`, `last_name`, `first_name`, `account_type_id`, `password`, `login`,`mail`,`first_input`,`last_modified`) VALUES
(80, 'Auzanne', 'Sylvain', 1, 'ef99eeb020b51a56698a244360058cb0', 'sauzanne', 'sauzanne@gmail.com',curdate(), null);

ALTER TABLE account ADD last_connection datetime null after mail;

ALTER TABLE account ADD token varchar(20) null after last_connection;

ALTER TABLE account ADD CONSTRAINT account_ck_token_unique UNIQUE (token)


--NT8r6iZ6DoAbeRZDtFrQ
INSERT INTO `account` (`last_name`, `first_name`, `account_type_id`, `password`, `login`,`mail`,last_connection,token,`first_input`,`last_modified`) VALUES
('Le baron', 'David', 1, 'a82bf3bcb11c1ff2fa27cf47f52cc71f4543c2b6dbdd57a84e07037bcdef93eec868e2b5074a504a377aeacd8c9ac0d01f1dd17fdd8889e8ff6e848150af806c', 'david.lebaron', 'david.lebaron49@gmail.com',null,null,curdate(), null);
