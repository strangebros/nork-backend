CREATE TABLE `keyword`
(
    `id`    int NOT NULL,
    `value` varchar(40) NULL,
    CONSTRAINT `PK_KEYWORD` PRIMARY KEY (`id`)
);

CREATE TABLE `workspace`
(
    `id`                 int          NOT NULL,
    `name`               varchar(40) NULL,
    `category`           varchar(20) NULL,
    `latitude`           decimal(20, 17) NULL,
    `longitude`          decimal(20, 17) NULL,
    `road_address`       varchar(100) NULL,
    `image_url`          varchar(255) NULL,
    `rating`             decimal(3, 1) NULL,
    `number_of_visitors` int NULL,
    `poi_id`             varchar(100) NOT NULL,
    CONSTRAINT `PK_WORKSPACE` PRIMARY KEY (`id`)
);

CREATE TABLE `workspace_keyword`
(
    `id`           int NOT NULL,
    `workspace_id` int NOT NULL,
    `keyword_id`   int NOT NULL,
    CONSTRAINT `PK_WORKSPACE_KEYWORD` PRIMARY KEY (`id`),
    CONSTRAINT `FK_workspace_TO_workspace_keyword_1` FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`),
    CONSTRAINT `FK_keyword_TO_workspace_keyword_1` FOREIGN KEY (`keyword_id`) REFERENCES `keyword` (`id`)
);

CREATE TABLE `member`
(
    `id`        int NOT NULL,
    `email`     varchar(40) NULL,
    `password`  varchar(40) NULL,
    `nickname`  varchar(40) NULL,
    `birthdate` date NULL,
    `position`  varchar(20) NULL,
    CONSTRAINT `PK_MEMBER` PRIMARY KEY (`id`)
);

CREATE TABLE `visited_space`
(
    `id`           int NOT NULL,
    `member_id`    int NOT NULL,
    `workspace_id` int NOT NULL,
    `rating_sum`   decimal(3, 1) NULL,
    `visit_count`  int NULL,
    CONSTRAINT `PK_VISITED_SPACE` PRIMARY KEY (`id`),
    CONSTRAINT `FK_member_TO_visited_space_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FK_workspace_TO_visited_space_1` FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`)
);

CREATE TABLE `visited_review`
(
    `id`               int NOT NULL,
    `visited_space_id` int NOT NULL,
    `visit_date`       date NULL,
    `start_time`       time NULL,
    `end_time`         time NULL,
    `activities`       varchar(255) NULL,
    `rating`           decimal(3, 1) NULL,
    `review_text`      text NULL,
    CONSTRAINT `PK_VISITED_REVIEW` PRIMARY KEY (`id`),
    CONSTRAINT `FK_visited_space_TO_visited_review_1` FOREIGN KEY (`visited_space_id`) REFERENCES `visited_space` (`id`)
);

CREATE TABLE `review_keyword`
(
    `id`                int NOT NULL,
    `keyword_id`        int NOT NULL,
    `visited_review_id` int NOT NULL,
    CONSTRAINT `PK_REVIEW_KEYWORD` PRIMARY KEY (`id`),
    CONSTRAINT `FK_keyword_TO_review_keyword_1` FOREIGN KEY (`keyword_id`) REFERENCES `keyword` (`id`),
    CONSTRAINT `FK_visited_review_TO_review_keyword_1` FOREIGN KEY (`visited_review_id`) REFERENCES `visited_review` (`id`)
);

CREATE TABLE `reservation`
(
    `id`                int NOT NULL,
    `member_id`         int NOT NULL,
    `workspace_id`      int NOT NULL,
    `visit_date`        date NULL,
    `visit_timeslot`    varchar(10) NULL,
    `activity`          varchar(255) NULL,
    `activity_duration` int NULL,
    CONSTRAINT `PK_RESERVATION` PRIMARY KEY (`id`),
    CONSTRAINT `FK_member_TO_reservation_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FK_workspace_TO_reservation_1` FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`)
);

CREATE TABLE `visited_review_image`
(
    `id`                int NOT NULL,
    `visited_review_id` int NOT NULL,
    `image_url`         varchar(255) NULL,
    CONSTRAINT `PK_VISITED_REVIEW_IMAGE` PRIMARY KEY (`id`),
    CONSTRAINT `FK_visited_review_TO_visited_review_image_1` FOREIGN KEY (`visited_review_id`) REFERENCES `visited_review` (`id`)
);
