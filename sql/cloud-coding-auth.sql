/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:4006
 Source Schema         : cloud-coding-auth

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 03/01/2024 00:22:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authorization
-- ----------------------------
DROP TABLE IF EXISTS `authorization`;
CREATE TABLE `authorization`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `identity_type` int NOT NULL COMMENT '登录类型(手机号/邮箱) 或第三方应用名称 (微信/微博等)',
  `identifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号/邮箱/第三方的唯一标识',
  `credential` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码凭证 (自建账号的保存密码, 第三方的保存 token)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1718849834611511297 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authorization
-- ----------------------------
INSERT INTO `authorization` VALUES (1718849834313715712, 1718849833810399232, 1, 'fred', '$2a$10$72gp1DdOEudQCwpFgdk8/.TqEunkg8e3gAPH/qyeVHcvWR8P6aitm');
INSERT INTO `authorization` VALUES (1718849834473099264, 1718849833810399232, 3, '2291649168@qq.com', '$2a$10$72gp1DdOEudQCwpFgdk8/.TqEunkg8e3gAPH/qyeVHcvWR8P6aitm');
INSERT INTO `authorization` VALUES (1718849834611511296, 1718849833810399232, 2, '15177457696', '$2a$10$72gp1DdOEudQCwpFgdk8/.TqEunkg8e3gAPH/qyeVHcvWR8P6aitm');

-- ----------------------------
-- Table structure for feature
-- ----------------------------
DROP TABLE IF EXISTS `feature`;
CREATE TABLE `feature`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能名',
  `status` bigint NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 939073 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feature
-- ----------------------------

-- ----------------------------
-- Table structure for feature_organization
-- ----------------------------
DROP TABLE IF EXISTS `feature_organization`;
CREATE TABLE `feature_organization`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `organization_id` bigint NOT NULL,
  `feature_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 935875 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feature_organization
-- ----------------------------

