-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: smiley
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `data_nascimento` date NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `sexo` varchar(255) NOT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `hasEncarregado` bit(1) NOT NULL,
  `morada` varchar(255) DEFAULT NULL,
  `naturalidade` varchar(255) NOT NULL,
  `notasImportantes` varchar(255) DEFAULT NULL,
  `encarregado_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ds00ptnkhrvctccivt3lr05au` (`nome`),
  KEY `FKgk2b3to4fm9kfw5soo2ik0gh0` (`encarregado_id`),
  CONSTRAINT `FKgk2b3to4fm9kfw5soo2ik0gh0` FOREIGN KEY (`encarregado_id`) REFERENCES `encarregado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'','2018-04-24',NULL,'Teste-1','MASCULINO',NULL,'\0',NULL,'Moçambique',NULL,NULL),(4,'','2018-04-24',NULL,'Teste-2','MASCULINO',NULL,'',NULL,'Moçambique',NULL,3),(5,'','2018-04-24',NULL,'Lazaro Sebastiao Mathe Junior','MASCULINO','825004957','\0','Matola A','Moçambique','Tem asma',NULL),(6,'\0','2000-11-01','melytm13@gmail.com','Melany Marangabassa','FEMININO','825032907','','Av. Julius Nyerere ','Mozambique','Ankle Sprain',NULL),(7,'','2018-05-09','teste@gmail.com','teste-3','MASCULINO','825699587','\0','Maputo','South Africa','Tem diabetes',6),(8,'','2018-05-01',NULL,'Irshad','MASCULINO','820001122','','','USA','',10),(9,'','1999-03-30',NULL,'Eduarda Samuel','FEMININO','848888102','\0','','Maputo','Tem asma, problemas de pele',9),(10,'','2018-05-02',NULL,'Lia Teresa Guibalo','FEMININO','826133918','','','Matola','',11),(11,'','2018-12-05',NULL,'Melissa Chloe Guibalo','FEMININO','826677889','','','Mocambique','',12),(12,'','2018-05-02',NULL,'Ayana Guibalo','FEMININO',NULL,'','','Maputo','',13),(13,'','2018-04-30',NULL,'Mayisha Lucas','FEMININO',NULL,'','','Cape Town','',14);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consulta`
--

DROP TABLE IF EXISTS `consulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consulta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `custoTotal` double NOT NULL,
  `dataRealizacao` datetime NOT NULL,
  `cliente_id` bigint(20) NOT NULL,
  `medico_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf9cypujx2jkto76r79sexcuok` (`cliente_id`),
  KEY `FK8ywlyw4q8xpfh10wp06oce4w0` (`medico_id`),
  KEY `FKl40d4ft9et0a4m83ypv4a8cyx` (`user_id`),
  CONSTRAINT `FK8ywlyw4q8xpfh10wp06oce4w0` FOREIGN KEY (`medico_id`) REFERENCES `medico` (`id`),
  CONSTRAINT `FKf9cypujx2jkto76r79sexcuok` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FKl40d4ft9et0a4m83ypv4a8cyx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consulta`
--

