-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 14, 2023 at 03:23 PM
-- Server version: 5.5.68-MariaDB
-- PHP Version: 8.0.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perrier27u`
--

-- --------------------------------------------------------

--
-- Table structure for table `S402_reservations`
--

CREATE TABLE `S402_reservations` (
  `id_reservation` int(11) NOT NULL,
  `nom` varchar(255) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `prenom` varchar(255) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `nb_pers` int(2) NOT NULL,
  `num_tel` int(10) NOT NULL,
  `id_restaurant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `S402_reservations`
--
ALTER TABLE `S402_reservations`
  ADD PRIMARY KEY (`id_reservation`),
  ADD KEY `id_restaurant` (`id_restaurant`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `S402_reservations`
--
ALTER TABLE `S402_reservations`
  MODIFY `id_reservation` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `S402_reservations`
--
ALTER TABLE `S402_reservations`
  ADD CONSTRAINT `S402_reservations_ibfk_1` FOREIGN KEY (`id_restaurant`) REFERENCES `S402_restaurants` (`id_restaurant`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
