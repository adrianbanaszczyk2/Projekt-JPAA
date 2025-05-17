package com.jpacourse.service;

import com.jpacourse.dto.AddressTO;

public interface AddressService
{
    AddressTO findById(final Long id);
}
