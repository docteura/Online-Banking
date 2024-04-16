package com.obs.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obs.entity.Beneficiary;
import com.obs.entity.PrimaryAccount;
import com.obs.entity.PrimaryTransaction;
import com.obs.entity.SavingsAccount;
import com.obs.entity.SavingsTransaction;
import com.obs.entity.User;
import com.obs.repository.PrimaryAccountRepository;
import com.obs.repository.PrimaryTransactionRepository;
import com.obs.repository.RecipientRepository;
import com.obs.repository.SavingsAccountRepository;
import com.obs.repository.SavingsTransactionRepository;
import com.obs.service.TransactionService;
import com.obs.service.UserService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private UserService userService;

	@Autowired
	private PrimaryTransactionRepository primaryTransactionDao;

	@Autowired
	private SavingsTransactionRepository savingsTransactionDao;

	@Autowired
	private PrimaryAccountRepository primaryAccountDao;

	@Autowired
	private SavingsAccountRepository savingsAccountDao;

	@Autowired
	private RecipientRepository recipientDao;

	public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList().stream()
				.sorted((p1, p2) -> p2.getDate().compareTo(p2.getDate())).collect(Collectors.toList());

		return primaryTransactionList;
	}

	public List<SavingsTransaction> findSavingsTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();

		return savingsTransactionList;
	}

	public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}

	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}

	public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}

	public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}

	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
		if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			savingsAccountDao.save(savingsAccount);

			Date date = new Date();

			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
					"Between account transfer from " + transferFrom + " to " + transferTo, "Account", "Finished",
					Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount, "DR");
			SavingsTransaction savingsTransaction = new SavingsTransaction(date,
					"Between account transfer from " + transferFrom + " to " + transferTo, "Transfer", "Finished",
					Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount, "CR");
			primaryTransactionDao.save(primaryTransaction);
			savingsTransactionDao.save(savingsTransaction);
		} else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			savingsAccountDao.save(savingsAccount);

			Date date = new Date();

			SavingsTransaction savingsTransaction = new SavingsTransaction(date,
					"Between account transfer from " + transferFrom + " to " + transferTo, "Transfer", "Finished",
					Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount, "DR");
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
					"Between account transfer from " + transferFrom + " to " + transferTo, "Account", "Finished",
					Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount, "CR");
			savingsTransactionDao.save(savingsTransaction);
			primaryTransactionDao.save(primaryTransaction);
		} else {
			throw new Exception("Invalid Transfer");
		}
	}

	public List<Beneficiary> findBeneficiaryList(Principal principal) {
		String username = principal.getName();
		List<Beneficiary> beneficiaries = recipientDao.findAll().stream() // convert list to stream
				.filter(beneficiary -> username.equals(beneficiary.getUser().getUsername())) // filters the line, equals to
																							// username
				.collect(Collectors.toList());

		return beneficiaries;
	}

	public Beneficiary saveBeneficiary(Beneficiary beneficiary) {
		return recipientDao.save(beneficiary);
	}

	public Beneficiary findBeneficiaryByName(String beneficiaryName) {
		return recipientDao.findByName(beneficiaryName);
	}

	public void deleteBeneficiaryByName(String beneficiaryName) {
		recipientDao.deleteByName(beneficiaryName);
	}

	public void toSomeoneElseTransfer(Beneficiary beneficiary, String accountType, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {
		if (accountType.equalsIgnoreCase("Primary")) {
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);

			Date date = new Date();

			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
					"Transfer to recipient " + beneficiary.getName(), "Transfer", "Finished", Double.parseDouble(amount),
					primaryAccount.getAccountBalance(), primaryAccount, "DR");
			primaryTransactionDao.save(primaryTransaction);
		} else if (accountType.equalsIgnoreCase("Savings")) {
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);

			Date date = new Date();

			SavingsTransaction savingsTransaction = new SavingsTransaction(date,
					"Transfer to recipient " + beneficiary.getName(), "Transfer", "Finished", Double.parseDouble(amount),
					savingsAccount.getAccountBalance(), savingsAccount, "DR");
			savingsTransactionDao.save(savingsTransaction);
		}
	}

	@Override
	public Beneficiary findBeneficiaryById(Long id) {
		return recipientDao.findById(id).orElse(null);
	}

	@Override
	public void deleteBeneficiaryById(Long id) {
		recipientDao.deleteById(id);
	}
}
