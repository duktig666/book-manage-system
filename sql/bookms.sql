-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: bookms
-- ------------------------------------------------------
-- Server version	8.0.12

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
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrator` (
  `adm_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `adm_count` varchar(32) NOT NULL COMMENT '管理员账号 6-16位字母或者数字',
  `adm_password` char(32) NOT NULL COMMENT '管理员密码',
  `adm_name` varchar(30) NOT NULL COMMENT '姓名',
  `adm_id_number` char(18) NOT NULL COMMENT '身份证号',
  `adm_tele` char(11) DEFAULT NULL COMMENT '手机号',
  `adm_email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `adm_keeppass` varchar(45) NOT NULL COMMENT '密保   ',
  `issuper` tinyint(1) unsigned NOT NULL COMMENT '是否超级管理员   0为管理员，1为超级管理员',
  PRIMARY KEY (`adm_id`),
  UNIQUE KEY `u_count` (`adm_count`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'123123','b51e8dbebd4ba8a8f342190a4b9f08d7','陈明','411224192302264556','15678984565','123456564@qq.com','啊啊啊',1),(3,'456456','b51e8dbebd4ba8a8f342190a4b9f08d7','小康','411224195608085656','15678786969','1593578426@qq.com','搜狗',0),(5,'111222','00b7691d86d96aebd21dd9e138f90840','小任','411224199804088119','15678984565','64646464@qq.com','123',0),(6,'316346','e10adc3949ba59abbe56e057f20f883e','萨汗国','411224199804088585','15632325888','5646464@qq.com','5',0);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `b_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图书id',
  `ISBN` char(10) NOT NULL COMMENT 'ISBN',
  `b_name` varchar(45) NOT NULL COMMENT '书名',
  `booktype` int(11) unsigned NOT NULL COMMENT '图书类型',
  `author` varchar(45) NOT NULL COMMENT '作者',
  `press` varchar(45) DEFAULT NULL COMMENT '出版社',
  `price` double(4,2) unsigned DEFAULT NULL COMMENT '价格',
  `inventory` int(11) unsigned DEFAULT NULL COMMENT '库存量',
  PRIMARY KEY (`b_id`),
  UNIQUE KEY `ISBN_UNIQUE` (`ISBN`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图书信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (6,'1596845632','反倒是',4,'擦拭','时代',37.30,0),(18,'7412583695','猪场兽医记事',2,'吴增坚','金盾出版社',15.00,76),(19,'7583674239','中兽医手册',2,'钟秀会','中国农业出版社',71.70,68),(20,'7896548325','执业兽医培训教程',2,'王泽川','中国农业出版社',45.30,33),(23,'7894561235','霍乱时期的爱情',2,'马尔克斯','南海出版',34.20,26),(28,'7895648932','独家记忆',2,'木浮生','百花洲文艺出版社',30.60,35),(31,'5573896','资治通鉴',2,'其他','中国书店',675.00,46),(32,'523541','中华上下五千年',2,'朱良志','线装书局',63.00,59),(35,'46546','红楼梦',2,'曹雪芹','人民日报出版社',24.00,57),(37,'53998423','浮生六记',2,'沈复','浙江文艺出版社',33.80,51),(38,'5668876','我喜欢生命本来的样子',2,'周国平','作家出版社',43.20,37),(56,'7894561236','Java程序设计基础',1,'陈国君','清华出版社',33.20,7),(57,'1111111111','阿贾克斯的发挥',2,'爱上了','撒',66.30,22),(61,'2789456135','数据库基础',1,'阿三','爱吃',33.60,65),(62,'7894563258','青云志',1,'许仙','出版社',22.30,35),(69,'1236598745','产生的',4,'注册','擦是否',36.20,127),(70,'1259684523','安德森',19,'造成的','啊速度非常',3.60,213),(71,'1598745632','删除ca',1,'擦上档次','擦大',36.20,125),(96,'﻿789562569','西游记',2,'曹雪芹','牛逼出版社',55.30,70),(97,'97873022','信息资源管理',2,'张凯','清华出版社',39.50,80),(98,'9787040','西方经济学',2,'吴易风','高教出版社',39.00,62),(99,'8474602','SPSS统计分析基础教程',2,'张文彤','高教出版社',46.00,55),(100,'29200722','ERP原理',2,'姬小利','立信会计出版社',30.00,69),(101,'5699483','挪威的森林',2,'林少华','上海译文出版社',24.00,39),(102,'46852379','白夜行',2,'东野圭吾','南海出版社',44.70,55),(103,'5956836','谁的青春不迷茫',2,'刘同','北京联合出版社',32.40,69),(104,'75958697','平凡的世界',2,'路遥','北京十月文艺出版社',74.50,56),(105,'33369824','猪场兽医记事',2,'吴增坚','金盾出版社',15.00,77),(106,'583674239','中兽医手册',2,'钟秀会','中国农业出版社',71.70,68),(107,'886652388','执业兽医培训教程',2,'王泽川','中国农业出版社',45.30,34),(108,'586677','生活十讲',2,'蒋勋','长江文艺出版社',34.50,68),(109,'25476663','中国伦理学史',2,'三浦藤作','山西人民出版社',49.30,58),(110,'42685239','霍乱时期的爱情',2,'马尔克斯','南海出版社',34.20,25),(111,'58523872','我与世界只差一个你',2,'张皓宸','天津人民出版社',27.00,36),(112,'53247635','复乐园',2,'渡边淳一','青岛出版社',39.00,39),(113,'55883388','光与影',2,'渡边淳一','青岛出版社',19.20,49),(114,'5336998','我不喜欢这个世界，我只喜欢你',2,'乔一','湖南少年儿童出版社',29.80,57),(115,'55358854','独家记忆',2,'木浮生','百花洲文艺出版社',30.60,35),(116,'533884','秦汉史',2,'吕思勉','北京理工大学出版社',16.80,53),(118,'5338869','活着',2,'余华','北京十月文艺出版社',21.00,69),(119,'3686984','三国演义',2,'罗贯中','吉林出版社',95.00,34),(120,'636842','红楼梦',2,'曹雪芹','人民日报出版社',58.00,60),(121,'1234567890','水浒传',2,'施耐庵','江苏凤凰文艺出版社',47.20,69),(124,'1596324589','爱上',4,'啊非常','摩擦',33.60,65);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booktype`
--

DROP TABLE IF EXISTS `booktype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booktype` (
  `bt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图书类型id',
  `bt_name` varchar(45) NOT NULL COMMENT '图书类型',
  PRIMARY KEY (`bt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图书类型信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booktype`
--

LOCK TABLES `booktype` WRITE;
/*!40000 ALTER TABLE `booktype` DISABLE KEYS */;
INSERT INTO `booktype` VALUES (1,'计算机'),(2,'爱情'),(4,'伦理'),(5,'兽医'),(19,'政治'),(53,'发女');
/*!40000 ALTER TABLE `booktype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow`
--

DROP TABLE IF EXISTS `borrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow` (
  `borrow_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '借阅信息id',
  `r_number` varchar(32) NOT NULL COMMENT '学号',
  `borrow_b_id` int(11) unsigned NOT NULL COMMENT '图书id',
  `borrowdate` int(10) unsigned NOT NULL COMMENT '借阅日期',
  `duedate` int(10) unsigned NOT NULL COMMENT '应还日期',
  `returndate` int(10) unsigned DEFAULT NULL COMMENT '实际归还日期',
  `isreturn` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0 代表 false——未归还\\n1 代表 true——已归还',
  PRIMARY KEY (`borrow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='借阅信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow`
--

LOCK TABLES `borrow` WRITE;
/*!40000 ALTER TABLE `borrow` DISABLE KEYS */;
INSERT INTO `borrow` VALUES (84,'20170744212',20,1550628545,1555812545,1550629006,1),(85,'20170744212',20,1550628552,1555812552,1550628997,1),(86,'20170744212',20,1550628558,1555812558,1550628568,1),(87,'20170744212',26,1550628582,1555812582,NULL,0),(88,'20170744212',28,1550628593,1555812593,1550810047,1),(89,'20170744212',33,1550628603,1555812603,NULL,0),(90,'20170744212',26,1550629012,1555813012,1550629046,1),(91,'20170744212',31,1550629060,1555813060,1575709962,1),(92,'20170744212',29,1550629168,1555813168,NULL,0),(93,'20170744212',10,1550641036,1555825036,NULL,0),(94,'20170744212',12,1550641489,1555825489,NULL,0),(95,'20170744212',15,1550649684,1555833684,1550729948,1),(96,'20170744212',56,1550649693,1555833693,NULL,0),(98,'20170744212',35,1550649837,1555833837,NULL,0),(100,'20170744214',23,1550651743,1555835743,1550651848,1),(101,'20170744214',15,1550651839,1555835839,NULL,0),(102,'20170744212',6,1550661234,1555845234,1550661252,1),(103,'20170744212',6,1550661286,1555845286,1550661382,1),(104,'20170744212',6,1550661403,1555845403,1550661419,1),(105,'20170744212',6,1550661447,1555845447,NULL,0),(106,'20170744236',26,1550730204,1555914204,1550730209,1),(107,'20170744212',62,1550810039,1555994039,NULL,0),(108,'20170744212',37,1550810122,1555994122,NULL,0),(110,'20170744321',35,1550838149,1558614149,1550838220,1),(111,'20170744321',31,1550838166,1558614166,NULL,0),(112,'20170744220',31,1565009308,1570193308,1565009320,1),(113,'20170744212',104,1575709929,1580893929,NULL,0);
/*!40000 ALTER TABLE `borrow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reader`
--

DROP TABLE IF EXISTS `reader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reader` (
  `number` varchar(32) NOT NULL COMMENT '学号',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `gender` char(3) DEFAULT NULL COMMENT '性别',
  `dept` varchar(30) DEFAULT NULL COMMENT '院系',
  `classes` varchar(30) DEFAULT NULL COMMENT '班级',
  `tele` char(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `logindate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `password` char(32) NOT NULL,
  `keeppass` varchar(45) NOT NULL,
  `reader_type` int(11) unsigned NOT NULL COMMENT '读者类型',
  PRIMARY KEY (`number`),
  UNIQUE KEY `number_UNIQUE` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='读者信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reader`
--

LOCK TABLES `reader` WRITE;
/*!40000 ALTER TABLE `reader` DISABLE KEYS */;
INSERT INTO `reader` VALUES ('20170744212','查询','男','信息工程学院','信工172','13569699696','464645646@qq.com','2019-02-20 08:27:23','e10adc3949ba59abbe56e057f20f883e','数据库',1),('20170744213','小黄','男','动物科技学院','动药172','13698987878','565646468965@qq.com','2019-02-20 11:20:24','e10adc3949ba59abbe56e057f20f883e','123',2),('20170744216','胡歌','男','生命科技学院','生科184','13569694848','346464646@qq.com','2019-02-21 00:42:01','e10adc3949ba59abbe56e057f20f883e','123',1),('20170744217','小韦','男','园艺园林学院','园林182','13256569898','5646476@qq.com','2019-02-20 13:38:34','e10adc3949ba59abbe56e057f20f883e','123',2),('20170744219','日上午','男','啊','叫你','15698984545','1593578885@qq.com','2019-02-21 13:02:55','e10adc3949ba59abbe56e057f20f883e','5',1),('20170744220','擦什','男','查数据库','陈铭','15678874554','1487660836@qq.com','2019-08-05 12:45:05','36e1a5072c78359066ed7715f5ff3da8','1',1),('20170744236','奥斯卡','男','擦看来','擦2','15639398989','634624641@qq.com','2019-02-21 06:22:11','e10adc3949ba59abbe56e057f20f883e','5',1),('20170744321','发生的','男','都是','考虑买','15968689898','4645654@qq.com','2019-02-22 12:17:44','e10adc3949ba59abbe56e057f20f883e','123',2);
/*!40000 ALTER TABLE `reader` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `readertype`
--

DROP TABLE IF EXISTS `readertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `readertype` (
  `rt_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '读者类型id',
  `rt_name` varchar(30) NOT NULL COMMENT '读者类型',
  `maxcount` int(11) unsigned NOT NULL COMMENT '最大借阅数量',
  `maxday` int(11) unsigned NOT NULL COMMENT '最大借阅天数',
  PRIMARY KEY (`rt_id`),
  UNIQUE KEY `rt_name_UNIQUE` (`rt_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='读者类型信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `readertype`
--

LOCK TABLES `readertype` WRITE;
/*!40000 ALTER TABLE `readertype` DISABLE KEYS */;
INSERT INTO `readertype` VALUES (1,'本科',25,60),(2,'博士',20,90),(3,'博士后',25,120);
/*!40000 ALTER TABLE `readertype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-17 22:06:14
