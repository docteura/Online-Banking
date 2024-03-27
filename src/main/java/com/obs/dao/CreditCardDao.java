package com.obs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.obs.domain.CreditCard;

@Repository
public interface CreditCardDao extends CrudRepository<CreditCard,Long>{

	 @Query(value = "select * from credit_card where user_id =?1", nativeQuery = true)
	List<CreditCard> findAllCardsByUserId(Long userId);

}
