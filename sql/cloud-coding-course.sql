/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:4006
 Source Schema         : cloud-coding-course

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 03/01/2024 00:22:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片id\n将来从资源服务中查询对应的图片作为封面',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程描述',
  `create_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1720748810524823553 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1720748501459144704, 'http://127.0.0.1:13001/cloud-coding-resource/resource/1720748465680338946', '测试课程', '测试课程', '2023-11-04 18:23:07.385349');
INSERT INTO `course` VALUES (1720748501459144705, 'http://127.0.0.1:13001/cloud-coding-resource/resource/1720748465680338946', '测试课程', '测试课程', '2023-11-04 18:23:07.385349');
INSERT INTO `course` VALUES (1720748501459144706, 'http://127.0.0.1:13001/cloud-coding-resource/resource/1720748465680338946', '测试课程', '测试课程', '2023-11-04 18:23:07.385349');
INSERT INTO `course` VALUES (1720748507872235520, 'http://127.0.0.1:13001/cloud-coding-resource/resource/1720748465680338946', '测试课程', '测试课程', '2023-11-04 18:23:08.895912');
INSERT INTO `course` VALUES (1720748531981094912, 'http://127.0.0.1:13001/cloud-coding-resource/resource/1720748465680338946', '测试课程', '测试课程', '2023-11-04 18:23:14.643319');
INSERT INTO `course` VALUES (1720748810524823552, 'http://127.0.0.1:13001/cloud-coding-resource/resource/1720748465680338946', '测试课程', '测试课程', '2023-11-04 18:24:21.054366');

-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `unit_id` bigint NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点资源id',
  `name` bigint NOT NULL COMMENT '节点名称',
  `type` bigint NOT NULL COMMENT '节点类型 可以是 视频节点 可以是 资料节点',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NOT NULL COMMENT '节点创建时间',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of node
-- ----------------------------

-- ----------------------------
-- Table structure for unit
-- ----------------------------
DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `course_id` bigint NOT NULL COMMENT '课程id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单元名称',
  `order` int NOT NULL COMMENT '单元排序',
  `create_time` datetime NOT NULL COMMENT '单元创建时间',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of unit
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
