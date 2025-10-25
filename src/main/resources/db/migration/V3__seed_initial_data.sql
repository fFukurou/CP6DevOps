-- V3__seed_initial_data.sql

-- Populate the DEITY_TYPE table
INSERT INTO deity_type (id_type, type_name, origin, description, lifespan, power_source)
VALUES (1, 'GOD', 'Offspring of Titans or other Gods', 'Major divine beings residing on Olympus or ruling major domains.', 'Immortal', 'Divine Authority/Domains');

INSERT INTO deity_type (id_type, type_name, origin, description, lifespan, power_source)
VALUES (2, 'TITAN', 'Born from Primordials (Gaia and Uranus)', 'Elder generation of deities preceding the Olympian Gods.', 'Immortal', 'Primordial/Elemental Power');

INSERT INTO deity_type (id_type, type_name, origin, description, lifespan, power_source)
VALUES (3, 'DEMI_GOD', 'Offspring of a God and a Mortal', 'Beings with divine heritage and often heroic potential, but typically mortal.', 'Mortal (usually)', 'Inherited Divine Traits/Blessings');

INSERT INTO deity_type (id_type, type_name, origin, description, lifespan, power_source)
VALUES (4, 'MONSTER', 'Born from Primordials or Cursed Beings', 'Often chaotic or dangerous creatures, sometimes with divine parentage.', 'Variable (often slain)', 'Primordial Chaos/Divine Curse');

INSERT INTO deity_type (id_type, type_name, origin, description, lifespan, power_source)
VALUES (5, 'PRIMORDIAL', 'Emerged from Chaos at the beginning', 'The first beings representing fundamental concepts like Earth, Sky, Night.', 'Immortal (Embodiments)', 'Fundamental Forces/Existence');

INSERT INTO deity_type (id_type, type_name, origin, description, lifespan, power_source)
VALUES (6, 'HUMAN', 'Created by Gods or Titans (Prometheus)', 'Mortals living in the world, subject to the whims of the gods.', 'Mortal', 'None (or temporary divine favor)');


-- Olympians (GOD, id_type = 1)
INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Zeus', 'Sky, Thunder, King of the Gods', 'Jupiter', 'Ruler of Olympus, wields thunderbolts.', 1);

INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Hera', 'Marriage, Women, Queen of the Gods', 'Juno', 'Wife and sister of Zeus.', 1);

INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Poseidon', 'Sea, Earthquakes, Horses', 'Neptune', 'Brother of Zeus, ruler of the seas.', 1);

INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Hades', 'Underworld, The Dead, Wealth', 'Pluto', 'Brother of Zeus, ruler of the underworld.', 1);

INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Hestia', 'Hearth, Home, Family', 'Vesta', 'Eldest sister of Zeus, goddess of the hearth.', 1);

INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Apollo', 'Music, Arts, Knowledge, Prophecy, Sun', 'Apollo', 'Son of Zeus and Leto, twin of Artemis.', 1);

-- Titan (TITAN, id_type = 2)
INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Cronus', 'Time, Harvest', 'Saturn', 'Leader of the Titans, father of Zeus.', 2);

-- Chthonic/Other (GOD, id_type = 1)
INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Persephone', 'Underworld Queen, Springtime', 'Proserpina', 'Daughter of Demeter, wife of Hades.', 1);

INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Zagreus', 'Orphic Mysteries, Rebirth', NULL, 'Son of Hades and Persephone (or Zeus in some myths).', 1);

-- Monster/Primordial (MONSTER, id_type = 4)
INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Typhon', 'Monsters, Storms', 'Typhoeus', 'Deadliest monster, challenged Zeus.', 4);

-- Demi-God (DEMI_GOD, id_type = 3)
INSERT INTO deity (name, domain, roman_name, description, id_type)
VALUES ('Heracles', 'Strength, Heroes', 'Hercules', 'Son of Zeus and Alcmene, famous for his twelve labors.', 3);


COMMIT;