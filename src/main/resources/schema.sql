create table if not exists keyword
(
    id    int auto_increment
        primary key,
    value varchar(40) not null
);

create table if not exists member
(
    id            int auto_increment
        primary key,
    email         varchar(40)  not null,
    password      varchar(255) not null,
    nickname      varchar(40)  not null,
    birthdate     date         null,
    position      varchar(20)  null,
    profile_image mediumtext   null,
    role          varchar(20)  not null,
    constraint idx_email
        unique (email),
    constraint idx_nickname
        unique (nickname)
);

create table if not exists workspace
(
    id                 int auto_increment
        primary key,
    name               varchar(40)     not null,
    category           varchar(20)     not null,
    latitude           decimal(20, 17) not null,
    longitude          decimal(20, 17) not null,
    road_address       varchar(100)    not null,
    rating             decimal(3, 1)   null,
    number_of_visitors int             null,
    poi_id             varchar(100)    not null,
    constraint idx_poi_id
        unique (poi_id)
);

create table if not exists reservation
(
    id                int auto_increment
        primary key,
    member_id         int          not null,
    workspace_id      int          not null,
    visit_start_date  date         not null,
    visit_timeslot    varchar(10)  null,
    activity          varchar(255) null,
    activity_duration int          null,
    constraint FK_member_TO_reservation_1
        foreign key (member_id) references member (id),
    constraint FK_workspace_TO_reservation_1
        foreign key (workspace_id) references workspace (id)
);

create table if not exists visited_review
(
    id             int auto_increment
        primary key,
    member_id      int           not null,
    workspace_id   int           not null,
    start_datetime datetime      not null,
    end_datetime   datetime      not null,
    activity       varchar(255)  null,
    rating         decimal(3, 1) null,
    review_text    text          null,
    constraint FK_member_TO_visited_review_1
        foreign key (member_id) references member (id),
    constraint FK_workspace_TO_visited_review_1
        foreign key (workspace_id) references workspace (id)
);

create table if not exists review_keyword
(
    id                int auto_increment
        primary key,
    keyword_id        int not null,
    visited_review_id int not null,
    constraint FK_keyword_TO_review_keyword_1
        foreign key (keyword_id) references keyword (id),
    constraint FK_visited_review_TO_review_keyword_1
        foreign key (visited_review_id) references visited_review (id)
);

create table if not exists visited_review_image
(
    id                int auto_increment
        primary key,
    visited_review_id int        not null,
    image             mediumtext not null,
    constraint FK_visited_review_TO_visited_review_image_1
        foreign key (visited_review_id) references visited_review (id)
);

create table if not exists visited_space
(
    id                    int auto_increment
        primary key,
    member_id             int      not null,
    workspace_id          int      not null,
    visit_count           int      not null,
    recent_visit_datetime datetime not null,
    constraint FK_member_TO_visited_space_1
        foreign key (member_id) references member (id),
    constraint FK_workspace_TO_visited_space_1
        foreign key (workspace_id) references workspace (id)
);

create table if not exists workspace_keyword
(
    id           int auto_increment
        primary key,
    workspace_id int not null,
    keyword_id   int not null,
    frequency    int not null,
    constraint FK_keyword_TO_workspace_keyword_1
        foreign key (keyword_id) references keyword (id),
    constraint FK_workspace_TO_workspace_keyword_1
        foreign key (workspace_id) references workspace (id)
);

