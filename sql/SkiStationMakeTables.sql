CREATE TABLE `CLIENT` (
  `ID` INTEGER PRIMARY KEY,
  `FIRST_NAME` varchar(30),
  `SECOND_NAME` varchar(30),
  `SURNAME` varchar(30),
  `DATE_OF_BIRTH` date,
  `PESEL` varchar(15),
  `PERSONAL_ID_NUMBER` varchar(15),
  `DATE_ENTERED` date,
  `PHONE` varchar(15),
  `E_MAIL` varchar(30)
);

CREATE TABLE `SKIPASS_TYPE` (
  `ID` INTEGER PRIMARY KEY,
  `DURATION` int,
  `DISCOUNT_TYPE` varchar(30),
  `PRICE` double
);

CREATE TABLE `SKIPASS` (
  `ID` INTEGER PRIMARY KEY,
  `CLIENT_ID` INTEGER,
  `SKIPASS_TYPE_ID` INTEGER,
  `RENTED` boolean,
  `ACTIVE` boolean,
  `DATE_FROM` date,
  `DATE_TO` date,
  CONSTRAINT fk_sp_client
      FOREIGN KEY (CLIENT_ID)
      REFERENCES CLIENT(ID)
  CONSTRAINT fk_sp_skipass_type
      FOREIGN KEY (SKIPASS_TYPE_ID)
      REFERENCES SKIPASS_TYPE(ID)
);

CREATE TABLE `EQUIPMENT` (
  `ID` INTEGER PRIMARY KEY,
  `NAME` varchar(50),
  `SERIAL_NUMBER` varchar(30),
  `TYPE` varchar(30),
  `RENT_PRICE` double,
  `CONDITION` varchar(30)
);

CREATE TABLE `EQUIPMENT_RENT` (
  `ID` INTEGER PRIMARY KEY,
  `CLIENT_ID` INTEGER,
  `EQUIPMENT_ID` INTEGER,
  `RENT_DATE` date,
  `RETURN_DATE` date,
  `RENT_TYPE` varchar(30),
  CONSTRAINT fk_rent_client
      FOREIGN KEY (CLIENT_ID)
      REFERENCES CLIENT(ID)
  CONSTRAINT fk_rent_eq
      FOREIGN KEY (EQUIPMENT_ID)
      REFERENCES EQUIPMENT(ID)
);

CREATE TABLE `EMPLOYEE` (
  `ID` INTEGER PRIMARY KEY,
  `FIRST_NAME` varchar(30),
  `SECOND_NAME` varchar(30),
  `SURNAME` varchar(30),
  `DATE_OF_BIRTH` date,
  `PESEL` varchar(15),
  `PERSONAL_ID_NUMBER` varchar(15),
  `PHONE` varchar(15),
  `E_MAIL` varchar(30),
  `BUILDING_NR` varchar(10),
  `APT_NR` varchar(10),
  `STREET_NR` varchar(10),
  `STREET_NAME` varchar(30),
  `CITY` varchar(30),
  `VOIVODESHIP` varchar(30),
  `ZIPCODE` varchar(10),
  `LOGIN` varchar(30) UNIQUE,
  `PASSWD` varchar(30),
  `ACCOUNT_NR` varchar(30),
  `BANK_NAME` varchar(50)
);