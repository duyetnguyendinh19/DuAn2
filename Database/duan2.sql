-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: duan2
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auth_role`
--

DROP TABLE IF EXISTS `auth_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auth_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `display_name` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_role`
--

LOCK TABLES `auth_role` WRITE;
/*!40000 ALTER TABLE `auth_role` DISABLE KEYS */;
INSERT INTO `auth_role` VALUES (1,'Administrators','Administrators',NULL,1),(2,'Users','Users',NULL,1),(3,'Staffs','Staffs',NULL,1);
/*!40000 ALTER TABLE `auth_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `first_name` varchar(15) DEFAULT NULL,
  `middle_name` varchar(15) DEFAULT NULL,
  `last_name` varchar(15) DEFAULT NULL,
  `full_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` varchar(1) DEFAULT '0',
  `salt` varchar(45) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `is_verified` tinyint(4) NOT NULL DEFAULT '1',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `user_type` tinyint(4) DEFAULT '1' COMMENT '0 -  system user; 1 - ; 2- affiliate; 4-sale staff',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`user_name`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (1,'admin','Admin',NULL,NULL,NULL,'admin@yo.com','1','5876695f8e4e1811','$2a$10$B3oHbYckX3Kn54nVHlhOo.SSS4DGTePm8VfDgl.pZ2cYy69y.2ZFe','2019-09-24 22:21:33',NULL,1,1,0),(2,'tanbv','Bùi','Văn','Tứn','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn','1','5876695f8e4e1811','$2a$10$Rso8wzBFOk.v69eTz3LFDubFXozXZmw06sPqUknmrP3NrauBvwErq','2019-10-03 15:40:22',NULL,1,1,1),(69,'duyetnd','','','','Nguyễn Đình Duyệt','duyetnd@gmail.com','1','5876695f8e4e1811','$2a$10$tN9jWuy0ALIQKIG.8Gr57eoNgGGEqr93/ZImGcYFOvjo.wrupg7RS','2019-10-08 11:19:28',NULL,1,1,2),(70,'trilm','','','','Lương Minh Trí','trilm@gmail.com','1','5876695f8e4e1811','$2a$10$mSOxNmIoD/We7WZXCpwlS..iv.iz.5uISkbChgr2Hbyls2X4txL/u','2019-10-08 19:34:02',NULL,1,1,1),(72,'tanbui21899','Test','Thôi','Nhé','Test Thôi Nhé','tanbui21899@fpt.com','0','5876695f8e4e1811','$2a$10$d2V36/33OOa06soOU0TtY.1z8z/dFaymMoQWrKtWs2dwtKiAIaGWS','2019-10-28 21:04:54',NULL,1,1,2),(74,'tanbui218991','Test','Tí','Thôi','Test Tí Thôi Mà','tanbui21899@fpt.edu.vn','0','5876695f8e4e1811','$2a$10$hcEjWrw1EhxG/6oCA4j6RemWjYrGavm9IePuVdhfqFJuMZVpnM3jS','2019-10-28 21:20:58',NULL,1,1,2),(75,'tanbv.dev','Test','Xíu','Thôi','Test Xíu Thôi','tanbv.dev@gmail.com','0','5876695f8e4e1811','$2a$10$Xcnnfpst7mdyIPjIx/wXv.mnnJp1wIPGEZh0w906p4I7GEt0la2Aq','2019-10-28 21:43:32',NULL,1,1,2),(77,'teoNguyenVan','Nguyễn','Văn','Tèo','Nguyễn Văn Tèo','tanbui21899@gmabcil.com','0','5876695f8e4e1811','$2a$10$EzIjKq/bx2uLRcOZSRS3tOwYuZvi7IhD1CHeIHUtSvhuOGO.1ybpu','2019-10-28 22:23:52',NULL,1,1,2),(78,'cartta','Test','Add',NULL,'Test Add Cart','tanbui21899@gmai09l.com','0','5876695f8e4e1811','$2a$10$rXEFNlzByjgq2VLSDM2zuu4KTQ.dLAypYCCdo0JHve3KjGtJOGBEG','2019-10-28 22:46:01',NULL,1,1,2),(79,'testnv','Nguyễn','Văn',NULL,'Nguyễn Văn Test','tanbui21899@gm2ail.com','0','5876695f8e4e1811','$2a$10$aMl.UUnQlyfbUCfBPL8N7.44UKlKPByPvd9yq4kVdQO1gZXWpRgrK','2019-10-28 22:52:16',NULL,1,1,2),(80,'annv','Nguyễn','Văn',NULL,'Nguyễn Văn An','tanbui21899@g2mail.com','0','5876695f8e4e1811','$2a$10$kxSkTQ2CaYxZ6t2dayHrQu2lTZqbPRfNQWqKbf4wuBoO5HksFi0..','2019-10-28 22:57:18',NULL,1,1,2),(81,'vantb','Test','Bùi',NULL,'Test Bùi Văn','tanbui21899@gm1ail.com','0','5876695f8e4e1811','$2a$10$PXPEK3pQxVyn9l3FZww6iOEpieqaOFUfm4e2Jma4fcP9UWA8od/O.','2019-10-29 20:35:15',NULL,1,1,2),(82,'tanbv1','Bùi','Văn',NULL,'Bùi Văn Tấn Online','tanbui21899@ga123il.com','0','5876695f8e4e1811','$2a$10$2tXTAh4P7NYH20vdpxfdieJL6CnfrekDQKsCHDREP1NSgTJXkhXNy','2019-10-31 19:23:30',NULL,1,1,2),(83,'testtb','Tấn','Bùi',NULL,'Tấn Bùi Test GG Live','tanbui21899@gmail.com','0','5876695f8e4e1811','$2a$10$Ux9mrYCopjQMBdTafL3N/OOwqjk.nvQh3X/J1r5HBjJM8LyUVktKO','2019-10-31 19:32:59',NULL,1,1,2);
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_role`
--

DROP TABLE IF EXISTS `auth_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auth_user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK_49bop9tj6tt0scnnvvjmnji4c` (`user_id`),
  CONSTRAINT `FK_49bop9tj6tt0scnnvvjmnji4c` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`),
  CONSTRAINT `FK_asv6my78ndgs160mlq2r3ad7r` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_role`
--

LOCK TABLES `auth_user_role` WRITE;
/*!40000 ALTER TABLE `auth_user_role` DISABLE KEYS */;
INSERT INTO `auth_user_role` VALUES (1,1),(2,1),(3,1),(3,2),(2,69),(3,70),(2,72),(2,74),(2,75),(2,77),(2,78),(2,79),(2,80),(2,81),(2,82),(2,83);
/*!40000 ALTER TABLE `auth_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_info`
--

DROP TABLE IF EXISTS `bank_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bank_info` (
  `id` bigint(20) NOT NULL,
  `bank_code` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bank_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bank_icon` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bank_type` int(1) DEFAULT NULL,
  `bank_info` text CHARACTER SET utf8 COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_info`
--

