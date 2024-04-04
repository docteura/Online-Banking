package com.obs.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.obs.domain.PrimaryAccount;
import com.obs.domain.Beneficiary;
import com.obs.domain.SavingsAccount;
import com.obs.domain.User;
import com.obs.service.TransactionService;
import com.obs.service.UserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/betweenAccounts")
    public String betweenAccounts(Model model) {
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
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();
        transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);

        return "redirect:/dashboard";
    }
    
    @GetMapping("/beneficiary")
    public String beneficiary(Model model, Principal principal) {
        List<Beneficiary> beneficiaries = transactionService.findRecipientList(principal);

        Beneficiary beneficiary = new Beneficiary();

        model.addAttribute("beneficiaries", beneficiaries);
        model.addAttribute("beneficiary", beneficiary);

        return "beneficiary";
    }

    @PostMapping("/beneficiary/save")
    public String beneficiaryPost(@ModelAttribute("recipient") Beneficiary recipient, Principal principal) {

        User user = userService.findByUsername(principal.getName());
        recipient.setUser(user);
        transactionService.saveRecipient(recipient);

        return "redirect:/transfer/beneficiary";
    }

    @GetMapping("/beneficiary/edit")
    public String beneficiaryEdit(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal){

        Beneficiary beneficiary = transactionService.findRecipientByName(recipientName);
        List<Beneficiary> beneficiaries = transactionService.findRecipientList(principal);

        model.addAttribute("beneficiaries", beneficiaries);
        model.addAttribute("beneficiary", beneficiary);

        return "beneficiary";
    }

    @GetMapping("/beneficiary/delete")
    @Transactional
    public String beneficiaryDelete(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal){

        transactionService.deleteRecipientByName(recipientName);

        List<Beneficiary> recipientList = transactionService.findRecipientList(principal);

        Beneficiary recipient = new Beneficiary();
        model.addAttribute("recipient", recipient);
        model.addAttribute("recipientList", recipientList);


        return "beneficiary";
    }

    @GetMapping("/toSomeoneElse")
    public String toSomeoneElse(Model model, Principal principal) {
        List<Beneficiary> beneficiaries = transactionService.findRecipientList(principal);

        model.addAttribute("beneficiaries", beneficiaries);
        model.addAttribute("accountType", "");

        return "toSomeoneElse";
    }

    @PostMapping("/toSomeoneElse")
    public String toSomeoneElsePost(@ModelAttribute("recipientName") String recipientName, @ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Beneficiary recipient = transactionService.findRecipientByName(recipientName);
        transactionService.toSomeoneElseTransfer(recipient, accountType, amount, user.getPrimaryAccount(), user.getSavingsAccount());

        return "redirect:/dashboard";
    }
}
