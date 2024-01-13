SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
CREATE TABLE IF NOT EXISTS `squid_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户编码',
  `wallet_address` varchar(100) CHARACTER SET utf8mb4  NOT NULL DEFAULT '' COMMENT '钱包地址',
  `domain_id` varchar(64) CHARACTER SET utf8mb4  NOT NULL COMMENT '区块链id',
  `nick_name` varchar(25) CHARACTER SET utf8mb4  NOT NULL COMMENT '昵称',
  `image_path` varchar(255) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '自定义头像地址',
  `pass_word` varchar(255) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '登录密码',
  `wallet_password` varchar(255) CHARACTER SET utf8mb4  NOT NULL COMMENT '钱包密码',
  `own_invitation_code` varchar(25) CHARACTER SET utf8mb4  NOT NULL COMMENT '当前用户自己的邀请码',
  `inviter_invitation_code` varchar(25) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '邀请人的邀请码',
  `email_address` varchar(64) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '邮箱地址',
  `user_status` varchar(10) CHARACTER SET utf8mb4  NOT NULL DEFAULT 'CREATING' COMMENT 'CREATING-创建中，ENABLE-有效，DISABLE-无效\r\n',
  `user_type` varchar(10) CHARACTER SET utf8mb4  NOT NULL COMMENT 'LOCAL-本地注册用户， THIRD-三方对接用户',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次的登录时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `idx_unique_wallet`(`wallet_address`, `domain_id`) USING BTREE,
  UNIQUE INDEX `idx_unique_invitation`(`own_invitation_code`) USING BTREE,
  UNIQUE INDEX `idx_unique_email`(`email_address`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;


CREATE TABLE IF NOT EXISTS `squid_ticket_index`  (
  `ticket_code` varchar(40) CHARACTER SET utf8mb4  NOT NULL COMMENT '商券号，由本系统生成的，返回给前端使用的',
  `open_index` int(0) NOT NULL COMMENT '开启的位置坐标',
  `index_type` varchar(16) CHARACTER SET utf8mb4  NOT NULL COMMENT '索引类型 IMAGE-图标索引，BONUS-奖金索引',
  `money` bigint(0) NULL DEFAULT NULL COMMENT '如果是BONUS类型，应该存储对应的奖金，单位：分',
  `image_id` bigint(0) NULL DEFAULT NULL COMMENT '如果是IMAGE类型，应该存储图标id',
  PRIMARY KEY (`ticket_code`, `open_index`, `index_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '商券索引信息表' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `squid_order_ticket`  (
  `ticket_code` varchar(40) CHARACTER SET utf8mb4  NOT NULL COMMENT '商券号，由本系统生成的，返回给前端使用的',
  `order_no` bigint(0) NOT NULL COMMENT '对应售出的订单号',
  `external_ticket_id` bigint(0) NOT NULL COMMENT '商券表ID，外部脚本生成的商券数据对应的id',
  `ticket_type` varchar(16) CHARACTER SET utf8mb4  NOT NULL COMMENT '商券类型，SCRATCH-刮刮乐，MINE-挖矿',
  `ticket_status` varchar(16) CHARACTER SET utf8mb4  NOT NULL COMMENT '商券状态，UNOPEN-未开，OPENED-已开，CASHED-已兑奖，OPENING-开采中（矿工券会用到）',
  `ticket_win_money` bigint(0) NOT NULL DEFAULT 0 COMMENT '该商券能够赢得的奖金，单位：分',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ticket_code`) USING BTREE,
  UNIQUE INDEX `unique_idx_ext_ticket_id`(`external_ticket_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  COMMENT = '订单-商券关系表' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `squid_order`  (
  `order_no` bigint(0) NOT NULL,
  `user_id` bigint(0) NOT NULL,
  `order_status` varchar(16) CHARACTER SET utf8mb4  NOT NULL COMMENT 'COMPLETE-完成，DEALING-处理中',
  `order_create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单创建时间',
  `order_update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '订单修改时间',
  `ticket_count` int(0) NOT NULL COMMENT '商券购买数量',
  `ticket_type` varchar(16) CHARACTER SET utf8mb4  NOT NULL,
  PRIMARY KEY (`order_no`) USING BTREE,
  INDEX `idx_perso_code`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  COMMENT = '订单表' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `squid_goods`  (
  `goods_code` varchar(16) CHARACTER SET utf8mb4  NOT NULL COMMENT '商品编码',
  `goods_title` varchar(32) CHARACTER SET utf8mb4  NOT NULL COMMENT '商品名称',
  `goods_price` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '商品价格，单位：分',
  `goods_order` int(0) NULL DEFAULT 0 COMMENT '商品位置排序',
  `goods_parent` varchar(16) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '父级商品编码',
  `goods_status` varchar(10) CHARACTER SET utf8mb4  NOT NULL DEFAULT 'ENABLE' COMMENT '商品状态 ENABLE-有效，DISABLE-无效',
  PRIMARY KEY (`goods_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  COMMENT = '货物信息表' ROW_FORMAT = Dynamic;
