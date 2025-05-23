-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 23, 2025 at 03:08 PM
-- Server version: 10.11.11-MariaDB-0+deb12u1
-- PHP Version: 8.2.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dirila_arbre`
--

-- --------------------------------------------------------

--
-- Table structure for table `ACCEDER`
--

CREATE TABLE `ACCEDER` (
  `id_utilisateur` int(11) NOT NULL,
  `id_arbre` int(11) NOT NULL,
  `code_acces` char(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ACCES_ARBRE`
--

CREATE TABLE `ACCES_ARBRE` (
  `code_acces` char(5) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ACCES_ARBRE`
--

INSERT INTO `ACCES_ARBRE` (`code_acces`, `libelle`) VALUES
('C', 'Créateur de l\'arbre'),
('E', 'Edition'),
('L', 'Lecture');

-- --------------------------------------------------------

--
-- Table structure for table `ACTIONS`
--

CREATE TABLE `ACTIONS` (
  `code_action` varchar(250) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ACTIONS`
--

INSERT INTO `ACTIONS` (`code_action`, `libelle`) VALUES
('mi_arbres', 'Liste Arbres'),
('mi_creer', 'Créer Arbre'),
('mi_parametres', 'Paramètres'),
('mi_utilisateurs', 'Liste Utilisateurs'),
('mn_admin', 'Menu Admin'),
('mn_arbres', 'Menu Arbres'),
('mn_compte', 'Menu Compte');

-- --------------------------------------------------------

--
-- Table structure for table `ARBRES`
--

CREATE TABLE `ARBRES` (
  `id_arbre` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `id_createur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `AUTORISER`
--

CREATE TABLE `AUTORISER` (
  `code_role` char(5) NOT NULL,
  `code_action` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `AUTORISER`
--

INSERT INTO `AUTORISER` (`code_role`, `code_action`) VALUES
('A', 'mi_arbres'),
('A', 'mi_creer'),
('A', 'mi_parametres'),
('A', 'mi_utilisateurs'),
('A', 'mn_admin'),
('A', 'mn_arbres'),
('A', 'mn_compte'),
('U', 'mi_arbres'),
('U', 'mi_creer'),
('U', 'mi_parametres'),
('U', 'mn_arbres'),
('U', 'mn_compte');

-- --------------------------------------------------------

--
-- Table structure for table `AVOIR_EVENEMENT`
--

CREATE TABLE `AVOIR_EVENEMENT` (
  `id_individu` int(11) NOT NULL,
  `id_evenement` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `EVENEMENTS`
--

CREATE TABLE `EVENEMENTS` (
  `id_evenement` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `id_type_evenement` int(11) NOT NULL,
  `lieu` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `GENRES`
--

CREATE TABLE `GENRES` (
  `code_genre` char(5) NOT NULL,
  `libelle` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `GENRES`
--

INSERT INTO `GENRES` (`code_genre`, `libelle`) VALUES
('F', 'Femme'),
('H', 'Homme'),
('N', 'Non-défini');

-- --------------------------------------------------------

--
-- Table structure for table `INDIVIDUS`
--

CREATE TABLE `INDIVIDUS` (
  `id_individu` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `code_genre` char(5) DEFAULT NULL,
  `id_arbre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `RELATIONS_MERE`
--

CREATE TABLE `RELATIONS_MERE` (
  `id_individu` int(11) NOT NULL,
  `id_mere` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `RELATIONS_PERE`
--

CREATE TABLE `RELATIONS_PERE` (
  `id_individu` int(11) NOT NULL,
  `id_pere` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ROLES`
--

CREATE TABLE `ROLES` (
  `code_role` char(5) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ROLES`
--

INSERT INTO `ROLES` (`code_role`, `libelle`) VALUES
('A', 'Administrateur'),
('U', 'Utilisateur');

-- --------------------------------------------------------

--
-- Table structure for table `STATUT`
--

CREATE TABLE `STATUT` (
  `id_statut` int(11) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `STATUT`
--

INSERT INTO `STATUT` (`id_statut`, `libelle`) VALUES
(1, 'En attente'),
(2, 'Validé');

-- --------------------------------------------------------

--
-- Table structure for table `TYPE_EVENEMENT`
--

CREATE TABLE `TYPE_EVENEMENT` (
  `id_type_evenement` int(11) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `TYPE_EVENEMENT`
--

INSERT INTO `TYPE_EVENEMENT` (`id_type_evenement`, `libelle`) VALUES
(1, 'Naissance'),
(2, 'Décès'),
(3, 'Mariage'),
(4, 'Divorce');

-- --------------------------------------------------------

--
-- Table structure for table `UTILISATEURS`
--

CREATE TABLE `UTILISATEURS` (
  `id_utilisateur` int(11) NOT NULL,
  `login` varchar(50) DEFAULT NULL,
  `mdp` varchar(250) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `mail_token` varchar(50) DEFAULT NULL,
  `mail_date` datetime DEFAULT NULL,
  `date_creation` datetime DEFAULT NULL,
  `id_statut` int(11) NOT NULL,
  `code_role` char(5) NOT NULL,
  `comment` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `UTILISATEURS`
--

INSERT INTO `UTILISATEURS` (`id_utilisateur`, `login`, `mdp`, `mail`, `mail_token`, `mail_date`, `date_creation`, `id_statut`, `code_role`, `comment`) VALUES
(5, 'admin', '$2y$10$OdPlzsjd8pAhq8aNAdxdoOdb2TBuAYHJs3CPPv7f3Jj.f5zuae7p.', 'admin.arbre@gmail.com', NULL, NULL, '2025-05-13 14:54:11', 2, 'A', 'Mot de passe : M2p_admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ACCEDER`
--
ALTER TABLE `ACCEDER`
  ADD PRIMARY KEY (`id_utilisateur`,`id_arbre`),
  ADD KEY `ACCEDER_ibfk_2` (`id_arbre`),
  ADD KEY `ACCEDER_ibfk_3` (`code_acces`);

--
-- Indexes for table `ACCES_ARBRE`
--
ALTER TABLE `ACCES_ARBRE`
  ADD PRIMARY KEY (`code_acces`);

--
-- Indexes for table `ACTIONS`
--
ALTER TABLE `ACTIONS`
  ADD PRIMARY KEY (`code_action`);

--
-- Indexes for table `ARBRES`
--
ALTER TABLE `ARBRES`
  ADD PRIMARY KEY (`id_arbre`),
  ADD KEY `ARBRES_ibfk_1` (`id_createur`);

--
-- Indexes for table `AUTORISER`
--
ALTER TABLE `AUTORISER`
  ADD PRIMARY KEY (`code_role`,`code_action`),
  ADD KEY `AUTORISER_ibfk_2` (`code_action`);

--
-- Indexes for table `AVOIR_EVENEMENT`
--
ALTER TABLE `AVOIR_EVENEMENT`
  ADD PRIMARY KEY (`id_individu`,`id_evenement`),
  ADD KEY `AVOIR_EVENEMENT_ibfk_2` (`id_evenement`);

--
-- Indexes for table `EVENEMENTS`
--
ALTER TABLE `EVENEMENTS`
  ADD PRIMARY KEY (`id_evenement`),
  ADD KEY `EVENEMENTS_ibfk_1` (`id_type_evenement`);

--
-- Indexes for table `GENRES`
--
ALTER TABLE `GENRES`
  ADD PRIMARY KEY (`code_genre`);

--
-- Indexes for table `INDIVIDUS`
--
ALTER TABLE `INDIVIDUS`
  ADD PRIMARY KEY (`id_individu`),
  ADD KEY `INDIVIDUS_ibfk_1` (`code_genre`),
  ADD KEY `INDIVIDUS_ibfk_2` (`id_arbre`);

--
-- Indexes for table `RELATIONS_MERE`
--
ALTER TABLE `RELATIONS_MERE`
  ADD PRIMARY KEY (`id_individu`),
  ADD UNIQUE KEY `id_individu` (`id_individu`),
  ADD KEY `RELATIONS_MERE_ibfk_2` (`id_mere`);

--
-- Indexes for table `RELATIONS_PERE`
--
ALTER TABLE `RELATIONS_PERE`
  ADD PRIMARY KEY (`id_individu`),
  ADD UNIQUE KEY `id_individu` (`id_individu`),
  ADD KEY `RELATIONS_PERE_ibfk_2` (`id_pere`);

--
-- Indexes for table `ROLES`
--
ALTER TABLE `ROLES`
  ADD PRIMARY KEY (`code_role`);

--
-- Indexes for table `STATUT`
--
ALTER TABLE `STATUT`
  ADD PRIMARY KEY (`id_statut`);

--
-- Indexes for table `TYPE_EVENEMENT`
--
ALTER TABLE `TYPE_EVENEMENT`
  ADD PRIMARY KEY (`id_type_evenement`);

--
-- Indexes for table `UTILISATEURS`
--
ALTER TABLE `UTILISATEURS`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD KEY `UTILISATEURS_ibfk_1` (`id_statut`),
  ADD KEY `UTILISATEURS_ibfk_2` (`code_role`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ARBRES`
--
ALTER TABLE `ARBRES`
  MODIFY `id_arbre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `EVENEMENTS`
--
ALTER TABLE `EVENEMENTS`
  MODIFY `id_evenement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `INDIVIDUS`
--
ALTER TABLE `INDIVIDUS`
  MODIFY `id_individu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `STATUT`
--
ALTER TABLE `STATUT`
  MODIFY `id_statut` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `TYPE_EVENEMENT`
--
ALTER TABLE `TYPE_EVENEMENT`
  MODIFY `id_type_evenement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `UTILISATEURS`
--
ALTER TABLE `UTILISATEURS`
  MODIFY `id_utilisateur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ACCEDER`
--
ALTER TABLE `ACCEDER`
  ADD CONSTRAINT `ACCEDER_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `UTILISATEURS` (`id_utilisateur`) ON DELETE CASCADE,
  ADD CONSTRAINT `ACCEDER_ibfk_2` FOREIGN KEY (`id_arbre`) REFERENCES `ARBRES` (`id_arbre`) ON DELETE CASCADE,
  ADD CONSTRAINT `ACCEDER_ibfk_3` FOREIGN KEY (`code_acces`) REFERENCES `ACCES_ARBRE` (`code_acces`) ON DELETE CASCADE;

--
-- Constraints for table `ARBRES`
--
ALTER TABLE `ARBRES`
  ADD CONSTRAINT `ARBRES_ibfk_1` FOREIGN KEY (`id_createur`) REFERENCES `UTILISATEURS` (`id_utilisateur`) ON DELETE CASCADE;

--
-- Constraints for table `AUTORISER`
--
ALTER TABLE `AUTORISER`
  ADD CONSTRAINT `AUTORISER_ibfk_1` FOREIGN KEY (`code_role`) REFERENCES `ROLES` (`code_role`) ON DELETE CASCADE,
  ADD CONSTRAINT `AUTORISER_ibfk_2` FOREIGN KEY (`code_action`) REFERENCES `ACTIONS` (`code_action`) ON DELETE CASCADE;

--
-- Constraints for table `AVOIR_EVENEMENT`
--
ALTER TABLE `AVOIR_EVENEMENT`
  ADD CONSTRAINT `AVOIR_EVENEMENT_ibfk_1` FOREIGN KEY (`id_individu`) REFERENCES `INDIVIDUS` (`id_individu`) ON DELETE CASCADE,
  ADD CONSTRAINT `AVOIR_EVENEMENT_ibfk_2` FOREIGN KEY (`id_evenement`) REFERENCES `EVENEMENTS` (`id_evenement`) ON DELETE CASCADE;

--
-- Constraints for table `EVENEMENTS`
--
ALTER TABLE `EVENEMENTS`
  ADD CONSTRAINT `EVENEMENTS_ibfk_1` FOREIGN KEY (`id_type_evenement`) REFERENCES `TYPE_EVENEMENT` (`id_type_evenement`) ON DELETE CASCADE;

--
-- Constraints for table `INDIVIDUS`
--
ALTER TABLE `INDIVIDUS`
  ADD CONSTRAINT `INDIVIDUS_ibfk_1` FOREIGN KEY (`code_genre`) REFERENCES `GENRES` (`code_genre`) ON DELETE CASCADE,
  ADD CONSTRAINT `INDIVIDUS_ibfk_2` FOREIGN KEY (`id_arbre`) REFERENCES `ARBRES` (`id_arbre`) ON DELETE CASCADE;

--
-- Constraints for table `RELATIONS_MERE`
--
ALTER TABLE `RELATIONS_MERE`
  ADD CONSTRAINT `RELATIONS_MERE_ibfk_1` FOREIGN KEY (`id_individu`) REFERENCES `INDIVIDUS` (`id_individu`) ON DELETE CASCADE,
  ADD CONSTRAINT `RELATIONS_MERE_ibfk_2` FOREIGN KEY (`id_mere`) REFERENCES `INDIVIDUS` (`id_individu`) ON DELETE CASCADE;

--
-- Constraints for table `RELATIONS_PERE`
--
ALTER TABLE `RELATIONS_PERE`
  ADD CONSTRAINT `RELATIONS_PERE_ibfk_1` FOREIGN KEY (`id_individu`) REFERENCES `INDIVIDUS` (`id_individu`) ON DELETE CASCADE,
  ADD CONSTRAINT `RELATIONS_PERE_ibfk_2` FOREIGN KEY (`id_pere`) REFERENCES `INDIVIDUS` (`id_individu`) ON DELETE CASCADE;

--
-- Constraints for table `UTILISATEURS`
--
ALTER TABLE `UTILISATEURS`
  ADD CONSTRAINT `UTILISATEURS_ibfk_1` FOREIGN KEY (`id_statut`) REFERENCES `STATUT` (`id_statut`) ON DELETE CASCADE,
  ADD CONSTRAINT `UTILISATEURS_ibfk_2` FOREIGN KEY (`code_role`) REFERENCES `ROLES` (`code_role`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
