package com.obs.service;

import java.security.Principal;

import com.obs.entity.PrimaryAccount;
import com.obs.entity.SavingsAccount;

public interface AccountService {
	PrimaryAccount createPrimaryAccount();

	SavingsAccount createSavingsAccount();

	void deposit(String accountType, double amount, Principal principal);

	void withdraw(String accountType, double amount, Principal principal);

}
