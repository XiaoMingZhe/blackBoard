/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : dbblackboard

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-20 11:45:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_blackboard`
-- ----------------------------
DROP TABLE IF EXISTS `tb_blackboard`;
CREATE TABLE `tb_blackboard` (
  `blackboard_id` char(32) NOT NULL,
  `enterprise_id` char(32) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` char(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`blackboard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `tb_blcaklist`
-- ----------------------------
DROP TABLE IF EXISTS `tb_blcaklist`;
CREATE TABLE `tb_blcaklist` (
  `blacklt_id` char(32) NOT NULL,
  `blacklter_id` char(32) DEFAULT NULL,
  `be_blacklter_id` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`blacklt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_blcaklist
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_comment`
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `comment_id` char(32) NOT NULL,
  `blackboard_id` char(32) DEFAULT NULL,
  `reply_id` char(32) DEFAULT NULL,
  `commenter_id` char(32) DEFAULT NULL,
  `comment_content` varchar(400) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `enterprise_id` char(32) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_image`
-- ----------------------------
DROP TABLE IF EXISTS `tb_image`;
CREATE TABLE `tb_image` (
  `image_id` char(32) NOT NULL,
  `blackboard_id` char(32) DEFAULT NULL,
  `image_path` varchar(500) DEFAULT NULL,
  `image_name` varchar(60) DEFAULT NULL,
  `order_number` int(10) DEFAULT NULL,
  `upload_date` date DEFAULT NULL,
  `enterprise_id` char(32) DEFAULT NULL,
  PRIMARY KEY (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

