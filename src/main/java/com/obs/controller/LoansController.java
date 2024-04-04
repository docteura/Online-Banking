package com.obs.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.obs.entity.User;
import com.obs.service.LoansService;
import com.obs.service.UserService;

@Controller
@RequestMapping("/loans")
public class LoansController {

	private static final Logger logger = LoggerFactory.getLogger(LoansController.class);
	
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
