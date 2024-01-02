/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:4006
 Source Schema         : cloud-coding-program

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 03/01/2024 00:21:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `folder_id` bigint NOT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'undefinedname' COMMENT '文件名',
  `create_time` datetime NOT NULL COMMENT '文件创建时间',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1728470579159371776 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES (263872, 264384, '#include<stdio.h>\r\n\r\nint main(){\r\n\r\n    printf(\"hello world \\n\");\r\n\r\n    int a,b;\r\n    scanf(\"%d %d\",&a,&b);\r\n\r\n    printf(\"%d\",a+b);\r\n\r\n    return 0;\r\n}', 'main.cpp', '2023-10-15 02:53:55', '2023-10-16 18:29:49');
INSERT INTO `file` VALUES (342208, 2240, 'sadsa\r\nasd', 'asd', '2023-10-15 03:43:11', '2023-10-15 17:23:28');
INSERT INTO `file` VALUES (720832, 186816, 'asdsa\r\n\r\n\r\nasd', 'asd', '2023-10-15 03:41:56', '2023-10-15 11:48:27');
INSERT INTO `file` VALUES (1728470405531963392, 1728470300221378560, 'asd\r\n\r\n\r\nasd', 'file1', '2023-11-26 01:47:13', '2023-12-09 21:35:58');
INSERT INTO `file` VALUES (1728470434447495168, 1728470300221378560, 'asd\r\n\r\n\r\nzxc', 'file2', '2023-11-26 01:47:20', '2023-12-09 21:36:05');
INSERT INTO `file` VALUES (1728470579159371776, 1728470489514512384, '', 'file1-1', '2023-11-26 01:47:54', '2023-11-26 01:47:54');

-- ----------------------------
-- Table structure for folder
-- ----------------------------
DROP TABLE IF EXISTS `folder`;
CREATE TABLE `folder`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NOT NULL COMMENT '父级ID',
  `program_id` bigint NOT NULL COMMENT '所属工程id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件夹名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1728470489514512384 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of folder
-- ----------------------------
INSERT INTO `folder` VALUES (2240, 186816, 379456, 'tst');
INSERT INTO `folder` VALUES (163840, 163840, 116992, 'trsa');
INSERT INTO `folder` VALUES (186816, 846592, 379456, 'sad');
INSERT INTO `folder` VALUES (264384, 264384, 659904, 'hello_world');
INSERT INTO `folder` VALUES (532032, 703744, 990400, 'main.cpp');
INSERT INTO `folder` VALUES (599744, 599744, 192768, 'te');
INSERT INTO `folder` VALUES (703744, 703744, 990400, 'aichen');
INSERT INTO `folder` VALUES (846592, 846592, 379456, 'sa');
INSERT INTO `folder` VALUES (1728470211805450240, 1728470211805450240, 1728470211704786944, 'ce');
INSERT INTO `folder` VALUES (1728470300221378560, 1728470211805450240, 1728470211704786944, 'folder1');
INSERT INTO `folder` VALUES (1728470360405446656, 1728470211805450240, 1728470211704786944, 'folder2');
INSERT INTO `folder` VALUES (1728470489514512384, 1728470300221378560, 1728470211704786944, 'folder1-1');

-- ----------------------------
-- Table structure for program
-- ----------------------------
DROP TABLE IF EXISTS `program`;
CREATE TABLE `program`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'undefined name' COMMENT '工程名',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工程描述',
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sdk` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编译器版本 jdk8 jdk11 jdk17 gcc11 py3.7',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1734975389509488641 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of program
-- ----------------------------
INSERT INTO `program` VALUES (116992, 'trsa', 'asd', 'C++', 'gcc:latest', '2023-10-19 13:18:58', '2023-10-19 13:18:58');
INSERT INTO `program` VALUES (192768, 'te', 'asd', 'C', 'gcc:latest', '2023-10-19 13:18:17', '2023-10-19 13:18:17');
INSERT INTO `program` VALUES (379456, 'sa', 'asd', 'cpp', 'gcc:latest', '2023-10-15 03:35:21', '2023-10-15 03:35:21');
INSERT INTO `program` VALUES (659904, 'hello_world', '测试C', 'cpp', 'gcc:latest', '2023-10-15 02:47:55', '2023-10-15 02:47:55');
INSERT INTO `program` VALUES (990400, 'aichen', 'ts', 'C++', 'gcc:latest', '2023-10-15 17:57:30', '2023-10-15 17:57:30');
INSERT INTO `program` VALUES (1728470211704786944, 'ce', 'qwe', 'C++', 'gcc:latest', '2023-11-26 01:46:27', '2023-11-26 01:46:27');
INSERT INTO `program` VALUES (1734975389509488640, '测试项目', 'sdk', 'Java', 'JDK8', '2023-12-14 00:35:42', '2023-12-14 00:35:42');
INSERT INTO `program` VALUES (1734975389509488641, '测试项目', 'sdk', 'Java', 'JDK8', '2023-12-14 00:35:42', '2023-12-14 00:35:42');

-- ----------------------------
-- Table structure for program_user
-- ----------------------------
DROP TABLE IF EXISTS `program_user`;
CREATE TABLE `program_user`  (
  `user_id` bigint UNSIGNED NOT NULL,
  `program_id` bigint NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '该用户在此工程中的角色 负责人 协助者',
  `id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1728470211784478720 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of program_user
-- ----------------------------
INSERT INTO `program_user` VALUES (1, 116992, 'master', 306240);
INSERT INTO `program_user` VALUES (1, 659904, 'master', 473856);
INSERT INTO `program_user` VALUES (1, 192768, 'master', 547840);
INSERT INTO `program_user` VALUES (1, 379456, 'master', 553536);
INSERT INTO `program_user` VALUES (1, 990400, 'master', 747392);
INSERT INTO `program_user` VALUES (1718849833810399232, 1728470211704786944, 'master', 1728470211784478720);

SET FOREIGN_KEY_CHECKS = 1;
