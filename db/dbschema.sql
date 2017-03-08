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
CREATE DATABASE IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mydb`;

-- Dumping structure for table mydb.cedrus_customer_data
CREATE TABLE IF NOT EXISTS `cedrus_customer_data` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `registration_date` date DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_id_UNIQUE` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table mydb.cedrus_customer_data: ~4 rows (approximately)
/*!40000 ALTER TABLE `cedrus_customer_data` DISABLE KEYS */;
REPLACE INTO `cedrus_customer_data` (`customer_id`, `firstname`, `lastname`, `address`, `birthday`, `gender`, `phone`, `registration_date`) VALUES
	(1, 'Roman', 'Kotsiy', 'Lviv, Dibrovna 6\r\n', '2017-03-08', 'male', '380970016107', '2017-03-08'),
	(2, 'Natalia', 'Gavriljuk', 'Lviv, Dibrovna  6', '2017-03-08', 'female', '+380506750566', '2017-03-08'),
	(3, 'Daniel', 'Gavriljuk', 'Lviv, Dibrovna  6', '2017-03-08', 'female', '+380506750566', '2017-03-08'),
	(4, 'Makar', 'Gavriljuk', 'Lviv, Dibrovna  6', '2017-03-08', 'female', '+380506750566', '2017-03-08');
/*!40000 ALTER TABLE `cedrus_customer_data` ENABLE KEYS */;

-- Dumping structure for table mydb.customer_attachments
CREATE TABLE IF NOT EXISTS `customer_attachments` (
  `customer_id` int(11) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mydb.customer_attachments: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_attachments` ENABLE KEYS */;

-- Dumping structure for table mydb.customer_profile
CREATE TABLE IF NOT EXISTS `customer_profile` (
  `customer_id` int(11) NOT NULL,
  `last_examination_date` date DEFAULT NULL,
  `next_examination_date` date DEFAULT NULL,
  `summary_report` text,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table mydb.customer_profile: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_profile` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
