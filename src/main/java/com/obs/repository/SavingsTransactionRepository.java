package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.entity.SavingsTransaction;

@Repository
public interface SavingsTransactionRepository extends CrudRepository<SavingsTransaction, Long> {

	List<SavingsTransaction> findAll();
}
