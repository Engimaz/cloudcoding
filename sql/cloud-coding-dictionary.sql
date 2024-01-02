/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:4006
 Source Schema         : cloud-coding-dictionary

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 03/01/2024 00:22:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `group_id` bigint NULL DEFAULT NULL COMMENT '组id',
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1738553630786719748 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES (1734620415382392833, 1734620415382392832, 'JDK8', 'JDK8');
INSERT INTO `dictionary` VALUES (1734620415382392834, 1734620415382392832, 'JDK11', 'JDK11');
INSERT INTO `dictionary` VALUES (1734620601022287873, 1734620601022287872, 'G++', 'G++');
INSERT INTO `dictionary` VALUES (1734620744098385921, 1734620744098385920, 'Python3', 'Python3');
INSERT INTO `dictionary` VALUES (1734621304990076929, 1734621304990076928, '公开', 'Public');
INSERT INTO `dictionary` VALUES (1734621304990076930, 1734621304990076928, '私有', 'Private');
INSERT INTO `dictionary` VALUES (1734621398598553600, 1734620206686408704, 'Java', 'Java');
INSERT INTO `dictionary` VALUES (1734621398598553601, 1734620206686408704, 'C++', 'C++');
INSERT INTO `dictionary` VALUES (1734621398598553602, 1734620206686408704, 'Python', 'Python');
INSERT INTO `dictionary` VALUES (1737846877828812800, 1737842895593017344, '公开', 'ApiPublic');
INSERT INTO `dictionary` VALUES (1737846877828812801, 1737842895593017344, '匿名', 'ApiAnonymity');
INSERT INTO `dictionary` VALUES (1737846877828812802, 1737842895593017344, '验证', 'ApiAuth');
INSERT INTO `dictionary` VALUES (1737846934896513024, 1737841934510198784, '正常', 'ApiNormal');
INSERT INTO `dictionary` VALUES (1737846934896513025, 1737841934510198784, '禁用', 'ApiDisabled');
INSERT INTO `dictionary` VALUES (1738553003985735680, 1738552716533305344, '公益组织', 'OrgNonProfit');
INSERT INTO `dictionary` VALUES (1738553003989929984, 1738552716533305344, '私立组织', 'OrgPrivate');
INSERT INTO `dictionary` VALUES (1738553630786719745, 1738553630786719744, '审核中', 'OrgAudit');
INSERT INTO `dictionary` VALUES (1738553630786719746, 1738553630786719744, '审核通过', 'OrgPass');
INSERT INTO `dictionary` VALUES (1738553630786719747, 1738553630786719744, '拒绝通过', 'OrgRefuse');
INSERT INTO `dictionary` VALUES (1742162235461079040, 1742162235456884736, '正常', 'PosNormal');
INSERT INTO `dictionary` VALUES (1742162235461079041, 1742162235456884736, '禁用', 'PosDisabled');

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES (1734620206686408704, 'Languages', '系统支持那些语言运行');
INSERT INTO `group` VALUES (1734620415382392832, 'JavaSDK', '支持java运行的SDK有哪些');
INSERT INTO `group` VALUES (1734620601022287872, 'C++SDK', '支持C++运行的SDK');
INSERT INTO `group` VALUES (1734620744098385920, 'PythonSDK', '支持Python运行的SDK');
INSERT INTO `group` VALUES (1734621304990076928, 'ProjectPolicy', '项目的测试 是公开还是私有');
INSERT INTO `group` VALUES (1737841934510198784, 'ApiStatus', '接口状态 可以使得接口禁止不对外访问');
INSERT INTO `group` VALUES (1737842895593017344, 'ApiScope', '接口可见性  公开直接可以访问 匿名 需要登录才能访问 验证 需要登录并且需权限');
INSERT INTO `group` VALUES (1738552716533305344, 'OrgType', '组织类型');
INSERT INTO `group` VALUES (1738553630786719744, 'OrgStatus', '组织状态');
INSERT INTO `group` VALUES (1742162235456884736, 'PositionStatus', '社团职位状态');

SET FOREIGN_KEY_CHECKS = 1;
