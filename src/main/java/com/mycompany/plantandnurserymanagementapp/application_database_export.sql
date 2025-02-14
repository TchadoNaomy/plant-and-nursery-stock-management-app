-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: plantdb
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `oders`
--

DROP TABLE IF EXISTS `oders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oders` (
  `oid` int NOT NULL AUTO_INCREMENT,
  `warehouse_manager_id` int NOT NULL,
  `supplier_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity_requested` int NOT NULL,
  `order_status` varchar(30) NOT NULL,
  `order_date` timestamp NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `warehouse_manager_id` (`warehouse_manager_id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `oders_ibfk_1` FOREIGN KEY (`warehouse_manager_id`) REFERENCES `users` (`uid`),
  CONSTRAINT `oders_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `users` (`uid`),
  CONSTRAINT `oders_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `plants` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oders`
--

LOCK TABLES `oders` WRITE;
/*!40000 ALTER TABLE `oders` DISABLE KEYS */;
INSERT INTO `oders` VALUES (11,1,2,3,10,'pending','2025-02-11 04:09:36'),(12,1,2,5,20,'pending','2025-02-11 04:09:36'),(13,1,2,3,10,'pending','2025-02-11 04:10:23'),(14,1,2,5,20,'approved','2025-02-11 04:10:23');
/*!40000 ALTER TABLE `oders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plants`
--

DROP TABLE IF EXISTS `plants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plants` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` text NOT NULL,
  `category` varchar(34) NOT NULL,
  `quantity_in_stock` int NOT NULL,
  `price` double(10,2) NOT NULL,
  `supplier_id` int NOT NULL,
  `date_added` timestamp NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plants`
--

LOCK TABLES `plants` WRITE;
/*!40000 ALTER TABLE `plants` DISABLE KEYS */;
INSERT INTO `plants` VALUES (2,'Folere','The worst','Mango',45,234.00,2,'2025-02-11 03:58:49'),(3,'Rose','Beautiful red rose','Flowering Plants',50,2.50,2,'2025-02-11 04:04:15'),(4,'Tulip','Colorful tulip flowers','Flowering Plants',75,1.75,2,'2025-02-11 04:04:15'),(5,'Sunflower','Large yellow sunflower','Flowering Plants',30,3.00,2,'2025-02-11 04:04:15'),(6,'Lavender','Fragrant purple lavender','Herbs',100,2.00,2,'2025-02-11 04:04:15'),(7,'Mint','Refreshing green mint','Herbs',60,1.50,2,'2025-02-11 04:04:15'),(8,'Rosemary','Aromatic rosemary herb','Herbs',80,2.25,2,'2025-02-11 04:04:15'),(9,'Cactus','Spiky desert plant','Succulents',40,4.00,2,'2025-02-11 04:04:15'),(10,'Aloe Vera','Medicinal aloe vera plant','Succulents',90,3.50,2,'2025-02-11 04:04:15'),(11,'Snake Plant','Easy-to-care-for houseplant','Indoor Plants',120,2.75,2,'2025-02-11 04:04:15'),(12,'Orchid','Elegant orchid flower','Indoor Plants',25,5.00,2,'2025-02-11 04:04:15');
/*!40000 ALTER TABLE `plants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `role` varchar(20) NOT NULL,
  `email` varchar(60) NOT NULL,
  `reset_token` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Naomi','naomitest','manager','naomi@gmail.com',NULL),(2,'Guyanne','guyannetest','supplier','guyanne@gmail.com',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-14 18:21:48
