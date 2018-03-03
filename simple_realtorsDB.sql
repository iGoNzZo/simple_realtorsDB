DROP DATABASE IF EXISTS oneProjectDB;
CREATE DATABASE oneProjectDB;

USE oneProjectDB;

DROP TABLE IF EXISTS USRealtors;
CREATE TABLE USRealtors(
	f_name VARCHAR(20),
	l_name VARCHAR(20),
	comp_name VARCHAR(45),
	street VARCHAR(30),
	city VARCHAR(20),
	county VARCHAR(30),
	c_state VARCHAR(3),
	zip INTEGER,
	phone VARCHAR(13),
	salary INTEGER,
	PRIMARY KEY(phone)
);

INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Steven", "Gonzalez", "Google", "170 Miller Rd", "Hollister", "San Benito", "CA", "95111", "831-801-8637", "110");
INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Carly", "Harrell", "Yahoo", "250 San Lorenzo", "Mountain View", "Santa Clara", "CA", "95111", "987-654-3211", "115");
INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Kloe", "Fidone", "Apple", "1 Memorial Dr", "Santa Clara", "Santa Clara", "CA", "95111", "987-321-6544", "100");
INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Cody", "Gosponeditch", "Samsung", "1 Lincon St", "San Jose", "Santa Clara", "CA", "95111", "654-831-9877", "70");
INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Danny","Roye", "Sony", "2 Chappel Ct", "San Francisco", "San Francisco", "CA", "95111", "831-207-0990", "56");
INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Rob", "Baily", "Lenovo", "3 Azul Ct", "Oakland", "Oakland County", "CA", "95111", "831-099-0207", "66");
INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Eddie", "Orozco", "Microsoft", "2 Main St", "Palo Alto", "Santa Clara", "CA", "95111", "408-842-7282", "90");
INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Nick", "Elmore", "McAfee", "77 Santa Clara St", "Los Angeles", "Los Angeles", "CA", "95111", "842-408-0207", "50");
INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Liz", "Barrientos", "Norton", "22 San Fernando St", "Sacramento", "Santa Clara", "CA", "95111", "831-329-0987", "87");
INSERT INTO USRealtors (f_name, l_name, comp_name, street, city, county, c_state, zip, phone, salary) VALUES("Maria", "Guiterez", "PayPal", "33 4th Street", "San Jose", "Santa Clara", "CA", "95111", "831-879-4455", "78");


LOAD DATA LOCAL INFILE 'C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\data\\company.txt' INTO TABLE USRealtors; 

