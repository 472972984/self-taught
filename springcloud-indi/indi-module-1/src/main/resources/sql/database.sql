CREATE TABLE `system_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `opt_type` varchar(255) DEFAULT NULL,
  `module_type` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `detail_content` text,
  `content_json` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;