LOCK TABLES `consulta` WRITE;
/*!40000 ALTER TABLE `consulta` DISABLE KEYS */;
INSERT INTO `consulta` VALUES (2,'',23200,'2019-02-22 14:18:46',1,1,1),(3,'',50600,'2019-02-22 14:45:31',1,1,1),(4,'',24400,'2019-02-22 00:00:00',5,1,1);
/*!40000 ALTER TABLE `consulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `guia` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4e4jgoy4gmbeknmv0io86dudn` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encarregado`
--

DROP TABLE IF EXISTS `encarregado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `encarregado` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encarregado`
--

LOCK TABLES `encarregado` WRITE;
/*!40000 ALTER TABLE `encarregado` DISABLE KEYS */;
INSERT INTO `encarregado` VALUES (3,'','Teste-2','825004957'),(4,'','Maria Luisa','825004957'),(5,'','Berta Cossa Marangabassa','826102314'),(6,'','Maria luisa Machado','828707950'),(7,'','','Marta'),(8,'','','Marta'),(9,'','Marta','848888102'),(10,'','Akil','829112123'),(11,'','','824775651'),(12,'','Tininha Lucas','825004957'),(13,'','Tininha','825004957'),(14,'','Nita Lucas','827172654');
/*!40000 ALTER TABLE `encarregado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medico`
--

DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `data_nascimento` date NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `sexo` varchar(255) NOT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medico`
--

LOCK TABLES `medico` WRITE;
/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO `medico` VALUES (1,'','2019-02-22',NULL,'Candinha','FEMININO',NULL),(2,'','2019-02-22',NULL,'Tania','FEMININO',NULL);
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `procedimento`
--

DROP TABLE IF EXISTS `procedimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `procedimento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `codigo` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `valor` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sfqskauthnphat4vbpn2dg9lg` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procedimento`
--

LOCK TABLES `procedimento` WRITE;
/*!40000 ALTER TABLE `procedimento` DISABLE KEYS */;
INSERT INTO `procedimento` VALUES (1,'','CT','Caterizacao',2200),(2,'','Tata23','Tatata',2000),(3,'','ConsT','Consulta normal',23200),(4,'','special','Consulta especial',23200),(5,'\0','new','Novo procedimento',250);
/*!40000 ALTER TABLE `procedimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `procedimentoconsulta`
--

DROP TABLE IF EXISTS `procedimentoconsulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `procedimentoconsulta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantidade` int(11) NOT NULL,
  `consulta_id` bigint(20) DEFAULT NULL,
  `procedimento_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5w8hoqce9rabkckck2ioq2fjf` (`consulta_id`),
  KEY `FKtqasdfayhyt5a3n5nl4akpy8y` (`procedimento_id`),
  CONSTRAINT `FK5w8hoqce9rabkckck2ioq2fjf` FOREIGN KEY (`consulta_id`) REFERENCES `consulta` (`id`),
  CONSTRAINT `FKtqasdfayhyt5a3n5nl4akpy8y` FOREIGN KEY (`procedimento_id`) REFERENCES `procedimento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procedimentoconsulta`
--

LOCK TABLES `procedimentoconsulta` WRITE;
/*!40000 ALTER TABLE `procedimentoconsulta` DISABLE KEYS */;
INSERT INTO `procedimentoconsulta` VALUES (1,1,2,4),(2,1,3,1),(3,1,3,2),(4,1,3,3),(5,1,3,4),(6,2,4,1),(7,10,4,2);
/*!40000 ALTER TABLE `procedimentoconsulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `profilename` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e3cxevuy0jnk2w71lidiqdxcb` (`profilename`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (1,'','Director geral'),(2,'','Administrador Informatico'),(3,'','Registrador');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessionhelper`
--

DROP TABLE IF EXISTS `sessionhelper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessionhelper` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `selectedUser` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_68m9w1biqldy0wwsllc1r2sbc` (`selectedUser`),
  CONSTRAINT `FK6g74m8cd6507kpm2k9951p4u3` FOREIGN KEY (`selectedUser`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessionhelper`
--

LOCK TABLES `sessionhelper` WRITE;
/*!40000 ALTER TABLE `sessionhelper` DISABLE KEYS */;
INSERT INTO `sessionhelper` VALUES (1,1);
/*!40000 ALTER TABLE `sessionhelper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaccao`
--

DROP TABLE IF EXISTS `transaccao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaccao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `code` bigint(20) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_if174yhk7av9f4t03aigb96ry` (`code`),
  UNIQUE KEY `UK_h7nxqj3ic3xbt073q9p9fkxv0` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaccao`
--

LOCK TABLES `transaccao` WRITE;
/*!40000 ALTER TABLE `transaccao` DISABLE KEYS */;
INSERT INTO `transaccao` VALUES (1,'',101,'/application/forms/Add-Client.fxml'),(3,'',103,'/application/forms/Add-Medico.fxml'),(4,'',104,'/application/forms/Add-Procedimento.fxml'),(5,'',105,'/application/forms/Add-Consulta.fxml'),(6,'',201,'/application/forms/Modify-Client.fxml'),(8,'',203,'/application/forms/Modify-Medico.fxml'),(9,'',206,'/application/forms/Modify-Profile.fxml'),(10,'',304,'/application/views/View-Procedimentos.fxml'),(11,'',301,'/application/views/View-Clients.fxml'),(12,'',305,'/application/views/View-Consultas.fxml'),(13,'',307,'/application/views/View-Consulta.fxml'),(14,'',302,'/application/views/settings/View-Users.fxml'),(15,'',308,'/application/views/settings/View-Perfis.fxml'),(16,'',309,'/application/views/settings/View-Transaccoes.fxml');
/*!40000 ALTER TABLE `transaccao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaccao_profile`
--

DROP TABLE IF EXISTS `transaccao_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaccao_profile` (
  `transaccoes_id` bigint(20) NOT NULL,
  `profiles_id` bigint(20) NOT NULL,
  KEY `FKfq5qtlr9ail1xl2vpcmwpenk8` (`profiles_id`),
  KEY `FK80i1x6kjr10ix1v81w2rhwy73` (`transaccoes_id`),
  CONSTRAINT `FK80i1x6kjr10ix1v81w2rhwy73` FOREIGN KEY (`transaccoes_id`) REFERENCES `transaccao` (`id`),
  CONSTRAINT `FKfq5qtlr9ail1xl2vpcmwpenk8` FOREIGN KEY (`profiles_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaccao_profile`
--

LOCK TABLES `transaccao_profile` WRITE;
/*!40000 ALTER TABLE `transaccao_profile` DISABLE KEYS */;
INSERT INTO `transaccao_profile` VALUES (1,1),(3,1),(4,1),(5,1),(6,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(1,2),(3,2),(4,2),(5,2),(6,2),(8,2),(9,2),(10,2),(11,2),(12,2),(14,2),(16,2),(15,2),(14,1),(15,1),(16,1);
/*!40000 ALTER TABLE `transaccao_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `profile_id` bigint(20) NOT NULL,
  `sessionHelper_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `FKhuydpjm1myork5g111lxmst2t` (`profile_id`),
  KEY `FKjr78f7e1wrjojw1fn6peliyjn` (`sessionHelper_id`),
  CONSTRAINT `FKhuydpjm1myork5g111lxmst2t` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  CONSTRAINT `FKjr78f7e1wrjojw1fn6peliyjn` FOREIGN KEY (`sessionHelper_id`) REFERENCES `sessionhelper` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'','lazaro','Yd3+4Zn96g+hvwkNQnLEqA==:6UkKqE/IBbF9THkSu6mFMg==','lazaro',1,NULL),(13,'','diana','zaTBagsy1tbECk14XUhrpQ==:J4nr1EKntBjQuQakhJhvvQ==','diana',3,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-07 10:56:53
