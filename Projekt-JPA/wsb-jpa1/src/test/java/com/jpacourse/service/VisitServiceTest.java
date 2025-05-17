package com.jpacourse.service;

import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Test
    public void shouldReturnVisitsForPatientById() {
        List<VisitEntity> visits = visitService.getVisitsByPatientId(1L);
        assertNotNull(visits);
        assertFalse(visits.isEmpty());
        for (VisitEntity v : visits) {
            assertEquals(1L, v.getPatient().getId());
        }
    }
}