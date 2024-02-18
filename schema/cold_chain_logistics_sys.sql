/*
 Navicat Premium Data Transfer

 Source Server         : docker
 Source Server Type    : MySQL
 Source Server Version : 80100
 Source Host           : localhost:3306
 Source Schema         : cold_chain_logistics_sys

 Target Server Type    : MySQL
 Target Server Version : 80100
 File Encoding         : 65001

 Date: 18/02/2024 12:35:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限菜单id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '前端path',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父级菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, '用户管理', '/user', '2024-02-06 20:44:45', '2024-02-06 20:44:47', NULL);

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源名',
  `url` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求url',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方法',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES (1, '接口测试', '/user/test', 'GET', '2024-02-17 14:59:20', '2024-02-17 14:59:22');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色表主键',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'superAdmin', '2024-02-05 14:00:02', '2024-02-05 14:00:03');
INSERT INTO `t_role` VALUES (2, 'Admin', '2024-02-05 14:00:46', '2024-02-05 14:00:50');
INSERT INTO `t_role` VALUES (3, 'Courier', '2024-02-05 14:02:39', '2024-02-05 14:02:40');
INSERT INTO `t_role` VALUES (4, 'User', '2024-02-05 14:03:02', '2024-02-05 14:03:03');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NULL DEFAULT NULL,
  `menu_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for t_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NULL DEFAULT NULL,
  `resource_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_resource
-- ----------------------------
INSERT INTO `t_role_resource` VALUES (1, 1, 1);
INSERT INTO `t_role_resource` VALUES (2, 4, 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户表主键',
  `user_info_id` bigint NULL DEFAULT NULL COMMENT '用户信息id',
  `role_id` bigint NULL DEFAULT NULL COMMENT '用户角色id',
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `is_disable` tinyint(1) NULL DEFAULT 0 COMMENT '是否封禁',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登陆时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_info_id`(`user_info_id` ASC) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (2, 1, 1, 'marsh', '$2a$10$XAXj/XlYsYtPQx5Iw3gnUO0O1WThz0ReWyQK94BQwstwE3WlphpAy', 0, '2024-02-17 13:29:42', '2024-02-17 13:29:43', '2024-02-18 12:21:22');
INSERT INTO `t_user` VALUES (3, 2, 4, 'lbwnb', '$2a$10$XAXj/XlYsYtPQx5Iw3gnUO0O1WThz0ReWyQK94BQwstwE3WlphpAy', 0, '2024-02-17 22:23:47', '2024-02-17 22:23:48', '2024-02-18 12:15:20');

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户信息表主键',
  `nickname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_info
-- ----------------------------
INSERT INTO `t_user_info` VALUES (1, 'lw', '########', '2300362038@qq.com', '19850530878', '2024-02-02 20:07:44', '2024-02-02 20:07:45');

SET FOREIGN_KEY_CHECKS = 1;
