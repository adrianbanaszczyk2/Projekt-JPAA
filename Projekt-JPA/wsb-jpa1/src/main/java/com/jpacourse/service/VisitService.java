package com.jpacourse.service;

import com.jpacourse.persistance.entity.VisitEntity;

import java.util.List;

public interface VisitService {
    List<VisitEntity> getVisitsByPatientId(Long patientId);
}