LOCK TABLES `bank_info` WRITE;
/*!40000 ALTER TABLE `bank_info` DISABLE KEYS */;
INSERT INTO `bank_info` VALUES (1,'VIETCOMBANK','Ngân hàng Ngoại thương (Vietcombank)','/static/bank/vietcombank_logo.png',0,''),(2,'VIETINBANK','Ngân hàng Công thương (Vietinbank)','/static/bank/vietinbank_logo.png',0,''),(3,'BIDV','Ngân hàng đầu tư và phát triển Việt Nam (BIDV)','/static/bank/bidv_logo.png',0,NULL),(4,'AGRIBANK','Ngân hàng Nông nghiệp (Agribank)','/static/bank/agribank_logo.png',0,NULL),(5,'SACOMBANK','Ngân hàng TMCP Sài Gòn Thương Tín (SacomBank)','/static/bank/sacombank_logo.png',0,NULL),(6,'TECHCOMBANK','Ngân hàng Kỹ thương Việt Nam (TechcomBank)	','/static/bank/techcombank_logo.png',0,NULL),(7,'ACB','Ngân hàng ACB','/static/bank/acb_logo.png',0,NULL),(8,'VPBANK','Ngân hàng Việt Nam Thịnh vượng (VPBank)','/static/bank/vpbank_logo.png',0,NULL),(9,'DONGABANK	','Ngân hàng Đông Á (DongABank)','/static/bank/dongabank_logo.png',0,NULL),(10,'EXIMBANK','Ngân hàng EximBank','/static/bank/eximbank_logo.png',0,NULL),(11,'TPBANK','Ngân hàng Tiên Phong (TPBank)','/static/bank/tpbank_logo.png',0,NULL),(12,'NCB','Ngân hàng Quốc dân (NCB)','/static/bank/ncb_logo.png',0,NULL),(13,'OJB','Ngân hàng Đại Dương (OceanBank)	','/static/bank/ojb_logo.png',0,NULL),(14,'MSBANK','Ngân hàng Hàng Hải (MSBANK)','/static/bank/msbank_logo.png',0,NULL),(15,'HDBANK','Ngan hàng HDBank','/static/bank/hdbank_logo.png',0,NULL),(16,'NAMABANK','Ngân hàng Nam Á (NamABank)','/static/bank/namabank_logo.png',0,NULL),(17,'OCB','Ngân hàng Phương Đông (OCB)','/static/bank/ocb_logo.png',0,NULL),(18,'VISA','Thẻ quốc tế Visa','/static/bank/visa_logo.png',2,NULL),(19,'MASTERCARD','Thẻ quốc tế MasterCard','/static/bank/mastercard_logo.png',2,NULL),(20,'JCB','Thẻ quốc tế JCB','/static/bank/jcb_logo.png',2,NULL),(21,'VNMART','Ví điện tử VnMart','/static/bank/vnmart_logo.png',0,NULL),(22,'SCB','Ngân hàng TMCP Sài Gòn (SCB)','/static/bank/scb_logo.png',0,NULL),(23,'IVB','Ngân hàng TNHH Indovina (IVB)','/static/bank/ivb_logo.png',0,NULL),(24,'ABBANK','Ngân hàng thương mại cổ phần An Bình (ABBANK)','/static/bank/abbank_logo.png',0,NULL),(25,'SHB','Ngân hàng Thương mại cổ phần Sài Gòn (SHB)','/static/bank/shb_logo.png',0,NULL),(26,'VIB','Ngân hàng Thương mại cổ phần Quốc tế Việt Nam (VIB)','/static/bank/vib_logo.png',0,NULL),(27,'VNPAYQR','Cổng thanh toán VNPAYQR','/static/bank/CTT-VNPAY-QR.png',0,NULL),(28,'VIETCAPITALBANK','Ngân Hàng Bản Việt','/static/bank/vccb_logo.png',0,NULL),(29,'PVCOMBANK','Ngân hàng TMCP Đại Chúng Việt Nam','/static/bank/PVComBank_logo.png',0,NULL),(30,'SAIGONBANK','Ngân hàng thương mại cổ phần Sài Gòn Công Thương','/static/bank/saigonbank.png',0,NULL),(31,'MBBANK','Ngân hàng thương mại cổ phần Quân đội','/static/bank/mbbank_logo.png',0,NULL),(32,'BACABANK','Ngân Hàng TMCP Bắc Á','/static/bank/bacabank_logo.png',0,NULL),(33,'UPI','UnionPay International','/static/bank/upi_logo.png',0,NULL),(34,'VIETCOMBANK','Ngân hàng Ngoại thương (Vietcombank)','/static/bank/vietcombank_logo.png',1,'{\"atmNumber\" : \"12500012312\" , \"name\" : \"Bùi Văn Tấn\",\"bank_name\": \"VietComBank chi nhánh Cầu Giấy, Hà Nội\", \"urlIpay\" : \"https://www.vietcombank.com.vn/IBanking2015/55c3c0a782b739e063efa9d5985e2ab4/Account/Login\"}'),(35,'VIETINBANK','Ngân hàng Công thương (Vietinbank)','/static/bank/vietinbank_logo.png',1,'{\"atmNumber\" : \"123123123\" , \"name\" : \"Nguyễn Đình Duyệt\",\"bank_name\": \"VietinBank chi nhánh Hà Đông, Hà Nội\", \"urlIpay\" : \"https://ebanking.vietinbank.vn/rcas/portal/web/retail/bflogin\"}');
/*!40000 ALTER TABLE `bank_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `total` float DEFAULT NULL,
  `payment` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `type_status` tinyint(4) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `isdelete` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mail_status` tinyint(4) DEFAULT NULL,
  `id_auth_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (26,19190000,1,1,3,'2019-10-21 20:38:46',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','10HIJICZWJ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(27,25250000,0,2,2,'2019-10-21 20:40:38',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','5GD9ZBIZ41','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(28,102010000,0,2,5,'2019-10-21 20:42:04',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','KJXJ9G16A2','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(29,16160000,1,1,3,'2019-10-21 21:01:54',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','YSBWRZDB18','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(30,23230000,1,1,0,'2019-10-21 21:05:53',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','FVWXOJPYNF','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(31,20200000,1,1,1,'2019-10-21 21:09:05',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','3J9VLINCEC','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(32,18180000,0,2,0,'2019-10-21 21:31:07',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','FMS7VPVBJR','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(33,35350000,0,2,5,'2019-10-23 11:54:48',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','Y2KWUMCHZC','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(34,51510000,0,1,4,'2019-10-23 11:57:05','admin','N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','X5N8HGZBCE','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(35,42420000,1,2,1,'2019-10-23 12:01:08',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','Y9Z23GMLK1','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(36,54540000,0,2,1,'2019-10-23 12:11:16',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','0NSZLV1ENK','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(37,25250000,0,2,5,'2019-10-23 16:34:54',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','Q9BT7L3BBI','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(38,50500000,0,2,5,'2019-10-23 16:41:35',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','ZKOXV247VT','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(39,50500000,0,2,5,'2019-10-23 16:43:01',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','8UTNBFUOA6','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(40,50500000,0,2,5,'2019-10-23 16:45:19',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','13T1OENY6A','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(41,50500000,0,2,5,'2019-10-23 16:45:22',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','GLRVZLN3QA','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(42,50500000,0,2,5,'2019-10-23 16:45:23',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','NFEK26AB3K','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(43,50500000,0,2,5,'2019-10-23 16:46:01',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','0NBDS2QYC4','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(44,50500000,0,2,5,'2019-10-23 16:46:21',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','GAH4OLLKPV','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(45,25250000,0,2,5,'2019-10-23 16:46:48',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','5YCJW6XPCJ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(46,25250000,0,2,5,'2019-10-23 17:08:51',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','V6SIS68TTE','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(47,16160000,1,2,5,'2019-10-23 17:35:02',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','6T4KU02EJF','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(48,25250000,1,1,5,'2019-10-23 18:17:10',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','3FJB2RHT4N','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(54,25250000,0,2,5,'2019-10-23 19:11:23',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','RTKRMR3DW3','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(55,25250000,1,1,0,'2019-10-23 19:16:13',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','P9STMJXBHZ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(56,25250000,1,2,5,'2019-10-24 14:30:46',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','CAZBEO8U20','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(57,25250000,1,2,5,'2019-10-24 14:30:51',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','S3HYJ166GJ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(58,16160000,1,2,5,'2019-10-24 14:42:03',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','CFVDLEW41R','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(59,5050000,1,2,1,'2019-10-24 14:55:53',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','ANTFQCQLIJ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(60,32825000,1,1,1,'2019-10-24 14:58:21',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','YDMDEENBSF','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(61,9090000,0,2,5,'2019-10-24 19:17:28',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','KGJQ90WUMY','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(62,16160000,0,2,5,'2019-10-24 19:23:49',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','KEFMFSIDN5','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(63,8080000,0,2,5,'2019-10-24 19:34:15',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','GQTXZHWHND','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(64,16160000,0,2,5,'2019-10-24 19:38:52',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','T4SEBFFEVR','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(65,4040000,0,2,5,'2019-10-24 19:46:43',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','UPGLR4F6AQ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(66,50500000,0,2,5,'2019-10-24 20:19:53',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','L60GUWT90F','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(67,25250000,0,2,5,'2019-10-24 20:23:24',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','EW8MHFQLOQ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(68,16160000,0,2,5,'2019-10-24 20:24:27',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','4SVALHAJMN','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(69,22725000,1,2,5,'2019-10-24 20:28:03',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','DWFH9P8OAJ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(70,16665000,1,2,1,'2019-10-24 20:34:05',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','5RBYYCT5YU','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(71,24240000,1,1,5,'2019-10-24 20:37:24',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','JUCODGS6KR','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(72,32320000,1,1,5,'2019-10-24 20:46:21',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','SSZ6AYGXJF','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(73,32320000,1,2,5,'2019-10-24 20:47:38',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','YIBQVPHQEX','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(74,32320000,1,2,5,'2019-10-24 20:47:45',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','EMDVZIZCNQ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(75,32320000,1,2,5,'2019-10-24 20:47:51',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','UNPVC5UFES','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(76,8080000,1,1,1,'2019-10-24 20:54:10',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','CMANKJVJ5Q','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(77,16160000,0,1,0,'2019-10-25 09:57:27',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','LK8Y0QQ52L','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',NULL,2),(78,16160000,1,1,1,'2019-10-25 10:14:43',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','XVTKXB8GF4','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',1,2),(79,32320000,0,1,0,'2019-10-25 10:26:43',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','CXJN62T21A','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',0,2),(80,25250000,0,1,0,'2019-10-25 10:26:58',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','YLAP39GFIM','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',0,2),(81,16160000,0,2,0,'2019-10-25 10:27:24',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','MG7QRXEXU9','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',0,2),(82,16160000,0,1,0,'2019-10-25 13:51:33',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','L3CZ49APVV','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',0,2),(84,16160000,1,1,5,'2019-10-28 19:51:22',NULL,'N','Cầu Giấy - Hà Nội','0338050500','RAVQJ2RB1N','Bui Van Tan','tanbvph05961@fpt.edu.vn',1,71),(85,16160000,1,2,5,'2019-10-28 20:48:46',NULL,'N','CG - HN','0339123123','K77J5ETAGL','Bui Van Tan','tanbvph05961@fpt.edu.vn',1,NULL),(86,16160000,1,1,5,'2019-10-28 21:03:00',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','LJXRUJYE2M','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',1,2),(87,25250000,1,2,5,'2019-10-28 21:04:55',NULL,'N','Cầu Giấy - Hà Nội','03323123123','8TZ1AOUYLH','Test Thôi Nhé','tanbui21899@gmail.com',1,72),(88,25250000,1,2,5,'2019-10-28 21:08:35',NULL,'N','Cầu Giấy - Hà Nội','03323123123','FYWYASX4WF','Test Thôi Nhé','tanbui21899@gmail.com',1,72),(89,16160000,1,2,5,'2019-10-28 21:23:04',NULL,'N','Test Tí Thôi','03123123123','CBUWBU7DWH','Test Tí Thôi Mà','tanbui21899@gmail.com',1,74),(90,16160000,1,2,5,'2019-10-28 21:30:24',NULL,'N','Test Tí Thôi','03123123123','CPVIYTAVBX','Test Tí Thôi Mà','tanbui21899@gmail.com',1,74),(91,16160000,1,1,5,'2019-10-28 21:34:19',NULL,'N','VEC','012345667812','FV3PQICDEV','TEst','tanbui21899@gmail.com',1,74),(92,25250000,1,1,5,'2019-10-28 21:50:11','admin','N','Ok I\'m fine','0231234112','ON9SOO37WZ','Test Xíu Thôi','tanbv.dev@gmail.com',1,75),(93,16160000,1,1,5,'2019-10-28 22:00:40',NULL,'N','OKe OkE','0999999999','ZHACBLLIQX','Oke Oke','tanbui21899@gmail.com',1,74),(94,16160000,1,2,5,'2019-10-28 22:25:14',NULL,'N','TEst Tèo Em','05412751231','WCOWZHWOTQ','Nguyễn Văn Tèo','tanbui21899@gmail.com',1,77),(95,4040000,1,1,5,'2019-10-28 22:29:34',NULL,'N','NGuyễn TEst','0312345120','ZORLJUPUST','Test Ok 4M','tanbui21899@gmail.com',1,77),(96,25250000,0,2,5,'2019-10-28 22:44:07',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','Y0CDMSUREO','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',1,2),(99,22725000,0,2,5,'2019-10-28 22:57:19',NULL,'N','TEst Tí','0676512352','FRMF3HA58M','Nguyễn Văn An','tanbui21899@gmail.com',1,80),(100,25250000,0,2,5,'2019-10-28 22:58:36',NULL,'N','Tsst','021313412312','BJB43F5P2T','Test','tanbui21899@gmail.com',1,80),(101,5050000,0,2,1,'2019-10-29 20:35:16',NULL,'N','Test Xíu Thôi Mà','01237865123','YXWNQ8ZRVO','Test Bùi Văn','tanbui21899@gmail.com',1,81),(102,23230000,0,2,2,'2019-10-29 20:36:11',NULL,'N','Ok I\'m Fine','01235671235','XIYVWFXZPN','Test Xíu Thôi Lần 2','tanbui21899@gmail.com',1,81),(103,27775000,0,2,5,'2019-10-31 19:21:45',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','ZQJKLQ7KSI','Bùi Văn Tứn Live','tanbvph05961@fpt.edu.vn',1,2),(104,25250000,1,1,1,'2019-10-31 19:23:31',NULL,'N','Cầu GIấy - Hà Nội - Test','0338123123','WKNRMYSPBJ','Bùi Văn Tấn Online','tanbui21899@gmail.com',1,82),(105,16160000,0,2,5,'2019-10-31 19:32:59',NULL,'N','TEst 1 Chút thôi','0338121233','OQ9MQ6QXQQ','Tấn Bùi Test GG Live','tanbui21899@gmail.com',1,83),(106,25250000,0,2,5,'2019-10-31 19:34:23',NULL,'N','Bạn Test Xíu','0338123123','SJFR07IP37','Tấn Bùi Test GG Live','tanbui21899@gmail.com',1,83),(107,25250000,1,1,5,'2019-10-31 19:35:56',NULL,'N','Test `1 Chút thôi','0338070700','RZMIX8VBSS','Tấn Bùi Test GG Live','tanbui21899@gmail.com',1,83),(108,25250000,0,2,5,'2019-11-10 17:24:02',NULL,'N','abcABC123456767','0338070700','GA0XRRHV2D','Bui Van Tan','tanbvph05961@fpt.edu.vn',1,2),(109,261590000,0,2,5,'2019-11-10 17:24:02',NULL,'N','Test Cầu Giấy - Hà Nội','0338070700','GZVJPGHGBF','Bùi Văn Tấn','tanbvph05961@fpt.edu.vn',1,2),(110,25250000,0,2,5,'2019-11-10 17:24:02',NULL,'N','aBS','0338070711','GAZT5JCVWO','Bui Van Tan','tanbvph05961@fpt.edu.vn',1,2),(111,25250000,0,2,2,'2019-11-11 19:29:40',NULL,'N','abcAB123456a@','0338070700','YNNFF2TT3R','Bui Van Tan','tanbvph05961@fpt.edu.vn',1,2),(112,41410000,0,2,1,'2019-11-11 20:19:49',NULL,'N','abcABC1234','0338070700','ZIO14WYXCS','Bui Van Tan','tanbvph05961@fpt.edu.vn',1,2),(113,7575000,0,2,1,'2019-11-16 16:16:00',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','82M4B4YFE1','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',1,2),(114,5544900,0,2,5,'2019-11-21 20:33:56',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','M2IFVMN4QU','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',1,2);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `isactive` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `isdelete` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Điện thoại',NULL,'Y','2019-05-10 20:30:15','N'),(2,'Iphone',1,'Y','2019-05-10 20:30:15','N'),(3,'SamSung',1,'Y','2019-05-10 20:30:15','N'),(4,'Xiaomi',1,'Y','2019-05-10 20:30:15','N'),(5,'Oppo',1,'Y','2019-05-10 20:30:15','N'),(6,'Tai nghe',NULL,'Y',NULL,'N'),(7,'SamSung',6,'Y',NULL,'N'),(8,'Bose',6,'Y',NULL,'N'),(9,'Langsdom',6,'Y',NULL,'N'),(10,'Xiaomi',6,'Y',NULL,'N'),(11,'Iphone',6,'Y',NULL,'N'),(12,'Remax',6,'Y',NULL,'N'),(13,'Phụ kiện',NULL,'Y',NULL,'N'),(14,'Kính cường lực',13,'Y',NULL,'N'),(15,'Ốp điện thoại',13,'Y',NULL,'N'),(16,'Miếng dán điện thoại',13,'Y',NULL,'N'),(17,'Máy tính bảng',NULL,'Y',NULL,'N'),(18,'Ipad 4',17,'Y',NULL,'N'),(19,'Huewei MediaPad M2',17,'Y',NULL,'N'),(20,'Google Pixcel C',17,'Y',NULL,'N'),(21,'Xiaomi Pad 4',17,'Y',NULL,'N');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `history` (
  `id` bigint(20) NOT NULL,
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info`
--

DROP TABLE IF EXISTS `info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `province` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `town` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `address` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `bank` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `company` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `atm_number_bank` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `info_plus` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `isdelete` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_auth_user` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info`
--

LOCK TABLES `info` WRITE;
/*!40000 ALTER TABLE `info` DISABLE KEYS */;
INSERT INTO `info` VALUES (1,'Thái Bình','Kiến Xương','Quang Bình - Kiến Xương -  Thái Bình','0338070700','1999-08-21','VIETTINBANK','Công ty Cổ phần Viễn Thông Tuổi Trẻ Yotel','212312354534','Từng học tại FPT PolyTechnic','N',2),(2,'Thái Bình','Kiến Xương','Quang Bình - Kiến Xương -  Thái Bình','0338070700','1999-08-21','VIETTINBANK','Công ty Cổ phần Viễn Thông Tuổi Trẻ Yotel','212312354534','Từng học tại FPT PolyTechnic','N',69);
/*!40000 ALTER TABLE `info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price_sale` float DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `mainImg` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `subImg` text COLLATE utf8_unicode_ci,
  `isdelete` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `info` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `rate` tinyint(4) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `id_category` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_category_idx` (`id_category`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Iphone X',18000000,47,'Ngon, Sạch, Đẹp',0,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',2,'2019-10-07 12:59:23',2),(2,'SamSung A50',6000000,116,'Đẹp, 4GB/64GB',5500000,0,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',1,'2019-05-12 12:40:50',3),(4,'Iphone 11',25000000,45,'Đẹp mê lì',0,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',3,'2019-07-20 15:32:56',2),(5,'Xiaomi Mi 5',7500000,198,'Phê',7000000,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',4,'2019-08-11 01:42:15',4),(6,'TABAAS',9000000,218,'Đẳng cấp',8800000,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',5,'2019-09-30 21:05:12',2),(7,'Iphone XS Max',25000000,51,'Khá Ô Tô Kê',24500000,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Ram 5GB Ô Tô kê',0,'2019-10-10 16:22:06',2),(8,'Test Sản Phẩm',23000000,198,'Test OK',21000000,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Test Ok',0,'2019-10-11 08:55:50',2),(9,'Test Tai Nghe',5000000,250,'Test',4500000,1,'http://localhost:8284/duan2_war/main-img/quickview_191011090147396.jpg','[\"http://localhost:8284/duan2_war/sub-img/2_191011090147408.jpg\",\"http://localhost:8284/duan2_war/sub-img/7_191011090147414.jpg\",\"http://localhost:8284/duan2_war/sub-img/8_191011090147419.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011090147426.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191011090147434.jpg\"]','N','Test',0,'2019-10-11 09:01:47',9),(10,'Test SamSung',22500000,997,'Test SamSUng',21000000,1,'http://localhost:8284/duan2_war/main-img/12_191011091043643.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191011091043665.jpg\",\"http://localhost:8284/duan2_war/sub-img/8_191011091043682.jpg\",\"http://localhost:8284/duan2_war/sub-img/4_191011091043692.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011091043701.jpg\",\"http://localhost:8284/duan2_war/sub-img/1_191011091043711.jpg\",\"http://localhost:8284/duan2_war/sub-img/2_191011091043721.jpg\"]','N','Test SamSung',0,'2019-10-11 09:10:44',3),(11,'Test',4000000,115,'Test',12000000,1,'http://localhost:8284/duan2_war/main-img/6_191011092904139.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191011092904157.jpg\"]','N','Test',0,'2019-10-11 09:29:04',4),(12,'Iphon Test',25000000,183,'abab',24500000,1,'http://localhost:8284/duan2_war/main-img/6_191011092904139.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191011092904157.jpg\"]','N','Đẹp lắm',0,'2019-10-11 13:55:03',5),(13,'Test Ok',16000000,62,'',0,1,'http://localhost:8284/duan2_war/main-img/6_191011092904139.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191011092904157.jpg\"]','N','Test ',0,'2019-10-11 14:03:49',4),(14,'Iphone Test',19000000,99,'Test',0,1,'http://localhost:8284/duan2_war/main-img/3_191011141753453.jpg','[\"http://localhost:8284/duan2_war/sub-img/4_191015084734846.jpg\"]','N','Test I\'m Fine',0,'2019-10-11 14:17:54',2),(15,'',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/3_191014084443898.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191014084443918.jpg\",\"http://localhost:8284/duan2_war/sub-img/6_191014084443937.jpg\",\"http://localhost:8284/duan2_war/sub-img/8_191014084443947.jpg\"]','M','a',0,'2019-10-14 08:44:44',3),(16,'',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014085133412.jpg','[\"http://localhost:8284/duan2_war/sub-img/1_191014085133422.jpg\"]','M','a',0,'2019-10-14 08:51:33',2),(17,'Bùi Văn Tấn',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014085557366.jpg','[\"http://localhost:8284/duan2_war/sub-img/8_191014085557402.jpg\"]','M','a',0,'2019-10-14 08:55:57',2),(18,'Test 14/10',23000000,1999,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014090305837.jpg','[\"http://localhost:8284/duan2_war/sub-img/9_191014090305849.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191014090305859.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191014090305867.jpg\"]','M','a',0,'2019-10-14 09:03:06',2),(19,'Test 14/10',23000000,1999,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014090335194.jpg','[\"http://localhost:8284/duan2_war/sub-img/9_191014090335205.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191014090335211.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191014090335218.jpg\"]','M','ababba',0,'2019-10-14 09:03:35',2),(20,'123123',23000000,1999,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014090341745.jpg','[\"http://localhost:8284/duan2_war/sub-img/9_191014090341754.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191014090341760.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191014090341771.jpg\"]','M','ababba',0,'2019-10-14 09:03:42',2),(21,'123123',23000000,1999,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014090346399.jpg','[\"http://localhost:8284/duan2_war/sub-img/9_191014090346407.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191014090346413.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191014090346420.jpg\"]','M','ababbababa',0,'2019-10-14 09:03:46',2),(22,'',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/4_191014090855271.jpg','[\"http://localhost:8284/duan2_war/sub-img/8_191014090855279.jpg\"]','M','a',0,'2019-10-14 09:08:55',5),(23,'',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/4_191014091329722.jpg','[\"http://localhost:8284/duan2_war/sub-img/8_191014091329739.jpg\"]','M','a',0,'2019-10-14 09:13:30',2),(24,'a123',25000000,1,'',0,1,'http://localhost:8284/duan2_war/main-img/4_191014092129709.jpg','[\"http://localhost:8284/duan2_war/sub-img/12_191014092129737.jpg\"]','M','abc213',0,'2019-10-14 09:21:30',3),(25,'Test Update',25000000,0,'Test Update',1000000,1,'http://localhost:8284/duan2_war/main-img/4_191014163638294.jpg','[\"http://localhost:8284/duan2_war/sub-img/4_191014172227578.jpg\"]','N','aba',0,'2019-10-14 09:31:18',7),(26,'SamSung A50s',5490000,99,'Samsung Galaxy A50 - 6GB/128GB - Chính hãng',5490000,1,'http://localhost:8284/duan2_war/main-img/201902280940503961_Samsung-Galaxy-A50-1_191116143416229.jpg','[\"http://localhost:8284/duan2_war/sub-img/201902280940503961_Samsung-Galaxy-A50-4_191116143416293.jpg\",\"http://localhost:8284/duan2_war/sub-img/201902280940503961_Samsung-Galaxy-A50-3_191116143416315.jpg\",\"http://localhost:8284/duan2_war/sub-img/201902280940503961_Samsung-Galaxy-A50-1_191116143416326.jpg\"]','N','Kích thước màn hình:6.4\nĐộ phân giải màn hình:1080 x 2340 pixels\nHệ điều hành:Android OS\nChip xử lý (CPU):Exynos 9610 Octa (10nm)\nRAM:6 GB\nMáy ảnh chính:Triple camera( 25 MP x 8 MP x 5 MP)\nBộ nhớ trong:128 GBDung lượng pin (mAh):4000 mAh',0,'2019-11-16 14:34:16',3),(27,'XiaoMi Mi 9',7500000,20,'Xiaomi Mi 9 - 6GB/128GB - Chính hãng',6500000,1,'http://localhost:8284/duan2_war/main-img/201902280940503961_Samsung-Galaxy-A50-3_191116152839758.jpg','[\"http://localhost:8284/duan2_war/sub-img/201902280940503961_Samsung-Galaxy-A50-4_191116152839777.jpg\",\"http://localhost:8284/duan2_war/sub-img/201902280940503961_Samsung-Galaxy-A50-1_191116152839789.jpg\",\"http://localhost:8284/duan2_war/sub-img/201902280940503961_Samsung-Galaxy-A50-2_191116152839798.jpg\"]','N','Kích thước màn hình:6.4 \nĐộ phân giải màn hình:1080 x 2340 pixels \nHệ điều hành:Android OS \nChip xử lý (CPU):Exynos 9610 Octa (10nm)\nRAM:6 GB \nMáy ảnh chính:Triple camera( 25 MP x 8 MP x 5 MP) \nBộ nhớ trong:128 GB\nDung lượng pin (mAh):4000 mAh',0,'2019-11-16 15:28:40',4);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_bill`
--

DROP TABLE IF EXISTS `product_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_product` bigint(20) NOT NULL,
  `id_bill` bigint(20) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `isdelete` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`id_product`,`id_bill`),
  KEY `fk_product_has_bill_product1_idx` (`id_product`),
  KEY `fk_bill_idx` (`id_bill`),
  CONSTRAINT `fk_bill` FOREIGN KEY (`id_bill`) REFERENCES `bill` (`id`),
  CONSTRAINT `fk_product` FOREIGN KEY (`id_product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_bill`
--

LOCK TABLES `product_bill` WRITE;
/*!40000 ALTER TABLE `product_bill` DISABLE KEYS */;
INSERT INTO `product_bill` VALUES (1,13,29,100,'N','2019-11-21 11:23:51'),(2,11,30,121,'N','2019-11-21 12:31:12'),(3,14,30,100,'N','2019-11-19 13:32:12'),(4,11,31,1,'N','2019-11-19 13:32:12'),(5,13,31,1,'N','2019-11-21 12:31:12'),(6,1,32,1,'N','2019-11-21 12:31:12'),(7,14,33,1,'N','2019-11-21 12:31:12'),(8,14,34,1,'N','2019-11-21 12:31:12'),(9,1,35,2,'N','2019-11-17 13:32:12'),(10,2,35,1,'N','2019-11-18 13:32:12'),(11,2,36,3,'N','2019-11-15 13:32:12'),(12,25,37,1,'N','2019-11-16 13:32:12'),(13,25,38,2,'N','2019-11-16 13:32:12'),(14,25,39,2,'N','2019-11-15 13:32:12'),(15,25,40,2,'N','2019-11-16 13:32:12'),(16,25,41,2,'N','2019-11-15 13:32:12'),(17,25,42,2,'N','2019-11-15 13:32:12'),(18,25,43,2,'N','2019-11-15 13:32:12'),(19,25,44,2,'N','2019-11-18 13:32:12'),(20,25,45,1,'N','2019-11-18 13:32:12'),(21,25,46,1,'N','2019-11-18 13:32:12'),(22,25,48,1,'N','2019-11-18 13:32:12'),(23,25,54,1,'N','2019-11-18 13:32:12'),(24,25,55,1,'N','2019-11-16 13:32:12'),(25,9,59,1,'N','2019-11-16 13:32:12'),(26,9,60,2,'N','2019-11-16 13:32:12'),(27,10,60,1,'N','2019-11-16 13:32:12'),(28,6,61,1,'N','2019-11-16 13:32:12'),(29,13,62,1,'N',NULL),(30,11,63,2,'N',NULL),(31,11,64,4,'N',NULL),(32,11,65,1,'N',NULL),(33,12,66,2,'N',NULL),(34,12,67,1,'N',NULL),(35,13,68,1,'N',NULL),(36,10,69,1,'N',NULL),(37,5,70,1,'N',NULL),(38,6,70,1,'N',NULL),(39,5,71,2,'N',NULL),(40,6,71,1,'N',NULL),(41,13,72,2,'N',NULL),(42,11,76,2,'N',NULL),(43,13,77,1,'N',NULL),(44,13,78,1,'N',NULL),(45,13,79,2,'N',NULL),(46,12,80,1,'N',NULL),(47,13,81,1,'N',NULL),(48,13,82,1,'N',NULL),(49,13,84,1,'N',NULL),(50,13,85,1,'N',NULL),(51,13,86,1,'N',NULL),(52,12,87,1,'N',NULL),(53,13,89,1,'N',NULL),(54,13,91,1,'N',NULL),(55,12,92,1,'N',NULL),(56,13,93,1,'N',NULL),(57,13,94,1,'N',NULL),(58,11,95,1,'N',NULL),(59,12,96,1,'N',NULL),(60,10,99,1,'N',NULL),(61,12,100,1,'N',NULL),(62,9,101,1,'N',NULL),(63,8,102,1,'N',NULL),(64,9,103,1,'N',NULL),(65,10,103,1,'N',NULL),(66,12,104,1,'N',NULL),(67,13,105,1,'N',NULL),(68,12,106,1,'N',NULL),(69,12,107,1,'N',NULL),(70,12,108,1,'N',NULL),(71,13,109,15,'N',NULL),(72,14,109,1,'N',NULL),(73,12,110,1,'N',NULL),(74,12,111,1,'N',NULL),(75,12,112,1,'N',NULL),(76,13,112,1,'N',NULL),(77,27,113,1,'N',NULL),(78,26,114,1,'N','2019-11-21 20:33:56');
/*!40000 ALTER TABLE `product_bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reject`
--

DROP TABLE IF EXISTS `reject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `code_bill` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reason` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reject`
--

LOCK TABLES `reject` WRITE;
/*!40000 ALTER TABLE `reject` DISABLE KEYS */;
INSERT INTO `reject` VALUES (1,'Bùi Văn Tứn','Y9Z23GMLK1','tanbvph05961@fpt.edu.vn','0338070700','1231abc','Quang Bình - Kiến Xương -  Thái Bình','2019-10-24 14:07:31'),(2,'Bùi Văn Tứn','ANTFQCQLIJ','tanbvph05961@fpt.edu.vn','0338070700','Test','Quang Bình - Kiến Xương -  Thái Bình','2019-10-24 14:57:33'),(3,'Bùi Văn Tứn','YDMDEENBSF','tanbvph05961@fpt.edu.vn','0338070700','Test123','Quang Bình - Kiến Xương -  Thái Bình','2019-10-24 15:01:13'),(4,'Bùi Văn Tứn','5RBYYCT5YU','tanbvph05961@fpt.edu.vn','0338070700','Test Reject','Quang Bình - Kiến Xương -  Thái Bình','2019-10-24 20:35:51'),(5,'Bùi Văn Tứn','CMANKJVJ5Q','tanbvph05961@fpt.edu.vn','0338070700','Test Reject 123456a@','Quang Bình - Kiến Xương -  Thái Bình','2019-10-24 20:55:46'),(6,'Bùi Văn Tứn','XVTKXB8GF4','tanbvph05961@fpt.edu.vn','0338070700','Test','Quang Bình - Kiến Xương -  Thái Bình','2019-10-25 10:16:41'),(7,'Bùi Văn Tấn Online','WKNRMYSPBJ','tanbui21899@gmail.com','0338123123','Test Reject','Cầu GIấy - Hà Nội - Test','2019-10-31 19:26:11'),(8,'Bùi Văn Tứn','82M4B4YFE1','tanbvph05961@fpt.edu.vn','0338070700','ates','Quang Bình - Kiến Xương -  Thái Bình','2019-11-16 16:19:11');
/*!40000 ALTER TABLE `reject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `problem` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `opinion` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `reply` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (1,'Bùi Văn Tấn','tanbv.dev@gmail.com','Test ','0338070700','Ok','2019-10-07 10:05:50','Câu hỏi của bạn được trả lời là :))'),(2,'Nguyễn Đình Duyệt','duyetnd@gmail.com','test','012312312','Demo','2019-10-06 00:00:00',NULL),(5,'Bùi Văn Tấn','tanbvph05961@fpt.edu.vn','Test','338070700','Test','2019-11-02 11:14:22','Soa nào'),(6,'Bùi Văn Tứn','tanbvph05961@fpt.edu.vn','Tứn Test','0338070700','Chưa Ok cho lắm','2019-11-16 17:03:42',NULL);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reply` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `rate` tinyint(4) DEFAULT NULL,
  `id_product` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (2,'Bùi Văn Tấn','tanbvph05961@fpt.edu.vn','2019-08-12 10:25:04','Đẹp hút hồn, không còn gì để nói về vẻ đẹp sản phẩm này','Nghĩ gì',1,2,26),(3,'Bùi Văn Tấn','tanbui21899@gmail.com','2019-08-12 10:25:04','Đẹp hút hồn, không còn gì để nói về vẻ đẹp sản phẩm này',NULL,1,3,2);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_google`
--

DROP TABLE IF EXISTS `user_google`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_google` (
  `id` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `verified_email` bit(1) DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `given_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `family_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `link` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `picture` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_google`
--

LOCK TABLES `user_google` WRITE;
/*!40000 ALTER TABLE `user_google` DISABLE KEYS */;
INSERT INTO `user_google` VALUES ('100505277070345731463','tanbui21899@gmail.com',_binary '',NULL,NULL,NULL,NULL,'https://lh6.googleusercontent.com/-R4zIXcnqp2E/AAAAAAAAAAI/AAAAAAAAAAA/MBMGg8CCGeU/photo.jpg'),('100513888585096139867','tanbv.dev@gmail.com',_binary '',NULL,NULL,NULL,NULL,'https://lh3.googleusercontent.com/a-/AAuE7mDhXZ0atOGRxXBOFmoL4NunwNKnvEnvZCuJgkV5'),('108256775120054043273','tanbvph05961@fpt.edu.vn',_binary '','Bui Van Tan','Bui','Van Tan',NULL,'https://lh3.googleusercontent.com/a-/AAuE7mCclN0m7wexEJWQkimvN9lO4TuOYcmSyS4TW1h4');
/*!40000 ALTER TABLE `user_google` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vnpay_trans_info`
--

DROP TABLE IF EXISTS `vnpay_trans_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vnpay_trans_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `vnp_locale` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_curr_code` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_order_info` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_order_type` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_amount` bigint(20) DEFAULT NULL,
  `vnp_ip_addr` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_create_date` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_bank_code` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_bank_tran_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_pay_date` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_transaction_no` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_response_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_bill` bigint(20) DEFAULT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vnpay_trans_info`
--

LOCK TABLES `vnpay_trans_info` WRITE;
/*!40000 ALTER TABLE `vnpay_trans_info` DISABLE KEYS */;
INSERT INTO `vnpay_trans_info` VALUES (10,'2019-10-18 16:30:27','vn','VND','thanh toan don hang','billpayment',1616000000,'10.10.11.149','20191018163026','NCB',NULL,NULL,NULL,NULL,NULL,'53W8EZLBBN',0),(13,'2019-10-18 17:36:06','vn','VND','thanh toan don hang','billpayment',3939000000,'10.10.11.149','20191018173605','NCB',NULL,NULL,NULL,NULL,NULL,'RPQVXPS0LI',0),(14,'2019-10-18 18:51:51','vn','VND','Bùi Văn Tứn thanh toan don hang','billpayment',2525000000,'192.168.1.227','20191018185150','NCB',NULL,NULL,NULL,NULL,NULL,'00USS052BV',0),(15,'2019-10-18 19:02:56','vn','VND','Thanh toan don hang ma VR9HYIUO7M','billpayment',3888500000,'192.168.1.227','20191018190256','NCB',NULL,NULL,NULL,NULL,NULL,'VR9HYIUO7M',0),(17,'2019-10-18 19:28:06','vn','VND','Thanh toan don hang ma DYMNI9RK44','billpayment',2525000000,'192.168.1.227','20191018192805','NCB',NULL,NULL,NULL,NULL,NULL,'DYMNI9RK44',0),(18,'2019-10-19 08:50:41','vn','VND','Thanh toan don hang ma PCUIB9NS3K','billpayment',1919000000,'10.10.11.149','20191019085041','NCB',NULL,NULL,NULL,NULL,NULL,'PCUIB9NS3K',0),(19,'2019-10-19 08:57:54','vn','VND','Thanh toan don hang ma EKRTIUVP6P','billpayment',2272500000,'10.10.11.149','20191019085753','NCB','20191019085818','20191019085808','13184735','00',NULL,'EKRTIUVP6P',1),(20,'2019-10-19 09:07:42','vn','VND','Thanh toan don hang ma L2JMYWJNVZ','billpayment',4040000,'10.10.11.149','20191019090741','NCB','20191019090811','20191019090753','13184737','00',18,'L2JMYWJNVZ',1),(21,'2019-10-19 09:51:02','vn','VND','Thanh toan don hang ma QAIXXCO0TQ','billpayment',25250000,'10.10.11.149','20191019095102','NCB','20191019095121','20191019095111','13184751','00',19,'QAIXXCO0TQ',1),(22,'2019-10-19 09:58:08','vn','VND','Thanh toan don hang ma RAXOAVAAKY','billpayment',23230000,'10.10.11.149','20191019095807','NCB','20191019095838','20191019095829','13184752','00',20,'RAXOAVAAKY',1),(23,'2019-10-19 10:06:06','vn','VND','Thanh toan don hang ma L9EHMTSL8E','billpayment',64135000,'10.10.11.149','20191019100605','NCB','20191019100625','20191019100616','13184757','00',21,'L9EHMTSL8E',1),(24,'2019-10-19 10:19:11','vn','VND','Thanh toan don hang ma QA0HUXSDR2','billpayment',19190000,'10.10.11.149','20191019101910','NCB','20191019101931','20191019101922','13184762','00',22,'QA0HUXSDR2',1),(25,'2019-10-19 16:57:47','vn','VND','Thanh toan don hang ma FCMQAKEYPS','billpayment',177760000,'127.0.0.1','20191019165747','NCB','20191019165919','20191019165828','13184827','00',23,'FCMQAKEYPS',1),(26,'2019-10-21 20:27:03','vn','VND','Thanh toan don hang ma BFQF6XPUIY','billpayment',16160000,'192.168.1.227','20191021202702','VISA',NULL,NULL,NULL,NULL,NULL,'BFQF6XPUIY',0),(27,'2019-10-21 20:28:44','vn','VND','Thanh toan don hang ma PA0LJNJ9SO','billpayment',16160000,'192.168.1.227','20191021202844','NCB','20191021202904','20191021202854','13185277','00',25,'PA0LJNJ9SO',1),(28,'2019-10-21 20:38:46','vn','VND','Thanh toan don hang ma 10HIJICZWJ','billpayment',19190000,'192.168.1.227','20191021203845','NCB','20191021203902','20191021203853','13185279','00',26,'10HIJICZWJ',1),(29,'2019-10-21 21:01:53','vn','VND','Thanh toan don hang ma YSBWRZDB18','billpayment',16160000,'192.168.1.227','20191021210153','NCB','20191021210228','20191021210158','13185281','00',29,'YSBWRZDB18',1),(30,'2019-10-21 21:05:53','vn','VND','Thanh toan don hang ma FVWXOJPYNF','billpayment',23230000,'192.168.1.227','20191021210552','NCB','20191021210608','20191021210557','13185282','00',30,'FVWXOJPYNF',1),(31,'2019-10-21 21:09:05','vn','VND','Thanh toan don hang ma 3J9VLINCEC','billpayment',20200000,'192.168.1.227','20191021210904','NCB','20191021210916','20191021210907','13185283','00',31,'3J9VLINCEC',1),(32,'2019-10-23 12:01:07','vn','VND','Thanh toan don hang ma Y9Z23GMLK1','billpayment',42420000,'10.10.11.149','20191023120107','NCB','20191023120128','20191023120116','13185709','00',35,'Y9Z23GMLK1',1),(33,'2019-10-23 17:35:02','vn','VND','Thanh toan don hang ma 6T4KU02EJF','billpayment',16160000,'10.10.11.149','20191023173501','NCB',NULL,NULL,NULL,NULL,NULL,'6T4KU02EJF',0),(34,'2019-10-23 17:36:19','vn','VND','Thanh toan don hang ma CZZFMSLKYU','billpayment',50500000,'10.10.11.149','20191023173619','NCB',NULL,NULL,NULL,NULL,NULL,'CZZFMSLKYU',0),(35,'2019-10-23 17:39:56','vn','VND','Thanh toan don hang ma 9Y1MNKVV8W','billpayment',75750000,'10.10.11.149','20191023173955','NCB',NULL,NULL,NULL,NULL,NULL,'9Y1MNKVV8W',0),(36,'2019-10-23 17:40:43','vn','VND','Thanh toan don hang ma ABU2AHQ1C5','billpayment',75750000,'10.10.11.149','20191023174043','NCB',NULL,NULL,NULL,NULL,NULL,'ABU2AHQ1C5',0),(37,'2019-10-23 17:41:22','vn','VND','Thanh toan don hang ma 5B4TNMQREQ','billpayment',75750000,'10.10.11.149','20191023174122','NCB',NULL,NULL,NULL,NULL,NULL,'5B4TNMQREQ',0),(38,'2019-10-23 17:42:47','vn','VND','Thanh toan don hang ma GWWEIFUY2I','billpayment',75750000,'10.10.11.149','20191023174246','NCB',NULL,NULL,NULL,NULL,NULL,'GWWEIFUY2I',0),(39,'2019-10-23 17:42:54','vn','VND','Thanh toan don hang ma PUVIWN7O6E','billpayment',75750000,'10.10.11.149','20191023174254','NCB',NULL,NULL,NULL,NULL,NULL,'PUVIWN7O6E',0),(40,'2019-10-23 17:43:22','vn','VND','Thanh toan don hang ma HPG7OY38H6','billpayment',75750000,'10.10.11.149','20191023174322','NCB',NULL,NULL,NULL,NULL,NULL,'HPG7OY38H6',0),(41,'2019-10-23 18:17:09','vn','VND','Thanh toan don hang ma 3FJB2RHT4N','billpayment',25250000,'192.168.1.227','20191023181709','NCB','20191023181752','20191023181742','13185933','00',48,'3FJB2RHT4N',1),(42,'2019-10-23 19:08:44','vn','VND','Thanh toan don hang ma KMA9AVSFPT','billpayment',75750000,'192.168.1.227','20191023190844','NCB',NULL,NULL,NULL,NULL,NULL,'KMA9AVSFPT',0),(43,'2019-10-23 19:09:22','vn','VND','Thanh toan don hang ma NDPE4V3QAA','billpayment',75750000,'192.168.1.227','20191023190921','NCB',NULL,NULL,NULL,NULL,NULL,'NDPE4V3QAA',0),(44,'2019-10-23 19:10:04','vn','VND','Thanh toan don hang ma RM4ZQBVR4Z','billpayment',75750000,'192.168.1.227','20191023191003','NCB',NULL,NULL,NULL,NULL,NULL,'RM4ZQBVR4Z',0),(45,'2019-10-23 19:10:30','vn','VND','Thanh toan don hang ma ZPUKNXND1Q','billpayment',75750000,'192.168.1.227','20191023191029','NCB',NULL,NULL,NULL,NULL,NULL,'ZPUKNXND1Q',0),(46,'2019-10-23 19:10:59','vn','VND','Thanh toan don hang ma 0TAXJGLJY8','billpayment',75750000,'192.168.1.227','20191023191058','NCB',NULL,NULL,NULL,NULL,NULL,'0TAXJGLJY8',0),(47,'2019-10-23 19:16:12','vn','VND','Thanh toan don hang ma P9STMJXBHZ','billpayment',25250000,'192.168.1.227','20191023191612','NCB','20191023191644','20191023191635','13185953','00',55,'P9STMJXBHZ',1),(48,'2019-10-24 14:30:45','vn','VND','Thanh toan don hang ma CAZBEO8U20','billpayment',25250000,'10.10.11.149','20191024143045','NCB',NULL,NULL,NULL,NULL,NULL,'CAZBEO8U20',0),(49,'2019-10-24 14:30:51','vn','VND','Thanh toan don hang ma S3HYJ166GJ','billpayment',25250000,'10.10.11.149','20191024143050','NCB',NULL,NULL,NULL,NULL,NULL,'S3HYJ166GJ',0),(50,'2019-10-24 14:42:03','vn','VND','Thanh toan don hang ma CFVDLEW41R','billpayment',16160000,'10.10.11.149','20191024144202','NCB',NULL,NULL,NULL,NULL,NULL,'CFVDLEW41R',0),(51,'2019-10-24 14:55:53','vn','VND','Thanh toan don hang ma ANTFQCQLIJ','billpayment',5050000,'10.10.11.149','20191024145552','NCB',NULL,NULL,NULL,NULL,NULL,'ANTFQCQLIJ',0),(52,'2019-10-24 14:58:21','vn','VND','Thanh toan don hang ma YDMDEENBSF','billpayment',32825000,'10.10.11.149','20191024145821','NCB','20191024145849','20191024145840','13186243','00',60,'YDMDEENBSF',1),(53,'2019-10-24 20:28:03','vn','VND','Thanh toan don hang ma DWFH9P8OAJ','billpayment',22725000,'192.168.1.227','20191024202802','NCB',NULL,NULL,NULL,NULL,NULL,'DWFH9P8OAJ',0),(54,'2019-10-24 20:34:05','vn','VND','Thanh toan don hang ma 5RBYYCT5YU','billpayment',16665000,'192.168.1.227','20191024203404','NCB',NULL,NULL,NULL,NULL,NULL,'5RBYYCT5YU',0),(55,'2019-10-24 20:37:24','vn','VND','Thanh toan don hang ma JUCODGS6KR','billpayment',24240000,'192.168.1.227','20191024203723','NCB','20191024203743','20191024203734','13186394','00',71,'JUCODGS6KR',1),(56,'2019-10-24 20:46:20','vn','VND','Thanh toan don hang ma SSZ6AYGXJF','billpayment',32320000,'192.168.1.227','20191024204620','NCB','20191024204706','20191024204655','13186396','00',72,'SSZ6AYGXJF',1),(57,'2019-10-24 20:47:37','vn','VND','Thanh toan don hang ma YIBQVPHQEX','billpayment',32320000,'192.168.1.227','20191024204737','NCB',NULL,NULL,NULL,NULL,NULL,'YIBQVPHQEX',0),(58,'2019-10-24 20:47:45','vn','VND','Thanh toan don hang ma EMDVZIZCNQ','billpayment',32320000,'192.168.1.227','20191024204744','NCB',NULL,NULL,NULL,NULL,NULL,'EMDVZIZCNQ',0),(59,'2019-10-24 20:47:51','vn','VND','Thanh toan don hang ma UNPVC5UFES','billpayment',32320000,'192.168.1.227','20191024204751','NCB',NULL,NULL,NULL,NULL,NULL,'UNPVC5UFES',0),(60,'2019-10-24 20:54:10','vn','VND','Thanh toan don hang ma CMANKJVJ5Q','billpayment',8080000,'192.168.1.227','20191024205410','NCB','20191024205428','20191024205419','13186398','00',76,'CMANKJVJ5Q',1),(61,'2019-10-25 10:14:43','vn','VND','Thanh toan don hang ma XVTKXB8GF4','billpayment',16160000,'10.10.11.149','20191025101442','NCB','20191025101520','20191025101509','13186494','00',78,'XVTKXB8GF4',1),(62,'2019-10-28 19:24:08','vn','VND','Thanh toan don hang ma AHXIJCCGYX','billpayment',41410000,'192.168.1.227','20191028192408','NCB',NULL,NULL,NULL,NULL,NULL,'AHXIJCCGYX',0),(63,'2019-10-28 19:51:21','vn','VND','Thanh toan don hang ma RAVQJ2RB1N','billpayment',16160000,'192.168.1.227','20191028195121','NCB','20191028195155','20191028195144','13187408','00',84,'RAVQJ2RB1N',1),(64,'2019-10-28 20:39:47','vn','VND','Thanh toan don hang ma K77J5ETAGL','billpayment',16160000,'192.168.1.227','20191028203946','NCB',NULL,NULL,NULL,NULL,NULL,'K77J5ETAGL',0),(65,'2019-10-28 21:02:56','vn','VND','Thanh toan don hang ma LJXRUJYE2M','billpayment',16160000,'192.168.1.227','20191028210256','NCB','20191028210317','20191028210308','13187419','00',86,'LJXRUJYE2M',1),(66,'2019-10-28 21:04:51','vn','VND','Thanh toan don hang ma 8TZ1AOUYLH','billpayment',25250000,'192.168.1.227','20191028210451','NCB',NULL,NULL,NULL,NULL,NULL,'8TZ1AOUYLH',0),(67,'2019-10-28 21:05:54','vn','VND','Thanh toan don hang ma FYWYASX4WF','billpayment',25250000,'192.168.1.227','20191028210553','NCB',NULL,NULL,NULL,NULL,NULL,'FYWYASX4WF',0),(68,'2019-10-28 21:09:33','vn','VND','Thanh toan don hang ma 1YIWEEUST3','billpayment',25250000,'192.168.1.227','20191028210932','NCB',NULL,NULL,NULL,NULL,NULL,'1YIWEEUST3',0),(69,'2019-10-28 21:20:17','vn','VND','Thanh toan don hang ma CBUWBU7DWH','billpayment',16160000,'192.168.1.227','20191028212016','NCB',NULL,NULL,NULL,NULL,NULL,'CBUWBU7DWH',0),(70,'2019-10-28 21:29:50','vn','VND','Thanh toan don hang ma CPVIYTAVBX','billpayment',16160000,'192.168.1.227','20191028212950','NCB',NULL,NULL,NULL,NULL,NULL,'CPVIYTAVBX',0),(71,'2019-10-28 21:33:30','vn','VND','Thanh toan don hang ma FV3PQICDEV','billpayment',16160000,'192.168.1.227','20191028213329','NCB','20191028213600','20191028213549','13187421','00',91,'FV3PQICDEV',1),(72,'2019-10-28 21:39:56','vn','VND','Thanh toan don hang ma SCLKRHM96G','billpayment',25250000,'192.168.1.227','20191028213956','NCB',NULL,NULL,NULL,NULL,NULL,'SCLKRHM96G',0),(73,'2019-10-28 21:43:15','vn','VND','Thanh toan don hang ma ON9SOO37WZ','billpayment',25250000,'192.168.1.227','20191028214315','NCB',NULL,NULL,NULL,NULL,NULL,'ON9SOO37WZ',0),(74,'2019-10-28 21:59:40','vn','VND','Thanh toan don hang ma ZHACBLLIQX','billpayment',16160000,'192.168.1.227','20191028215939','NCB','20191028220146','20191028220137','13187430','00',93,'ZHACBLLIQX',1),(75,'2019-10-28 22:03:05','vn','VND','Thanh toan don hang ma GRHIERVXW9','billpayment',25250000,'192.168.1.227','20191028220304','NCB',NULL,NULL,NULL,NULL,NULL,'GRHIERVXW9',0),(76,'2019-10-28 22:05:55','vn','VND','Thanh toan don hang ma 3JU5EJVTEX','billpayment',25250000,'192.168.1.227','20191028220555','NCB',NULL,NULL,NULL,NULL,NULL,'3JU5EJVTEX',0),(77,'2019-10-28 22:23:33','vn','VND','Thanh toan don hang ma WCOWZHWOTQ','billpayment',16160000,'192.168.1.227','20191028222333','NCB',NULL,NULL,NULL,NULL,NULL,'WCOWZHWOTQ',0),(78,'2019-10-28 22:29:32','vn','VND','Thanh toan don hang ma ZORLJUPUST','billpayment',4040000,'192.168.1.227','20191028222931','NCB','20191028223035','20191028222942','13187436','00',95,'ZORLJUPUST',1),(79,'2019-10-31 19:23:30','vn','VND','Thanh toan don hang ma WKNRMYSPBJ','billpayment',25250000,'192.168.1.227','20191031192330','NCB','20191031192344','20191031192335','13188527','00',104,'WKNRMYSPBJ',1),(80,'2019-10-31 19:35:56','vn','VND','Thanh toan don hang ma RZMIX8VBSS','billpayment',25250000,'192.168.1.227','20191031193556','NCB','20191031193609','20191031193600','13188530','00',107,'RZMIX8VBSS',1);
/*!40000 ALTER TABLE `vnpay_trans_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'duan2'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-21 21:03:32
