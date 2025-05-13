SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `ACCEDER` (
  `id_utilisateur` int(11) NOT NULL,
  `id_arbre` int(11) NOT NULL,
  `code_acces` char(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `ACCES_ARBRE` (
  `code_acces` char(5) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `ACCES_ARBRE` (`code_acces`, `libelle`) VALUES
('C', 'Créateur de l\'arbre'),
('E', 'Edition'),
('L', 'Lecture');

CREATE TABLE `ACTIONS` (
  `code_action` varchar(250) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `ACTIONS` (`code_action`, `libelle`) VALUES
('mi_arbres', 'Liste Arbres'),
('mi_creer', 'Créer Arbre'),
('mi_parametres', 'Paramètres'),
('mi_utilisateurs', 'Liste Utilisateurs'),
('mn_admin', 'Menu Admin'),
('mn_arbres', 'Menu Arbres'),
('mn_compte', 'Menu Compte');

CREATE TABLE `ARBRES` (
  `id_arbre` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `id_createur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `AUTORISER` (
  `code_role` char(5) NOT NULL,
  `code_action` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

CREATE TABLE `AVOIR_EVENEMENT` (
  `id_individu` int(11) NOT NULL,
  `id_evenement` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `EVENEMENTS` (
  `id_evenement` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `id_type_evenement` int(11) NOT NULL,
  `lieu` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `GENRES` (
  `code_genre` char(5) NOT NULL,
  `libelle` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `GENRES` (`code_genre`, `libelle`) VALUES
('F', 'Femme'),
('H', 'Homme'),
('N', 'Non-défini');

CREATE TABLE `INDIVIDUS` (
  `id_individu` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `code_genre` char(5) DEFAULT NULL,
  `id_arbre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `RELATIONS_MERE` (
  `id_individu` int(11) NOT NULL,
  `id_mere` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `RELATIONS_PERE` (
  `id_individu` int(11) NOT NULL,
  `id_pere` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `ROLES` (
  `code_role` char(5) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `ROLES` (`code_role`, `libelle`) VALUES
('A', 'Administrateur'),
('U', 'Utilisateur');

CREATE TABLE `STATUT` (
  `id_statut` int(11) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `STATUT` (`id_statut`, `libelle`) VALUES
(1, 'En attente'),
(2, 'Validé');

CREATE TABLE `TYPE_EVENEMENT` (
  `id_type_evenement` int(11) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `TYPE_EVENEMENT` (`id_type_evenement`, `libelle`) VALUES
(1, 'Naissance'),
(2, 'Décès'),
(3, 'Mariage'),
(4, 'Décès');

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

INSERT INTO `UTILISATEURS` (`id_utilisateur`, `login`, `mdp`, `mail`, `mail_token`, `mail_date`, `date_creation`, `id_statut`, `code_role`, `comment`) VALUES
(5, 'admin', '$2y$10$OdPlzsjd8pAhq8aNAdxdoOdb2TBuAYHJs3CPPv7f3Jj.f5zuae7p.', 'admin.arbre@gmail.com', NULL, NULL, '2025-05-13 14:54:11', 2, 'A', 'Mot de passe : M2p_admin');


ALTER TABLE `ACCEDER`
  ADD PRIMARY KEY (`id_utilisateur`,`id_arbre`),
  ADD KEY `id_arbre` (`id_arbre`),
  ADD KEY `code_acces` (`code_acces`);

ALTER TABLE `ACCES_ARBRE`
  ADD PRIMARY KEY (`code_acces`);

ALTER TABLE `ACTIONS`
  ADD PRIMARY KEY (`code_action`);

ALTER TABLE `ARBRES`
  ADD PRIMARY KEY (`id_arbre`),
  ADD KEY `id_createur` (`id_createur`);

ALTER TABLE `AUTORISER`
  ADD PRIMARY KEY (`code_role`,`code_action`),
  ADD KEY `code_action` (`code_action`);

ALTER TABLE `AVOIR_EVENEMENT`
  ADD PRIMARY KEY (`id_individu`,`id_evenement`),
  ADD KEY `id_evenement` (`id_evenement`);

ALTER TABLE `EVENEMENTS`
  ADD PRIMARY KEY (`id_evenement`),
  ADD KEY `id_type_evenement` (`id_type_evenement`);

ALTER TABLE `GENRES`
  ADD PRIMARY KEY (`code_genre`);

ALTER TABLE `INDIVIDUS`
  ADD PRIMARY KEY (`id_individu`),
  ADD KEY `code_genre` (`code_genre`),
  ADD KEY `id_arbre` (`id_arbre`);

ALTER TABLE `RELATIONS_MERE`
  ADD PRIMARY KEY (`id_individu`),
  ADD KEY `id_mere` (`id_mere`);

ALTER TABLE `RELATIONS_PERE`
  ADD PRIMARY KEY (`id_individu`),
  ADD KEY `id_pere` (`id_pere`);

ALTER TABLE `ROLES`
  ADD PRIMARY KEY (`code_role`);

ALTER TABLE `STATUT`
  ADD PRIMARY KEY (`id_statut`);

ALTER TABLE `TYPE_EVENEMENT`
  ADD PRIMARY KEY (`id_type_evenement`);

ALTER TABLE `UTILISATEURS`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD KEY `id_statut` (`id_statut`),
  ADD KEY `code_role` (`code_role`);


ALTER TABLE `ARBRES`
  MODIFY `id_arbre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `EVENEMENTS`
  MODIFY `id_evenement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `INDIVIDUS`
  MODIFY `id_individu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

ALTER TABLE `STATUT`
  MODIFY `id_statut` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `TYPE_EVENEMENT`
  MODIFY `id_type_evenement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `UTILISATEURS`
  MODIFY `id_utilisateur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;


ALTER TABLE `ACCEDER`
  ADD CONSTRAINT `ACCEDER_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `UTILISATEURS` (`id_utilisateur`),
  ADD CONSTRAINT `ACCEDER_ibfk_2` FOREIGN KEY (`id_arbre`) REFERENCES `ARBRES` (`id_arbre`),
  ADD CONSTRAINT `ACCEDER_ibfk_3` FOREIGN KEY (`code_acces`) REFERENCES `ACCES_ARBRE` (`code_acces`);

ALTER TABLE `ARBRES`
  ADD CONSTRAINT `ARBRES_ibfk_1` FOREIGN KEY (`id_createur`) REFERENCES `UTILISATEURS` (`id_utilisateur`);

ALTER TABLE `AUTORISER`
  ADD CONSTRAINT `AUTORISER_ibfk_1` FOREIGN KEY (`code_role`) REFERENCES `ROLES` (`code_role`),
  ADD CONSTRAINT `AUTORISER_ibfk_2` FOREIGN KEY (`code_action`) REFERENCES `ACTIONS` (`code_action`);

ALTER TABLE `AVOIR_EVENEMENT`
  ADD CONSTRAINT `AVOIR_EVENEMENT_ibfk_1` FOREIGN KEY (`id_individu`) REFERENCES `INDIVIDUS` (`id_individu`),
  ADD CONSTRAINT `AVOIR_EVENEMENT_ibfk_2` FOREIGN KEY (`id_evenement`) REFERENCES `EVENEMENTS` (`id_evenement`);

ALTER TABLE `EVENEMENTS`
  ADD CONSTRAINT `EVENEMENTS_ibfk_1` FOREIGN KEY (`id_type_evenement`) REFERENCES `TYPE_EVENEMENT` (`id_type_evenement`);

ALTER TABLE `INDIVIDUS`
  ADD CONSTRAINT `INDIVIDUS_ibfk_1` FOREIGN KEY (`code_genre`) REFERENCES `GENRES` (`code_genre`),
  ADD CONSTRAINT `INDIVIDUS_ibfk_2` FOREIGN KEY (`id_arbre`) REFERENCES `ARBRES` (`id_arbre`);

ALTER TABLE `RELATIONS_MERE`
  ADD CONSTRAINT `RELATIONS_MERE_ibfk_1` FOREIGN KEY (`id_individu`) REFERENCES `INDIVIDUS` (`id_individu`),
  ADD CONSTRAINT `RELATIONS_MERE_ibfk_2` FOREIGN KEY (`id_mere`) REFERENCES `INDIVIDUS` (`id_individu`);

ALTER TABLE `RELATIONS_PERE`
  ADD CONSTRAINT `RELATIONS_PERE_ibfk_1` FOREIGN KEY (`id_individu`) REFERENCES `INDIVIDUS` (`id_individu`),
  ADD CONSTRAINT `RELATIONS_PERE_ibfk_2` FOREIGN KEY (`id_pere`) REFERENCES `INDIVIDUS` (`id_individu`);

ALTER TABLE `UTILISATEURS`
  ADD CONSTRAINT `UTILISATEURS_ibfk_1` FOREIGN KEY (`id_statut`) REFERENCES `STATUT` (`id_statut`),
  ADD CONSTRAINT `UTILISATEURS_ibfk_2` FOREIGN KEY (`code_role`) REFERENCES `ROLES` (`code_role`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
