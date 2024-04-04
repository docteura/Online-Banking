package com.obs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.entity.PrimaryAccount;

@Repository
public interface PrimaryAccountRepository extends CrudRepository<PrimaryAccount, Long> {

	PrimaryAccount findByAccountNumber(String accountNumber);
}
