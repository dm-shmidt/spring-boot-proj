insert into "customer" ("birth_date", "email", "firstname", "lastname", "middlename", "phone_number", "id")
values ('2000-01-01', 'j.man@good.com', 'John', 'Manning', '', '8-555-555-555', 1);

insert into "customer" ("birth_date", "email", "firstname", "lastname", "middlename", "phone_number", "id")
values ('1999-11-21', 'candy.lollipop@good.com', 'Candy', 'Lollipop', '', '2-098-234-235', 2);

insert into "customer" ("birth_date", "email", "firstname", "lastname", "middlename", "phone_number", "id")
values ('1950-02-12', 'harry.k@good.com', 'Gregor', 'Kaan', 'van', '7-939-009-736', 3);

insert into "customer" ("birth_date", "email", "firstname", "lastname", "middlename", "phone_number", "id")
values ('1937-08-20', 'cindy.mcg@good.com', 'Cindy', 'MacGregor', 'Mindy', '3-738-384-049', 4);

insert into "customer" ("birth_date", "email", "firstname", "lastname", "middlename", "phone_number", "id")
values ('1978-09-30', 'upsal.nipman@good.com', 'Upsaal', 'Nipman', '', '0-234-847-489', 5);

insert into "customer" ("birth_date", "email", "firstname", "lastname", "middlename", "phone_number", "id")
values ('1998-04-07', 'korky.lazer@good.com', 'Kork', 'Lazerwood', 'fon', '9-234-456-985', 6);

insert into "customer" ("birth_date", "email", "firstname", "lastname", "middlename", "phone_number", "id")
values ('2010-03-09', 'j.ingerman@good.com', 'James', 'Ingerman', '', '8-755-492-472', 7);

insert into "quotation" ("beginning_of_insurance", "date_of_signing_mortgage", "insured_amount", "id", "customer_id")
values ('2010-01-20', '2010-01-11', 1000, 1, 1);

insert into "subscription" ("start_date", "valid_until", "id", "quotation_id")
values ('2023-01-21', '2025-01-11', 1, 1);
