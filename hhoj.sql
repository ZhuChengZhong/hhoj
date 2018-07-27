-- MySQL dump 10.13  Distrib 5.7.21, for linux-glibc2.12 (x86_64)
--
-- Host: localhost    Database: hhoj
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_comment` (
  `commentId` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `content` text NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`commentId`),
  KEY `fk_comment_user` (`uid`),
  KEY `fk_comment_problem` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_contest`
--

DROP TABLE IF EXISTS `t_contest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_contest` (
  `contestId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(250) NOT NULL,
  `contestDesc` varchar(250) NOT NULL,
  `joinNumber` int(11) NOT NULL DEFAULT '0',
  `startTime` bigint(20) NOT NULL,
  `timeLimit` int(11) DEFAULT NULL,
  `startJoinTime` bigint(20) NOT NULL,
  `endJoinTime` bigint(20) NOT NULL,
  `contestPassword` varchar(200) NOT NULL,
  `initiator` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`contestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_contest_problem`
--

DROP TABLE IF EXISTS `t_contest_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_contest_problem` (
  `cpId` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `contestId` int(11) NOT NULL,
  `accepted` int(11) NOT NULL,
  `submited` int(11) NOT NULL,
  PRIMARY KEY (`cpId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_contest_user`
--

DROP TABLE IF EXISTS `t_contest_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_contest_user` (
  `cuId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `contestId` int(11) NOT NULL,
  `solved` int(11) NOT NULL DEFAULT '0',
  `useTotalTime` int(11) NOT NULL DEFAULT '0',
  `useTotalMemary` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cuId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_language`
--

DROP TABLE IF EXISTS `t_language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_language` (
  `languageId` int(11) NOT NULL,
  `languageName` varchar(30) NOT NULL,
  `compileCommand` varchar(30) NOT NULL,
  `runCommand` varchar(30) NOT NULL,
  PRIMARY KEY (`languageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_problem`
--

DROP TABLE IF EXISTS `t_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_problem` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `pDesc` text NOT NULL,
  `inputExample` text NOT NULL,
  `outputExample` text NOT NULL,
  `hint` varchar(250) NOT NULL,
  `source` varchar(100) NOT NULL,
  `timeLimit` int(11) NOT NULL DEFAULT '1000',
  `memaryLimit` int(11) NOT NULL DEFAULT '65535',
  `createTime` bigint(20) NOT NULL,
  `accepted` int(11) NOT NULL DEFAULT '0',
  `submited` int(11) NOT NULL DEFAULT '0',
  `typeId` int(11) NOT NULL,
  `publish` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`pid`),
  KEY `fk_problem_type` (`typeId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_problem_type`
--

DROP TABLE IF EXISTS `t_problem_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_problem_type` (
  `typeId` int(11) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(30) NOT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_submit`
--

DROP TABLE IF EXISTS `t_submit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_submit` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `result` varchar(40) NOT NULL,
  `useTime` int(11) NOT NULL DEFAULT '0',
  `useMemary` int(11) NOT NULL DEFAULT '0',
  `code` text NOT NULL,
  `submitTime` bigint(20) NOT NULL,
  `languageId` int(11) NOT NULL,
  `judged` tinyint(4) NOT NULL DEFAULT '0',
  `contestId` int(11) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `fk_submit_user` (`uid`),
  KEY `fk_submit_problem` (`pid`),
  KEY `fk_submit_language` (`languageId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_testpoint`
--

DROP TABLE IF EXISTS `t_testpoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_testpoint` (
  `pointId` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `input` text NOT NULL,
  `output` text NOT NULL,
  PRIMARY KEY (`pointId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` int(11) NOT NULL DEFAULT '0',
  `accepted` int(11) NOT NULL DEFAULT '0',
  `submited` int(11) NOT NULL DEFAULT '0',
  `solved` int(11) NOT NULL DEFAULT '0',
  `registTime` bigint(20) NOT NULL DEFAULT '0',
  `lastLoginTime` bigint(20) NOT NULL DEFAULT '0',
  `sign` varchar(200) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-18 15:11:51
