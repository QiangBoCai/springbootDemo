/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.23 : Database - myblog
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`myblog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `myblog`;

/*Table structure for table `biz_wangyi_play` */

DROP TABLE IF EXISTS `biz_wangyi_play`;

CREATE TABLE `biz_wangyi_play` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `play_id` varchar(30) NOT NULL COMMENT '网易歌单id',
  `play_name` varchar(100) NOT NULL COMMENT '网易歌单名',
  `status` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `commit` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `biz_wangyi_play` */

insert  into `biz_wangyi_play`(`id`,`play_id`,`play_name`,`status`,`create_time`,`update_time`,`commit`) values (1,'448927692','我喜欢的音乐',0,'2018-03-08 13:25:38','2018-03-08 13:25:38','http://music.163.com/api/playlist/detail?id=448927692&updateTime=-1'),(2,'499010115','周星驰',0,'2018-03-08 13:26:10','2018-03-08 13:26:10',NULL),(3,'473216126','伍佰',0,'2018-03-08 13:26:16','2018-03-08 13:26:16',NULL),(4,'477142078','伊人月下戴红妆',0,'2018-03-08 13:26:24','2018-03-08 13:26:24',NULL),(5,'879855523','好听的英文歌曲',0,'2018-03-08 13:28:19','2018-03-08 13:28:19',NULL),(6,'866426412','穿透灵魂的柔情女声·十大伤感女声',0,'2018-03-08 13:28:58','2018-03-08 13:28:58',NULL),(7,'813229871','最后的莫西干人',0,'2018-03-08 13:29:13','2018-03-08 13:29:13',NULL),(8,'522532786','中国十大名曲',0,'2018-03-08 13:29:23','2018-03-08 13:29:23',NULL),(9,'520172117','♪适合学习英语的歌曲',0,'2018-03-08 13:29:43','2018-03-08 13:29:43',NULL),(10,'2414108221','菠菜小智的收藏',1,'2018-09-10 14:02:07','2018-09-10 14:02:07',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
