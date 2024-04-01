package com.obs.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.obs.domain.User;
import com.obs.service.LoansService;
import com.obs.service.UserService;

@Controller
@RequestMapping("/loans")
public class LoansController {

	@Autowired
	private UserService userService;

	@Autowired
	LoansService loansService;

	@GetMapping("/list")
	public String loansList(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());

		return "loansList";
	}
	
	@GetMapping("/details")
	public String loanDetails(@RequestParam(value = "loanId") String loanId, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("loanId", loanId);
		return "loansDetails";
	}
}
