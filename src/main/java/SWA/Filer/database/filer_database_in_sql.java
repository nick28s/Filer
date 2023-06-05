/*package SWA.Filer.database;

public class filer_database_in_sql {
}*/
/*CREATE TABLE `files` (
        `id` varchar(255) NOT NULL,
        `data` longblob,
        `fileName` varchar(255) DEFAULT NULL,
        `fileType` varchar(255) DEFAULT NULL,
        `directoryID` int DEFAULT NULL,
        PRIMARY KEY (`id`)
        FOREIGN KEY (directoryID) REFERENCES directories(id)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

        CREATE TABLE `groups` (
        `id` int NOT NULL AUTO_INCREMENT,
        `name` varchar(255) NOT NULL,
        PRIMARY KEY (`id`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

        CREATE TABLE `user_groups` (
        `user_id` int NOT NULL,
        `group_id` int NOT NULL,
        PRIMARY KEY (`user_id`,`group_id`),
        KEY `group_id` (`group_id`),
        CONSTRAINT `user_groups_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
        CONSTRAINT `user_groups_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

        CREATE TABLE `users` (
        `id` int NOT NULL AUTO_INCREMENT,
        `username` varchar(50) NOT NULL,
        `password` varchar(255) NOT NULL,
        PRIMARY KEY (`id`)
        ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3

        CREATE TABLE `directories` (
        `id` int NOT NULL AUTO_INCREMENT,
        `name` varchar(255) NOT NULL,
        PRIMARY KEY (`id`)
        ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3*/



/**/
