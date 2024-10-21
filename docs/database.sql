CREATE DATABASE touristGuide;
use touristGuide;

CREATE table tag
(
    ID  int primary key auto_increment not null,
    tag varchar (255)
);

CREATE TABLE city
(
    ID   int primary key auto_increment not null,
    city varchar (255)
);

CREATE table tourist_attraction
(
    ID             int primary key auto_increment not null,
    name           varchar(255)                   not null,
    description    varchar(255)                   not null,
    price          double,
    convertedPrice double,
    cityID int,
    foreign key (cityID) REFERENCES city(ID)
);
CREATE TABLE attractions_tags(
                                 attractionID int,
                                 tagID int,
                                 foreign key (attractionID) REFERENCES tourist_attraction(ID),
                                 foreign key (tagID) REFERENCES  tag(ID)
);
