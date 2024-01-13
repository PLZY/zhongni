-- 表字段修改
-- ALTER TABLE `squid_user` CHANGE COLUMN  `aaaa` `aaaa` varchar(12) DEFAULT NULL COMMENT '最后一次的登录时间';

-- 表字段新增
-- ALTER TABLE `squid_user` add COLUMN `aaaa` VARCHAR(20) DEFAULT NULL;

-- 表字段删除
-- ALTER TABLE  `squid_user` DROP `aaaa`;

-- 加普通索引
-- alter table `squid_user` add index idx_aaaa (aaaa);

-- 加唯一索引
-- alter table `squid_user` add unique idx_aaaa(aaaa);

-- 删除某个索引
-- alter table `squid_user` drop index idx_aaaa;


-- 新增存储过程，可用于重复执行新增表字段的SQL语句
-- call add_table_column('squid_user', 'aaaa', 'varchar(32) DEFAULT NULL', '3333333');

-- 新增存储过程，可用于重复执行新增表索引的SQL语句
-- call add_table_index('squid_user', 'idx_aaaa', 'unique', 'aaaa,bbbb');

DROP PROCEDURE if exists ADD_TABLE_COLUMN;
CREATE PROCEDURE `ADD_TABLE_COLUMN`(IN tableName varchar(64),IN columnName varchar(64),IN columnDesc varchar(64), IN columnComment varchar(256))
BEGIN
    IF NOT EXISTS (SELECT * FROM information_schema.COLUMNS
                   WHERE  table_name = tableName
                     AND column_name = columnName)
    THEN
        SET @strSql = CONCAT('ALTER TABLE ', tableName, ' ADD COLUMN ', columnName, ' ', columnDesc, ' COMMENT ','\'', columnComment, '\'') ;
        PREPARE stmt FROM @strSql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END
;

DROP PROCEDURE if exists ADD_TABLE_INDEX;
CREATE PROCEDURE `ADD_TABLE_INDEX`(IN `tableName` varchar(64),IN `indexName` varchar(64),IN `indexType` varchar(10),IN `indexColumns` varchar(256))
BEGIN
    IF NOT EXISTS (SELECT * FROM information_schema.statistics
                   WHERE  table_name = tableName
                     AND index_name = indexName)
    THEN
        SET @strSql = CONCAT('ALTER TABLE ', tableName, ' ADD ', indexType,' ', indexName, '(', indexColumns, ')') ;
        PREPARE stmt FROM @strSql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END
;