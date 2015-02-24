# Sequel Pro dump
# Version 2492
# http://code.google.com/p/sequel-pro
#
# Host: 127.0.0.1 (MySQL 5.0.89)
# Database: simpleworkflow
# Generation Time: 2015-02-24 16:31:51 +0000
# ************************************************************

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table sequence_flow
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sequence_flow`;

CREATE TABLE `sequence_flow` (
  `id` bigint(11) NOT NULL auto_increment,
  `workflow_Id` bigint(11) NOT NULL,
  `from_task` varchar(128) NOT NULL,
  `to_task` varchar(128) NOT NULL,
  `interval_time` bigint(13) NOT NULL default '0',
  `create_time` bigint(13) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;



# Dump of table task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` bigint(11) NOT NULL auto_increment,
  `workflow_id` bigint(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `type` varchar(64) NOT NULL default 'User',
  `data` text,
  `timeout` int(11) NOT NULL,
  `status` varchar(64) NOT NULL default 'Created',
  `create_time` bigint(13) NOT NULL,
  `queued_time` bigint(13) default NULL,
  `dispatch_time` bigint(13) default NULL,
  `start_time` bigint(13) default NULL,
  `end_time` bigint(13) default NULL,
  `timeout_time` bigint(13) default NULL,
  `callback_endpoint` varchar(512) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;



# Dump of table task_event
# ------------------------------------------------------------

DROP TABLE IF EXISTS `task_event`;

CREATE TABLE `task_event` (
  `id` bigint(11) NOT NULL auto_increment,
  `task_id` bigint(11) default NULL,
  `workflow_id` bigint(11) default NULL,
  `name` varchar(128) default NULL,
  `data` text,
  `event_time` bigint(13) default NULL,
  `create_time` bigint(13) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;



# Dump of table workflow
# ------------------------------------------------------------

DROP TABLE IF EXISTS `workflow`;

CREATE TABLE `workflow` (
  `id` bigint(11) NOT NULL auto_increment,
  `name` varchar(256) default NULL,
  `status` varchar(64) default 'Created',
  `app_id` varchar(256) default NULL,
  `app_task_queue` varchar(1024) default NULL,
  `create_time` bigint(13) default NULL,
  `scheduled_start_time` bigint(13) default NULL,
  `dispatch_time` bigint(13) default NULL,
  `start_time` bigint(13) default NULL,
  `end_time` bigint(13) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;






/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
