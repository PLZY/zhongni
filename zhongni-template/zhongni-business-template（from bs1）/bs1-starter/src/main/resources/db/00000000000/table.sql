-- 零号脚本，数据库初始化的时候必须先建立此数据表，才能进行后续的操作
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
CREATE TABLE IF NOT EXISTS `squid_system_info` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
    `application_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '系统名称',
    `deploy_host` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '部署主机',
    `current_db_version` varchar(16) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '当前数据库脚本的版本号',
    `current_system_version` varchar(16) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '当前系统版本号',
    `last_update_status` varchar(16) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '上一次更新状态',
    `last_update_datetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次脚本更新的时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;