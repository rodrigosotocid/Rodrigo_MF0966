CREATE DATABASE  IF NOT EXISTS `mf0966_3` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mf0966_3`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mf0966_3
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `resena`
--

DROP TABLE IF EXISTS `resena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resena` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `cod_imparticion` int NOT NULL,
  `cod_alumno` int NOT NULL,
  `resena` text COLLATE utf8_bin NOT NULL,
  `fResena` datetime DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `cod_imparticion_idx` (`cod_imparticion`),
  KEY `cod_alumno_idx` (`cod_alumno`),
  CONSTRAINT `cod_alumno` FOREIGN KEY (`cod_alumno`) REFERENCES `alumno` (`codigo`),
  CONSTRAINT `cod_imparticion` FOREIGN KEY (`cod_imparticion`) REFERENCES `imparticion` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resena`
--

LOCK TABLES `resena` WRITE;
/*!40000 ALTER TABLE `resena` DISABLE KEYS */;
INSERT INTO `resena` VALUES (1,9,2,'Este curso es inigualable, jamás en la vida he visto al mejor.','2020-03-22 00:00:00'),(2,9,4,'Noticia y comentario, generalmente de corta extensión, que se hace sobre una obra literaria, de arte o científica y se publica en un periódico o en una revista.','2020-03-22 00:00:00'),(3,50,4,'Se suele usar para referirse a un acto cultural y deportivo o en la crítica literaria y artística. En una reseña se hace un recuento del contenido de la obra o evento, ...','2020-03-26 00:00:00');
/*!40000 ALTER TABLE `resena` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-26 13:51:30
