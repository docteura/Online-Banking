package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.entity.PrimaryTransaction;

@Repository
public interface PrimaryTransactionRepository extends CrudRepository<PrimaryTransaction, Long> {

	List<PrimaryTransaction> findAll();
}
