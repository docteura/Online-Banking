package com.obs.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.domain.Beneficiary;

@Repository
public interface RecipientDao extends CrudRepository<Beneficiary, Long> {
    List<Beneficiary> findAll();

    Beneficiary findByName(String recipientName);

    void deleteByName(String recipientName);
}
