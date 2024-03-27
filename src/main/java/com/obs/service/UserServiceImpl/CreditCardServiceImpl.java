package com.obs.service.UserServiceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.obs.dao.CreditCardDao;
import com.obs.domain.CreditCard;
import com.obs.service.CreditCardService;

@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	CreditCardDao creditCardDao;

	@Override
	public void saveCreditCard(CreditCard creditCard) {
		creditCard.setDate(new Date());
		creditCard = updateCardLimits(creditCard);
		creditCardDao.save(creditCard);
	}

	private CreditCard updateCardLimits(CreditCard creditCard) {
		switch (creditCard.getCardType()) {
		case "V":
			creditCard.setTotalCreditLimit(new BigDecimal(20000));
			creditCard.setAvailableCredit(new BigDecimal(20000));
			creditCard.setWithdrwalCredit(new BigDecimal(8000));
			creditCard.setLoanAvailable(new BigDecimal(20000));
			break;
		case "A":
			creditCard.setTotalCreditLimit(new BigDecimal(40000));
			creditCard.setAvailableCredit(new BigDecimal(40000));
			creditCard.setWithdrwalCredit(new BigDecimal(15000));
			creditCard.setLoanAvailable(new BigDecimal(40000));
			break;
		case "M":
			creditCard.setTotalCreditLimit(new BigDecimal(30000));
			creditCard.setAvailableCredit(new BigDecimal(30000));
			creditCard.setWithdrwalCredit(new BigDecimal(10000));
			creditCard.setLoanAvailable(new BigDecimal(30000));
			break;
		case "D":
			creditCard.setTotalCreditLimit(new BigDecimal(15000));
			creditCard.setAvailableCredit(new BigDecimal(15000));
			creditCard.setWithdrwalCredit(new BigDecimal(8000));
			creditCard.setLoanAvailable(new BigDecimal(15000));
			break;
		case "G":
			creditCard.setTotalCreditLimit(new BigDecimal(10000));
			creditCard.setAvailableCredit(new BigDecimal(10000));
			creditCard.setWithdrwalCredit(new BigDecimal(5000));
			creditCard.setLoanAvailable(new BigDecimal(10000));
			break;
		default:
			break;
		}

		return creditCard;

	}

	@Override
	public List<CreditCard> findAllCardsByUserId(Long userId) {
		return creditCardDao.findAllCardsByUserId(userId);
	}

	@Override
	public CreditCard findCCById(Long cardId) {
		return creditCardDao.findById(cardId).orElse(null);
	}

}
