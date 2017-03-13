-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.17-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for mydb
DROP DATABASE IF EXISTS `mydb`;
CREATE DATABASE IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mydb`;

-- Dumping structure for table mydb.customer_attachment
DROP TABLE IF EXISTS `customer_attachment`;
CREATE TABLE IF NOT EXISTS `customer_attachment` (
  `attachment_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `record_id` int(11) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `attached_file` mediumblob,
  PRIMARY KEY (`attachment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table mydb.customer_attachment: ~0 rows (approximately)
DELETE FROM `customer_attachment`;
/*!40000 ALTER TABLE `customer_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_attachment` ENABLE KEYS */;

-- Dumping structure for table mydb.customer_profile
DROP TABLE IF EXISTS `customer_profile`;
CREATE TABLE IF NOT EXISTS `customer_profile` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `registration_date` date DEFAULT NULL,
  `doctor` varchar(255) DEFAULT NULL,
  `direction` varchar(255) DEFAULT NULL,
  `next_examination_datetime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_id_UNIQUE` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- Dumping data for table mydb.customer_profile: ~1 rows (approximately)
DELETE FROM `customer_profile`;
/*!40000 ALTER TABLE `customer_profile` DISABLE KEYS */;
INSERT INTO `customer_profile` (`customer_id`, `firstname`, `lastname`, `address`, `birthday`, `gender`, `phone`, `registration_date`, `doctor`, `direction`, `next_examination_datetime`) VALUES
	(33, 'Roman', 'Kotsiy', 'Lviv', '2017-03-13', 'MALE', '05067', '2017-03-13', 'Ali', 'med ticks', '2017-03-13 23:51');
/*!40000 ALTER TABLE `customer_profile` ENABLE KEYS */;

-- Dumping structure for table mydb.customer_record
DROP TABLE IF EXISTS `customer_record`;
CREATE TABLE IF NOT EXISTS `customer_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `record_datetime` varchar(255) DEFAULT NULL,
  `doctor` varchar(255) DEFAULT NULL,
  `summary_report` text,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table mydb.customer_record: ~1 rows (approximately)
DELETE FROM `customer_record`;
/*!40000 ALTER TABLE `customer_record` DISABLE KEYS */;
INSERT INTO `customer_record` (`record_id`, `customer_id`, `record_datetime`, `doctor`, `summary_report`) VALUES
	(12, 33, '2017-03-13 23:41', 'Ali', 'ooo'),
	(13, 33, '2017-03-13 23:48', 'Ali', 'let rock'),
	(14, 33, '2017-03-13 23:50', 'Ali', 'sad'),
	(15, 33, '2017-03-13 23:51', 'Ali', 'asd');
/*!40000 ALTER TABLE `customer_record` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
