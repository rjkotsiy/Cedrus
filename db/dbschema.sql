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

-- Dumping data for table mydb.cerdus_customer_data: ~2 rows (approximately)
/*!40000 ALTER TABLE `cerdus_customer_data` DISABLE KEYS */;
REPLACE INTO `cerdus_customer_data` (`customer_id`, `firstname`, `lastname`, `address`, `birthday`, `gender`, `phone`, `registration_date`) VALUES
	(1, 'Roman', 'Kotsiy', 'Lviv, Dibrovna 6\r\n', NULL, NULL, '380970016107', NULL),
	(2, 'Natalia', 'Gavriljuk', 'Lviv, Dibrovna  6', NULL, NULL, '+380506750566', NULL);
/*!40000 ALTER TABLE `cerdus_customer_data` ENABLE KEYS */;

-- Dumping data for table mydb.customer_attachments: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_attachments` ENABLE KEYS */;

-- Dumping data for table mydb.customer_profile: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_profile` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