-- ----------------------------
-- Table structure for feature_url
-- ----------------------------
DROP TABLE IF EXISTS `feature_url`;
CREATE TABLE `feature_url`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `feature_id` bigint NOT NULL,
  `url_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feature_url
-- ----------------------------
INSERT INTO `feature_url` VALUES (21, 65024, 1);
INSERT INTO `feature_url` VALUES (22, 254272, 1);
INSERT INTO `feature_url` VALUES (23, 533184, 1);

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(48) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '资源的id，多个用逗号分隔',
  `client_secret` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '客户端的秘钥',
  `scope` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '客户端的权限，多个用逗号分隔',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '授权类型，五种，多个用逗号分隔',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '授权码模式的跳转uri',
  `authorities` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限，多个用逗号分隔',
  `access_token_validity` int NULL DEFAULT NULL COMMENT 'access_token的过期时间，单位毫秒，覆盖掉硬编码',
  `refresh_token_validity` int NULL DEFAULT NULL COMMENT 'refresh_token的过期时间，单位毫秒，覆盖掉硬编码',
  `additional_information` varchar(4096) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '扩展字段，JSON',
  `autoapprove` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '默认false，是否自动授权',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('cloud-coding', 'res1', '$2a$10$HWuOIx8C.YvlhLwp2j5LYe/r8B04xtcFmuu6t1XEBrnr2JLGFcc0q', 'all', 'authorization_code,client_credentials,implicit,refresh_token,password,phone,gitee', 'http://www.baidu.com', NULL, NULL, NULL, NULL, 'false');

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名',
  `status` bigint NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织头像',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织宣传图',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织介绍',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织地区',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织地址',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织类型',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1717572640765513729 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES (1742216059408224256, '测试组织', 1738553630786719745, 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210839310319617', 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210847950585857', '测试组织', '130102001', '123', '1738553003985735680', '2024-01-03 00:07:32', '2024-01-03 00:07:32');
INSERT INTO `organization` VALUES (1742216145693446144, '测试组织', 1738553630786719745, 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210839310319617', 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210847950585857', '测试组织', '130102001', '123', '1738553003985735680', '2024-01-03 00:08:25', '2024-01-03 00:08:25');
INSERT INTO `organization` VALUES (1742217372082769921, '测试组织', 1738553630786719745, 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210839310319617', 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210847950585857', '测试组织', '130102001', '123', '1738553003985735680', '2024-01-03 00:12:45', '2024-01-03 00:12:45');
INSERT INTO `organization` VALUES (1742217412985622528, '测试组织', 1738553630786719745, 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210839310319617', 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210847950585857', '测试组织', '130102001', '123', '1738553003985735680', '2024-01-03 00:12:56', '2024-01-03 00:12:56');
INSERT INTO `organization` VALUES (1742217673149911040, '测试组织', 1738553630786719745, 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210839310319617', 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210847950585857', '测试组织', '130102001', '123', '1738553003985735680', '2024-01-03 00:16:03', '2024-01-03 00:16:03');
INSERT INTO `organization` VALUES (1742218390887600128, '测试组织', 1738553630786719745, 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210839310319617', 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210847950585857', '测试组织', '130102001', '123', '1738553003985735680', '2024-01-03 00:16:49', '2024-01-03 00:16:49');
INSERT INTO `organization` VALUES (1742218475130195968, '测试组织', 1738553630786719745, 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210839310319617', 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210847950585857', '测试组织', '130102001', '123', '1738553003985735680', '2024-01-03 00:17:09', '2024-01-03 00:17:09');
INSERT INTO `organization` VALUES (1742218563428683776, '测试组织', 1738553630786719745, 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210839310319617', 'http://127.0.0.1:3001/cloud-coding-resource/resource/1742210847950585857', '测试组织', '130102001', '123', '1738553003985735680', '2024-01-03 00:17:29', '2024-01-03 00:17:29');

-- ----------------------------
-- Table structure for organization_record
-- ----------------------------
DROP TABLE IF EXISTS `organization_record`;
CREATE TABLE `organization_record`  (
  `id` bigint NOT NULL,
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '组织id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请理由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of organization_record
-- ----------------------------

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `organization_id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '职位名称',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位代码',
  `status` bigint NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1717572787373215745 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position` VALUES (1742215356509982720, 1742215275794796544, '社长', 'master', 1742162235461079040, '2024-01-03 00:06:18', '2024-01-03 00:06:18');
INSERT INTO `position` VALUES (1742216059412418560, 1742216059408224256, '社长', 'master', 1742162235461079040, '2024-01-03 00:07:32', '2024-01-03 00:07:32');
INSERT INTO `position` VALUES (1742216145693446145, 1742216145693446144, '社长', 'master', 1742162235461079040, '2024-01-03 00:08:25', '2024-01-03 00:08:25');
INSERT INTO `position` VALUES (1742217372082769922, 1742217372082769921, '社长', 'master', 1742162235461079040, '2024-01-03 00:12:45', '2024-01-03 00:12:45');
INSERT INTO `position` VALUES (1742217412985622529, 1742217412985622528, '社长', 'master', 1742162235461079040, '2024-01-03 00:12:56', '2024-01-03 00:12:56');
INSERT INTO `position` VALUES (1742217673149911041, 1742217673149911040, '社长', 'master', 1742162235461079040, '2024-01-03 00:16:03', '2024-01-03 00:16:03');
INSERT INTO `position` VALUES (1742218390900183040, 1742218390887600128, '社长', 'master', 1742162235461079040, '2024-01-03 00:16:49', '2024-01-03 00:16:49');
INSERT INTO `position` VALUES (1742218475130195969, 1742218475130195968, '社长', 'master', 1742162235461079040, '2024-01-03 00:17:09', '2024-01-03 00:17:09');
INSERT INTO `position` VALUES (1742218563428683777, 1742218563428683776, '社长', 'master', 1742162235461079040, '2024-01-03 00:17:29', '2024-01-03 00:17:29');

-- ----------------------------
-- Table structure for url
-- ----------------------------
DROP TABLE IF EXISTS `url`;
CREATE TABLE `url`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口名',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口',
  `status` bigint NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口描述',
  `scope` bigint NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 865735 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of url
-- ----------------------------
INSERT INTO `url` VALUES (865735, '/test/api', '接口地址', 1737846934896513024, '接口地址', 1737846877828812800, '2023-12-23 20:33:33', '2023-12-23 20:33:33');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  `sex` tinyint NOT NULL COMMENT '性别',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态 0 正常 1 异常',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1718849833810399233 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1718849833810399232, 'fred', 'http://localhost:3001/cloud-coding-resource/resource/1718849755450241025', 1, 0, '2023-10-30 12:38:30', '2023-10-30 12:38:30');

-- ----------------------------
-- Table structure for user_position
-- ----------------------------
DROP TABLE IF EXISTS `user_position`;
CREATE TABLE `user_position`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `position_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1717572789491339265 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_position
-- ----------------------------
INSERT INTO `user_position` VALUES (1742215285076791296, 1742215356509982720, 1718849833810399232);
INSERT INTO `user_position` VALUES (1742216059420807168, 1742216059412418560, 1718849833810399232);
INSERT INTO `user_position` VALUES (1742216196763291648, 1742216145693446145, 1718849833810399232);
INSERT INTO `user_position` VALUES (1742217372107935745, 1742217372082769922, 1718849833810399232);
INSERT INTO `user_position` VALUES (1742217414394908672, 1742217412985622529, 1718849833810399232);
INSERT INTO `user_position` VALUES (1742218203632898048, 1742217673149911041, 1718849833810399232);
INSERT INTO `user_position` VALUES (1742218392997335040, 1742218390900183040, 1718849833810399232);
INSERT INTO `user_position` VALUES (1742218477311234048, 1742218475130195969, 1718849833810399232);
INSERT INTO `user_position` VALUES (1742218563428683778, 1742218563428683777, 1718849833810399232);

SET FOREIGN_KEY_CHECKS = 1;
