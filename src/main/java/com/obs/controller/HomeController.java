package com.obs.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.obs.entity.PrimaryAccount;
import com.obs.entity.SavingsAccount;
import com.obs.entity.User;
import com.obs.repository.RoleRepository;
import com.obs.security.UserRole;
import com.obs.service.EmailService;
import com.obs.service.UserService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleDao;

	@Autowired
	private EmailService emailService;

	@GetMapping("/")
	public String home(Model model) {
		String success = (String) model.asMap().get("success");
		Boolean flag = (Boolean) model.asMap().get("flag");
		model.addAttribute("success", success);
		model.addAttribute("flag", flag);
		return "redirect:/index";
	}

	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		User user = new User();

		model.addAttribute("user", user);

		return "signup";
	}

	@PostMapping("/signup")
	public String signupPost(@ModelAttribute("user") User user, Model model,
			final RedirectAttributes redirectAttributess) {
		logger.info("****** creating new user ****** ");
		if (userService.checkUserExists(user.getUsername(), user.getEmail())) {

			if (userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists", true);
			}

			if (userService.checkUsernameExists(user.getUsername())) {
				model.addAttribute("usernameExists", true);
			}

			return "signup";
		} else {
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));

			userService.createUser(user, userRoles);

			return "redirect:/";
		}
	}

	@GetMapping("/verify-account")
	public String verifyAccount(@RequestParam String email, @RequestParam String otp, Model model,
			final RedirectAttributes redirectAttributess) {
		logger.info("verifyAccount ---> email : {}, OTP : {} ", email, otp);
		String message = emailService.verifyAccount(email, otp);
		redirectAttributess.addFlashAttribute("success", message);
		return "redirect:/regenerate-otp";
	}

	@GetMapping("/regenerate-otp")
	public String regenerateOTP(Model model, final RedirectAttributes redirectAttributess) {
		logger.info("****** regenerateOTP ****** ");
		User user = new User();
		model.addAttribute("user", user);
		return "regenerate-otp";
	}

	@PostMapping("/regenerate-otp")
	public String regenerateOtp(@ModelAttribute("user") User user, final RedirectAttributes redirectAttributess) {
		logger.info("regenerateOtp ---> email : {}", user.getEmail());
		String message = emailService.regenerateOtp(user.getEmail());
		redirectAttributess.addFlashAttribute("success", message);
		redirectAttributess.addFlashAttribute("flag", true);
		return "redirect:/";
	}

	@GetMapping("/dashboard")
	public String dashboard(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();

		model.addAttribute("primaryAccount", primaryAccount);
		model.addAttribute("savingsAccount", savingsAccount);

		return "dashboard";
	}
}
