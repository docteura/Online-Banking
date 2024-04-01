package com.obs.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.obs.domain.PrimaryAccount;
import com.obs.domain.PrimaryTransaction;
import com.obs.domain.SavingsAccount;
import com.obs.domain.SavingsTransaction;
import com.obs.domain.User;
import com.obs.service.AccountService;
import com.obs.service.TransactionService;
import com.obs.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/primaryAccount")
	public String primaryAccount(Model model, Principal principal) {
		List<PrimaryTransaction> primaryTransactionList = transactionService.findPrimaryTransactionList(principal.getName());
		
		User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("primaryTransactionList", primaryTransactionList);
		
		return "primaryAccount";
	}

	@GetMapping("/savingsAccount")
    public String savingsAccount(Model model, Principal principal) {
		List<SavingsTransaction> savingsTransactionList = transactionService.findSavingsTransactionList(principal.getName());
        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsTransactionList", savingsTransactionList);

        return "savingsAccount";
    }
	
	@GetMapping("/deposit")
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @PostMapping("/deposit")
    public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.deposit(accountType, Double.parseDouble(amount), principal);

        return "redirect:/dashboard";
    }
    
    @GetMapping("/withdraw")
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.withdraw(accountType, Double.parseDouble(amount), principal);

        return "redirect:/dashboard";
    }
}
