package com.jpacourse.service.impl;

import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.VisitService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VisitEntity> getVisitsByPatientId(Long patientId) {
        return entityManager.createQuery(
                        "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId", VisitEntity.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }
}