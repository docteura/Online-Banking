package com.obs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.entity.SavingsAccount;

@Repository
public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Long> {

	SavingsAccount findByAccountNumber(String accountNumber);
}
