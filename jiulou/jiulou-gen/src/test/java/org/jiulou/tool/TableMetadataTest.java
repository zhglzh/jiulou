package org.jiulou.tool;

import org.junit.Test;

public class TableMetadataTest {
	
	@Test
	public void testParse(){
		String sqlCreate = "CREATE TABLE `user` (\r\n"
			+"  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,\r\n"
			+"  `password` varchar(64) DEFAULT NULL,\r\n"
			+"  `email` varchar(64) DEFAULT NULL COMMENT '邮件：唯一索引，用户用来登录',\r\n"
			+"  `username` varchar(32) NOT NULL COMMENT '网站内部昵称用户名 默认是username.domin可以是中文、数字、字母、下划线可以修改，但不能重复，不允许为空',\r\n"
			+"  `login_count` int(10) unsigned NOT NULL DEFAULT '0',\r\n"
			+"  `status` tinyint(4) DEFAULT '1' COMMENT '1:registered	新注册2:trial			试用期账号3:purchased		付费账号4:overdue		超过服务期限5:special		特殊账号6:escape		用户要求删除自己的账号7:locked		已锁定，暂时不可用',\r\n"
			+"  `register_date` datetime DEFAULT NULL COMMENT '用户注册时间',\r\n"
			+"  `trial_from` datetime DEFAULT NULL COMMENT '试用期开始时间',\r\n"
			+"  `trial_to` datetime DEFAULT NULL COMMENT '试用期结束时间',\r\n"
			+"  `purchase_date` datetime DEFAULT NULL COMMENT '付款时间',\r\n"
			+"  `valid_date_from` datetime DEFAULT NULL COMMENT '有限期开始时间',\r\n"
			+"  `valid_date_to` datetime DEFAULT NULL COMMENT '有效期结束时间',\r\n"
			+"  `hash` char(32) DEFAULT NULL,\r\n"
			+"  `locale` char(10) DEFAULT NULL,\r\n"
			+"  PRIMARY KEY (`id`),\r\n"
			+"  UNIQUE KEY `uq_username` (`username`),\r\n"
			+"  UNIQUE KEY `uq_email` (`email`),\r\n"
			+"  UNIQUE KEY `uq_hash` (`hash`)\r\n"
			+") ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表：记录所有站内用户'";
	
	
		TableMetadata tm = new TableMetadata();
		tm.parse(sqlCreate);
		System.out.print(tm);
	}
}
