
--alter database maildb default character set utf8;//修改数据库的字符集
--alter table tableAllTypes default character set utf8;//修改表的字符集

CREATE DATABASE demo DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

/*test table,with all mysql datatypes*/
CREATE TABLE tableAllTypes(
id				 BIGINT AUTO_INCREMENT NOT NULL, /* primary key */
column_boolean       BOOLEAN, 
column_byte          TINYINT,		/*1 Byte*/
column_short         SMALLINT,		/*2 Bytes */
column_int           INTEGER,		/*-- INT 4 Bytes*/
column_long          BIGINT,		/*-- 8 Bytes*/
column_float         FLOAT,			/*-- 4 Bytes FLOAT(M,D)*/
column_double        DOUBLE PRECISION,	/*-- 8 Bytes*/
column_bigdecimal    DECIMAL(13,0),	/*-- NUMERIC is implemented as DECIMAL*/
column_bit			 BIT(8),			/*-- BIT(M) enables storage of M-bit values. M can range from 1 to 64.*/

column_char          CHAR(255),		/*-- 0 to 255*/
column_varchar       VARCHAR(254), 	/*-- 0 to 65,535*/
column_binary		 BINARY(255),	/*-- */
column_varbinary	 VARBINARY(500),/*--*/
column_asciistream1  TINYTEXT,
column_asciistream2  TEXT,
column_asciistream3  MEDIUMTEXT,
column_asciistream4  LONGTEXT,
column_blob1         TINYBLOB,
column_blob2         BLOB,
column_blob3         MEDIUMBLOB,
column_blob4         LONGBLOB,
column_enum			 ENUM('small', 'medium', 'large'),	/*-- ENUM*/
column_set			 SET('one', 'two') NOT NULL,		/*-- maximum of 64 different members*/

column_datetime 	 DATETIME,	/*-- '1000-01-01 00:00:00' to '9999-12-31 23:59:59'. */
column_date          DATE,		/*-- '1000-01-01' to '9999-12-31'*/
column_time          TIME,		/*-- '-838:59:59' to '838:59:59*/
column_year			 YEAR,		/*-- YEAR(2) or YEAR(4),1901 to 2155*/
column_timestamp	  TIMESTAMP DEFAULT 0,/*-- '1970-01-01 00:00:01' UTC to '2038-01-19 03:14:07' UTC*/
updatetime	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

	PRIMARY KEY(id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8  AUTO_INCREMENT=1 COMMENT = 'test table,with all mysql datatypes';


CREATE TABLE test2(

id			 BIGINT auto_increment=1 NOT NULL, -- primary key
name         VARCHAR(200), 	-- 0 to 65,535
mainId		 BIGINT,
inserttime	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP NO UPDATE,
updatetime	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY(id)

)

ALTER TABLE test2 ADD CONSTRAINT test2_tableAllTypes_id FOREIGN KEY(mainId) REFERENCES tableAllTypes(id) ON UPDATE CASCADE

drop table if EXISTS `user` ;
CREATE TABLE IF NOT EXISTS `user` (
    id bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    password varchar(64) DEFAULT NULL,
    email varchar(64) DEFAULT NULL 	comment '邮件：唯一索引，用户用来登录',
    username varchar(32) not NULL comment'
网站内部昵称
用户名 默认是username.domin
可以是中文、数字、字母、下划线
可以修改，但不能重复，不允许为空',
    login_count int(10) unsigned NOT null DEFAULT '0',
    status TINYINT DEFAULT 1 comment '
1:registered	新注册
2:trial			试用期账号
3:purchased		付费账号
4:overdue		超过服务期限
5:special		特殊账号
6:escape		用户要求删除自己的账号
7:locked		已锁定，暂时不可用',
    register_date datetime DEFAULT null comment'用户注册时间',
    trial_from datetime DEFAULT null comment'试用期开始时间',
    trial_to datetime DEFAULT null comment'试用期结束时间',
    purchase_date datetime DEFAULT null comment'付款时间',
    valid_date_from datetime DEFAULT null comment'有限期开始时间',
    valid_date_to datetime DEFAULT null comment'有效期结束时间',
    hash char(32) DEFAULT NULL,
    locale char(10) DEFAULT NULL,
    uuid char(36) NOT NULL,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
    
    constraint pk_id PRIMARY KEY (id),
    constraint uq_email unique(email),
    constraint uq_username unique(username),
    constraint uq_uuid unique(uuid),
    constraint uq_hash unique(hash)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 
comment '用户表：记录所有站内用户';

drop table if EXISTS `user_oth` ;
CREATE TABLE IF NOT EXISTS user_oth (
	id bigint UNSIGNED NOT NULL AUTO_INCREMENT,
	username varchar(255) not NULL comment'第三方网站登录账号',
	domin varchar(10) not NULL comment'第三方网站domin，如QQ,weibo,msn,baidu,gmail',
	user_id bigint UNSIGNED NOT NULL comment'外键to users id',
	first_login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	more_info varchar(4000) not null,
	
	constraint pk_id primary key (id),
	constraint uq_user_domain unique ( username, domin ),
	constraint fk_users foreign key (user_id) references users (id),
	index indx_username_domin (username,domin)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
comment '其他网站用户，如百度、腾讯、新浪等
站外用户必定绑定到一个站内用户 ，所以可以多个外部帐号对应一个内部帐号';

create table IF NOT EXISTS roles (
	role_name varchar(100) primary key
)ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
comment '角色表';

create table IF NOT EXISTS user_roles(
	username varchar(100) not null,
	role_name varchar(100) not null,
	constraint uq_user_roles unique ( username, role_name )
)ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
comment '用户角色表';

create table IF NOT EXISTS roles_permissions (
    role_name varchar(100) not null,
    permission varchar(200) not null,
	primary key (role_name, permission)
)ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
comment '角色权限表';

/** 
 * 企业表，企业用户 
 **/
create table corporations (

    /** 
     * id
     * code
     * name
     * desc
     * userid foreign key to users

     * 
     */
);