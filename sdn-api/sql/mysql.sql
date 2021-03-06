-- ----------------------------
-- VPN卡信息表
-- ----------------------------
DROP TABLE IF EXISTS `vpn_card`;
CREATE TABLE `vpn_card` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `realNumber` bigint(12) NOT NULL COMMENT '真实号',
  `bizIP` varchar(16) NOT NULL COMMENT '业务IP',
  `stopIP` varchar(16) NOT NULL COMMENT '停用IP',
  `specialIP` varchar(16) NOT NULL COMMENT '特殊IP',
  `offsetBizIP` varchar(16) NOT NULL COMMENT '偏移后业务IP',
  `offsetStopIP` varchar(16) NOT NULL COMMENT '偏移后停机IP',
  `offsetSpecialIP` varchar(16) NOT NULL COMMENT '偏移后特殊IP',
  `insertDate` datetime NOT NULL COMMENT '入库时间：2016-01-01 00:00:00',
  `invalid` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态：0（有效）1（过期）',
  PRIMARY KEY (`id`),
  KEY `vpn_card_realNumber_index` (`realNumber`) USING BTREE,
  KEY `vpn_card_invalid_index` (`invalid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- VPN用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `vpn_user`;
CREATE TABLE `vpn_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `realNumber` bigint(12) NOT NULL COMMENT '真实号',
  `iccid` varchar(20) NOT NULL COMMENT 'VPN卡串号',
  `sponsorNumber` varchar(16) NOT NULL COMMENT '担保人手机号',
  `sponsorName` varchar(64) NOT NULL COMMENT '担保人姓名',
  `sponsorIDType` tinyint(2) NOT NULL COMMENT '担保人证件类型：参考数据1，2，3...',
  `sponsorIDNumber` varchar(32) NOT NULL COMMENT '担保人证件号码：参考数据342622XXXXXXXX8888或其它',
  `userNumber` varchar(16) DEFAULT NULL COMMENT '使用人手机号',
  `userName` varchar(64) NOT NULL COMMENT '使用人姓名',
  `userIDType` tinyint(2) NOT NULL COMMENT '使用人证件类型：参考数据1，2，3...',
  `userIDNumber` varchar(32) NOT NULL COMMENT '使用人证件号码：参考数据342622XXXXXXXX8888或其它',
  `registerDate` datetime NOT NULL COMMENT '开户时间：2016-01-01 00:00:00',
  `cancelDate` datetime NOT NULL COMMENT '销户时间：2016-01-01 00:00:00',
  `registerAgent` varchar(64) DEFAULT NULL COMMENT '开户代理商',
  `modifyDate` datetime NOT NULL COMMENT '变更时间：2016-01-01 00:00:00',
  `imageOne` varchar(255) NOT NULL COMMENT '身份认证照片一',
  `imageTwo` varchar(255) NOT NULL COMMENT '身份认证照片二',
  `imageThree` varchar(255) NOT NULL COMMENT '身份认证照片三',
  `invalid` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态：0（有效）1（过期）',
  PRIMARY KEY (`id`),
  KEY `vpn_user_realNumber_index` (`realNumber`) USING BTREE,
  KEY `vpn_user_invalid_index` (`invalid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 
-- ----------------------------
-- VPN用户地理位置信息表（该表仅做字段参考，存储采用HBase）
-- ----------------------------
DROP TABLE IF EXISTS `vpn_position`;
CREATE TABLE `vpn_position` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `realNumber` bigint(12) NOT NULL COMMENT '真实号',
  `bizIP` varchar(16) NOT NULL COMMENT '业务IP：192.168.0.1',
  `sac` varchar(16) NOT NULL COMMENT '基站SAC信息或CELLID信息：参考数据34162',
  `lac` varchar(16) NOT NULL COMMENT '基站LAC信息：参考数据25840',
  `address` varchar(16) NOT NULL COMMENT '详细地址',
  `flow` bigint(20) NOT NULL COMMENT '流量值',
  `time` datetime NOT NULL COMMENT '时间：2016-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
