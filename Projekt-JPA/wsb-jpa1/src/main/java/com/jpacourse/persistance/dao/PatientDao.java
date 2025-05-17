package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long>
{
    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime time, String description);
    List<PatientEntity> findByLastName(String lastName);
    List<VisitEntity> findVisitsByPatientId(Long patientId);
    List<PatientEntity> findPatientsWithMoreThanXVisits(long visitCount);
    List<PatientEntity> findPatientsBornBefore(LocalDate date);
}
