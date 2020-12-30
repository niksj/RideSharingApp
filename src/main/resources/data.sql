DROP TABLE IF EXISTS billionaires;

CREATE TABLE billionaires (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  career VARCHAR(250) DEFAULT NULL
);

INSERT INTO billionaires (first_name, last_name, career) VALUES
  ('Aliko', 'Dangote', 'Billionaire Industrialist'),
  ('Bill', 'Gates', 'Billionaire Tech Entrepreneur'),
  ('Folrunsho', 'Alakija', 'Billionaire Oil Magnate');

CREATE TABLE location(
    x INT NOT NULL,
    y INT NOT NULL
);

CREATE TABLE driver(
 id VARCHAR(250) AUTO_INCREMENT  PRIMARY KEY,
 driverName VARCHAR(250) NOT NULL,
    isAvailable BIT NOT NULL,

    currentLocation VARCHAR(250) NOT NULL,
    vehicle VARCHAR(250) NOT NULL,

    currentride VARCHAR(250) NOT NULL,
    rating DOUBLE
);

ALTER TABLE driver ADD CONSTRAINT temperature_band_txt_f01 FOREIGN KEY(currentLocation)
REFERENCES temperature_band (temperature_band_code);