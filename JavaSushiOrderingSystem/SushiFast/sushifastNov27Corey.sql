-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 28, 2018 at 06:30 AM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sushifast`
--

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `item_ID` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `description` text NOT NULL,
  `image_Path` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `type` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`item_ID`, `name`, `description`, `image_Path`, `price`, `type`) VALUES
(1, 'Edamame', 'Edamame beans from Japan. They are green and contain beans. Some salt is put on them.', 'images/edamame.jpg', 12.76, 'appetizer'),
(2, 'Assorted Tempura', 'Tempura including 2 shrimp, 1 sweet potato, 1 bean, and 1 eggplant.', 'images/tempura.jpeg', 10.33, 'main'),
(3, 'Gyoza', 'Pan fried dumplings with sauce. They are crispy and fried.', 'images/gyoza.jpg', 7.54, 'appetizer'),
(6, 'Green Tea Ice Cream', 'Green ice cream in the green tea variety. Sweet and made with cream and green tea and matcha powder.', 'images/greenicecream.jpg', 4.43, 'dessert'),
(7, 'Miso Soup', 'Silken tofu soup.', 'images/msio.jpg', 1.5, 'appetizer'),
(8, 'Wanton Soup', 'Meat dumpling soup.', 'images/wonton.jpg', 3.75, 'appetizer'),
(9, 'Goma-ae', 'Spinach with sesame dressing.', 'images/goma-ae.jpg', 3.5, 'appetizer'),
(10, 'Vegetable Udon', 'Vegetable soup with udon noodles.', 'images/vegudon.jpg', 8.5, 'main'),
(11, 'Seafood Udon', 'Seafood soup with udon noodles.', 'images/seaudon.jpg', 10.5, 'main'),
(12, 'Vegetable Teriyaki', 'Vegetables with teriyki sauce.', 'images/vegteri.jpg', 7.95, 'main'),
(13, 'Beef Teriyaki', 'Beef with teriyaki sauce.', 'images/beefteri.jpg', 9.5, 'main'),
(14, 'Chicken Teriyaki', 'Chicken with teriyaki sauce.', 'images/chickenteri.jpg', 8.95, 'main'),
(15, 'California Roll', 'Crab, mayonnaise, rice, seaweed, cucumber.', 'images/california.jpg', 6.75, 'main'),
(16, 'Rainbow Roll', 'Fish, avocado, ebi, on california roll.', 'images/rainbow.jpg', 9.5, 'main'),
(17, 'Unagi Roll', 'Eel and sweet sauce, on california roll.', 'images/unagi.jpg', 8.5, 'main'),
(18, 'Mango Ice Cream', 'Sweet mango ice cream.', 'images/mango.jpg', 2.5, 'dessert'),
(19, 'Purin', 'Silken pudding with caramel.', 'images/purin.jpg', 5.25, 'dessert');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_ID` int(11) NOT NULL,
  `table_ID` int(11) NOT NULL,
  `payment_ID` int(11) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_ID`, `table_ID`, `payment_ID`, `status`) VALUES
(1, 2, 0, 'Complete'),
(2, 2, 0, 'Complete'),
(3, 2, 0, 'Complete'),
(4, 2, 0, 'Complete'),
(5, 2, 0, 'Complete'),
(6, 2, 0, 'Complete');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `order_ID` int(11) NOT NULL,
  `item_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`order_ID`, `item_ID`) VALUES
(5, 2),
(5, 10),
(5, 11),
(5, 12),
(5, 13),
(5, 14),
(5, 15),
(5, 16),
(5, 17),
(6, 2),
(6, 10),
(6, 11),
(6, 12),
(6, 13),
(6, 14),
(6, 15),
(6, 16),
(6, 17);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `payment_ID` int(11) NOT NULL,
  `order_ID` int(11) NOT NULL,
  `order_Total` double NOT NULL,
  `payment_Type` varchar(25) NOT NULL,
  `return_Code` varchar(8) NOT NULL,
  `status_Flag` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`payment_ID`, `order_ID`, `order_Total`, `payment_Type`, `return_Code`, `status_Flag`) VALUES
(1, 1, 45.32, 'Visa', 'OALB9B41', 1),
(2, 1, 45.32, 'Visa', 'U35CP7S2', 0),
(3, 1, 45.32, 'Visa', '2V0KHVLU', 0),
(4, 1, 45.32, 'Visa', 'JGYG13HD', 0),
(5, 1, 45.32, 'Visa', '1ZAWV0ER', 0),
(6, 1, 45.32, 'Visa', 'W927T72O', 0),
(7, 1, 45.32, 'Visa', '2QX00YKP', 0),
(8, 1, 45.32, 'Visa', 'TST0629D', 0),
(9, 1, 45.32, 'Visa', 'QRAVDFSM', 0),
(10, 1, 45.32, 'Visa', 'SD76MUXB', 0),
(11, 1, 45.32, 'Visa', 'NF91S14W', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`item_ID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_ID`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`payment_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `item_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `payment_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
