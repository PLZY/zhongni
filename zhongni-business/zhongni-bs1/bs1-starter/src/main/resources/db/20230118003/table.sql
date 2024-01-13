CREATE TABLE IF NOT EXISTS `bets` (
    `Id` bigint NOT NULL DEFAULT '0',
    `Name` varchar(64) CHARACTER SET utf8mb4  DEFAULT NULL,
    `Line` varchar(64) CHARACTER SET utf8mb4  DEFAULT NULL,
    `provider_id` bigint DEFAULT NULL,
    `type` varchar(50) CHARACTER SET utf8mb4  DEFAULT NULL,
    `BaseLine` varchar(64) CHARACTER SET utf8mb4  DEFAULT NULL,
    `Status` varchar(64) CHARACTER SET utf8mb4  DEFAULT NULL,
    `StartPrice` varchar(64) CHARACTER SET utf8mb4  DEFAULT NULL,
    `Price` varchar(64) CHARACTER SET utf8mb4  DEFAULT NULL,
    `LastUpdate` varchar(64) CHARACTER SET utf8mb4  DEFAULT NULL,
    `State` int NOT NULL DEFAULT '0',
    `Settlement` int DEFAULT NULL,
    `market_id` int DEFAULT NULL,
    `event_id` bigint DEFAULT NULL,
    `calculate_price` varchar(255) DEFAULT NULL,
    `key` int NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`key`),
    UNIQUE KEY `Name_Line_BaseLine_market_id_event_id` (`Name`,`Line`,`BaseLine`,`market_id`,`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2959952 DEFAULT CHARSET=utf8mb4 ;


CREATE TABLE IF NOT EXISTS `events` (
    `FixtureId` bigint DEFAULT NULL,
    `Livescore` varchar(50) CHARACTER SET utf8mb4  DEFAULT NULL,
    `type` varchar(50) CHARACTER SET utf8mb4  DEFAULT NULL,
    `score_status` int DEFAULT NULL,
    `score_current_period` int DEFAULT NULL,
    `away_id` bigint DEFAULT NULL,
    `home_id` bigint DEFAULT NULL,
    `score_position_one` varchar(50) DEFAULT '0',
    `score_position_two` varchar(50) DEFAULT '0',
    `corner_one` varchar(50) DEFAULT '0',
    `corner_two` varchar(50) DEFAULT '0',
    `yellow_card_one` varchar(50) DEFAULT '0',
    `yellow_card_two` varchar(50) DEFAULT '0',
    `red_card_one` varchar(50) DEFAULT '0',
    `red_card_two` varchar(50) DEFAULT '0',
    `home` varchar(50) DEFAULT NULL,
    `away` varchar(50) DEFAULT NULL,
    `StartDate` varchar(50) DEFAULT NULL,
    `event_time` varchar(50) DEFAULT NULL,
    `anime_id` bigint DEFAULT NULL,
    `league_id` int DEFAULT NULL,
    `lan_data` text,
    `settle` int DEFAULT '0',
    UNIQUE KEY `FixtureId` (`FixtureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

CREATE TABLE IF NOT EXISTS `league` (
    `league_id` int NOT NULL,
    `sport_id` int NOT NULL,
    `league_name` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;


CREATE TABLE IF NOT EXISTS `markets` (
    `market_id` bigint NOT NULL COMMENT 'id',
    `market_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
    `market_sort` tinyint NOT NULL DEFAULT '0' COMMENT '排序',
    `belong_plate_id` bigint NOT NULL COMMENT '所属盘口',
    `belong_sports_id` bigint NOT NULL COMMENT '所属运动',
    `contains_rows` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '包含的行，多个值使用@_@隔开',
    `contains_column` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '包含的列，多个值使用@_@隔开',
    PRIMARY KEY (`market_id`) USING BTREE,
    UNIQUE KEY `unique_idx_sport_plate` (`market_name`,`belong_plate_id`,`belong_sports_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `sports` (
    `id` int NOT NULL AUTO_INCREMENT,
    `sport_id` char(25) CHARACTER SET utf8 DEFAULT NULL,
    `sport_name` mediumtext NOT NULL,
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ;


CREATE TABLE IF NOT EXISTS `plate` (
    `plate_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `plate_name` varchar(255) NOT NULL,
    `sports_id` int(11) NOT NULL,
    PRIMARY KEY (`plate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;

CREATE TABLE IF NOT EXISTS `squid_order_lottery` (
    `lottery_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '彩票唯一编码',
    `bet_id` bigint NOT NULL COMMENT '赔率ID',
    `lottery_stake` bigint NOT NULL COMMENT '下注金额，单位：分',
    `lottery_odd` varchar(255)  NOT NULL COMMENT '赔率',
    `order_no` bigint NOT NULL COMMENT '所属订单',
    `lottery_type` varchar(32)  NOT NULL COMMENT '彩票类型',
    `lottery_status` varchar(16) NOT NULL COMMENT '彩票状态OPEN-未开奖，OPENED-已开奖',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`lottery_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `squid_order` CHANGE COLUMN  `ticket_type` `ticket_type` varchar(32)  NOT NULL COMMENT '商券类型';