package com.obs.service;

import java.util.List;

import com.obs.domain.CreditCard;

public interface CreditCardService {

	void saveCreditCard(CreditCard creditCard);

	List<CreditCard> findAllCardsByUserId(Long userId);

	CreditCard findCCById(Long cardId);

}
