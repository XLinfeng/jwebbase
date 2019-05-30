CREATE TABLE suser(
	`id` INT auto_increment not null PRIMARY KEY,
	`username` VARCHAR(32) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`enable` TINYINT(1),
	`locked` TINYINT(1)
);

CREATE TABLE role(
	`id` INT NOT NULL auto_increment PRIMARY KEY,
	`roleCode` VARCHAR(32) NOT NULL,
	`roleName` VARCHAR(32) NOT NULL
);

CREATE TABLE suser_role(
	`id` INT auto_increment NOT NULL PRIMARY KEY,
	`uid` INT NOT NULL,
	`rid` INT NOT NULL
);
alter table suser_role add constraint `fk_user` foreign key (`uid`) references suser(`id`);
alter table suser_role add constraint `fk_role` foreign key (`rid`) references role(`id`);


/*添加测试数据*/
INSERT into role (`rolecode`,`rolename`) VALUES('ROLE_dba','数据库管理员');
INSERT into role (`rolecode`,`rolename`) VALUES('ROLE_admin','系统管理员');
INSERT into role (`rolecode`,`rolename`) VALUES('ROLE_user','系统用户');

INSERT INTO suser(`username`,`password`,`enable`,`locked`) VALUES ('root','$2a$10$WlBHrAQhejUVhCwTAjYIMuBvUbNT7UXnGlj3kVnKOrMQ85rr5cY6S',1,0);
INSERT INTO suser(`username`,`password`,`enable`,`locked`) VALUES ('admin','$2a$10$WlBHrAQhejUVhCwTAjYIMuBvUbNT7UXnGlj3kVnKOrMQ85rr5cY6S',1,0);
INSERT INTO suser(`username`,`password`,`enable`,`locked`) VALUES ('f0146','$2a$10$WlBHrAQhejUVhCwTAjYIMuBvUbNT7UXnGlj3kVnKOrMQ85rr5cY6S',1,0);

INSERT INTO suser_role(`uid`,`rid`) VALUES ('1','1');
INSERT INTO suser_role(`uid`,`rid`) VALUES ('1','2');
INSERT INTO suser_role(`uid`,`rid`) VALUES ('2','2');
INSERT INTO suser_role(`uid`,`rid`) VALUES ('3','3');

CREATE TABLE menu(
	`id` INT auto_increment NOT NULL PRIMARY KEY,
	`pattern` VARCHAR(255) NOT NULL
);

CREATE TABLE menu_role(
	`id` INT auto_increment NOT NULL PRIMARY KEY,
	`mid` INT NOT NULL,
	`rid` INT NOT NULL
);

alter table menu_role add constraint `fk_menu_role` foreign key (`rid`) references role(`id`);

INSERT INTO menu(`pattern`) values ('/db/**');
INSERT INTO menu(`pattern`) values ('/admin/**');
INSERT INTO menu(`pattern`) values ('/user/**');

INSERT INTO menu_role(`mid`,`rid`) values (1,1);
INSERT INTO menu_role(`mid`,`rid`) values (2,2);
INSERT INTO menu_role(`mid`,`rid`) values (3,3);


SELECT m.*,r.id as rid,r.roleCode as rcode,r.roleName as rname from menu m LEFT JOIN menu_role mr
            on m.id = mr.mid
            LEFT JOIN role r
            on
            mr.rid = r.id