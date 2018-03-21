/*DROP DATABASE IF EXISTS hhoj;*/
CREATE DATABASE `hhoj` 


DROP TABLE IF EXISTS `t_user`;
/*用户表*/
CREATE TABLE `t_user` (
  `uid` INT(11) NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(30) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `role` INT(11) NOT NULL DEFAULT '0',
  `accepted` INT(11) NOT NULL DEFAULT '0',
  `submited` INT(11) NOT NULL DEFAULT '0',
   `solved`  INT(11) NOT NULL DEFAULT '0',
  `registTime` BIGINT(20) NOT NULL DEFAULT '0',
  `lastLoginTime` BIGINT(20) NOT NULL DEFAULT '0',
  `sign` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_problem_type`;
/*试题类型表*/
CREATE TABLE `t_problem_type` (
  `typeId` INT(11) NOT NULL AUTO_INCREMENT,
  `typeName` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `t_problem`;
/*试题表*/
CREATE TABLE `t_problem` (
  `pid` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `pDesc` TEXT NOT NULL,
  `inputExample` TEXT NOT NULL,
  `outputExample` TEXT NOT NULL,
  `hint` VARCHAR(250) NOT NULL,
  `source` VARCHAR(100) NOT NULL,
  `timeLimit` INT(11) NOT NULL DEFAULT '1000',
  `memaryLimit` INT(11) NOT NULL DEFAULT '65535',
  `createTime` BIGINT(20) NOT NULL,
  `accepted` INT(11) NOT NULL DEFAULT '0',
  `submited` INT(11) NOT NULL DEFAULT '0',
  `typeId` INT(11) NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `fk_problem_type` (`typeId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


/*测试点*/
DROP TABLE IF EXISTS t_testpoint;
CREATE TABLE t_testpoint(
   `pointId` INT NOT NULL AUTO_INCREMENT,
   `pid` INT NOT NULL,
   `input` TEXT NOT NULL,
   `output` TEXT NOT NULL,
   PRIMARY KEY(`pointId`)

)ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_language`;
/*编程语言表*/
CREATE TABLE `t_language` (
  `languageId` INT(11) NOT NULL,
  `languageName` VARCHAR(30) NOT NULL,
  `compileCommand`VARCHAR(30) NOT NULL,
  `runCommand`  VARCHAR(30)  NOT NULL,  
  PRIMARY KEY (`languageId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `t_submit`;
/*用户提交表*/
CREATE TABLE `t_submit` (
  `sid` INT(11) NOT NULL AUTO_INCREMENT,
  `uid` INT(11) NOT NULL,
  `pid` INT(11) NOT NULL,
  `result` VARCHAR(40) NOT NULL,
  `useTime` INT(11) NOT NULL DEFAULT '0',
  `useMemary` INT(11) NOT NULL DEFAULT '0',
  `code` TEXT NOT NULL,
  `submitTime` BIGINT(20) NOT NULL,
  `languageId` INT(11) NOT NULL,
  `judged` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`sid`),
  KEY `fk_submit_user` (`uid`),
  KEY `fk_submit_problem` (`pid`),
  KEY `fk_submit_language` (`languageId`)

) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_comment`;
/*评论表*/
CREATE TABLE `t_comment` (
  `commentId` INT(11) NOT NULL,
  `uid` INT(11) NOT NULL,
  `type` INT(11) NOT NULL,
  `content` TEXT NOT NULL,
  `pid` INT(11) NOT NULL,
  PRIMARY KEY (`commentId`),
  KEY `fk_comment_user` (`uid`),
  KEY `fk_comment_problem` (`pid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `t_contest`;
/*竞赛表*/
CREATE TABLE `t_contest` (
  `contestId` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(250) NOT NULL,
  `desc` VARCHAR(250) NOT NULL,
  `startTime` BIGINT(20) NOT NULL,
  `endTime` BIGINT(20) NOT NULL,
  `startJoinTime` BIGINT(20) NOT NULL,
  `endJoinTime` BIGINT(20) NOT NULL,
  `contestPassword` VARCHAR(200) NOT NULL,
  `initiator` INT(11) DEFAULT NULL,
  PRIMARY KEY (`contestId`),
  KEY `fk_contest_user` (`initiator`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `t_contest_problem`;
/*竞赛对应的试题表*/
CREATE TABLE `t_contest_problem` (
  `pid` INT(11) NOT NULL,
  `contestId` INT(11) NOT NULL,
  `accepted` INT(11) NOT NULL,
  `submited` INT(11) NOT NULL,
  PRIMARY KEY (`pid`,`contestId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `t_contest_user`;
/*参加竞赛的用户表*/
CREATE TABLE `t_contest_user` (
  `uid` INT(11) NOT NULL,
  `contestId` INT(11) NOT NULL,
  PRIMARY KEY (`uid`,`contestId`),
  KEY `fk_contest` (`contestId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


