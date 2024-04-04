package com.obs.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);

	@Autowired
	private UserService userService;

	@Autowired
	LoansService loansService;

	@GetMapping("/details")
	public String settings(Principal principal, Model model) {
		logger.info("SettingsController --> settings ----> START");
		User user = userService.findByUsername(principal.getName());

		model.addAttribute("user", user);
		return "settings";
	}
}
