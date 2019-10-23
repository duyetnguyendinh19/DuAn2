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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (1,'admin','Admin',NULL,NULL,NULL,'admin@yo.com','1','5876695f8e4e1811','$2a$10$B3oHbYckX3Kn54nVHlhOo.SSS4DGTePm8VfDgl.pZ2cYy69y.2ZFe','2019-09-24 22:21:33',NULL,1,1,0),(2,'tanbv','Bùi','Văn','Tứn','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn','1','5876695f8e4e1811','$2a$10$XXaa5vhOSKqFtkyAXp3mr.URnTGQk7GnB0NYYJIxKfShEijW72iWW','2019-10-03 15:40:22',NULL,1,1,1),(69,'duyetnd','','','','Nguyễn Đình Duyệt','duyetnd@gmail.com','1','5876695f8e4e1811','$2a$10$tN9jWuy0ALIQKIG.8Gr57eoNgGGEqr93/ZImGcYFOvjo.wrupg7RS','2019-10-08 11:19:28',NULL,1,1,2),(70,'trilm','','','','Lương Minh Trí','trilm@gmail.com','1','5876695f8e4e1811','$2a$10$mSOxNmIoD/We7WZXCpwlS..iv.iz.5uISkbChgr2Hbyls2X4txL/u','2019-10-08 19:34:02',NULL,1,1,1);
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
INSERT INTO `auth_user_role` VALUES (1,1),(2,1),(3,1),(3,2),(2,69),(3,70);
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
  `id_auth_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (26,19190000,1,1,3,'2019-10-21 20:38:46',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','10HIJICZWJ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(27,25250000,0,2,2,'2019-10-21 20:40:38',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','5GD9ZBIZ41','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(28,102010000,0,2,5,'2019-10-21 20:42:04',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','KJXJ9G16A2','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(29,16160000,1,1,3,'2019-10-21 21:01:54',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','YSBWRZDB18','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(30,23230000,1,1,0,'2019-10-21 21:05:53',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','FVWXOJPYNF','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(31,20200000,1,1,1,'2019-10-21 21:09:05',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','3J9VLINCEC','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(32,18180000,0,2,0,'2019-10-21 21:31:07',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','FMS7VPVBJR','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(33,35350000,0,2,5,'2019-10-23 11:54:48',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','Y2KWUMCHZC','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(34,51510000,0,1,4,'2019-10-23 11:57:05','admin','N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','X5N8HGZBCE','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(35,42420000,1,2,2,'2019-10-23 12:01:08',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','Y9Z23GMLK1','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(36,54540000,0,2,1,'2019-10-23 12:11:16',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','0NSZLV1ENK','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(37,25250000,0,2,5,'2019-10-23 16:34:54',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','Q9BT7L3BBI','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(38,50500000,0,2,5,'2019-10-23 16:41:35',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','ZKOXV247VT','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(39,50500000,0,2,5,'2019-10-23 16:43:01',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','8UTNBFUOA6','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(40,50500000,0,2,5,'2019-10-23 16:45:19',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','13T1OENY6A','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(41,50500000,0,2,5,'2019-10-23 16:45:22',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','GLRVZLN3QA','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(42,50500000,0,2,5,'2019-10-23 16:45:23',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','NFEK26AB3K','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(43,50500000,0,2,5,'2019-10-23 16:46:01',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','0NBDS2QYC4','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(44,50500000,0,2,5,'2019-10-23 16:46:21',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','GAH4OLLKPV','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(45,25250000,0,2,5,'2019-10-23 16:46:48',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','5YCJW6XPCJ','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(46,25250000,0,2,5,'2019-10-23 17:08:51',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','V6SIS68TTE','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(47,16160000,1,2,5,'2019-10-23 17:35:02',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','6T4KU02EJF','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2),(48,25250000,1,1,5,'2019-10-23 18:17:10',NULL,'N','Quang Bình - Kiến Xương -  Thái Bình','0338070700','3FJB2RHT4N','Bùi Văn Tứn','tanbvph05961@fpt.edu.vn',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Iphone X',18000000,47,'Ngon, Sạch, Đẹp',0,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',2,'2019-10-07 12:59:23',2),(2,'SamSung A50',6000000,116,'Đẹp, 4GB/64GB',5500000,0,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',1,'2019-05-12 12:40:50',3),(4,'Iphone 11',25000000,45,'Đẹp mê lì',0,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',3,'2019-07-20 15:32:56',2),(5,'Xiaomi Mi 5',7500000,200,'Phê',7000000,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',4,'2019-08-11 01:42:15',4),(6,'Oppo F9',9000000,220,'Đẳng cấp',8800000,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Sản xuất năm 2019',5,'2019-09-30 21:05:12',5),(7,'Iphone XS Max',25000000,51,'Khá Ô Tô Kê',24500000,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Ram 5GB Ô Tô kê',0,'2019-10-10 16:22:06',2),(8,'Test Sản Phẩm',23000000,199,'Test OK',21000000,1,'http://localhost:8284/duan2_war/main-img/1_191011085549529.jpg','[\"http://localhost:8284/duan2_war/sub-img/6_191011085549554.jpg\",\"http://localhost:8284/duan2_war/sub-img/9_191011085549568.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011085549575.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011085549582.jpg\"]','N','Test Ok',0,'2019-10-11 08:55:50',2),(9,'Test Tai Nghe',5000000,250,'Test',4500000,1,'http://localhost:8284/duan2_war/main-img/quickview_191011090147396.jpg','[\"http://localhost:8284/duan2_war/sub-img/2_191011090147408.jpg\",\"http://localhost:8284/duan2_war/sub-img/7_191011090147414.jpg\",\"http://localhost:8284/duan2_war/sub-img/8_191011090147419.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191011090147426.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191011090147434.jpg\"]','N','Test',0,'2019-10-11 09:01:47',9),(10,'Test SamSung',22500000,999,'Test SamSUng',21000000,1,'http://localhost:8284/duan2_war/main-img/12_191011091043643.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191011091043665.jpg\",\"http://localhost:8284/duan2_war/sub-img/8_191011091043682.jpg\",\"http://localhost:8284/duan2_war/sub-img/4_191011091043692.jpg\",\"http://localhost:8284/duan2_war/sub-img/3_191011091043701.jpg\",\"http://localhost:8284/duan2_war/sub-img/1_191011091043711.jpg\",\"http://localhost:8284/duan2_war/sub-img/2_191011091043721.jpg\"]','N','Test SamSung',0,'2019-10-11 09:10:44',3),(11,'Test',4000000,123,'Test',12000000,1,'http://localhost:8284/duan2_war/main-img/6_191011092904139.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191011092904157.jpg\"]','N','Test',0,'2019-10-11 09:29:04',4),(12,'Oppo Test',25000000,199,'',24500000,1,'http://localhost:8284/duan2_war/main-img/6_191011092904139.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191011092904157.jpg\"]','N','Đẹp lắm',0,'2019-10-11 13:55:03',5),(13,'Test Ok',16000000,96,'',0,1,'http://localhost:8284/duan2_war/main-img/6_191011092904139.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191011092904157.jpg\"]','N','Test ',0,'2019-10-11 14:03:49',4),(14,'Iphone Test',19000000,0,'',0,1,'http://localhost:8284/duan2_war/main-img/3_191011141753453.jpg','[\"http://localhost:8284/duan2_war/sub-img/4_191015084734846.jpg\"]','N','Test I\'m Fine',0,'2019-10-11 14:17:54',2),(15,'',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/3_191014084443898.jpg','[\"http://localhost:8284/duan2_war/sub-img/7_191014084443918.jpg\",\"http://localhost:8284/duan2_war/sub-img/6_191014084443937.jpg\",\"http://localhost:8284/duan2_war/sub-img/8_191014084443947.jpg\"]','M','a',0,'2019-10-14 08:44:44',3),(16,'',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014085133412.jpg','[\"http://localhost:8284/duan2_war/sub-img/1_191014085133422.jpg\"]','M','a',0,'2019-10-14 08:51:33',2),(17,'Bùi Văn Tấn',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014085557366.jpg','[\"http://localhost:8284/duan2_war/sub-img/8_191014085557402.jpg\"]','M','a',0,'2019-10-14 08:55:57',2),(18,'Test 14/10',23000000,1999,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014090305837.jpg','[\"http://localhost:8284/duan2_war/sub-img/9_191014090305849.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191014090305859.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191014090305867.jpg\"]','M','a',0,'2019-10-14 09:03:06',2),(19,'Test 14/10',23000000,1999,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014090335194.jpg','[\"http://localhost:8284/duan2_war/sub-img/9_191014090335205.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191014090335211.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191014090335218.jpg\"]','M','ababba',0,'2019-10-14 09:03:35',2),(20,'123123',23000000,1999,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014090341745.jpg','[\"http://localhost:8284/duan2_war/sub-img/9_191014090341754.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191014090341760.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191014090341771.jpg\"]','M','ababba',0,'2019-10-14 09:03:42',2),(21,'123123',23000000,1999,'',0,1,'http://localhost:8284/duan2_war/main-img/7_191014090346399.jpg','[\"http://localhost:8284/duan2_war/sub-img/9_191014090346407.jpg\",\"http://localhost:8284/duan2_war/sub-img/10_191014090346413.jpg\",\"http://localhost:8284/duan2_war/sub-img/12_191014090346420.jpg\"]','M','ababbababa',0,'2019-10-14 09:03:46',2),(22,'',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/4_191014090855271.jpg','[\"http://localhost:8284/duan2_war/sub-img/8_191014090855279.jpg\"]','M','a',0,'2019-10-14 09:08:55',5),(23,'',0,1,'',0,1,'http://localhost:8284/duan2_war/main-img/4_191014091329722.jpg','[\"http://localhost:8284/duan2_war/sub-img/8_191014091329739.jpg\"]','M','a',0,'2019-10-14 09:13:30',2),(24,'a123',25000000,1,'',0,1,'http://localhost:8284/duan2_war/main-img/4_191014092129709.jpg','[\"http://localhost:8284/duan2_war/sub-img/12_191014092129737.jpg\"]','M','abc213',0,'2019-10-14 09:21:30',3),(25,'Test Update',25000000,0,'Test Update',1000000,1,'http://localhost:8284/duan2_war/main-img/4_191014163638294.jpg','[\"http://localhost:8284/duan2_war/sub-img/4_191014172227578.jpg\"]','N','aba',0,'2019-10-14 09:31:18',7);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_bill`
