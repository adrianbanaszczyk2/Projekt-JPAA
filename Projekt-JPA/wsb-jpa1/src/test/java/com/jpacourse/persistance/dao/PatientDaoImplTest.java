package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.Specialization;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PatientDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PatientDao patientDao;

    @BeforeEach
    public void cleanDatabase() {
        // Disable foreign key constraints temporarily
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        // Delete from all child tables first (in proper order)
        entityManager.createNativeQuery("DELETE FROM visit").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM patient_to_address").executeUpdate();

        // Then delete from parent tables
        entityManager.createNativeQuery("DELETE FROM patient").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM address").executeUpdate();

        // Reset sequences
        entityManager.createNativeQuery("ALTER TABLE patient ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE address ALTER COLUMN id RESTART WITH 1").executeUpdate();

        // Re-enable foreign key constraints
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    @Test
    public void shouldFindPatientsByLastName() {
        // given
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Adrian");
        patient.setLastName("Krzysztof");
        patient.setTelephoneNumber("01234567");
        patient.setEmail("Adrian@Krzysztof.com");
        patient.setPatientNumber("0091");
        patient.setDateOfBirth(LocalDate.of(1964, 4, 17));
        patient.setIsalive(false);

        entityManager.persist(patient);
        entityManager.flush();
        entityManager.clear();

        // when
        List<PatientEntity> result = patientDao.findByLastName("Krzysztof");

        // then
        assertFalse(result.isEmpty());
        assertEquals("Krzysztof", result.get(0).getLastName());
    }
    @Test
    @Transactional
    public void shouldFindPatientsWithMoreThanXVisits() {

        Long patientId = 1L;
        Long actualVisitCount = entityManager.createQuery(
                        "SELECT COUNT(v) FROM VisitEntity v WHERE v.patient.id = :patientId", Long.class)
                .setParameter("patientId", patientId)
                .getSingleResult();

        System.out.println("Liczba wizyt dla pacjenta ID=" + patientId + ": " + actualVisitCount);


        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
        System.out.println("Liczba wizyt (przez mapowanie): " +
                (patient != null ? patient.getVisitEntities().size() : "null"));


        List<PatientEntity> alternativeResult = entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE SIZE(p.visitEntities) > 2", PatientEntity.class)
                .getResultList();
        System.out.println("Wynik alternatywnego zapytania: " + alternativeResult.size());


        List<PatientEntity> result = patientDao.findPatientsWithMoreThanXVisits(2);
        System.out.println("Wynik głównego zapytania: " + result.size());


        assertEquals(1, result.size(), "Powinien być 1 pacjent z więcej niż 2 wizytami");
    }
    @Test
    @Transactional
    public void shouldFindPatientsBornBeforeDate() {
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Adrian");
        patient.setLastName("Krzysztof");
        patient.setTelephoneNumber("01234567");
        patient.setEmail("Adrian@Krzysztof.com");
        patient.setPatientNumber("0091");
        patient.setDateOfBirth(LocalDate.of(1964, 4, 17));
        patient.setIsalive(false);
        entityManager.persist(patient);

        LocalDate date = LocalDate.of(2000, 1, 1);
        List<PatientEntity> result = patientDao.findPatientsBornBefore(date);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(p -> p.getLastName().equals("Krzysztof")));
    }
    @Test
    @Transactional
    @Rollback
    public void shouldLoadPatientWithVisits() {

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Adrian");
        doctor.setLastName("Kacper");
        doctor.setDoctorNumber("AK0123" + System.currentTimeMillis());
        doctor.setEmail("adrian" + System.currentTimeMillis() + "@gmail.com");
        doctor.setTelephoneNumber("012345678");
        doctor.setSpecialization(Specialization.SURGEON);
        entityManager.persist(doctor);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Michal");
        patient.setLastName("Jakub");
        patient.setTelephoneNumber("123456789");
        patient.setEmail("jakub" + System.currentTimeMillis() + "@gmail.com");
        patient.setPatientNumber("MJ958" + System.currentTimeMillis());
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setIsalive(true);
        entityManager.persist(patient);

        patientDao.addVisitToPatient(patient.getId(), doctor.getId(), LocalDateTime.now(), "Wizyta pierwsza");
        patientDao.addVisitToPatient(patient.getId(), doctor.getId(), LocalDateTime.now().plusHours(1), "Wizyta druga");

        entityManager.flush();
        entityManager.clear();

        PatientEntity loaded = entityManager.find(PatientEntity.class, patient.getId());
        System.out.println("Ilosc wizyt pacjenta: " + loaded.getVisitEntities().size());
        assertEquals(2, loaded.getVisitEntities().size());
    }
}