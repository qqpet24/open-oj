/*
 Navicat Premium Data Transfer

 Source Server         : aliYun
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 101.37.20.199:3306
 Source Schema         : online-judge

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 30/12/2021 21:35:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bulletin
-- ----------------------------
DROP TABLE IF EXISTS `bulletin`;
CREATE TABLE `bulletin`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime NOT NULL DEFAULT '2016-05-13 19:24:00',
  `importance` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1007 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bulletin
-- ----------------------------
INSERT INTO `bulletin` VALUES (1004, 32, 'sfd', 'Sdfs', '2021-11-23 12:23:21', 23);
INSERT INTO `bulletin` VALUES (1005, 322, 'sfd', 'Sdfs', '2021-11-23 12:23:21', 23);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problems` json NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `category_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '题目类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, 'array', '[1, 2, 3, 1000]');
INSERT INTO `category` VALUES (2, 'data structure', '[1, 2, 4, 5, 1001]');
INSERT INTO `category` VALUES (3, 'binary tree', '[1, 2, 3, 100]');

-- ----------------------------
-- Table structure for compile_info
-- ----------------------------
DROP TABLE IF EXISTS `compile_info`;
CREATE TABLE `compile_info`  (
  `id` bigint NOT NULL,
  `solution_id` bigint NOT NULL DEFAULT 0,
  `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of compile_info
-- ----------------------------

-- ----------------------------
-- Table structure for contest
-- ----------------------------
DROP TABLE IF EXISTS `contest`;
CREATE TABLE `contest`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `private` tinyint NOT NULL DEFAULT 0,
  `lang_mask` int NOT NULL DEFAULT 0 COMMENT 'bits for LANG to mask',
  `password` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `user_id` bigint NOT NULL,
  `problems` json NULL,
  `participants` json NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contest
-- ----------------------------
INSERT INTO `contest` VALUES (1, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlksblablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlksblablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlksblablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlksblablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlksblablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlksblablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlksblablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlksblablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"name\": \"xiaohei\", \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"name\": \"xiaohong\", \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"name\": \"xiaolan\", \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"name\": \"a\", \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"name\": \"c\", \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"name\": \"e\", \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (2, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (3, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (4, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (5, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (6, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (7, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (8, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (9, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (10, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');
INSERT INTO `contest` VALUES (11, 'contest 1', '2021-12-28 18:22:41', '2021-12-31 18:22:44', '第三周周赛的描述，blablabaljasjlfsjlksdfjlkjlskdfjkldsfalljksfdjlkfsjlkajklsfdaljklsfadjlksfadljkljlks', 0, 0, '123456', 2, '[{\"id\": 1, \"score\": 10, \"title\": \"problem-title\"}, {\"id\": 2, \"score\": 20, \"title\": \"problem-title\"}, {\"id\": 3, \"score\": 30, \"title\": \"problem-title\"}, {\"id\": 4, \"score\": 40, \"title\": \"problem-title\"}]', '[{\"id\": 2, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 4, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 5, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 12, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 13, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}, {\"id\": 14, \"avatar\": \"https://joeschmoe.io/api/v1/random\"}]');

-- ----------------------------
-- Table structure for custom_input
-- ----------------------------
DROP TABLE IF EXISTS `custom_input`;
CREATE TABLE `custom_input`  (
  `id` bigint NOT NULL,
  `solution_id` bigint NOT NULL DEFAULT 0,
  `input_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of custom_input
-- ----------------------------

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES (1, 'hello.txt', 1, '101.37.20.199:8888/group1/M00/00/00/ZSUUx2GOaU6AOdo1AAAAAAAAAAA783.txt', 'txt', 'xiaohong', '2021-11-12');
INSERT INTO `file` VALUES (2, 'hello.txt', 1, '101.37.20.199:8888/group1/M00/00/00/ZSUUx2GOaliAWkorAAAADRdJ1Pc193.txt', 'txt', 'xiaohong', '2021-11-12');
INSERT INTO `file` VALUES (3, '发泄.png', 2, '101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNpIGAHceUAAFQxqNMTB4621.png', 'png', 'xiaohei', '2021-12-30');
INSERT INTO `file` VALUES (4, '发泄.png', 2, '101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNpO-AB--QAAFQxqNMTB4191.png', 'png', 'xiaohei', '2021-12-30');
INSERT INTO `file` VALUES (5, '发泄.png', 2, '101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNpRmAAn63AAFQxqNMTB4117.png', 'png', 'xiaohei', '2021-12-30');
INSERT INTO `file` VALUES (6, '发泄.png', 2, '101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNpSmAfKQSAAFQxqNMTB4798.png', 'png', 'xiaohei', '2021-12-30');
INSERT INTO `file` VALUES (7, '发泄.png', 2, '101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNpiKAUyTXAAFQxqNMTB4296.png', 'png', 'xiaohei', '2021-12-30');
INSERT INTO `file` VALUES (8, '发泄.png', 2, '101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNp16AM1zOAAFQxqNMTB4859.png', 'png', 'xiaohei', '2021-12-30');
INSERT INTO `file` VALUES (9, '发泄.png', 2, 'http://101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNp6WACQHPAAFQxqNMTB4089.png', 'png', 'xiaohei', '2021-12-30');
INSERT INTO `file` VALUES (10, '发泄.png', 2, 'http://101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNp-aAb_OJAAFQxqNMTB4826.png', 'png', 'xiaohei', '2021-12-30');
INSERT INTO `file` VALUES (11, '不惧妖邪.png', 2, 'http://101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNqFmAFFw7AAIKVdxdZo0577.png', 'png', 'xiaohei', '2021-12-30');
INSERT INTO `file` VALUES (12, '发泄.png', 2, 'http://101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNqZOATEjmAAFQxqNMTB4115.png', 'png', 'xiaohei', '2021-12-30');

-- ----------------------------
-- Table structure for loginlog
-- ----------------------------
DROP TABLE IF EXISTS `loginlog`;
CREATE TABLE `loginlog`  (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ip` varchar(46) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_log_index`(`user_id`, `time`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of loginlog
-- ----------------------------

-- ----------------------------
-- Table structure for mail_record
-- ----------------------------
DROP TABLE IF EXISTS `mail_record`;
CREATE TABLE `mail_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `time` datetime NULL DEFAULT NULL,
  `from` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `to` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL COMMENT '0成功1失败',
  `subject` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mail_record
-- ----------------------------
INSERT INTO `mail_record` VALUES (1, '2021-12-30 21:10:32', 's', 's', 0, 'a', 'sd');
INSERT INTO `mail_record` VALUES (2, '2021-12-30 21:04:48', 'a', 'b', 0, 's', 'sd');
INSERT INTO `mail_record` VALUES (3, '2021-12-30 21:04:49', 'a', 'b', 0, 's', 'sd');
INSERT INTO `mail_record` VALUES (4, '2021-12-30 21:13:42', '2256788742@qq.com', '710599274@qq.com', 0, 'Test1', 'Text1:Across Greatwall.');
INSERT INTO `mail_record` VALUES (5, '2021-12-30 21:13:54', '2256788742@qq.com', '710599274@qq.com', 0, 'Test2', 'Text2:Across Greatwall.');
INSERT INTO `mail_record` VALUES (6, '2021-12-30 21:14:31', '2256788742@qq.com', '710599274@qq.com', 0, 'Test1', 'Text1:Across Greatwall.');
INSERT INTO `mail_record` VALUES (7, '2021-12-30 21:14:34', '2256788742@qq.com', '71059799274@qq.com', 1, 'Test2', 'Text2:Across Greatwall.');
INSERT INTO `mail_record` VALUES (8, '2021-12-30 21:16:18', '2256788742@qq.com', '710599274@qq.com', 0, 'Test1', 'Text1:Across Greatwall.');
INSERT INTO `mail_record` VALUES (9, '2021-12-30 21:16:20', '2256788742@qq.com', '71059799274@qq.com', 1, 'Test2', 'Text2:Across Greatwall.');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sender` bigint NOT NULL,
  `receiver` bigint NOT NULL,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `new_message` tinyint(1) NOT NULL DEFAULT 1,
  `reply` tinyint NULL DEFAULT 0,
  `in_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`sender`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1013 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for online
-- ----------------------------
DROP TABLE IF EXISTS `online`;
CREATE TABLE `online`  (
  `id` bigint NOT NULL,
  `ip` varchar(46) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `ua` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `refer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `last_move` int NOT NULL,
  `first_time` int NULL DEFAULT NULL,
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING HASH,
  UNIQUE INDEX `hash`(`id`) USING HASH
) ENGINE = MEMORY AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of online
-- ----------------------------

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `input_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `output_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `sample_input` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `sample_output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `in_date` datetime NULL DEFAULT NULL,
  `time_limit` decimal(10, 3) NOT NULL DEFAULT 0.000,
  `memory_limit` int NOT NULL DEFAULT 0,
  `accepted` int NULL DEFAULT 0,
  `submit` int NULL DEFAULT 0,
  `categories` json NULL,
  `lists` json NULL,
  `difficulty` int NULL DEFAULT NULL,
  `star` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1475775119672766473 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES (1, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 12, 3, '[\"array\", \"data structure\"]', NULL, 0, 5561);
INSERT INTO `problem` VALUES (1000, 'Two Sum', 'Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 45, 46, '[\"array\", \"data structure\"]', NULL, 1, 7892);
INSERT INTO `problem` VALUES (1001, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 2, 9364);
INSERT INTO `problem` VALUES (1002, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 0, 5523);
INSERT INTO `problem` VALUES (1004, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 0, 5523);
INSERT INTO `problem` VALUES (2, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 0, 5561);
INSERT INTO `problem` VALUES (3, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 1, 7892);
INSERT INTO `problem` VALUES (4, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 2, 9364);
INSERT INTO `problem` VALUES (5, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 0, 5523);
INSERT INTO `problem` VALUES (6, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 0, 5523);
INSERT INTO `problem` VALUES (7, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 0, 5561);
INSERT INTO `problem` VALUES (8, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 1, 7892);
INSERT INTO `problem` VALUES (9, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 2, 9364);
INSERT INTO `problem` VALUES (40, 'Two Sum', 'Given an array of integers` nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 0, 5523);
INSERT INTO `problem` VALUES (41, 'Two Sum', 'Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to target.\r\n\r\nYou may assume that each input would have **exactly one solution**, and you may not use the same element twice.\r\n\r\nYou can return the answer in any order.\r\n\r\n**Example 1:**\r\n\r\n```\r\nInput: nums = [2,7,11,15], target = 9\r\nOutput: [0,1]\r\nOutput: Because nums[0] + nums[1] == 9, we return [0, 1].\r\n```\r\n\r\n**Example 2:**\r\n\r\n```\r\nInput: nums = [3,2,4], target = 6\r\nOutput: [1,2]\r\n```\r\n\r\n**Example 3:**\r\n\r\n```\r\nInput: nums = [3,3], target = 6\r\nOutput: [0,1]\r\n```\r\n\r\n**Constraints:**\r\n\r\n- 2 <= nums.length <= 104\r\n\r\n- -109 <= nums[i] <= 109\r\n\r\n- -109 <= target <= 109\r\n\r\n- **Only one valid answer exists.**', NULL, NULL, NULL, NULL, NULL, NULL, 0.000, 0, 0, 0, '[\"array\", \"data structure\"]', NULL, 0, 5523);

-- ----------------------------
-- Table structure for problem_list
-- ----------------------------
DROP TABLE IF EXISTS `problem_list`;
CREATE TABLE `problem_list`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problems` json NULL,
  `star` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem_list
-- ----------------------------
INSERT INTO `problem_list` VALUES (1, 'A', '[1, 2, 5, 4]', 56);
INSERT INTO `problem_list` VALUES (2, 'B', '[22, 352]', 5);

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `time` datetime NOT NULL DEFAULT '2016-05-13 19:24:00',
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` int NOT NULL DEFAULT 0 COMMENT '0正常1禁止访问',
  `ip` varchar(46) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `problem_id` bigint NULL DEFAULT NULL,
  `detail` int NULL DEFAULT NULL COMMENT 'null一个问题下的回复（评论） 如果不是，比如说是5，就是对id为5的comment进行评论（评论的评论）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `author_id`(`user_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES (19, 4, '2021-12-30 19:47:25', 'sads', 0, '127.0.0.1', 1, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_role_name_uindex`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN');
INSERT INTO `role` VALUES (2, 'USER');

-- ----------------------------
-- Table structure for runtime_info
-- ----------------------------
DROP TABLE IF EXISTS `runtime_info`;
CREATE TABLE `runtime_info`  (
  `id` bigint NOT NULL,
  `solution_id` bigint NOT NULL DEFAULT 0,
  `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of runtime_info
-- ----------------------------

-- ----------------------------
-- Table structure for sim
-- ----------------------------
DROP TABLE IF EXISTS `sim`;
CREATE TABLE `sim`  (
  `id` bigint NOT NULL,
  `solution_id` bigint NULL DEFAULT NULL,
  `sim_solution_id` bigint NULL DEFAULT NULL,
  `sim` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sim
-- ----------------------------

-- ----------------------------
-- Table structure for solution
-- ----------------------------
DROP TABLE IF EXISTS `solution`;
CREATE TABLE `solution`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `problem_id` bigint NOT NULL DEFAULT 0,
  `user_id` bigint NOT NULL,
  `time` int NOT NULL DEFAULT 0,
  `memory` int NOT NULL DEFAULT 0,
  `in_date` datetime NOT NULL DEFAULT '2016-05-13 19:24:00',
  `result` smallint NOT NULL DEFAULT 0,
  `language` int UNSIGNED NOT NULL DEFAULT 0,
  `ip` char(46) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `valid` tinyint NOT NULL DEFAULT 1,
  `code_length` int NOT NULL DEFAULT 0,
  `judgetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `pass_rate` decimal(3, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  `lint_error` int UNSIGNED NOT NULL DEFAULT 0,
  `judger` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'LOCAL',
  `file_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`user_id`) USING BTREE,
  INDEX `pid`(`problem_id`) USING BTREE,
  INDEX `res`(`result`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of solution
-- ----------------------------

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varbinary(60) NOT NULL,
  `status` int NOT NULL DEFAULT 0,
  `top_level` int NOT NULL DEFAULT 0,
  `contest_id` bigint NULL DEFAULT NULL,
  `problem_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cid`(`contest_id`, `problem_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `register_time` datetime NULL DEFAULT NULL,
  `access_time` datetime NULL DEFAULT NULL,
  `role_id` bigint NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `score` int NULL DEFAULT NULL,
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `submit` int NULL DEFAULT NULL,
  `solved` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'xiao-hei', '$2a$10$Z0be4zg.xFwPdQVQo0fi/.xjneM1nOYHwbT1NBDaX78P2EufmT5ue', 'xiaohei', '127.0.0.1', '2021-12-01 13:14:24', '2021-12-30 21:02:28', 1, 'http://101.37.20.199:8888/group1/M00/00/00/ZSUUx2HNqZOATEjmAAFQxqNMTB4115.png', 999, 'xmu-xiaohei', 'xiao-hei@xmu.com', 5976, 5873);
INSERT INTO `user` VALUES (4, 'xiaohong', '$2a$10$1fqjZc0PQD.ynq.zptoGdePr/5wd00jMgM70VmcrVUi6VBsT3zEl6', 'xiaohong', '0:0:0:0:0:0:0:1', '2021-12-01 15:07:11', '2021-12-30 15:43:43', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (5, 'xiaolan', '$2a$10$M.v38bc4Cs5/I3GkCcL/We1vydV75II8L5Em4UkiTUjSm0WWTPrKu', 'xiaolan', '0:0:0:0:0:0:0:1', '2021-12-01 15:20:58', '2021-12-01 15:20:58', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (12, 'a', '$2a$10$d0D4seYc7bSBOtH1uYh4DOEh5OvSJfcDn43kfEjXrjZ1bp.iLHAyu', 'a', '0:0:0:0:0:0:0:1', '2021-12-01 15:30:41', '2021-12-01 16:54:18', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (13, 'c', '$2a$10$bmereniaglKs8ZmGVNC9UO/m8pEetvfxPW3fY7hOQAlEoDF72t5yO', 'c', '0:0:0:0:0:0:0:1', '2021-12-01 15:40:39', '2021-12-01 15:40:39', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (14, 'e', '$2a$10$L2MQtVRWMsn93zdoWHOsDOJdNaoecQZ9p2OdgBERTD3v8CW8HX5xS', 'e', '0:0:0:0:0:0:0:1', '2021-12-01 15:41:04', '2021-12-01 15:41:04', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (15, 'd', '$2a$10$Ra6g2wRwVr.ZI1q7UxUlVeMw1pe1Jo3edTj2k1q/4m5wZR.986gIS', 'd', '0:0:0:0:0:0:0:1', '2021-12-01 15:53:41', '2021-12-01 15:53:41', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (16, 'f', '$2a$10$lrEauZL7vnPUuyLgKQTuiuwvv5o2na63dhldnozGh21aHwoS5yyIi', 'f', '0:0:0:0:0:0:0:1', '2021-12-01 15:55:05', '2021-12-01 16:07:29', 2, NULL, NULL, NULL, NULL, NULL, NULL);

