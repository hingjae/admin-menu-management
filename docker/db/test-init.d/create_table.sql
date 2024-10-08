CREATE TABLE `menu`
(
    `id` int not null AUTO_INCREMENT,
    `name` varchar(255) not null,
    `parent_id` int,
    `menu_order` int,
    `icon` varchar(255),
    PRIMARY KEY (`id`),
    CONSTRAINT fk_parent_menu
        FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user`
(
    `id`     varchar(255) NOT NULL,
    `password`   varchar(255) NOT NULL,
    `authorities` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
