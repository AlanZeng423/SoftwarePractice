-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: localhost    Database: elm_admin
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `business`
--

DROP TABLE IF EXISTS `business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business` (
  `businessId` int NOT NULL AUTO_INCREMENT COMMENT '商家编号',
  `password` varchar(20) NOT NULL COMMENT '商家密码',
  `businessName` varchar(40) NOT NULL COMMENT '商家名称',
  `businessAddress` varchar(50) DEFAULT NULL COMMENT '商家地址',
  `businessExplain` varchar(40) DEFAULT NULL COMMENT '商家介绍',
  `starPrice` decimal(5,2) DEFAULT '0.00' COMMENT '起送费',
  `deliveryPrice` decimal(5,2) DEFAULT '0.00' COMMENT '配送费',
  PRIMARY KEY (`businessId`)
) ENGINE=InnoDB AUTO_INCREMENT=10009 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business`
--

LOCK TABLES `business` WRITE;
/*!40000 ALTER TABLE `business` DISABLE KEYS */;
INSERT INTO `business` VALUES (10001,'123','万家饺子（软件园E18店）','沈阳市浑南区软件园E18楼1-121-3号','各种饺子',18.00,3.50),(10002,'123','小锅饭豆腐馆（全运店）','沈阳市全运路126号','小锅套餐',15.00,3.00),(10003,'123','麦当劳麦乐送（全运路店）','沈阳市全运路53号麦当劳','汉堡薯条',25.00,4.00),(10004,'123','米村拌饭（浑南店）','沈阳市浑南区彩霞街26号','拌饭套餐',18.00,3.00),(10005,'123','申记串道（中海康城店）','沈阳市中海康城社区48号','烤串炸串',20.00,4.00),(10006,'123','半亩良田排骨米饭','沈阳市和平区三好街126号','排骨米饭',22.00,4.00),(10007,'123','茶兮鲜果饮品（国际软件园店）','沈阳市和平区南京南街345号','甜品饮品',20.00,3.00);
/*!40000 ALTER TABLE `business` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-14 21:39:17
