package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.entity.Beneficiary;

@Repository
public interface RecipientRepository extends CrudRepository<Beneficiary, Long> {
	List<Beneficiary> findAll();

	Beneficiary findByName(String recipientName);

	void deleteByName(String recipientName);
}
