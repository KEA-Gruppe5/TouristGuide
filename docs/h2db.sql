-- Create the TAG table
CREATE TABLE tag (
                     ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                     tag VARCHAR(255)
);

-- Create the CITY table
CREATE TABLE city (
                      ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                      city VARCHAR(255)
);

-- Create the TOURIST_ATTRACTION table
CREATE TABLE tourist_attraction (
                                    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(255) NOT NULL,
                                    description VARCHAR(255) NOT NULL,
                                    price DOUBLE,
                                    convertedPrice DOUBLE,
                                    cityID BIGINT,
                                    FOREIGN KEY (cityID) REFERENCES city(ID)
);

-- Create the ATTRACTIONS_TAGS linking table
CREATE TABLE attractions_tags (
                                  attractionID BIGINT,
                                  tagID BIGINT,
                                  FOREIGN KEY (attractionID) REFERENCES tourist_attraction(ID),
                                  FOREIGN KEY (tagID) REFERENCES tag(ID)
);

-- Insert cities into the CITY table
INSERT INTO city (city)
VALUES
    ('Copenhagen'),
    ('Aarhus'),
    ('Odense'),
    ('Aalborg');

-- Insert tags into the TAG table
INSERT INTO tag (tag)
VALUES
    ('Child Friendly'),
    ('Free'),
    ('Art'),
    ('Museum'),
    ('Open air');

-- Inserting Tivoli
INSERT INTO tourist_attraction (name, description, cityID, price)
VALUES ('Tivoli', 'entertainment park', (SELECT ID FROM city WHERE city = 'Copenhagen'), 500);

-- Inserting Christiansborg
INSERT INTO tourist_attraction (name, description, cityID, price)
VALUES ('Christiansborg', 'Parliament', (SELECT ID FROM city WHERE city = 'Copenhagen'), 100);

-- Inserting Nyhavn
INSERT INTO tourist_attraction (name, description, cityID, price)
VALUES ('Nyhavn', 'Main street', (SELECT ID FROM city WHERE city = 'Copenhagen'), NULL);

-- Inserting AroS
INSERT INTO tourist_attraction (name, description, cityID, price)
VALUES ('AroS', 'Art museum', (SELECT ID FROM city WHERE city = 'Aarhus'), 200);

-- Inserting tags for Tivoli
INSERT INTO attractions_tags (attractionID, tagID)
VALUES
    ((SELECT ID FROM tourist_attraction WHERE name = 'Tivoli'),
     (SELECT ID FROM tag WHERE tag = 'Child Friendly')),
    ((SELECT ID FROM tourist_attraction WHERE name = 'Tivoli'),
     (SELECT ID FROM tag WHERE tag = 'Art'));

-- Inserting tags for Christiansborg
INSERT INTO attractions_tags (attractionID, tagID)
VALUES (
           (SELECT ID FROM tourist_attraction WHERE name = 'Christiansborg'),
           (SELECT ID FROM tag WHERE tag = 'Museum')
       );

-- Inserting tags for Nyhavn
INSERT INTO attractions_tags (attractionID, tagID)
VALUES
    ((SELECT ID FROM tourist_attraction WHERE name = 'Nyhavn'),
     (SELECT ID FROM tag WHERE tag = 'Open air')),
    ((SELECT ID FROM tourist_attraction WHERE name = 'Nyhavn'),
     (SELECT ID FROM tag WHERE tag = 'Child Friendly'));

-- Inserting tags for AroS
INSERT INTO attractions_tags (attractionID, tagID)
VALUES
    ((SELECT ID FROM tourist_attraction WHERE name = 'AroS'),
     (SELECT ID FROM tag WHERE tag = 'Museum')),
    ((SELECT ID FROM tourist_attraction WHERE name = 'AroS'),
     (SELECT ID FROM tag WHERE tag = 'Art'));