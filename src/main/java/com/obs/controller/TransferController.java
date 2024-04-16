package com.obs.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.obs.entity.Beneficiary;
import com.obs.entity.PrimaryAccount;
import com.obs.entity.SavingsAccount;
import com.obs.entity.User;
import com.obs.service.TransactionService;
import com.obs.service.UserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {

	private static final Logger logger = LoggerFactory.getLogger(TransferController.class);
	
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/betweenAccounts")
    public String betweenAccounts(Model model) {
    	logger.info("TransferController --> betweenAccounts ----> START");
        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");

        return "betweenAccounts";
    }

    @PostMapping("/betweenAccounts")
    public String betweenAccountsPost(
            @ModelAttribute("transferFrom") String transferFrom,
            @ModelAttribute("transferTo") String transferTo,
            @ModelAttribute("amount") String amount,
            Principal principal
    ) throws Exception {
    	logger.info("TransferController --> betweenAccountsPost ----> START");
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();
        transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);

        return "redirect:/dashboard";
    }
    
    @GetMapping("/beneficiary")
    public String beneficiary(Model model, Principal principal) {
    	logger.info("TransferController --> beneficiary ----> START");
        List<Beneficiary> beneficiaries = transactionService.findBeneficiaryList(principal);

        Beneficiary beneficiary = new Beneficiary();

        model.addAttribute("beneficiaries", beneficiaries);
        model.addAttribute("beneficiary", beneficiary);

        return "beneficiary";
    }

    @PostMapping("/beneficiary/save")
    public String beneficiaryPost(@ModelAttribute("beneficiary") Beneficiary beneficiary, Principal principal) {
    	logger.info("TransferController --> beneficiaryPost ----> START");
        User user = userService.findByUsername(principal.getName());
        beneficiary.setUser(user);
        transactionService.saveBeneficiary(beneficiary);

        return "redirect:/transfer/beneficiary";
    }

    @GetMapping("/beneficiary/edit")
    public String beneficiaryEdit(@RequestParam(value = "id") Long id, Model model, Principal principal){
    	logger.info("TransferController --> beneficiaryEdit ----> START");
        Beneficiary beneficiary = transactionService.findBeneficiaryById(id);
        List<Beneficiary> beneficiaries = transactionService.findBeneficiaryList(principal);

        model.addAttribute("beneficiaries", beneficiaries);
        model.addAttribute("beneficiary", beneficiary);

        return "beneficiary";
    }

    @GetMapping("/beneficiary/delete")
    @Transactional
    public String beneficiaryDelete(@RequestParam(value = "id") Long id, Model model, Principal principal){
    	logger.info("TransferController --> beneficiaryDelete ----> START");
        transactionService.deleteBeneficiaryById(id);

        List<Beneficiary> beneficiaries = transactionService.findBeneficiaryList(principal);

        Beneficiary beneficiary = new Beneficiary();
        model.addAttribute("beneficiary", beneficiary);
        model.addAttribute("beneficiaries", beneficiaries);


        return "beneficiary";
    }

    @GetMapping("/toSomeoneElse")
    public String toSomeoneElse(Model model, Principal principal) {
    	logger.info("TransferController --> toSomeoneElse ----> START");
        List<Beneficiary> beneficiaries = transactionService.findBeneficiaryList(principal);

        model.addAttribute("beneficiaries", beneficiaries);
        model.addAttribute("accountType", "");

        return "toSomeoneElse";
    }

    @PostMapping("/toSomeoneElse")
    public String toSomeoneElsePost(@ModelAttribute("recipientName") String recipientName, @ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal) {
    	logger.info("TransferController --> toSomeoneElsePost ----> START");
    	User user = userService.findByUsername(principal.getName());
        Beneficiary recipient = transactionService.findBeneficiaryByName(recipientName);
        transactionService.toSomeoneElseTransfer(recipient, accountType, amount, user.getPrimaryAccount(), user.getSavingsAccount());

        return "redirect:/dashboard";
    }
}
