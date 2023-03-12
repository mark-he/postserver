

INSERT INTO `user` VALUES (1,'admin','ADMIN','mark.he@eagletsoft.com','18934354820',1,NULL,0,'2017-10-09 16:00:00',1,'2017-12-09 03:24:28',NULL,1,0,NULL);
INSERT INTO `user_auth` VALUES (1,1,'3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d',NULL,0);
INSERT INTO `permission` (`id`, `name`, `enabled`, `uri`, `description`, `sort`) VALUES ('1', '系统管理员', 1, '*:*', '系统管理、维护的权限', '0');
INSERT INTO `position` (`id`, `name`, `enabled`, `create_by`, `update_by`, `deleted`) VALUES ('1', '超级管理员', '1', '-1', '-1', '0');
INSERT INTO `position_permission` (`id`, `position_id`, `permission_id`) VALUES ('1', '1', '1');