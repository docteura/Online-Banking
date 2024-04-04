package com.obs.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.obs.entity.PrimaryTransaction;
import com.obs.entity.SavingsTransaction;
import com.obs.entity.User;
import com.obs.service.TransactionService;
import com.obs.service.UserService;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping(value = "/user/all")
	public List<User> userList() {
		return userService.findUserList();
	}

	@GetMapping("/user/primary/transaction")
	public List<PrimaryTransaction> getPrimaryTransactionList(@RequestParam("username") String username) {
		return transactionService.findPrimaryTransactionList(username);
	}

	@GetMapping("/user/savings/transaction")
	public List<SavingsTransaction> getSavingsTransactionList(@RequestParam("username") String username) {
		return transactionService.findSavingsTransactionList(username);
	}

	@GetMapping("/user/{username}/enable")
	public void enableUser(@PathVariable("username") String username) {
		userService.enableUser(username);
	}

	@GetMapping("/user/{username}/disable")
	public void diableUser(@PathVariable("username") String username) {
		userService.disableUser(username);
	}
}