--

DROP TABLE IF EXISTS `product_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_bill` (
  `id_product` bigint(20) NOT NULL,
  `id_bill` bigint(20) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `isdelete` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`,`id_product`,`id_bill`),
  KEY `fk_product_has_bill_product1_idx` (`id_product`),
  KEY `fk_bill_idx` (`id_bill`),
  CONSTRAINT `fk_bill` FOREIGN KEY (`id_bill`) REFERENCES `bill` (`id`),
  CONSTRAINT `fk_product` FOREIGN KEY (`id_product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_bill`
--

LOCK TABLES `product_bill` WRITE;
/*!40000 ALTER TABLE `product_bill` DISABLE KEYS */;
INSERT INTO `product_bill` VALUES (13,29,100,'N',1),(11,30,121,'N',2),(14,30,100,'N',3),(11,31,1,'N',4),(13,31,1,'N',5),(1,32,1,'N',6),(14,33,1,'N',7),(14,34,1,'N',8),(1,35,2,'N',9),(2,35,1,'N',10),(2,36,3,'N',11),(25,37,1,'N',12),(25,38,2,'N',13),(25,39,2,'N',14),(25,40,2,'N',15),(25,41,2,'N',16),(25,42,2,'N',17),(25,43,2,'N',18),(25,44,2,'N',19),(25,45,1,'N',20),(25,46,1,'N',21),(25,48,1,'N',22);
/*!40000 ALTER TABLE `product_bill` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (1,'Bùi Văn Tấn','tanbv.dev@gmail.com','Test ','0338070700','Ok','2019-10-07 10:05:50','abx'),(2,'Nguyễn Đình Duyệt','duyetnd@gmail.com','test','012312312','Demo','2019-10-06 00:00:00',NULL);
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
INSERT INTO `review` VALUES (2,'Bùi Văn Tấn','2019-08-12 10:25:04','Đẹp hút hồn, không còn gì để nói về vẻ đẹp sản phẩm này',NULL,1,NULL,2),(3,'Bùi Văn Tấn','2019-08-12 10:25:04','Đẹp hút hồn, không còn gì để nói về vẻ đẹp sản phẩm này','',1,NULL,2);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vnpay_trans_info`
--

LOCK TABLES `vnpay_trans_info` WRITE;
/*!40000 ALTER TABLE `vnpay_trans_info` DISABLE KEYS */;
INSERT INTO `vnpay_trans_info` VALUES (10,'2019-10-18 16:30:27','vn','VND','thanh toan don hang','billpayment',1616000000,'10.10.11.149','20191018163026','NCB',NULL,NULL,NULL,NULL,NULL,'53W8EZLBBN',0),(13,'2019-10-18 17:36:06','vn','VND','thanh toan don hang','billpayment',3939000000,'10.10.11.149','20191018173605','NCB',NULL,NULL,NULL,NULL,NULL,'RPQVXPS0LI',0),(14,'2019-10-18 18:51:51','vn','VND','Bùi Văn Tứn thanh toan don hang','billpayment',2525000000,'192.168.1.227','20191018185150','NCB',NULL,NULL,NULL,NULL,NULL,'00USS052BV',0),(15,'2019-10-18 19:02:56','vn','VND','Thanh toan don hang ma VR9HYIUO7M','billpayment',3888500000,'192.168.1.227','20191018190256','NCB',NULL,NULL,NULL,NULL,NULL,'VR9HYIUO7M',0),(17,'2019-10-18 19:28:06','vn','VND','Thanh toan don hang ma DYMNI9RK44','billpayment',2525000000,'192.168.1.227','20191018192805','NCB',NULL,NULL,NULL,NULL,NULL,'DYMNI9RK44',0),(18,'2019-10-19 08:50:41','vn','VND','Thanh toan don hang ma PCUIB9NS3K','billpayment',1919000000,'10.10.11.149','20191019085041','NCB',NULL,NULL,NULL,NULL,NULL,'PCUIB9NS3K',0),(19,'2019-10-19 08:57:54','vn','VND','Thanh toan don hang ma EKRTIUVP6P','billpayment',2272500000,'10.10.11.149','20191019085753','NCB','20191019085818','20191019085808','13184735','00',NULL,'EKRTIUVP6P',1),(20,'2019-10-19 09:07:42','vn','VND','Thanh toan don hang ma L2JMYWJNVZ','billpayment',4040000,'10.10.11.149','20191019090741','NCB','20191019090811','20191019090753','13184737','00',18,'L2JMYWJNVZ',1),(21,'2019-10-19 09:51:02','vn','VND','Thanh toan don hang ma QAIXXCO0TQ','billpayment',25250000,'10.10.11.149','20191019095102','NCB','20191019095121','20191019095111','13184751','00',19,'QAIXXCO0TQ',1),(22,'2019-10-19 09:58:08','vn','VND','Thanh toan don hang ma RAXOAVAAKY','billpayment',23230000,'10.10.11.149','20191019095807','NCB','20191019095838','20191019095829','13184752','00',20,'RAXOAVAAKY',1),(23,'2019-10-19 10:06:06','vn','VND','Thanh toan don hang ma L9EHMTSL8E','billpayment',64135000,'10.10.11.149','20191019100605','NCB','20191019100625','20191019100616','13184757','00',21,'L9EHMTSL8E',1),(24,'2019-10-19 10:19:11','vn','VND','Thanh toan don hang ma QA0HUXSDR2','billpayment',19190000,'10.10.11.149','20191019101910','NCB','20191019101931','20191019101922','13184762','00',22,'QA0HUXSDR2',1),(25,'2019-10-19 16:57:47','vn','VND','Thanh toan don hang ma FCMQAKEYPS','billpayment',177760000,'127.0.0.1','20191019165747','NCB','20191019165919','20191019165828','13184827','00',23,'FCMQAKEYPS',1),(26,'2019-10-21 20:27:03','vn','VND','Thanh toan don hang ma BFQF6XPUIY','billpayment',16160000,'192.168.1.227','20191021202702','VISA',NULL,NULL,NULL,NULL,NULL,'BFQF6XPUIY',0),(27,'2019-10-21 20:28:44','vn','VND','Thanh toan don hang ma PA0LJNJ9SO','billpayment',16160000,'192.168.1.227','20191021202844','NCB','20191021202904','20191021202854','13185277','00',25,'PA0LJNJ9SO',1),(28,'2019-10-21 20:38:46','vn','VND','Thanh toan don hang ma 10HIJICZWJ','billpayment',19190000,'192.168.1.227','20191021203845','NCB','20191021203902','20191021203853','13185279','00',26,'10HIJICZWJ',1),(29,'2019-10-21 21:01:53','vn','VND','Thanh toan don hang ma YSBWRZDB18','billpayment',16160000,'192.168.1.227','20191021210153','NCB','20191021210228','20191021210158','13185281','00',29,'YSBWRZDB18',1),(30,'2019-10-21 21:05:53','vn','VND','Thanh toan don hang ma FVWXOJPYNF','billpayment',23230000,'192.168.1.227','20191021210552','NCB','20191021210608','20191021210557','13185282','00',30,'FVWXOJPYNF',1),(31,'2019-10-21 21:09:05','vn','VND','Thanh toan don hang ma 3J9VLINCEC','billpayment',20200000,'192.168.1.227','20191021210904','NCB','20191021210916','20191021210907','13185283','00',31,'3J9VLINCEC',1),(32,'2019-10-23 12:01:07','vn','VND','Thanh toan don hang ma Y9Z23GMLK1','billpayment',42420000,'10.10.11.149','20191023120107','NCB','20191023120128','20191023120116','13185709','00',35,'Y9Z23GMLK1',1),(33,'2019-10-23 17:35:02','vn','VND','Thanh toan don hang ma 6T4KU02EJF','billpayment',16160000,'10.10.11.149','20191023173501','NCB',NULL,NULL,NULL,NULL,NULL,'6T4KU02EJF',0),(34,'2019-10-23 17:36:19','vn','VND','Thanh toan don hang ma CZZFMSLKYU','billpayment',50500000,'10.10.11.149','20191023173619','NCB',NULL,NULL,NULL,NULL,NULL,'CZZFMSLKYU',0),(35,'2019-10-23 17:39:56','vn','VND','Thanh toan don hang ma 9Y1MNKVV8W','billpayment',75750000,'10.10.11.149','20191023173955','NCB',NULL,NULL,NULL,NULL,NULL,'9Y1MNKVV8W',0),(36,'2019-10-23 17:40:43','vn','VND','Thanh toan don hang ma ABU2AHQ1C5','billpayment',75750000,'10.10.11.149','20191023174043','NCB',NULL,NULL,NULL,NULL,NULL,'ABU2AHQ1C5',0),(37,'2019-10-23 17:41:22','vn','VND','Thanh toan don hang ma 5B4TNMQREQ','billpayment',75750000,'10.10.11.149','20191023174122','NCB',NULL,NULL,NULL,NULL,NULL,'5B4TNMQREQ',0),(38,'2019-10-23 17:42:47','vn','VND','Thanh toan don hang ma GWWEIFUY2I','billpayment',75750000,'10.10.11.149','20191023174246','NCB',NULL,NULL,NULL,NULL,NULL,'GWWEIFUY2I',0),(39,'2019-10-23 17:42:54','vn','VND','Thanh toan don hang ma PUVIWN7O6E','billpayment',75750000,'10.10.11.149','20191023174254','NCB',NULL,NULL,NULL,NULL,NULL,'PUVIWN7O6E',0),(40,'2019-10-23 17:43:22','vn','VND','Thanh toan don hang ma HPG7OY38H6','billpayment',75750000,'10.10.11.149','20191023174322','NCB',NULL,NULL,NULL,NULL,NULL,'HPG7OY38H6',0),(41,'2019-10-23 18:17:09','vn','VND','Thanh toan don hang ma 3FJB2RHT4N','billpayment',25250000,'192.168.1.227','20191023181709','NCB','20191023181752','20191023181742','13185933','00',48,'3FJB2RHT4N',1);
/*!40000 ALTER TABLE `vnpay_trans_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-23 19:03:00
