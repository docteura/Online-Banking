package com.obs.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.obs.entity.PrimaryAccount;
import com.obs.entity.PrimaryTransaction;
import com.obs.entity.SavingsAccount;
import com.obs.entity.SavingsTransaction;
import com.obs.entity.User;
import com.obs.service.AccountService;
import com.obs.service.TransactionService;
import com.obs.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/primaryAccount")
	public String primaryAccount(Model model, Principal principal) {
		logger.info("AccountController --> primaryAccount ---> START");
		List<PrimaryTransaction> primaryTransactionList = transactionService.findPrimaryTransactionList(principal.getName());
		
		User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("primaryTransactionList", primaryTransactionList);
        logger.info("AccountController --> primaryAccount ---> END");
		return "primaryAccount";
	}

	@GetMapping("/savingsAccount")
    public String savingsAccount(Model model, Principal principal) {
		logger.info("AccountController --> savingsAccount ---> START");
		List<SavingsTransaction> savingsTransactionList = transactionService.findSavingsTransactionList(principal.getName());
        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsTransactionList", savingsTransactionList);
        logger.info("AccountController --> savingsAccount ---> END");
        return "savingsAccount";
    }
	
	@GetMapping("/deposit")
    public String deposit(Model model) {
		logger.info("AccountController --> deposit ---> START");
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @PostMapping("/deposit")
    public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
    	logger.info("AccountController --> depositPOST ---> START");
    	accountService.deposit(accountType, Double.parseDouble(amount), principal);

        return "redirect:/dashboard";
    }
    
    @GetMapping("/withdraw")
    public String withdraw(Model model) {
    	logger.info("AccountController --> withdraw ---> START");
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
    	logger.info("AccountController --> withdrawPOST ---> START");
    	accountService.withdraw(accountType, Double.parseDouble(amount), principal);

        return "redirect:/dashboard";
    }
}
