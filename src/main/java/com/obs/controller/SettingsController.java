package com.obs.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.obs.entity.User;
import com.obs.service.LoansService;
import com.obs.service.UserService;

@Controller
@RequestMapping("/settings")
public class SettingsController {

	@Autowired
	private UserService userService;

	@Autowired
	LoansService loansService;

	@GetMapping("/details")
	public String settings(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());

		model.addAttribute("user", user);
		return "settings";
	}
}
