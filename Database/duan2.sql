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
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (1,'admin','Admin',NULL,NULL,NULL,'admin@yo.com','1','5876695f8e4e1811','$2a$10$B3oHbYckX3Kn54nVHlhOo.SSS4DGTePm8VfDgl.pZ2cYy69y.2ZFe','2019-09-24 22:21:33',NULL,1,1,0),(2,'tanbv','Bùi','Văn','Tứn','Bùi Văn Tứn','tanbvph05961@gmail.com','1','5876695f8e4e1811','$2a$10$XXaa5vhOSKqFtkyAXp3mr.URnTGQk7GnB0NYYJIxKfShEijW72iWW','2019-10-03 15:40:22',NULL,1,1,2);
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
INSERT INTO `auth_user_role` VALUES (1,1),(2,1),(3,1),(2,2),(3,2);
/*!40000 ALTER TABLE `auth_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_info`
--

DROP TABLE IF EXISTS `bank_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bank_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bank_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bank_icon` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_info`
--

LOCK TABLES `bank_info` WRITE;
/*!40000 ALTER TABLE `bank_info` DISABLE KEYS */;
INSERT INTO `bank_info` VALUES (1,'VIETCOMBANK','Ngân hàng Ngoại thương (Vietcombank)','/static/bank/vietcombank_logo.png'),(2,'VIETINBANK','Ngân hàng Công thương (Vietinbank)','/static/bank/vietinbank_logo.png'),(3,'BIDV','Ngân hàng đầu tư và phát triển Việt Nam (BIDV)','/static/bank/bidv_logo.png'),(4,'AGRIBANK','Ngân hàng Nông nghiệp (Agribank)','/static/bank/agribank_logo.png'),(5,'SACOMBANK','Ngân hàng TMCP Sài Gòn Thương Tín (SacomBank)','/static/bank/sacombank_logo.png'),(6,'TECHCOMBANK','Ngân hàng Kỹ thương Việt Nam (TechcomBank)	','/static/bank/techcombank_logo.png'),(7,'ACB','Ngân hàng ACB','/static/bank/acb_logo.png'),(8,'VPBANK','Ngân hàng Việt Nam Thịnh vượng (VPBank)','/static/bank/vpbank_logo.png'),(9,'DONGABANK	','Ngân hàng Đông Á (DongABank)','/static/bank/dongabank_logo.png'),(10,'EXIMBANK','Ngân hàng EximBank','/static/bank/eximbank_logo.png'),(11,'TPBANK','Ngân hàng Tiên Phong (TPBank)','/static/bank/tpbank_logo.png'),(12,'NCB','Ngân hàng Quốc dân (NCB)','/static/bank/ncb_logo.png'),(13,'OJB','Ngân hàng Đại Dương (OceanBank)	','/static/bank/ojb_logo.png'),(14,'MSBANK','Ngân hàng Hàng Hải (MSBANK)','/static/bank/msbank_logo.png'),(15,'HDBANK','Ngan hàng HDBank','/static/bank/hdbank_logo.png'),(16,'NAMABANK','Ngân hàng Nam Á (NamABank)','/static/bank/namabank_logo.png'),(17,'OCB','Ngân hàng Phương Đông (OCB)','/static/bank/ocb_logo.png'),(18,'VISA','Thẻ quốc tế Visa','/static/bank/visa_logo.png'),(19,'MASTERCARD','Thẻ quốc tế MasterCard','/static/bank/mastercard_logo.png'),(20,'JCB','Thẻ quốc tế JCB','/static/bank/jcb_logo.png'),(21,'VNMART','Ví điện tử VnMart','/static/bank/vnmart_logo.png'),(22,'SCB','Ngân hàng TMCP Sài Gòn (SCB)','/static/bank/scb_logo.png'),(23,'IVB','Ngân hàng TNHH Indovina (IVB)','/static/bank/ivb_logo.png'),(24,'ABBANK','Ngân hàng thương mại cổ phần An Bình (ABBANK)','/static/bank/abbank_logo.png'),(25,'SHB','Ngân hàng Thương mại cổ phần Sài Gòn (SHB)','/static/bank/shb_logo.png'),(26,'VIB','Ngân hàng Thương mại cổ phần Quốc tế Việt Nam (VIB)','/static/bank/vib_logo.png'),(27,'VNPAYQR','Cổng thanh toán VNPAYQR','/static/bank/CTT-VNPAY-QR.png'),(28,'VIETCAPITALBANK','Ngân Hàng Bản Việt','/static/bank/vccb_logo.png'),(29,'PVCOMBANK','Ngân hàng TMCP Đại Chúng Việt Nam','/static/bank/PVComBank_logo.png'),(30,'SAIGONBANK','Ngân hàng thương mại cổ phần Sài Gòn Công Thương','/static/bank/saigonbank_logo.png'),(31,'MBBANK','Ngân hàng thương mại cổ phần Quân đội','/static/bank/mbbank_logo.png'),(32,'BACABANK','Ngân Hàng TMCP Bắc Á','/static/bank/bacabank_logo.png'),(33,'UPI','UnionPay International','/static/bank/upi_logo.png');
/*!40000 ALTER TABLE `bank_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bill` (
  `id` bigint(20) NOT NULL,
  `total` float DEFAULT NULL,
  `payment` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `created_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `isdelete` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
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
  `status` int(1) DEFAULT NULL,
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
INSERT INTO `category` VALUES (1,'Điện thoại',0,1,'2019-05-10 20:30:15','N'),(2,'Iphone',1,1,'2019-05-10 20:30:15','N'),(3,'SamSung',1,1,'2019-05-10 20:30:15','N'),(4,'Xiaomi',1,1,'2019-05-10 20:30:15','N'),(5,'Oppo',1,1,'2019-05-10 20:30:15','N'),(6,'Tai nghe',0,1,NULL,'N'),(7,'SamSung',6,1,NULL,'N'),(8,'Bose',6,1,NULL,'N'),(9,'Langsdom',6,1,NULL,'N'),(10,'Xiaomi',6,1,NULL,'N'),(11,'Iphone',6,1,NULL,'N'),(12,'Remax',6,1,NULL,'N'),(13,'Phụ kiện',0,1,NULL,'N'),(14,'Kính cường lực',13,1,NULL,'N'),(15,'Ốp điện thoại',13,1,NULL,'N'),(16,'Miếng dán điện thoại',13,1,NULL,'N'),(17,'Máy tính bảng',0,1,NULL,'N'),(18,'Ipad 4',17,1,NULL,'N'),(19,'Huewei MediaPad M2',17,1,NULL,'N'),(20,'Google Pixcel C',17,1,NULL,'N'),(21,'Xiaomi Pad 4',17,1,NULL,'N');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info`
--

LOCK TABLES `info` WRITE;
/*!40000 ALTER TABLE `info` DISABLE KEYS */;
INSERT INTO `info` VALUES (1,'Thái Bình','Kiến Xương','Quang Bình - Kiến Xương -  Thái Bình','0338070700','1999-08-21','VIETTINBANK','Công ty Cổ phần Viễn Thông Tuổi Trẻ Yotel','212312354534','Từng học tại FPT PolyTechnic','N',2);
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
  `id_category` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_category_idx` (`id_category`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Iphone X',18000000,50,'Ngon, Sạch, Đẹp',0,1,'assets/teamplates/img/product/7.jpg','[\"assets/teamplates/img/product/7.jpg\",\"assets/teamplates/img/product/6.jpg\",\"assets/teamplates/img/product/5.jpg\"]','N','Sản xuất năm 2019',2),(2,'SamSung A50',6000000,120,'Đẹp, 4GB/64GB',5500000,0,'assets/teamplates/img/product/5.jpg','[\"assets/teamplates/img/product/7.jpg\",\"assets/teamplates/img/product/6.jpg\",\"assets/teamplates/img/product/5.jpg\"]','N','Sản xuất năm 2019',3),(4,'Iphone 11',25000000,45,'Đẹp mê lì',0,1,'assets/teamplates/img/product/6.jpg','[\"assets/teamplates/img/product/7.jpg\",\"assets/teamplates/img/product/6.jpg\",\"assets/teamplates/img/product/5.jpg\"]','N','Sản xuất năm 2019',2),(5,'Xiaomi Mi 5',7500000,200,'Phê',7000000,1,'assets/teamplates/img/product/4.jpg','[\"assets/teamplates/img/product/7.jpg\",\"assets/teamplates/img/product/6.jpg\",\"assets/teamplates/img/product/5.jpg\"]','N','Sản xuất năm 2019',4),(6,'Oppo F9',9000000,220,'Đẳng cấp',8800000,1,'assets/teamplates/img/product/2.jpg','[\"assets/teamplates/img/product/7.jpg\",\"assets/teamplates/img/product/6.jpg\",\"assets/teamplates/img/product/5.jpg\"]','N','Sản xuất năm 2019',5);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_bill`
--

LOCK TABLES `product_bill` WRITE;
/*!40000 ALTER TABLE `product_bill` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
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
  `id_product` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'Nguyễn Văn A','2019-10-05 08:30:52','Chất lượng tốt, sản phẩm đẹp, dùng ok',NULL,2),(2,'Bùi Văn Tấn','2019-08-12 10:25:04','Đẹp hút hồn, không còn gì để nói về vẻ đẹp sản phẩm này',NULL,2);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-05 14:44:22
