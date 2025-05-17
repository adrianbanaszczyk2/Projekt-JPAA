insert into address (id, address_line1, address_line2, city, postal_code)
values (1, 'klonowa', '5A', 'krakow', '30-001'),
       (2, 'bukowa', '17B', 'krakow', '30-001'),
       (3, 'dębowa', '22C', 'krakow', '30-001'),
       (4, 'sosnowa', '8D', 'krakow', '30-001');

-- Pacjenci
insert into patient (date_of_birth, id, email, first_name, last_name, patient_number, telephone_number, isalive)
values ('1990-03-15', 1, 'anna.kowalska@example.com', 'Anna', 'Kowalska', '100200', '500600700', 'true'),
       ('1985-07-22', 2, 'marek.nowak@example.com', 'Marek', 'Nowak', '200300', '600700800', 'false');

-- Lekarze
insert into doctor (id, doctor_number, email, first_name, last_name, telephone_number, specialization)
values (10, 'D333', 'alicja.chirurg@data.sql', 'Alicja', 'Rak', '700800900', 'SURGEON'),
       (11, 'D444', 'jan.derm@data.sql', 'Jan', 'Kowalski', '800900000', 'DERMATOLOGIST');

-- Wizyty
insert into visit (doctor_id, id, patient_id, time, description)
values(10, 1, 1, '2025-03-21 09:00:00', 'kontrola po zabiegu'),
      (10, 2, 2, '2025-03-21 10:00:00', 'badanie ogólne'),
      (10, 3, 1, '2025-03-21 11:00:00', 'drobną operacja'),
      (11, 4, 1, '2025-03-21 12:00:00', 'ocena skóry'),
      (11, 5, 2, '2025-03-21 13:00:00', 'konsultacja dermatologiczna');

-- Zabiegi
insert into medical_treatment (id, visit_id, description, type)
values (1, 1, 'sprawdzenie szwów', 'USG'),
       (2, 2, 'badanie jamy brzusznej', 'USG'),
       (3, 3, 'zdjęcie RTG kolana', 'RTG'),
       (4, 4, 'kontrola znamienia', 'USG'),
       (5, 5, 'badanie skóry głowy', 'USG');

-- Powiązania pacjentów z adresami
insert into patient_to_address (address_id, patient_id)
values (2, 1),
       (3, 2);

-- Powiązania lekarzy z adresami — zaktualizowane doctor_id
insert into doctor_to_address (address_id, doctor_id)
values (2, 10),
       (4, 11);