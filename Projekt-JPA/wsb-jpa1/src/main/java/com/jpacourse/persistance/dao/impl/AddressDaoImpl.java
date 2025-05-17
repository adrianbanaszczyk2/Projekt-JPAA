package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.AddressDao;
import com.jpacourse.persistance.entity.AddressEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AddressDaoImpl extends AbstractDao<AddressEntity, Long> implements AddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AddressDaoImpl() {
        super(AddressEntity.class);
    }

}