
INSERT INTO `account` (`id`, `last_name`, `first_name`, `account_type_id`, `password`, `login`,`mail`,`first_input`,`last_modified`) VALUES
(1000, 'Auzanne', 'Sylvain', 4, 'ef99eeb020b51a56698a244360058cb0', 'admin','sauzanne@gmail.com',curdate(), null);

INSERT INTO `account` (`id`, `last_name`, `first_name`, `account_type_id`, `password`, `login`,`mail`,`first_input`,`last_modified`) VALUES
(80, 'Auzanne', 'Sylvain', 1, 'ef99eeb020b51a56698a244360058cb0', 'sauzanne', 'sauzanne@gmail.com',curdate(), null);

ALTER TABLE account ADD last_connection datetime null after mail;

ALTER TABLE account ADD token varchar(20) null after last_connection;

ALTER TABLE account ADD CONSTRAINT account_ck_token_unique UNIQUE (token)


