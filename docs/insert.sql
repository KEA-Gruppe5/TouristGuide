INSERT INTO CITY (id, city)
VALUES
    (1, 'Copenhagen'),
    (2, 'Aarhus'),
    (3, 'Odense'),
    (4, 'Aalborg');

INSERT INTO TAG (id, tag)
VALUES
    (1, 'Child Friendly'),
    (2, 'Free'),
    (3, 'Art'),
    (4, 'Museum'),
    (5, 'Open air');


-- Inserting Tivoli
INSERT INTO TOURIST_ATTRACTION (name, description, cityid, price)
VALUES ('Tivoli', 'entertainment park', (SELECT id FROM CITY WHERE city = 'COPENHAGEN'), 500);

-- Inserting Christiansborg
INSERT INTO TOURIST_ATTRACTION (name, description, cityid, price)
VALUES ('Christiansborg', 'Parliament', (SELECT id FROM CITY WHERE city = 'COPENHAGEN'), 100);

-- Inserting Nyhavn
INSERT INTO TOURIST_ATTRACTION (name, description, cityid, price)
VALUES ('Nyhavn', 'Main street', (SELECT id FROM CITY WHERE city = 'COPENHAGEN'), NULL);

-- Inserting AroS
INSERT INTO TOURIST_ATTRACTION (name, description, cityid, price)
VALUES ('AroS', 'Art museum', (SELECT id FROM CITY WHERE city = 'AARHUS'), 200);


-- Get the IDs of the attractions and tags
-- Inserting tags for Tivoli
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES (
           (SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Tivoli'),
           (SELECT id FROM TAG WHERE tag = 'CHILD_FRIENDLY')
       );
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES (
           (SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Tivoli'),
           (SELECT id FROM TAG WHERE tag = 'ART')
       );

-- Inserting tags for Christiansborg
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES (
           (SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Christiansborg'),
           (SELECT id FROM TAG WHERE tag = 'MUSEUM')
       );

-- Inserting tags for Nyhavn
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES (
           (SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Nyhavn'),
           (SELECT id FROM TAG WHERE tag = 'OPEN_AIR')
       );
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES (
           (SELECT id FROM TOURIST_ATTRACTION WHERE name = 'Nyhavn'),
           (SELECT id FROM TAG WHERE tag = 'CHILD_FRIENDLY')
       );

-- Inserting tags for AroS
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES (
           (SELECT id FROM TOURIST_ATTRACTION WHERE name = 'AroS'),
           (SELECT id FROM TAG WHERE tag = 'MUSEUM')
       );
INSERT INTO ATTRACTIONS_TAGS (attractionid, tagid)
VALUES (
           (SELECT id FROM TOURIST_ATTRACTION WHERE name = 'AroS'),
           (SELECT id FROM TAG WHERE tag = 'ART')
       );