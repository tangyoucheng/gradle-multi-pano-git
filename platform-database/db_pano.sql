/*
 Navicat Premium Data Transfer

 Source Server         : mysql8_120.78.13.166_root
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 120.78.13.166:3306
 Source Schema         : db_pano

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 23/08/2019 15:54:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pano_exposition
-- ----------------------------
DROP TABLE IF EXISTS `pano_exposition`;
CREATE TABLE `pano_exposition`  (
  `exposition_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '展览会ID',
  `exposition_name` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '展览会名称',
  `exposition_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会类型:0：引进展览、1：输出展览、2：原创展览',
  `preload_file_path` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会预加载文件路径',
  `preload_file_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会预加载文件类型:0：图片、1：视频',
  `flow_info_file_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会浮动信息ID',
  `flow_info_file_x` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会浮动信息横坐标',
  `flow_info_file_y` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会浮动信息纵坐标',
  `flow_info_file_scale` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会浮动信息大小',
  `exposition_sound_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会下背景音乐ID',
  `exposition_selected_buttons` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会工具条中选定的按钮',
  `exposition_recommend_info` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会下推荐路线统一提示信息',
  `expo_go_scene_tooltip` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前往场景的全局的提示信息',
  `exposition_start_date` date NULL DEFAULT NULL COMMENT '展览会开展日期',
  `exposition_end_date` date NULL DEFAULT NULL COMMENT '展览会撤展日期',
  `exposition_folder_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会文件夹的ID',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态:0：规划中、1：进行中、2：已结束',
  `status_notes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '状态说明',
  `notes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说明',
  `release_date` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `vr_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'VR标示:\r\n0：非VR、1：VR',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`exposition_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '展览会信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pano_exposition
-- ----------------------------
INSERT INTO `pano_exposition` VALUES ('5it1jtu7gdvu1', '创建展览测试1', '1', NULL, NULL, '5it1r6apjrbne', '1451', '35', '0.9', NULL, 'btn_in,btn_out,btn_left,btn_right,btn_up,btn_down,btn_autorot,btn_share,btn_fs,', NULL, '点击前往', '2019-08-05', '2019-08-05', NULL, '0', '展览状态说明', '展览说明', '2019-08-23 15:22:04', '0', 0, 'tenant', '2019-08-05 13:50:03', 'tenant', '2019-08-22 15:33:55');
INSERT INTO `pano_exposition` VALUES ('5it1jtukiz7dp', '创建展览测试1', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-08-05', '2019-08-05', NULL, '0', '展览状态说明', '展览说明', NULL, '0', 0, 'tenant', '2019-08-05 13:56:21', 'tenant', '2019-08-05 13:56:21');

-- ----------------------------
-- Table structure for pano_exposition_map
-- ----------------------------
DROP TABLE IF EXISTS `pano_exposition_map`;
CREATE TABLE `pano_exposition_map`  (
  `exposition_map_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '展览会地图ID',
  `exposition_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '展览会ID',
  `exposition_map_use_state` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '展览会地图使用状态:0：未使用、1：使用中',
  `exposition_map_name` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '展览会地图名称',
  `exposition_map_path` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会地图路径',
  `exposition_map_sort_key` decimal(2, 0) NULL DEFAULT NULL COMMENT '地图现实顺序',
  `notes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说明',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`exposition_map_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '展览会地图信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pano_exposition_map
-- ----------------------------
INSERT INTO `pano_exposition_map` VALUES ('5it1q8z2o1ri6', '5it1jtu7gdvu1', '1', '导航图一', '5it1jtu7gdvu1/images/5it1q8z2o1ri6/5it1q8z2o1ri6.jpg', NULL, '', 0, 'tenant', '2019-08-19 11:08:18', 'tenant', '2019-08-19 12:05:59');
INSERT INTO `pano_exposition_map` VALUES ('5it1q917s1dik', '5it1jtu7gdvu1', '0', '导航图二', '5it1jtu7gdvu1/images/5it1q917s1dik/5it1q917s1dik.jpg', NULL, '', 0, 'tenant', '2019-08-19 11:44:18', 'tenant', '2019-08-19 12:05:59');

-- ----------------------------
-- Table structure for pano_exposition_map_hotspot
-- ----------------------------
DROP TABLE IF EXISTS `pano_exposition_map_hotspot`;
CREATE TABLE `pano_exposition_map_hotspot`  (
  `exposition_map_hotspot_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '热点的ID',
  `exposition_map_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '热点所在展览会地图的ID',
  `exposition_map_hotspot_x` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '热点横坐标',
  `exposition_map_hotspot_y` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '热点纵坐标',
  `exposition_map_hotspot_heading` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '热点雷达初始角度',
  `exposition_map_hotspot_align` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '热点的对齐方式',
  `panorama_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '热点链接的全景图ID',
  `exposition_map_hotspot_url` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '热点url',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`exposition_map_hotspot_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '展览会地图上热点信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pano_exposition_map_hotspot
-- ----------------------------
INSERT INTO `pano_exposition_map_hotspot` VALUES ('5it1q9bnqt4fc', '5it1q8z2o1ri6', '162', '187', '-70', NULL, '5it1lns36n3w6', 'static/pano/pano/common/template/mappoint_blue.png', 0, 'tenant', '2019-08-19 13:19:47', 'tenant', '2019-08-19 18:52:59');
INSERT INTO `pano_exposition_map_hotspot` VALUES ('5it1q9bnqxlfd', '5it1q8z2o1ri6', '259', '180', '-180', NULL, '5it1l7fuhzmnh', 'static/pano/pano/common/template/mappoint_blue.png', 0, 'tenant', '2019-08-19 13:19:47', 'tenant', '2019-08-19 18:52:59');
INSERT INTO `pano_exposition_map_hotspot` VALUES ('5it1qa7ef2pp9', '5it1q8z2o1ri6', '151', '80', NULL, NULL, '5it1lnn9a74nm', 'static/pano/pano/common/template/mappoint_blue.png', 0, 'tenant', '2019-08-19 18:52:59', 'tenant', '2019-08-19 18:53:22');

-- ----------------------------
-- Table structure for pano_group_role_user
-- ----------------------------
DROP TABLE IF EXISTS `pano_group_role_user`;
CREATE TABLE `pano_group_role_user`  (
  `group_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公共组ID',
  `role_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职责ID',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户',
  `start_date` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始日',
  `end_date` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束日',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`group_id`, `role_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公共组职责用户信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pano_grp_role_user_tmpl
-- ----------------------------
DROP TABLE IF EXISTS `pano_grp_role_user_tmpl`;
CREATE TABLE `pano_grp_role_user_tmpl`  (
  `tmpl_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板ID',
  `tmpl_json` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置json',
  `tmpl_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名',
  `public_flg` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公开flg',
  `deafult_flg` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认flg',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`tmpl_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公共组职责用户权限模板_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pano_hotspot_url
-- ----------------------------
DROP TABLE IF EXISTS `pano_hotspot_url`;
CREATE TABLE `pano_hotspot_url`  (
  `hotspot_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '全景图上热点的ID',
  `hotspot_url_object_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图上热点URL对象的ID',
  `sort_key` decimal(2, 0) NULL DEFAULT NULL COMMENT '显示顺序',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`hotspot_id`, `hotspot_url_object_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '热点URL信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pano_hotspot_url
-- ----------------------------
INSERT INTO `pano_hotspot_url` VALUES ('5it1ny69ud1v8', '5it1lnn9a74nm', NULL, 0, 'tenant', '2019-08-15 14:36:52', 'tenant', '2019-08-15 14:36:52');
INSERT INTO `pano_hotspot_url` VALUES ('5it1nz5aght9o', '5it1lns36n3w6', NULL, 0, 'tenant', '2019-08-15 14:37:25', 'tenant', '2019-08-15 14:37:25');
INSERT INTO `pano_hotspot_url` VALUES ('5it1ovjvf01cj', '5it1nybc07qso', 1, 0, 'tenant', '2019-08-22 15:24:23', 'tenant', '2019-08-22 15:24:23');
INSERT INTO `pano_hotspot_url` VALUES ('5it1rn5pi5gqf', '5it1rnmczihru', 1, 0, 'tenant', '2019-08-22 17:59:51', 'tenant', '2019-08-22 17:59:51');
INSERT INTO `pano_hotspot_url` VALUES ('5it1rn5pi5gqf', '5it1rnmek1lrv', 2, 0, 'tenant', '2019-08-22 17:59:51', 'tenant', '2019-08-22 17:59:51');
INSERT INTO `pano_hotspot_url` VALUES ('5it1s3o82aoag', '5it1rmm6e229v', 1, 0, 'tenant', '2019-08-23 15:17:10', 'tenant', '2019-08-23 15:17:10');

-- ----------------------------
-- Table structure for pano_material
-- ----------------------------
DROP TABLE IF EXISTS `pano_material`;
CREATE TABLE `pano_material`  (
  `material_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '素材ID',
  `exposition_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '展览会ID',
  `material_type_id` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '素材种类ID:1：平面图、2：音频、3：视频、4：场景切换图、5：普通热点图、6：LOGO图、7：图片浮动信息 、8：文字浮动信息',
  `material_name` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '素材名称',
  `material_path` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '素材路径',
  `gif_width` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'gif图片宽',
  `gif_height` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'gif图片高',
  `gif_frame_count` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'gif的帧数',
  `gif_delay_time` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'gif的延迟时间',
  `flow_text_info` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文字浮动信息内容',
  `text_info` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图文的文字信息',
  `notes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说明',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`material_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '素材信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pano_material
-- ----------------------------
INSERT INTO `pano_material` VALUES ('5it1n1lke0dhc', 'common_material', '4', '单箭头', 'material/common_material/images/5it1n1lke0dhc/5it1n1lke0dhc.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-12 13:49:28', 'tenant', '2019-08-12 13:49:28');
INSERT INTO `pano_material` VALUES ('5it1n1qir3zgz', 'common_material', '4', '单箭头二', 'material/common_material/images/5it1n1qir3zgz/5it1n1qir3zgz.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-12 14:29:05', 'tenant', '2019-08-12 14:29:05');
INSERT INTO `pano_material` VALUES ('5it1n2eiph822', 'common_material', '4', '单箭头三', 'material/common_material/images/5it1n2eiph822/5it1n2eiph822.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-12 18:31:57', 'tenant', '2019-08-12 18:31:57');
INSERT INTO `pano_material` VALUES ('5it1nhh31ld1f', 'common_material', '4', '单箭头四', 'material/common_material/images/5it1nhh31ld1f/5it1nhh31ld1f.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-13 09:47:24', 'tenant', '2019-08-13 09:47:24');
INSERT INTO `pano_material` VALUES ('5it1ny6nbauva', 'common_material', '5', '定位图标', 'material/common_material/images/5it1ny6nbauva/5it1ny6nbauva.png', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-14 10:48:55', 'tenant', '2019-08-14 10:48:55');
INSERT INTO `pano_material` VALUES ('5it1nybc07qso', 'common_material', '1', '旁箭头', 'material/common_material/images/5it1nybc07qso/5it1nybc07qso.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-14 11:28:19', 'tenant', '2019-08-14 11:28:19');
INSERT INTO `pano_material` VALUES ('5it1nybelxtsq', 'common_material', '4', '胖箭头', 'material/common_material/images/5it1nybelxtsq/5it1nybelxtsq.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-14 11:28:48', 'tenant', '2019-08-14 11:28:48');
INSERT INTO `pano_material` VALUES ('5it1nzbcqn69u', 'common_material', '6', 'logo图测试', 'material/common_material/images/5it1nzbcqn69u/5it1nzbcqn69u.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-14 17:33:00', 'tenant', '2019-08-14 17:33:00');
INSERT INTO `pano_material` VALUES ('5it1r69bl5en5', 'common_material', '8', '文字浮动信息-家装', 'dummy', NULL, NULL, NULL, NULL, '精品内家装', NULL, '', 0, 'tenant', '2019-08-21 12:35:06', 'tenant', '2019-08-21 12:35:06');
INSERT INTO `pano_material` VALUES ('5it1r6apjrbne', 'common_material', '7', '图片浮动信息-内家装', 'material/common_material/images/5it1r6apjrbne/5it1r6apjrbne.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-21 13:01:11', 'tenant', '2019-08-21 13:01:11');
INSERT INTO `pano_material` VALUES ('5it1rmm6e229v', 'common_material', '1', '平面图一', 'material/common_material/images/5it1rmm6e229v/5it1rmm6e229v.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-22 11:25:25', 'tenant', '2019-08-22 11:25:25');
INSERT INTO `pano_material` VALUES ('5it1rmm7vyk9w', 'common_material', '1', '平面图二', 'material/common_material/images/5it1rmm7vyk9w/5it1rmm7vyk9w.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-22 11:25:38', 'tenant', '2019-08-22 11:25:38');
INSERT INTO `pano_material` VALUES ('5it1rmm867n9x', 'common_material', '1', '平面图三', 'material/common_material/images/5it1rmm867n9x/5it1rmm867n9x.jpg', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-22 11:26:05', 'tenant', '2019-08-22 11:26:05');
INSERT INTO `pano_material` VALUES ('5it1rmm9m4x9y', 'common_material', '1', '平面图四', 'material/common_material/images/5it1rmm9m4x9y/5it1rmm9m4x9y.PNG', NULL, NULL, NULL, NULL, NULL, NULL, '', 0, 'tenant', '2019-08-22 11:26:28', 'tenant', '2019-08-22 11:26:28');
INSERT INTO `pano_material` VALUES ('5it1rnmczihru', 'common_material', '9', '图文信息测试一', 'material/common_material/images/5it1rnmczihru/5it1rnmczihru.PNG', NULL, NULL, NULL, NULL, NULL, '图文信息文字测试一', '', 0, 'tenant', '2019-08-22 17:33:02', 'tenant', '2019-08-22 17:33:02');
INSERT INTO `pano_material` VALUES ('5it1rnmek1lrv', 'common_material', '9', '图文信息测试二', 'material/common_material/images/5it1rnmek1lrv/5it1rnmek1lrv.jpg', NULL, NULL, NULL, NULL, NULL, '图文信息文字测试二', '', 0, 'tenant', '2019-08-22 17:33:36', 'tenant', '2019-08-22 17:33:36');

-- ----------------------------
-- Table structure for pano_panorama
-- ----------------------------
DROP TABLE IF EXISTS `pano_panorama`;
CREATE TABLE `pano_panorama`  (
  `panorama_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '全景图ID',
  `exposition_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '展览会ID',
  `panorama_sound_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景下背景音乐ID',
  `panorama_sort_key` decimal(2, 0) NULL DEFAULT NULL COMMENT '场景显示序号',
  `start_scene_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '首个场景标示:0：不是首个场景、1：是首个场景',
  `panorama_name` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全景图名称',
  `panorama_path` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全景图路径',
  `panorama_hlookat` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全景图水平视角',
  `panorama_vlookat` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全景图垂直视角',
  `thumbnail_show_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展览会是否显示缩略图:0：不显示缩略图、1：显示所略图',
  `thumb_note` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩略图备注',
  `notes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说明',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`panorama_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '全景图信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pano_panorama
-- ----------------------------
INSERT INTO `pano_panorama` VALUES ('5it1l7fuhzmnh', '5it1jtu7gdvu1', '', 1, '1', '场景一', '5it1jtu7gdvu1/panoramas/5it1l7fuhzmnh/', '-134', '22', '1', '玄关', '', 0, 'tenant', '2019-08-08 13:41:53', 'tenant', '2019-08-16 14:22:57');
INSERT INTO `pano_panorama` VALUES ('5it1lnn9a74nm', '5it1jtu7gdvu1', NULL, 3, '0', '场景三', '5it1jtu7gdvu1/panoramas/5it1lnn9a74nm/', '-332', '6', '1', '客厅', '', 0, 'tenant', '2019-08-09 11:40:58', 'tenant', '2019-08-12 14:01:02');
INSERT INTO `pano_panorama` VALUES ('5it1lns36n3w6', '5it1jtu7gdvu1', NULL, 2, '0', '场景二', '5it1jtu7gdvu1/panoramas/5it1lns36n3w6/', NULL, NULL, '0', NULL, '', 0, 'tenant', '2019-08-09 12:22:14', 'tenant', '2019-08-12 14:01:02');

-- ----------------------------
-- Table structure for pano_panorama_hotspot
-- ----------------------------
DROP TABLE IF EXISTS `pano_panorama_hotspot`;
CREATE TABLE `pano_panorama_hotspot`  (
  `hotspot_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图上热点的ID',
  `panorama_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '热点所在全景图的ID',
  `hotspot_scale` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图上热点的大小',
  `hotspot_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图上热点种类:1：多边形热点 、2：导航热点、 3：普通热点、  4：LOGO热点',
  `hotspot_image_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图上热点图片ID',
  `external_link_address` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外部链接地址',
  `hotspot_url_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图上热点URL种类:1：全景图、2：素材图、3：声音、4：视频、5：外部链接',
  `next_recommend_hotspot_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该导航点链接的下个场景中的推荐路线点ID',
  `recommend_info` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐路线点上的提示信息',
  `hotspot_tooltip` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图上热点的提示信息',
  `hotspot_ath` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图上热点水平显示位置',
  `hotspot_atv` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图上热点垂直显示位置',
  `panorama_hlookat` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全景图水平视角',
  `panorama_vlookat` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全景图垂直视角',
  `polygon_fillcolor` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多边形颜色',
  `polygon_fillalpha` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多边形透明度',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`hotspot_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '全景图上热点信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pano_panorama_hotspot
-- ----------------------------
INSERT INTO `pano_panorama_hotspot` VALUES ('5it1jtu7gdvu1', '5it1l7fuhzmnh', '1', '5', NULL, NULL, NULL, '5it1ny680v1v7', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'tenant', '2019-08-14 15:21:21', 'tenant', '2019-08-23 15:24:30');
INSERT INTO `pano_panorama_hotspot` VALUES ('5it1ny5xmyev5', '5it1lns36n3w6', '1', '2', '5it1nhh31ld1f', NULL, NULL, NULL, NULL, NULL, '-6', '20', NULL, NULL, NULL, NULL, 0, 'tenant', '2019-08-14 10:36:38', 'tenant', '2019-08-14 10:49:10');
INSERT INTO `pano_panorama_hotspot` VALUES ('5it1ny680v1v7', '5it1lns36n3w6', '0.4', '2', '5it1n1lke0dhc', NULL, NULL, NULL, '推荐路线', NULL, '34', '-6', NULL, NULL, NULL, NULL, 0, 'tenant', '2019-08-14 10:41:22', 'tenant', '2019-08-14 15:29:55');
INSERT INTO `pano_panorama_hotspot` VALUES ('5it1ny69ud1v8', '5it1l7fuhzmnh', '0.3', '2', '5it1n1lke0dhc', NULL, '1', NULL, '推荐路线', NULL, '-93', '38', '-52', '2', NULL, NULL, 0, 'tenant', '2019-08-14 10:42:07', 'tenant', '2019-08-23 15:24:30');
INSERT INTO `pano_panorama_hotspot` VALUES ('5it1ny6ox2zvc', '5it1lns36n3w6', '1', '3', '5it1ny6nbauva', NULL, NULL, NULL, NULL, NULL, '-32', NULL, NULL, NULL, NULL, NULL, 0, 'tenant', '2019-08-14 10:49:11', 'tenant', '2019-08-14 10:49:11');
INSERT INTO `pano_panorama_hotspot` VALUES ('5it1nz5aght9o', '5it1l7fuhzmnh', '0.4', '2', '5it1n1lke0dhc', NULL, '1', NULL, NULL, NULL, '-164', '4', NULL, NULL, NULL, NULL, 0, 'tenant', '2019-08-14 16:30:23', 'tenant', '2019-08-23 15:24:30');
INSERT INTO `pano_panorama_hotspot` VALUES ('5it1ovjvf01cj', '5it1l7fuhzmnh', '0.7', '3', '5it1ny6nbauva', '', '2', NULL, NULL, NULL, '130', '-6', NULL, NULL, NULL, NULL, 0, 'tenant', '2019-08-16 12:24:33', 'tenant', '2019-08-23 15:24:30');
INSERT INTO `pano_panorama_hotspot` VALUES ('5it1rn5pi5gqf', '5it1l7fuhzmnh', '1', '3', '5it1ny6nbauva', '', '6', NULL, NULL, NULL, '11', '-20', NULL, NULL, NULL, NULL, 0, 'tenant', '2019-08-22 14:53:00', 'tenant', '2019-08-23 15:24:30');
INSERT INTO `pano_panorama_hotspot` VALUES ('5it1s3o82aoag', '5it1l7fuhzmnh', '1', '1', NULL, '', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0xf039e1', '0.57', 0, 'tenant', '2019-08-23 14:38:18', 'tenant', '2019-08-23 15:24:30');

-- ----------------------------
-- Table structure for pano_polygon_hotspot
-- ----------------------------
DROP TABLE IF EXISTS `pano_polygon_hotspot`;
CREATE TABLE `pano_polygon_hotspot`  (
  `polygon_point_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '多边形的点的ID',
  `polygon_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '多边形ID',
  `polygon_point_ath` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多边形的点的水平显示位置',
  `polygon_point_atv` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多边形的点的垂直显示位置',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`polygon_point_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '多边形热点位置信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pano_polygon_hotspot
-- ----------------------------
INSERT INTO `pano_polygon_hotspot` VALUES ('5it1s3rwvoob0', '5it1s3o82aoag', '47', '-17', 0, 'tenant', '2019-08-23 15:00:14', 'tenant', '2019-08-23 15:00:14');
INSERT INTO `pano_polygon_hotspot` VALUES ('5it1s3rwvrlb1', '5it1s3o82aoag', '71', '-19', 0, 'tenant', '2019-08-23 15:00:14', 'tenant', '2019-08-23 15:00:14');
INSERT INTO `pano_polygon_hotspot` VALUES ('5it1s3rwvuhb2', '5it1s3o82aoag', '78', '13', 0, 'tenant', '2019-08-23 15:00:15', 'tenant', '2019-08-23 15:00:15');
INSERT INTO `pano_polygon_hotspot` VALUES ('5it1s3rww5jb3', '5it1s3o82aoag', '45', '22', 0, 'tenant', '2019-08-23 15:00:15', 'tenant', '2019-08-23 15:00:15');
INSERT INTO `pano_polygon_hotspot` VALUES ('5it1s3rww8gb4', '5it1s3o82aoag', '41', '-3', 0, 'tenant', '2019-08-23 15:00:15', 'tenant', '2019-08-23 15:00:15');
INSERT INTO `pano_polygon_hotspot` VALUES ('5it1s3rwwbcb5', '5it1s3o82aoag', '41', '-12', 0, 'tenant', '2019-08-23 15:00:15', 'tenant', '2019-08-23 15:00:15');

-- ----------------------------
-- Table structure for pano_process_items
-- ----------------------------
DROP TABLE IF EXISTS `pano_process_items`;
CREATE TABLE `pano_process_items`  (
  `process_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '待处理事项ID',
  `type_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '种类ID:filelibrary_item:文档添加更新',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID:不设定时，全员可访问。',
  `summary` varchar(360) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '摘要',
  `parameter1` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数1',
  `parameter2` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数2',
  `parameter3` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数3',
  `parameter4` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数4',
  `parameter5` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数5',
  `deadline` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理期限',
  `notes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说明',
  `process_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理标示:0：未处理、1：已处理',
  `priority` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优先级',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`process_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '待处理事项信息_后台管理用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `platform_admin_menu`;
CREATE TABLE `platform_admin_menu`  (
  `MENU_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `MENU_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名',
  `MENU_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单URL',
  `PARENT_MENU_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父菜单',
  `WEB_FONT` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网页字体',
  `DISP_FLAG` decimal(3, 0) NULL DEFAULT NULL COMMENT '表示顺',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_admin_menu
-- ----------------------------
INSERT INTO `platform_admin_menu` VALUES ('platform01', '系统管理', '', 'platform', '<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>', 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0101', '管理员列表', 'admin/platform0201/', 'platform01', NULL, 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0102', '管理员角色', 'admin/platform0202/', 'platform01', NULL, 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0103', '菜单权限管理', 'admin/platform0203/', 'platform01', NULL, 3, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform02', '公司信息管理', '', 'platform', '<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>', 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0201', '公司管理', 'admin/platform0399/', 'platform02', NULL, 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform03', '系统监测', '', 'platform', '<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>', 3, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0301', '在线用户', 'admin/platform9901/', 'platform03', NULL, 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0302', '数据监控', 'admin/platform9902/', 'platform03', NULL, 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0303', '服务监控', 'admin/platform9903/', 'platform03', NULL, 3, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0304', '定时任务', 'admin/platform9905/', 'platform03', NULL, 4, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0305', '重复任务', 'admin/platform9906/', 'platform03', NULL, 5, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform0306', '日志管理', '', 'platform03', '<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>', 6, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform030601', '登录日志', 'admin/platform9904/', 'platform0306', NULL, 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_admin_menu` VALUES ('platform030602', '任务日志', 'admin/platform9907/', 'platform0306', NULL, 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');

-- ----------------------------
-- Table structure for platform_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `platform_admin_role`;
CREATE TABLE `platform_admin_role`  (
  `ROLE_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份编码',
  `ROLE_NAME` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份名',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_admin_role
-- ----------------------------
INSERT INTO `platform_admin_role` VALUES ('5it0lubez1t6z', '数据分析后台管理员', 0, 'system', '2019-07-31 15:32:51', 'system', '2019-07-31 15:32:51');

-- ----------------------------
-- Table structure for platform_admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `platform_admin_role_menu`;
CREATE TABLE `platform_admin_role_menu`  (
  `ROLE_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份编码',
  `MENU_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `DEPARTMENT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编码',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`ROLE_ID`, `MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单访问权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_admin_role_user
-- ----------------------------
DROP TABLE IF EXISTS `platform_admin_role_user`;
CREATE TABLE `platform_admin_role_user`  (
  `ROLE_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份编码',
  `ADMIN_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员编码',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`ROLE_ID`, `ADMIN_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色系统管理员关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `platform_admin_user`;
CREATE TABLE `platform_admin_user`  (
  `ADMIN_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员编号',
  `ADMIN_LOGIN_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员登陆编码',
  `ADMIN_NAME` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员名称',
  `ADMIN_PASSWORD` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员密码',
  `ADMIN_EMAIL` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员邮箱',
  `ADMIN_STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员状态:1：激活 0：冻结',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`ADMIN_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_admin_user
-- ----------------------------
INSERT INTO `platform_admin_user` VALUES ('5it0asuw70gql', 'system', '平台超级管理员用户', '$2a$10$TySlasKSSXEV3UU5SElp9OMGgvW8Tx920NBgPL8ewtllhwcz8.qXu', NULL, '1', 0, 'system', '2019-07-07 12:04:30', 'system', '2019-07-07 12:04:30');

-- ----------------------------
-- Table structure for platform_company
-- ----------------------------
DROP TABLE IF EXISTS `platform_company`;
CREATE TABLE `platform_company`  (
  `COMPANY_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公司编码',
  `COMPANY_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公司名',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`COMPANY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公司表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_company
-- ----------------------------
INSERT INTO `platform_company` VALUES ('5it0lubvn1jtl', '天涯海角', 0, 'system', '2019-07-31 15:40:29', 'system', '2019-07-31 15:40:29');

-- ----------------------------
-- Table structure for platform_department
-- ----------------------------
DROP TABLE IF EXISTS `platform_department`;
CREATE TABLE `platform_department`  (
  `COMPANY_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公司编码',
  `DEPARTMENT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门编码',
  `DEPARTMENT_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名',
  `DEPARTMENT_HIERARCHY` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门层级',
  `PARENT_DEPARTMENT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父部门',
  `DISP_FLAG` decimal(3, 0) NULL DEFAULT NULL COMMENT '表示顺',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`COMPANY_ID`, `DEPARTMENT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_department_user
-- ----------------------------
DROP TABLE IF EXISTS `platform_department_user`;
CREATE TABLE `platform_department_user`  (
  `DEPARTMENT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门编码',
  `MEMBER_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员编码',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`DEPARTMENT_ID`, `MEMBER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门用户关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_job_cron
-- ----------------------------
DROP TABLE IF EXISTS `platform_job_cron`;
CREATE TABLE `platform_job_cron`  (
  `JOB_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务ID',
  `JOB_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `JOB_GROUP` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务组名',
  `METHOD_NAME` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务方法',
  `METHOD_PARAMS` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法参数',
  `CRON_EXPRESSION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron执行表达式',
  `TRIGGER_REMARK` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '触发器说明',
  `PRIORITY` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优先级:数值越大优先级越高',
  `MISFIRE_POLICY` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '计划执行错误策略:1：立即执行 2；执行一次 3：放弃执行',
  `JOB_STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务状态:1：正常 0：暂停',
  `REMARK` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`JOB_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_job_log
-- ----------------------------
DROP TABLE IF EXISTS `platform_job_log`;
CREATE TABLE `platform_job_log`  (
  `JOB_LOG_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务日志ID',
  `JOB_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务ID',
  `JOB_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `JOB_GROUP` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务组名',
  `METHOD_NAME` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务方法',
  `METHOD_PARAMS` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法参数',
  `JOB_MESSAGE` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行状态:0：正常 1：失败',
  `EXCEPTION_INFO` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常信息',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`JOB_LOG_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_job_repeat
-- ----------------------------
DROP TABLE IF EXISTS `platform_job_repeat`;
CREATE TABLE `platform_job_repeat`  (
  `JOB_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务ID',
  `JOB_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `JOB_GROUP` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务组名',
  `METHOD_NAME` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务方法',
  `METHOD_PARAMS` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法参数',
  `REPEAT_COUNT` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重复次数',
  `REPEAT_INTERVAL` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重复间隔',
  `START_DATE` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `END_DATE` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `TRIGGER_REMARK` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '触发器说明',
  `PRIORITY` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优先级:数值越大优先级越高',
  `MISFIRE_POLICY` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '计划执行错误策略',
  `JOB_STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务状态:1：正常 0：暂停',
  `REMARK` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`JOB_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '重复任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_login_info
-- ----------------------------
DROP TABLE IF EXISTS `platform_login_info`;
CREATE TABLE `platform_login_info`  (
  `INFO_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '访问ID',
  `LOGIN_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `IP_ADDRESS` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP地址',
  `LOGIN_ADDRESS` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `BROWSER` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器类型',
  `OPERATING_SYSTEM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录状态（1成功 0失败）',
  `MSG` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提示消息',
  `LOGIN_TIME` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`INFO_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_login_info
-- ----------------------------
INSERT INTO `platform_login_info` VALUES ('5it0lucinb5cc', 'system', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-07-31 15:51:02');
INSERT INTO `platform_login_info` VALUES ('5it0luciyclcd', 'system', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 15:51:17');
INSERT INTO `platform_login_info` VALUES ('5it0luclaaqce', 'system', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-07-31 15:52:25');
INSERT INTO `platform_login_info` VALUES ('5it0luclhrecf', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 15:52:35');
INSERT INTO `platform_login_info` VALUES ('5it0lug5nbdd1', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 16:12:14');
INSERT INTO `platform_login_info` VALUES ('5it0lugcte4d2', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-07-31 16:15:49');
INSERT INTO `platform_login_info` VALUES ('5it0lugcy7rd3', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 16:15:55');
INSERT INTO `platform_login_info` VALUES ('5it0luhj2eqg7', '1', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '0', '当前登录的用户不存在', '2019-07-31 16:35:20');
INSERT INTO `platform_login_info` VALUES ('5it0luhj6n8g8', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 16:35:25');
INSERT INTO `platform_login_info` VALUES ('5it0luhjp5zg9', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-07-31 16:35:49');
INSERT INTO `platform_login_info` VALUES ('5it0luhku1xga', 'system', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 16:36:02');
INSERT INTO `platform_login_info` VALUES ('5it0luhwd8wgb', 'system', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-07-31 16:41:41');
INSERT INTO `platform_login_info` VALUES ('5it0luhwkpsgc', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 16:41:50');
INSERT INTO `platform_login_info` VALUES ('5it0lulvvo5gd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-07-31 17:08:36');
INSERT INTO `platform_login_info` VALUES ('5it0lulw0hxge', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 17:08:43');
INSERT INTO `platform_login_info` VALUES ('5it0lumpl7vgf', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-07-31 17:22:22');
INSERT INTO `platform_login_info` VALUES ('5it0lumq2ypgg', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 17:22:45');
INSERT INTO `platform_login_info` VALUES ('5it0lumuky0gh', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-07-31 17:24:55');
INSERT INTO `platform_login_info` VALUES ('5it0lumzxj5gi', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-07-31 17:27:05');
INSERT INTO `platform_login_info` VALUES ('5it1igoc6o1kc', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-02 16:22:47');
INSERT INTO `platform_login_info` VALUES ('5it1igocgs7kd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-02 16:23:00');
INSERT INTO `platform_login_info` VALUES ('5it1igodgv6ke', '1', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '0', '当前登录的用户不存在', '2019-08-02 16:23:07');
INSERT INTO `platform_login_info` VALUES ('5it1igodlcokf', 'system', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-02 16:23:13');
INSERT INTO `platform_login_info` VALUES ('5it1igoe1y8kg', 'system', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-02 16:23:34');
INSERT INTO `platform_login_info` VALUES ('5it1iguybgllm', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-02 17:33:57');
INSERT INTO `platform_login_info` VALUES ('5it1igv3w37ln', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-02 17:36:17');
INSERT INTO `platform_login_info` VALUES ('5it1igviqp7qv', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-02 17:43:10');
INSERT INTO `platform_login_info` VALUES ('5it1ih0j4yfbq', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-02 18:27:27');
INSERT INTO `platform_login_info` VALUES ('5it1ih0jcnfbr', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-02 18:27:37');
INSERT INTO `platform_login_info` VALUES ('5it1jt6d7faxj', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 09:49:04');
INSERT INTO `platform_login_info` VALUES ('5it1jt6dllwxk', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-05 09:49:22');
INSERT INTO `platform_login_info` VALUES ('5it1jtc9tuvxl', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-05 10:48:24');
INSERT INTO `platform_login_info` VALUES ('5it1jtcr1jpxm', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 10:56:27');
INSERT INTO `platform_login_info` VALUES ('5it1jthklu7xo', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-05 11:37:25');
INSERT INTO `platform_login_info` VALUES ('5it1jtiovsh24', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 11:56:04');
INSERT INTO `platform_login_info` VALUES ('5it1jtitd954j', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 11:58:13');
INSERT INTO `platform_login_info` VALUES ('5it1jtldc1yhp', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 12:01:04');
INSERT INTO `platform_login_info` VALUES ('5it1jtldmy4hq', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 12:01:18');
INSERT INTO `platform_login_info` VALUES ('5it1jtlg1fahr', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 12:02:30');
INSERT INTO `platform_login_info` VALUES ('5it1jtm1s6mfd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 12:12:45');
INSERT INTO `platform_login_info` VALUES ('5it1jtoo4q6kc', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 12:56:47');
INSERT INTO `platform_login_info` VALUES ('5it1jtrk9rchi', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 13:05:06');
INSERT INTO `platform_login_info` VALUES ('5it1jtrrj4k91', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 13:08:44');
INSERT INTO `platform_login_info` VALUES ('5it1jtsrpzhcr', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 13:25:33');
INSERT INTO `platform_login_info` VALUES ('5it1jtszykics', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 13:29:17');
INSERT INTO `platform_login_info` VALUES ('5it1jttsan4jb', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-05 13:42:39');
INSERT INTO `platform_login_info` VALUES ('5it1jtu2cq9tx', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 13:47:08');
INSERT INTO `platform_login_info` VALUES ('5it1jtuke0ddo', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 13:55:50');
INSERT INTO `platform_login_info` VALUES ('5it1jtxnmb3hq', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 14:07:40');
INSERT INTO `platform_login_info` VALUES ('5it1jty8pk6s4', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 14:17:24');
INSERT INTO `platform_login_info` VALUES ('5it1jtz73qago', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 14:33:28');
INSERT INTO `platform_login_info` VALUES ('5it1ju3fweaya', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-05 15:04:57');
INSERT INTO `platform_login_info` VALUES ('5it1jubh55ryb', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 16:39:52');
INSERT INTO `platform_login_info` VALUES ('5it1jufdppndm', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 17:05:10');
INSERT INTO `platform_login_info` VALUES ('5it1jufoxx48r', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 17:10:34');
INSERT INTO `platform_login_info` VALUES ('5it1jugzzw08s', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '0', '当前登录的用户不存在', '2019-08-05 17:32:30');
INSERT INTO `platform_login_info` VALUES ('5it1juh09c68t', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 17:32:42');
INSERT INTO `platform_login_info` VALUES ('5it1juln3ul3t', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-05 18:10:26');
INSERT INTO `platform_login_info` VALUES ('5it1k9t2pdslk', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 10:05:04');
INSERT INTO `platform_login_info` VALUES ('5it1k9uelzw63', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 10:27:39');
INSERT INTO `platform_login_info` VALUES ('5it1k9uet0564', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 10:27:48');
INSERT INTO `platform_login_info` VALUES ('5it1k9ulcqudh', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 10:30:54');
INSERT INTO `platform_login_info` VALUES ('5it1k9vj5usdi', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 10:46:31');
INSERT INTO `platform_login_info` VALUES ('5it1k9z8iakdj', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 11:08:53');
INSERT INTO `platform_login_info` VALUES ('5it1ka0ct4ldk', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 11:27:33');
INSERT INTO `platform_login_info` VALUES ('5it1ka0es3adl', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 11:28:25');
INSERT INTO `platform_login_info` VALUES ('5it1ka0nep8hz', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 11:32:27');
INSERT INTO `platform_login_info` VALUES ('5it1ka4ruxki0', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 12:01:53');
INSERT INTO `platform_login_info` VALUES ('5it1ka5ckgei1', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 12:11:20');
INSERT INTO `platform_login_info` VALUES ('5it1ka7t6kbi2', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 12:52:54');
INSERT INTO `platform_login_info` VALUES ('5it1ka87pzyi3', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 12:59:32');
INSERT INTO `platform_login_info` VALUES ('5it1kabojuri6', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 13:17:56');
INSERT INTO `platform_login_info` VALUES ('5it1kabsn2yi7', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 13:19:47');
INSERT INTO `platform_login_info` VALUES ('5it1kadsiohig', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 13:53:01');
INSERT INTO `platform_login_info` VALUES ('5it1kagtzhzih', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 14:04:08');
INSERT INTO `platform_login_info` VALUES ('5it1kan0sraja', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 15:08:04');
INSERT INTO `platform_login_info` VALUES ('5it1kana5h25j', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 15:12:40');
INSERT INTO `platform_login_info` VALUES ('5it1kb177y96z', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 17:46:18');
INSERT INTO `platform_login_info` VALUES ('5it1kb1e7fp70', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 17:49:44');
INSERT INTO `platform_login_info` VALUES ('5it1kb4k4iy2z', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 18:02:59');
INSERT INTO `platform_login_info` VALUES ('5it1kb5kj0r31', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 18:19:57');
INSERT INTO `platform_login_info` VALUES ('5it1kb5o8vc32', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 18:21:31');
INSERT INTO `platform_login_info` VALUES ('5it1kbaop5b3l', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-06 19:05:50');
INSERT INTO `platform_login_info` VALUES ('5it1kbaptnw3m', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-06 19:06:02');
INSERT INTO `platform_login_info` VALUES ('5it1kqkodztbm', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 11:43:24');
INSERT INTO `platform_login_info` VALUES ('5it1kqp5l9hjp', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 12:18:45');
INSERT INTO `platform_login_info` VALUES ('5it1kqpg6rajq', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-07 12:23:40');
INSERT INTO `platform_login_info` VALUES ('5it1kqq8gx2a7', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 12:36:59');
INSERT INTO `platform_login_info` VALUES ('5it1kqqedf1ct', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 12:39:35');
INSERT INTO `platform_login_info` VALUES ('5it1kqudwak1e', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 13:06:31');
INSERT INTO `platform_login_info` VALUES ('5it1kr8ysvngo', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 15:51:21');
INSERT INTO `platform_login_info` VALUES ('5it1krdg0wfgq', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-07 16:26:43');
INSERT INTO `platform_login_info` VALUES ('5it1krezfdxst', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 16:52:28');
INSERT INTO `platform_login_info` VALUES ('5it1krjgojfve', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 17:27:51');
INSERT INTO `platform_login_info` VALUES ('5it1krk19z5vg', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-07 17:37:12');
INSERT INTO `platform_login_info` VALUES ('5it1kro9lux3y', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 18:08:19');
INSERT INTO `platform_login_info` VALUES ('5it1krqjk5wmd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-07 18:46:43');
INSERT INTO `platform_login_info` VALUES ('5it1krut6g3mg', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-07 19:18:10');
INSERT INTO `platform_login_info` VALUES ('5it1l6rqbk1e8', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 09:36:30');
INSERT INTO `platform_login_info` VALUES ('5it1l6yrpfbuc', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-08 10:54:52');
INSERT INTO `platform_login_info` VALUES ('5it1l6yy67uud', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 10:57:54');
INSERT INTO `platform_login_info` VALUES ('5it1l72ze6iuo', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-08 11:25:30');
INSERT INTO `platform_login_info` VALUES ('5it1l72zsodup', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 11:25:48');
INSERT INTO `platform_login_info` VALUES ('5it1l750b7asi', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 11:59:32');
INSERT INTO `platform_login_info` VALUES ('5it1l78on26sk', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-08 12:21:06');
INSERT INTO `platform_login_info` VALUES ('5it1l78tjkyaq', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 12:23:34');
INSERT INTO `platform_login_info` VALUES ('5it1l79d8ypqr', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 12:32:54');
INSERT INTO `platform_login_info` VALUES ('5it1l7aylqoqy', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 12:59:30');
INSERT INTO `platform_login_info` VALUES ('5it1l7f7efyn6', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 13:30:59');
INSERT INTO `platform_login_info` VALUES ('5it1l7fobxuna', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-08 13:38:48');
INSERT INTO `platform_login_info` VALUES ('5it1l7fokq1nb', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 13:39:00');
INSERT INTO `platform_login_info` VALUES ('5it1l7fqhf1nc', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-08 13:39:49');
INSERT INTO `platform_login_info` VALUES ('5it1l7fqm54nd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 13:39:55');
INSERT INTO `platform_login_info` VALUES ('5it1l7fsibbne', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-08 13:40:43');
INSERT INTO `platform_login_info` VALUES ('5it1l7fsnspnf', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 13:40:50');
INSERT INTO `platform_login_info` VALUES ('5it1l7fwbm0nj', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-08 13:42:21');
INSERT INTO `platform_login_info` VALUES ('5it1l7fwgfenk', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 13:42:27');
INSERT INTO `platform_login_info` VALUES ('5it1l7guh40wv', 'tenant', '172.16.192.200', NULL, 'Microsoft Edge', 'Windows 10', '1', '登陆系统成功', '2019-08-08 13:58:15');
INSERT INTO `platform_login_info` VALUES ('5it1l7k4wziww', 'tenant', '172.16.192.200', NULL, 'Microsoft Edge', 'Windows 10', '1', '退出系统成功', '2019-08-08 14:13:41');
INSERT INTO `platform_login_info` VALUES ('5it1l82ezdenf', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 17:20:43');
INSERT INTO `platform_login_info` VALUES ('5it1l82kmmxng', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-08 17:23:07');
INSERT INTO `platform_login_info` VALUES ('5it1l87ksfanh', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-08 18:07:12');
INSERT INTO `platform_login_info` VALUES ('5it1lna9mshni', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-09 09:22:23');
INSERT INTO `platform_login_info` VALUES ('5it1lnbzl4fnj', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-09 09:51:14');
INSERT INTO `platform_login_info` VALUES ('5it1lnlatdxnk', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-09 11:07:54');
INSERT INTO `platform_login_info` VALUES ('5it1lnrthunw4', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-09 12:17:04');
INSERT INTO `platform_login_info` VALUES ('5it1lnu7i38w9', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-09 12:57:16');
INSERT INTO `platform_login_info` VALUES ('5it1lnwre57wa', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-09 13:00:04');
INSERT INTO `platform_login_info` VALUES ('5it1lnxs48swc', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-09 13:17:17');
INSERT INTO `platform_login_info` VALUES ('5it1lnz0x1swd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-09 13:38:07');
INSERT INTO `platform_login_info` VALUES ('5it1lo351jjwe', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-09 14:07:18');
INSERT INTO `platform_login_info` VALUES ('5it1lo8vmu6sf', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-09 15:03:57');
INSERT INTO `platform_login_info` VALUES ('5it1log0j78x3', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-09 16:23:43');
INSERT INTO `platform_login_info` VALUES ('5it1logw5a3ch', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-09 16:38:18');
INSERT INTO `platform_login_info` VALUES ('5it1n12vg2gad', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-12 10:31:13');
INSERT INTO `platform_login_info` VALUES ('5it1n174a0xqa', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-12 11:02:44');
INSERT INTO `platform_login_info` VALUES ('5it1n19mn940v', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-12 11:45:00');
INSERT INTO `platform_login_info` VALUES ('5it1n1j0si5gt', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-12 13:02:36');
INSERT INTO `platform_login_info` VALUES ('5it1n1j0xcigu', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-12 13:02:43');
INSERT INTO `platform_login_info` VALUES ('5it1n1lv7rlhe', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-12 13:50:15');
INSERT INTO `platform_login_info` VALUES ('5it1n1sbfkmh1', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-12 14:58:49');
INSERT INTO `platform_login_info` VALUES ('5it1n2eidad21', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-12 18:31:24');
INSERT INTO `platform_login_info` VALUES ('5it1n2g2pd924', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-12 18:57:53');
INSERT INTO `platform_login_info` VALUES ('5it1nhg4x621d', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 09:28:49');
INSERT INTO `platform_login_info` VALUES ('5it1nhg8vib1e', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 09:30:33');
INSERT INTO `platform_login_info` VALUES ('5it1nhlb7o21h', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 10:15:40');
INSERT INTO `platform_login_info` VALUES ('5it1nhlbg9s1i', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 10:15:52');
INSERT INTO `platform_login_info` VALUES ('5it1nhrowme1k', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 11:22:57');
INSERT INTO `platform_login_info` VALUES ('5it1nhrsomt1l', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 11:24:33');
INSERT INTO `platform_login_info` VALUES ('5it1nhwnu6j6l', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 12:06:05');
INSERT INTO `platform_login_info` VALUES ('5it1nhyjako3g', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 12:37:53');
INSERT INTO `platform_login_info` VALUES ('5it1ni37tt63y', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 13:16:16');
INSERT INTO `platform_login_info` VALUES ('5it1ni4ls3ptm', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 13:39:46');
INSERT INTO `platform_login_info` VALUES ('5it1ni4lx40tn', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 13:39:53');
INSERT INTO `platform_login_info` VALUES ('5it1ni50wejto', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 13:46:52');
INSERT INTO `platform_login_info` VALUES ('5it1ni51221tp', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 13:46:59');
INSERT INTO `platform_login_info` VALUES ('5it1ni8ecwakt', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 14:03:18');
INSERT INTO `platform_login_info` VALUES ('5it1ni9barmsu', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 14:18:55');
INSERT INTO `platform_login_info` VALUES ('5it1ni9fld95n', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 14:20:56');
INSERT INTO `platform_login_info` VALUES ('5it1ni9ymphbi', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 14:29:44');
INSERT INTO `platform_login_info` VALUES ('5it1nias5x0ca', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 14:43:22');
INSERT INTO `platform_login_info` VALUES ('5it1nibhn2fpi', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 14:55:10');
INSERT INTO `platform_login_info` VALUES ('5it1nierulkpj', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 15:10:26');
INSERT INTO `platform_login_info` VALUES ('5it1niflukbcg', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 15:24:25');
INSERT INTO `platform_login_info` VALUES ('5it1nih43yj7k', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 15:49:57');
INSERT INTO `platform_login_info` VALUES ('5it1nih545z7l', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 15:50:04');
INSERT INTO `platform_login_info` VALUES ('5it1nime5vt7p', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 16:38:24');
INSERT INTO `platform_login_info` VALUES ('5it1nina62t7q', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 16:53:17');
INSERT INTO `platform_login_info` VALUES ('5it1niqia6t7r', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 17:07:35');
INSERT INTO `platform_login_info` VALUES ('5it1niqieil7s', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 17:07:40');
INSERT INTO `platform_login_info` VALUES ('5it1nis3xch1h', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 17:34:24');
INSERT INTO `platform_login_info` VALUES ('5it1niscikd1i', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 17:38:25');
INSERT INTO `platform_login_info` VALUES ('5it1nixu4fb8c', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 18:30:44');
INSERT INTO `platform_login_info` VALUES ('5it1nixubfx8d', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-13 18:30:53');
INSERT INTO `platform_login_info` VALUES ('5it1nj2y3ot8e', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-13 19:16:28');
INSERT INTO `platform_login_info` VALUES ('5it1ny431qigy', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 10:05:31');
INSERT INTO `platform_login_info` VALUES ('5it1ny45mfo0h', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 10:06:51');
INSERT INTO `platform_login_info` VALUES ('5it1ny4fgoqv4', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 10:11:10');
INSERT INTO `platform_login_info` VALUES ('5it1nybahb2sn', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 11:26:35');
INSERT INTO `platform_login_info` VALUES ('5it1nycd2r9ss', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-14 11:44:36');
INSERT INTO `platform_login_info` VALUES ('5it1nyib625zo', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 12:44:25');
INSERT INTO `platform_login_info` VALUES ('5it1nymjn6oiz', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 13:15:38');
INSERT INTO `platform_login_info` VALUES ('5it1nynotsij0', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-14 13:35:00');
INSERT INTO `platform_login_info` VALUES ('5it1nyouqc6j1', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 13:54:15');
INSERT INTO `platform_login_info` VALUES ('5it1nz4grm59n', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 16:16:37');
INSERT INTO `platform_login_info` VALUES ('5it1nz6ewkh9p', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-14 16:49:10');
INSERT INTO `platform_login_info` VALUES ('5it1nz6yo109q', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 16:58:32');
INSERT INTO `platform_login_info` VALUES ('5it1nzap93n9r', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-14 17:21:11');
INSERT INTO `platform_login_info` VALUES ('5it1nzbb3tv9s', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-14 17:31:31');
INSERT INTO `platform_login_info` VALUES ('5it1nzfpb4aa1', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-14 18:05:12');
INSERT INTO `platform_login_info` VALUES ('5it1oejfbk6u4', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 09:37:57');
INSERT INTO `platform_login_info` VALUES ('5it1oeko9f3g4', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 09:58:53');
INSERT INTO `platform_login_info` VALUES ('5it1oepryseg5', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-15 10:44:24');
INSERT INTO `platform_login_info` VALUES ('5it1oepwih8g6', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 10:46:37');
INSERT INTO `platform_login_info` VALUES ('5it1oeqhgjp8c', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 10:56:14');
INSERT INTO `platform_login_info` VALUES ('5it1oet61m48d', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-15 11:01:20');
INSERT INTO `platform_login_info` VALUES ('5it1oet65db8e', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 11:01:25');
INSERT INTO `platform_login_info` VALUES ('5it1oeu4s138f', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-15 11:17:41');
INSERT INTO `platform_login_info` VALUES ('5it1oeu9g5k8g', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 11:19:59');
INSERT INTO `platform_login_info` VALUES ('5it1of0okuo8h', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-15 12:27:42');
INSERT INTO `platform_login_info` VALUES ('5it1of15tk78i', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 12:35:47');
INSERT INTO `platform_login_info` VALUES ('5it1of7y7wbjj', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 13:49:49');
INSERT INTO `platform_login_info` VALUES ('5it1ofhxookjk', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-15 15:17:21');
INSERT INTO `platform_login_info` VALUES ('5it1ofi5vryrq', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 15:21:03');
INSERT INTO `platform_login_info` VALUES ('5it1ofohklrrr', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-15 16:27:26');
INSERT INTO `platform_login_info` VALUES ('5it1ofuuebgv7', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 17:34:02');
INSERT INTO `platform_login_info` VALUES ('5it1ofuywgqv8', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 17:36:12');
INSERT INTO `platform_login_info` VALUES ('5it1ofv9sh5ve', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 17:41:20');
INSERT INTO `platform_login_info` VALUES ('5it1ofw6d7gvf', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-15 17:56:40');
INSERT INTO `platform_login_info` VALUES ('5it1ofzbogcq8', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 18:09:27');
INSERT INTO `platform_login_info` VALUES ('5it1og1ua2r1s', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-15 18:51:54');
INSERT INTO `platform_login_info` VALUES ('5it1og5g5p58n', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-15 19:12:14');
INSERT INTO `platform_login_info` VALUES ('5it1ov3yyarrd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 09:57:24');
INSERT INTO `platform_login_info` VALUES ('5it1ov85kilre', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-16 10:27:51');
INSERT INTO `platform_login_info` VALUES ('5it1ov8gfjprf', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 10:32:58');
INSERT INTO `platform_login_info` VALUES ('5it1ovcld14c0', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 11:02:06');
INSERT INTO `platform_login_info` VALUES ('5it1ovcmhkv9o', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 11:02:59');
INSERT INTO `platform_login_info` VALUES ('5it1ovcww2em2', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 11:07:44');
INSERT INTO `platform_login_info` VALUES ('5it1ovdwxoam3', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-16 11:24:26');
INSERT INTO `platform_login_info` VALUES ('5it1ovfnazjcd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '0', '当前密码错误', '2019-08-16 11:53:36');
INSERT INTO `platform_login_info` VALUES ('5it1ovfne3rce', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 11:53:40');
INSERT INTO `platform_login_info` VALUES ('5it1ovjmgugcg', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-16 12:20:15');
INSERT INTO `platform_login_info` VALUES ('5it1ovjtarhch', 'tenant1', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '0', '当前登录的用户不存在', '2019-08-16 12:23:34');
INSERT INTO `platform_login_info` VALUES ('5it1ovjte1wci', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 12:23:38');
INSERT INTO `platform_login_info` VALUES ('5it1ovl1x4jck', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-16 12:44:16');
INSERT INTO `platform_login_info` VALUES ('5it1ovr0ihwcl', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 13:44:28');
INSERT INTO `platform_login_info` VALUES ('5it1ovuldyecm', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-16 14:04:41');
INSERT INTO `platform_login_info` VALUES ('5it1ovv27arcn', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 14:12:25');
INSERT INTO `platform_login_info` VALUES ('5it1ovwuul0co', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-16 14:42:41');
INSERT INTO `platform_login_info` VALUES ('5it1ow36ddfmv', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 15:48:57');
INSERT INTO `platform_login_info` VALUES ('5it1ow8eop3mw', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-16 16:36:43');
INSERT INTO `platform_login_info` VALUES ('5it1ow8r0vo1k', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 16:42:18');
INSERT INTO `platform_login_info` VALUES ('5it1owczzhh2i', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 17:13:55');
INSERT INTO `platform_login_info` VALUES ('5it1owdzs5x2j', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-16 17:30:25');
INSERT INTO `platform_login_info` VALUES ('5it1oweryc4w6', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 17:43:39');
INSERT INTO `platform_login_info` VALUES ('5it1owilggwdq', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-16 18:07:55');
INSERT INTO `platform_login_info` VALUES ('5it1q8nf2yfjz', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-19 09:12:32');
INSERT INTO `platform_login_info` VALUES ('5it1q8o14adk0', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 09:23:00');
INSERT INTO `platform_login_info` VALUES ('5it1q8twcy5v0', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 10:21:15');
INSERT INTO `platform_login_info` VALUES ('5it1q8zzlwhku', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 11:23:45');
INSERT INTO `platform_login_info` VALUES ('5it1q97q8pg3q', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 12:53:45');
INSERT INTO `platform_login_info` VALUES ('5it1q9c4a7dur', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 13:27:18');
INSERT INTO `platform_login_info` VALUES ('5it1q9csfqj5f', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 13:38:45');
INSERT INTO `platform_login_info` VALUES ('5it1q9dv1uw4s', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 13:56:47');
INSERT INTO `platform_login_info` VALUES ('5it1q9hx2qjc8', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 14:25:00');
INSERT INTO `platform_login_info` VALUES ('5it1q9uvb4q6z', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 16:42:12');
INSERT INTO `platform_login_info` VALUES ('5it1qa1uywfsk', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-19 17:59:54');
INSERT INTO `platform_login_info` VALUES ('5it1qac7iqtpa', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-19 19:33:35');
INSERT INTO `platform_login_info` VALUES ('5it1qpjwa9s8f', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-20 11:32:41');
INSERT INTO `platform_login_info` VALUES ('5it1qpkvz2us8', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-20 11:49:06');
INSERT INTO `platform_login_info` VALUES ('5it1qpupy5esc', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-20 13:14:21');
INSERT INTO `platform_login_info` VALUES ('5it1qpuz7smsd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-20 13:18:53');
INSERT INTO `platform_login_info` VALUES ('5it1qpwquqksh', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-20 13:48:22');
INSERT INTO `platform_login_info` VALUES ('5it1qpwrfmxsi', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-20 13:48:50');
INSERT INTO `platform_login_info` VALUES ('5it1qq1133iig', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-20 14:20:18');
INSERT INTO `platform_login_info` VALUES ('5it1qq28ijdcp', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-20 14:40:45');
INSERT INTO `platform_login_info` VALUES ('5it1qq2tob4cq', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-20 14:50:32');
INSERT INTO `platform_login_info` VALUES ('5it1qqcvxpicr', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-20 16:19:33');
INSERT INTO `platform_login_info` VALUES ('5it1qqeyy4fcs', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-20 16:54:33');
INSERT INTO `platform_login_info` VALUES ('5it1qqinv8oct', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-20 17:16:35');
INSERT INTO `platform_login_info` VALUES ('5it1qqj0z3sol', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-20 17:22:46');
INSERT INTO `platform_login_info` VALUES ('5it1qqkfo8kom', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-20 17:46:12');
INSERT INTO `platform_login_info` VALUES ('5it1qqkft3yon', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-20 17:46:18');
INSERT INTO `platform_login_info` VALUES ('5it1qqpc3t7oo', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-20 18:28:43');
INSERT INTO `platform_login_info` VALUES ('5it1r5qikqfop', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-21 09:18:17');
INSERT INTO `platform_login_info` VALUES ('5it1r5xk0m47q', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-21 10:36:42');
INSERT INTO `platform_login_info` VALUES ('5it1r61y6a5n2', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-21 11:10:21');
INSERT INTO `platform_login_info` VALUES ('5it1r61yz4qn3', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-21 11:10:58');
INSERT INTO `platform_login_info` VALUES ('5it1r6mhpelnh', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-21 14:55:24');
INSERT INTO `platform_login_info` VALUES ('5it1rmbkm3z9u', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-22 09:46:43');
INSERT INTO `platform_login_info` VALUES ('5it1rn47cpeqd', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-22 14:27:33');
INSERT INTO `platform_login_info` VALUES ('5it1rn4sk1iqe', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-22 14:37:23');
INSERT INTO `platform_login_info` VALUES ('5it1rn9i8usfw', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-22 15:16:40');
INSERT INTO `platform_login_info` VALUES ('5it1rngasq1rt', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-22 16:30:49');
INSERT INTO `platform_login_info` VALUES ('5it1s2t3789zl', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-23 09:15:23');
INSERT INTO `platform_login_info` VALUES ('5it1s2t3foezm', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-23 09:15:34');
INSERT INTO `platform_login_info` VALUES ('5it1s2u3t28s7', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-23 09:32:31');
INSERT INTO `platform_login_info` VALUES ('5it1s34trk01q', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-23 11:12:38');
INSERT INTO `platform_login_info` VALUES ('5it1s3gd748uk', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-23 13:06:20');
INSERT INTO `platform_login_info` VALUES ('5it1s3gz8m2zl', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-23 13:16:49');
INSERT INTO `platform_login_info` VALUES ('5it1s3h5a0q9g', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-23 13:19:30');
INSERT INTO `platform_login_info` VALUES ('5it1s3octjhan', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '退出系统成功', '2019-08-23 14:40:40');
INSERT INTO `platform_login_info` VALUES ('5it1s3od0pnao', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '登陆系统成功', '2019-08-23 14:40:49');

-- ----------------------------
-- Table structure for platform_member
-- ----------------------------
DROP TABLE IF EXISTS `platform_member`;
CREATE TABLE `platform_member`  (
  `MEMBER_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员编号',
  `MEMBER_LOGIN_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员登陆编号',
  `MEMBER_NAME` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员名',
  `MEMBER_PASSWORD` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员密码',
  `MEMBER_EMAIL` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员电子邮箱',
  `MEMBER_PHONE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员手机号',
  `MEMBER_STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员状态:1：激活  0：冻结',
  `MEMBER_IDENTIFICATION_STATE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员认证状态:1：实名认证 0：待认证',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`MEMBER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_member
-- ----------------------------
INSERT INTO `platform_member` VALUES ('5it0lu4zewctz', 'tenant', 'tenant', '$2a$10$6d.fYm.bRAa0PM2tjh5R8O4DFlbwYJBA2IXCetCaYwXc2vx8BegZG', NULL, NULL, '1', '0', 0, '5it0lu4zewctz', '2019-07-31 14:24:48', '5it0lu4zewctz', '2019-07-31 14:24:48');

-- ----------------------------
-- Table structure for platform_member_extend
-- ----------------------------
DROP TABLE IF EXISTS `platform_member_extend`;
CREATE TABLE `platform_member_extend`  (
  `MEMBER_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员编号',
  `MEMBER_DEREGISTER_STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员注销状态:1:注销待审核、0:已注销',
  `MEMBER_DEREGISTER_REASON` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员注销原因',
  `MEMBER_REGISTER_TIME` datetime(0) NOT NULL COMMENT '会员注册时间',
  `MEMBER_PROFILE_PICTURE` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '会员头像',
  `MEMBER_REAL_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员真实姓名',
  `MEMBER_IDCARD` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员身份证号',
  `REMARKS` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`MEMBER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员信息扩展表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_member_extend
-- ----------------------------
INSERT INTO `platform_member_extend` VALUES ('5it0lu4zewctz', NULL, NULL, '2019-07-31 14:24:48', NULL, NULL, NULL, NULL, 0, '5it0lu4zewctz', '2019-07-31 14:24:48', '5it0lu4zewctz', '2019-07-31 14:24:48');

-- ----------------------------
-- Table structure for platform_menu
-- ----------------------------
DROP TABLE IF EXISTS `platform_menu`;
CREATE TABLE `platform_menu`  (
  `MENU_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `MENU_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名',
  `MENU_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单URL',
  `PARENT_MENU_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父菜单',
  `WEB_FONT` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网页字体',
  `DISP_FLAG` decimal(3, 0) NULL DEFAULT NULL COMMENT '表示顺',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_menu
-- ----------------------------
INSERT INTO `platform_menu` VALUES ('pano', '全景管理', '', NULL, '<i class=\"fa fa-cogs fa-lg\" aria-hidden=\"true\"></i>', 5, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('pano01', '全景管理', '', 'pano', '<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>', 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('pano0101', '展览一览', 'member/pano0102/', 'pano01', NULL, 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('pano0102', '展厅管理', 'member/pano0110/', 'pano01', NULL, 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz', '系统管理', '', NULL, '<i class=\"fa fa-cogs fa-lg\" aria-hidden=\"true\"></i>', 5, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz01', '基础信息管理', '', 'platformz', '<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>', 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0101', '用户列表', 'member/platformz0101/', 'platformz01', NULL, 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0102', '用户角色', 'member/platformz0102/', 'platformz01', NULL, 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0103', '菜单权限管理', 'member/platformz0103/', 'platformz01', NULL, 3, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz02', '部门信息管理', '', 'platformz', '<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>', 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0201', '部门用户', 'member/platformz0201/', 'platformz02', NULL, 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0202', '部门管理', 'member/platformz0199/', 'platformz02', NULL, 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz03', '基础数据', '', 'platformz', '<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>', 3, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0301', '通用字典表', 'member/platformz0301/', 'platformz03', NULL, 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0302', '字典表platformz0302', 'member/platformz0302/', 'platformz03', NULL, 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz04', '工作流程管理', '', 'platformz', '<i class=\"fa fa-bars\" aria-hidden=\"true\"></i>', 4, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0401', '模型列表', 'act/goActModel', 'platformz04', NULL, 1, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0402', '流程管理', 'act/goAct', 'platformz04', NULL, 2, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0403', '请假流程', 'member/leave/showLeave', 'platformz04', NULL, 3, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0404', '待办任务', 'member/leave/showTask', 'platformz04', NULL, 4, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');
INSERT INTO `platform_menu` VALUES ('platformz0405', '已办任务', 'member/leave/showHiTask', 'platformz04', NULL, 5, 0, 'cisadmin', '2018-05-30 16:45:14', 'scadmin', '2018-05-30 16:45:14');

-- ----------------------------
-- Table structure for platform_online_user
-- ----------------------------
DROP TABLE IF EXISTS `platform_online_user`;
CREATE TABLE `platform_online_user`  (
  `SESSION_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户会话id',
  `LOGIN_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `IP_ADDRESS` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP地址',
  `LOGIN_ADDRESS` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `BROWSER` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器类型',
  `OPERATING_SYSTEM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在线状态（1在线 0离线）',
  `START_DATE` datetime(0) NULL DEFAULT NULL COMMENT '会话创建时间',
  `LAST_ACCESS_DATE` datetime(0) NULL DEFAULT NULL COMMENT '会话最后访问时间',
  `EXPIRE_TIME` decimal(5, 0) NULL DEFAULT NULL COMMENT '超时时间，单位为分钟',
  PRIMARY KEY (`SESSION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '在线用户记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_online_user
-- ----------------------------
INSERT INTO `platform_online_user` VALUES ('6B526C898489004A9C33BB177CF6C44A', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '2019-08-23 13:16:38', '2019-08-23 13:16:49', 150);
INSERT INTO `platform_online_user` VALUES ('D74CA7A0A6991B9638D42F7E692B692B', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '2019-08-23 14:40:49', '2019-08-23 15:48:41', 150);
INSERT INTO `platform_online_user` VALUES ('F88315294BE860F2144289ECEE9C0787', 'tenant', '172.16.192.200', NULL, 'Firefox', 'Windows 10', '1', '2019-08-23 11:12:38', '2019-08-23 13:15:01', 150);

-- ----------------------------
-- Table structure for platform_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `platform_operate_log`;
CREATE TABLE `platform_operate_log`  (
  `MODULE_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模块主键',
  `RECORD_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据记录主键',
  `OPERATE_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志主键',
  `MODULE_TITLE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块标题',
  `BUSINESS_TYPE` int(2) NULL DEFAULT NULL COMMENT '业务类型:（0其它 1新增 2修改 3删除）',
  `METHOD` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `OPERATOR_TYPE` int(2) NULL DEFAULT NULL COMMENT '操作类别:（0其它 1后台用户 2手机端用户）',
  `OPERATE_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人员',
  `DEPARTMENT_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `OPERATE_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求URL',
  `OPERATE_IP` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主机地址',
  `OPERATE_LOCATION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作地点',
  `OPERATE_PARAM` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `SUCCESS_STATUS` int(1) NULL DEFAULT NULL COMMENT '操作状态:（1正常 0异常）',
  `ERROR_MSG` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '错误消息',
  `OPERATE_TIME` datetime(0) NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`MODULE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_role
-- ----------------------------
DROP TABLE IF EXISTS `platform_role`;
CREATE TABLE `platform_role`  (
  `ROLE_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份编码',
  `ROLE_NAME` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份名',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `platform_role_menu`;
CREATE TABLE `platform_role_menu`  (
  `ROLE_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份编码',
  `MENU_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `DEPARTMENT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编码',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`ROLE_ID`, `MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单访问权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for platform_role_user
-- ----------------------------
DROP TABLE IF EXISTS `platform_role_user`;
CREATE TABLE `platform_role_user`  (
  `ROLE_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份编码',
  `MEMBER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员编码',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标识:1：删除 0：未删除',
  `CREATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime(0) NOT NULL COMMENT '创建日期',
  `LAST_UPDATE_USER_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终更新者',
  `LAST_UPDATE_DATE` datetime(0) NOT NULL COMMENT '最终更新日期',
  PRIMARY KEY (`ROLE_ID`, `MEMBER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色用户关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blob_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '以 Blob 类型存储存放日历信息， quartz可配置一个日历来指定一个时间范围' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cron_expression` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time_zone_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储 Cron Trigger，包括 Cron 表达式和时区信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `entry_id` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_class_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_durable` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_update_data` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储每一个已配置的 jobDetail 的详细信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lock_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储程序的悲观锁的信息(假如使用了悲观锁)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('PlatformScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储已暂停的 Trigger 组的信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储少量的有关 Scheduler 的状态信息，假如是用于集群中，可以看到其他的 Scheduler 实例' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储简单的 Trigger，包括重复次数，间隔，以及已触发的次数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `str_prop_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `str_prop_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `str_prop_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `int_prop_1` int(11) NULL DEFAULT NULL,
  `int_prop_2` int(11) NULL DEFAULT NULL,
  `long_prop_1` bigint(20) NULL DEFAULT NULL,
  `long_prop_2` bigint(20) NULL DEFAULT NULL,
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL,
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL,
  `bool_prop_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bool_prop_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储程序的悲观锁的信息(假如使用了悲观锁)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `next_fire_time` bigint(13) NULL DEFAULT NULL,
  `prev_fire_time` bigint(13) NULL DEFAULT NULL,
  `priority` int(11) NULL DEFAULT NULL,
  `trigger_state` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) NULL DEFAULT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `misfire_instr` smallint(2) NULL DEFAULT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储已配置的 Trigger 的信息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
