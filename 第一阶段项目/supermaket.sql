/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50529
 Source Host           : localhost:3306
 Source Schema         : supermaket

 Target Server Type    : MySQL
 Target Server Version : 50529
 File Encoding         : 65001

 Date: 01/04/2019 16:53:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clock_info
-- ----------------------------
DROP TABLE IF EXISTS `clock_info`;
CREATE TABLE `clock_info`  (
  `clock_id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clock_in_time` datetime NULL DEFAULT NULL,
  `clock_off_time` datetime NULL DEFAULT NULL,
  `clock_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`clock_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of clock_info
-- ----------------------------
INSERT INTO `clock_info` VALUES (1, 'S0002', '2018-08-01 11:30:52', '2018-08-01 18:30:30', '2018-08-01');
INSERT INTO `clock_info` VALUES (2, 's0004', '2018-08-02 09:46:52', '2018-08-02 17:30:30', '2018-08-02');
INSERT INTO `clock_info` VALUES (3, 's0005', '2018-08-03 08:46:52', '2018-08-03 17:30:30', '2018-08-03');
INSERT INTO `clock_info` VALUES (4, 's0003', '2018-08-04 09:13:52', '2018-08-04 18:30:30', '2018-08-04');
INSERT INTO `clock_info` VALUES (5, 's0004', '2018-08-05 09:46:52', '2018-08-05 18:30:30', '2018-08-05');
INSERT INTO `clock_info` VALUES (6, 's0005', NULL, '2018-08-06 18:30:30', '2018-08-06');
INSERT INTO `clock_info` VALUES (7, 'S0005', '2018-08-01 08:46:52', '2018-08-01 18:30:30', '2018-08-01');
INSERT INTO `clock_info` VALUES (8, 'S0002', '2018-08-02 09:46:52', '2018-08-02 17:30:30', '2018-08-02');
INSERT INTO `clock_info` VALUES (9, 'S0006', '2018-08-03 08:46:52', '2018-08-03 17:30:30', '2018-08-03');
INSERT INTO `clock_info` VALUES (10, 'S0003', '2018-08-01 09:46:52', '2018-08-01 18:30:30', '2018-08-01');
INSERT INTO `clock_info` VALUES (11, 'S0004', '2018-08-02 09:46:52', '2018-08-02 17:30:30', '2018-08-02');
INSERT INTO `clock_info` VALUES (12, 'S0003', '2018-08-03 08:46:52', '2018-08-03 17:30:30', '2018-08-03');
INSERT INTO `clock_info` VALUES (13, 'S0004', '2018-08-04 09:13:52', '2018-08-04 18:30:30', '2018-08-04');
INSERT INTO `clock_info` VALUES (14, 'S0005', '2018-08-05 09:46:52', '2018-08-05 14:30:30', '2018-08-05');
INSERT INTO `clock_info` VALUES (15, 'S0006', NULL, '2018-08-06 18:30:30', '2018-08-06');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` int(11) NOT NULL,
  `remark` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`number`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  INDEX `role_id_FK`(`role`) USING BTREE,
  CONSTRAINT `role_id_FK` FOREIGN KEY (`role`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('S0001', '洪七公', 'admin', '男', '15919754435', 1, 1);
INSERT INTO `employee` VALUES ('S0002', '杨过', '1234', '男', '15919764435', 2, 1);
INSERT INTO `employee` VALUES ('S0003', '黄蓉', '1111', '女', '15949754435', 2, 1);
INSERT INTO `employee` VALUES ('S0004', '小龙女', '1234', '女', '15919757435', 2, 1);
INSERT INTO `employee` VALUES ('S0005', '郭靖', 'admin', '男', '15919754485', 1, 1);
INSERT INTO `employee` VALUES ('S0006', '郭襄', '1234', '男', '15919759485', 3, 1);
INSERT INTO `employee` VALUES ('S0007', '黄老邪', '1111', '男', '15919754425', 3, 1);
INSERT INTO `employee` VALUES ('S0008', '段誉', '1111', '男', '15919724485', 3, 1);
INSERT INTO `employee` VALUES ('S0009', '段誉', '1111', '男', '15919724985', 2, 1);
INSERT INTO `employee` VALUES ('S0010', '乔峰', '1234', '男', '15919754415', 2, 1);
INSERT INTO `employee` VALUES ('S0011', '扫地僧', 'admin', '男', '15919751415', 1, 1);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `c_number` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_price` decimal(10, 2) NULL DEFAULT NULL,
  `vip_price` decimal(10, 2) NULL DEFAULT NULL,
  `inventory` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`c_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '洗衣粉', 10.50, 8.40, 990);
INSERT INTO `goods` VALUES (2, '洗衣机', 1200.00, 960.00, 300);
INSERT INTO `goods` VALUES (3, '电视机', 5000.00, 4000.00, 6);
INSERT INTO `goods` VALUES (4, '空调', 3400.00, 2720.00, 45);
INSERT INTO `goods` VALUES (5, '电磁炉', 120.00, 96.00, 3);
INSERT INTO `goods` VALUES (6, '牙膏', 12.00, 9.60, 5000);
INSERT INTO `goods` VALUES (7, '洗面奶', 10.00, 8.00, 30);
INSERT INTO `goods` VALUES (8, '香皂', 4.00, 3.20, 800);
INSERT INTO `goods` VALUES (9, '奶粉', 100.00, 80.00, 5000);
INSERT INTO `goods` VALUES (10, '苹果', 10.00, 8.00, 400);
INSERT INTO `goods` VALUES (11, '大米', 3.00, 2.40, 60000);
INSERT INTO `goods` VALUES (12, '烂白菜', 12.00, 9.60, 5000);
INSERT INTO `goods` VALUES (13, '口香糖', 11.00, 8.80, 1008);
INSERT INTO `goods` VALUES (14, '大白兔', 10.00, 8.00, 4500);
INSERT INTO `goods` VALUES (15, '味精', 1.00, 0.80, 50000);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL,
  `r_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员');
INSERT INTO `role` VALUES (2, '收银员');
INSERT INTO `role` VALUES (3, '采购员');

-- ----------------------------
-- Table structure for sell_info
-- ----------------------------
DROP TABLE IF EXISTS `sell_info`;
CREATE TABLE `sell_info`  (
  `s_c_number` int(50) NULL DEFAULT NULL,
  `s_quantity` int(11) NULL DEFAULT NULL,
  `s_time` datetime NULL DEFAULT NULL,
  `s_e_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_vip_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `s_c_number_FK`(`s_c_number`) USING BTREE,
  INDEX `s_e_number_FK`(`s_e_number`) USING BTREE,
  INDEX `s_e_vip_number`(`s_vip_number`) USING BTREE,
  CONSTRAINT `s_e_vip_number` FOREIGN KEY (`s_vip_number`) REFERENCES `vip` (`v_number`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `s_c_number_FK` FOREIGN KEY (`s_c_number`) REFERENCES `goods` (`c_number`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `s_e_number_FK` FOREIGN KEY (`s_e_number`) REFERENCES `employee` (`number`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sell_info
-- ----------------------------
INSERT INTO `sell_info` VALUES (2, 2, '2019-01-17 23:35:33', 'S0002', NULL);
INSERT INTO `sell_info` VALUES (2, 100, '2019-04-01 14:17:24', 's0002', 'vip201901020001');
INSERT INTO `sell_info` VALUES (2, 100, '2019-04-01 14:18:28', 's0002', 'vip201901020001');

-- ----------------------------
-- Table structure for vip
-- ----------------------------
DROP TABLE IF EXISTS `vip`;
CREATE TABLE `vip`  (
  `v_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `v_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_score` int(11) NULL DEFAULT NULL,
  `v_phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `v_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`v_number`) USING BTREE,
  UNIQUE INDEX `v_phone`(`v_phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vip
-- ----------------------------
INSERT INTO `vip` VALUES ('null', 'null', NULL, 'null', '0000-00-00');
INSERT INTO `vip` VALUES ('vip201901020001', '东方不败', 2025, '15844760501', '2019-01-17');
INSERT INTO `vip` VALUES ('vip201901020002', '令狐冲', 150, '15844760502', '2019-01-17');
INSERT INTO `vip` VALUES ('vip201901020003', '任盈盈', 1200, '15844760401', '2019-01-17');

-- ----------------------------
-- Table structure for work_date
-- ----------------------------
DROP TABLE IF EXISTS `work_date`;
CREATE TABLE `work_date`  (
  `work_date_id` int(11) NOT NULL AUTO_INCREMENT,
  `work_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`work_date_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of work_date
-- ----------------------------
INSERT INTO `work_date` VALUES (1, '2018-08-01');
INSERT INTO `work_date` VALUES (2, '2018-08-02');
INSERT INTO `work_date` VALUES (3, '2018-08-03');
INSERT INTO `work_date` VALUES (4, '2018-08-04');
INSERT INTO `work_date` VALUES (5, '2018-08-06');
INSERT INTO `work_date` VALUES (6, '2018-08-07');
INSERT INTO `work_date` VALUES (7, '2018-08-08');
INSERT INTO `work_date` VALUES (8, '2018-08-09');
INSERT INTO `work_date` VALUES (9, '2018-08-10');
INSERT INTO `work_date` VALUES (10, '2018-08-13');
INSERT INTO `work_date` VALUES (11, '2018-08-14');
INSERT INTO `work_date` VALUES (12, '2018-08-15');
INSERT INTO `work_date` VALUES (13, '2018-08-16');
INSERT INTO `work_date` VALUES (14, '2018-08-17');
INSERT INTO `work_date` VALUES (15, '2018-08-18');
INSERT INTO `work_date` VALUES (16, '2018-08-20');
INSERT INTO `work_date` VALUES (17, '2018-08-21');
INSERT INTO `work_date` VALUES (18, '2018-08-22');
INSERT INTO `work_date` VALUES (19, '2018-08-23');
INSERT INTO `work_date` VALUES (20, '2018-08-24');
INSERT INTO `work_date` VALUES (21, '2018-08-27');
INSERT INTO `work_date` VALUES (22, '2018-08-28');
INSERT INTO `work_date` VALUES (23, '2019-01-17');
INSERT INTO `work_date` VALUES (24, '2019-01-18');
INSERT INTO `work_date` VALUES (25, '2019-01-19');
INSERT INTO `work_date` VALUES (26, '2019-01-20');
INSERT INTO `work_date` VALUES (27, '2019-01-21');
INSERT INTO `work_date` VALUES (28, '2019-01-22');
INSERT INTO `work_date` VALUES (29, '2019-01-23');

-- ----------------------------
-- View structure for check_info
-- ----------------------------
DROP VIEW IF EXISTS `check_info`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`localhost` SQL SECURITY DEFINER VIEW `check_info` AS select t.work_date,t.employee_no,clock_in_time,clock_off_time, 
	case 
	        when diff_in_time<-120
			then '旷工'
		when diff_in_time<0 
			then '迟到'
		when diff_in_time>0
			then '正常'
		else '忘记打卡'
	end as diff_in_status,
	case 
	        when diff_off_time>120
			then '旷工'
		when diff_off_time>0 
			then '早退'
		when diff_off_time<0
			then '正常'
		else '忘记打卡'
	end as diff_off_time


from check_info_son t ;

-- ----------------------------
-- View structure for check_info_son
-- ----------------------------
DROP VIEW IF EXISTS `check_info_son`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`localhost` SQL SECURITY DEFINER VIEW `check_info_son` AS select work_date,employee_no,clock_in_time,clock_off_time,
TIMESTAMPDIFF(MINUTE, clock_in_time, concat(clock_date,' 09:00:00')) as diff_in_time,
TIMESTAMPDIFF(MINUTE, clock_off_time, concat(clock_date,' 18:00:00')) as diff_off_time
from work_date w 
left join clock_info t 
on t.clock_date = w.work_date ;

-- ----------------------------
-- Triggers structure for table sell_info
-- ----------------------------
DROP TRIGGER IF EXISTS `t1`;
delimiter ;;
CREATE TRIGGER `t1` AFTER INSERT ON `sell_info` FOR EACH ROW begin
update goods set inventory=inventory-new.s_quantity where c_number=new.s_c_number;
update VIP set v_score =v_score+(new.s_quantity*10) where v_number=new.s_vip_number;
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
