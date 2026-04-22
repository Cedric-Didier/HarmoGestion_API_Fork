SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `harmogestion`
--
CREATE DATABASE IF NOT EXISTS `harmogestion` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `harmogestion`;

--
-- Structure de la table `membre`
--
CREATE TABLE `membre`
(
    `id_membre` INT NOT NULL AUTO_INCREMENT,
    `nom_membre` VARCHAR(30) NOT NULL,
    `prenom_membre` VARCHAR(30) NOT NULL,
    `date_inscription_membre` DATE NOT NULL,
    PRIMARY KEY(`id_membre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

--
-- Structure de la table `instrument`
--
CREATE TABLE `instrument`
(
    `id_instrument` INT NOT NULL AUTO_INCREMENT,
    `libelle_instrument` VARCHAR(50) NOT NULL,
    PRIMARY KEY(`id_instrument`),
    UNIQUE KEY `libelle_instrument` (`libelle_instrument`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

--
-- Structure de la table `cours`
--
CREATE TABLE `cours`
(
    `id_cours` INT NOT NULL AUTO_INCREMENT,
    `date_cours` DATETIME NOT NULL,
    `duree_cours` TINYINT NOT NULL,
    `id_instrument` INT NOT NULL,
    `id_membre_enseignant` INT NOT NULL,
    PRIMARY KEY(`id_cours`),
    FOREIGN KEY(`id_instrument`) REFERENCES `instrument`(`id_instrument`),
    FOREIGN KEY(`id_membre_enseignant`) REFERENCES `membre`(`id_membre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

--
-- Structure de la table `representation`
--
CREATE TABLE `representation`
(
    `id_representation` INT NOT NULL AUTO_INCREMENT,
    `nom_representation` VARCHAR(80) NOT NULL,
    `date_representation` DATETIME NOT NULL,
    `lieu_representation` VARCHAR(100) NOT NULL,
    PRIMARY KEY(`id_representation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

--
-- Structure de la table `pratiquer`
--
CREATE TABLE `pratiquer`
(
    `id_membre` INT NOT NULL,
    `id_instrument` INT NOT NULL,
    `apprentissage_en_cours` BOOLEAN NOT NULL,
    PRIMARY KEY(`id_membre`, `id_instrument`),
    FOREIGN KEY(`id_membre`) REFERENCES `membre`(`id_membre`),
    FOREIGN KEY(`id_instrument`) REFERENCES `instrument`(`id_instrument`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

--
-- Structure de la table `participer_cours`
--
CREATE TABLE `participer_cours`
(
    `id_membre_apprenant` INT NOT NULL,
    `id_cours` INT NOT NULL,
    PRIMARY KEY(`id_membre_apprenant`, `id_cours`),
    FOREIGN KEY(`id_membre_apprenant`) REFERENCES `membre`(`id_membre`),
    FOREIGN KEY(`id_cours`) REFERENCES `cours`(`id_cours`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

--
-- Structure de la table `participer_representation`
--
CREATE TABLE `participer_representation`
(
    `id_membre` INT NOT NULL,
    `id_representation` INT NOT NULL,
    PRIMARY KEY(`id_membre`, `id_representation`),
    FOREIGN KEY(`id_membre`) REFERENCES `membre`(`id_membre`),
    FOREIGN KEY(`id_representation`) REFERENCES `representation`(`id_representation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

--
-- Structure de la table `instruments_representation`
--
CREATE TABLE `instruments_representation`
(
    `id_instrument` INT NOT NULL,
    `id_representation` INT NOT NULL,
    PRIMARY KEY(`id_instrument`, `id_representation`),
    FOREIGN KEY(`id_instrument`) REFERENCES `instrument`(`id_instrument`),
    FOREIGN KEY(`id_representation`) REFERENCES `representation`(`id_representation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `membre`
--
INSERT INTO `membre` (`id_membre`, `nom_membre`, `prenom_membre`, `date_inscription_membre`)
VALUES (1, 'Didier', 'Cédric', '2026-04-13'),
       (2, 'Brucker', 'Rodolphe', '2026-04-13'),
       (3, 'Ugolini', 'Cyril', '2026-04-13'),
       (4, 'Seiwert', 'Thomas', '2026-04-13');

--
-- Données de la table `instrument`
--
INSERT INTO `instrument` (`id_instrument`, `libelle_instrument`)
VALUES (1, 'Triangle'),
       (2, 'Tuba'),
       (3, 'Contrebasse'),
       (4, 'Violon'),
       (5, 'Alto');

--
-- Déchargement des données de la table `pratiquer`
--
INSERT INTO `pratiquer` (`id_membre`, `id_instrument`, `apprentissage_en_cours`)
VALUES (1, 1, 1),
       (1, 5, 0),
       (2, 1, 0),
       (2, 4, 0),
       (3, 1, 1),
       (3, 2, 0),
       (4, 1, 1),
       (4, 4, 0);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;