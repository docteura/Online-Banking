package com.obs.service;

import java.security.Principal;
import java.util.List;

import com.obs.entity.Beneficiary;
import com.obs.entity.PrimaryAccount;
import com.obs.entity.PrimaryTransaction;
import com.obs.entity.SavingsAccount;
import com.obs.entity.SavingsTransaction;

public interface TransactionService {
	List<PrimaryTransaction> findPrimaryTransactionList(String username);

	List<SavingsTransaction> findSavingsTransactionList(String username);

	void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);

	void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

	void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);

	void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);

	void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount,
			SavingsAccount savingsAccount) throws Exception;

	List<Beneficiary> findRecipientList(Principal principal);

	Beneficiary saveRecipient(Beneficiary recipient);

	Beneficiary findRecipientByName(String recipientName);

	void deleteRecipientByName(String recipientName);

	void toSomeoneElseTransfer(Beneficiary recipient, String accountType, String amount, PrimaryAccount primaryAccount,
			SavingsAccount savingsAccount);

	Beneficiary findRecipientById(Long id);

	void deleteRecipientById(Long id);
}
