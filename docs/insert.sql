use touristguide;

-- Insert cities into the CITY table
INSERT INTO CITY (id, city)
VALUES
    (1, 'Copenhagen'),
    (2, 'Aarhus'),
    (3, 'Odense'),
    (4, 'Aalborg');

-- Insert tags into the TAG table
INSERT INTO TAG (id, tag)
VALUES
    (1, 'Child Friendly'),
    (2, 'Free'),
    (3, 'Art'),
    (4, 'Museum'),
    (5, 'Open air');

-- Inserting Tivoli
INSERT INTO TOURIST_ATTRACTION (name, description, cityid, price)
VALUES ('Tivoli', 'entertainment park', (SELECT id FROM CITY WHERE city = 'Copenhagen'), 500);

-- Inserting Christiansborg
INSERT INTO TOURIST_ATTRACTION (name, description, cityid, price)
VALUES ('Christiansborg', 'Parliament', (SELECT id FROM CITY WHERE city = 'Copenhagen'), 100);

-- Inserting Nyhavn
INSERT INTO TOURIST_ATTRACTION (name, description, cityid, price)
VALUES ('Nyhavn', 'Main street', (SELECT id FROM CITY WHERE city = 'Copenhagen'), NULL);

-- Inserting AroS
INSERT INTO TOURIST_ATTRACTION (name, description, cityid, price)
VALUES ('AroS', 'Art museum', (SELECT id FROM CITY WHERE city = 'Aarhus'), 200);

-- Inserting tags for Tivoli
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES
    ((SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Tivoli'),
     (SELECT id FROM TAG WHERE tag = 'Child Friendly')),
    ((SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Tivoli'),
     (SELECT id FROM TAG WHERE tag = 'Art'));

-- Inserting tags for Christiansborg
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES (
           (SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Christiansborg'),
           (SELECT id FROM TAG WHERE tag = 'Museum')
       );

-- Inserting tags for Nyhavn
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES
    ((SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Nyhavn'),
     (SELECT id FROM TAG WHERE tag = 'Open air')),
    ((SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Nyhavn'),
     (SELECT id FROM TAG WHERE tag = 'Child Friendly'));

-- Inserting tags for AroS
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES
    ((SELECT id FROM TOURIST_ATTRACTION WHERE name = 'AroS'),
     (SELECT id FROM TAG WHERE tag = 'Museum')),
    ((SELECT id FROM TOURIST_ATTRACTION WHERE name = 'AroS'),
     (SELECT id FROM TAG WHERE tag = 'Art'));