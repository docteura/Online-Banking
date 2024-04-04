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
import org.springframework.web.bind.annotation.RequestParam;

import com.obs.entity.CreditCard;
import com.obs.entity.User;
import com.obs.service.CreditCardService;
import com.obs.service.UserService;

@Controller
@RequestMapping("/cards")
public class CardController {

	@Autowired
	private UserService userService;

	@Autowired
	private CreditCardService creditCardService;

	@GetMapping("/list")
	public String cards(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		List<CreditCard> cards = creditCardService.findAllCardsByUserId(user.getUserId());
		model.addAttribute("cards", cards);

		return "cards";
	}

	@GetMapping("/addNewCC")
	public String addNew(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		CreditCard creditCard = new CreditCard();
		model.addAttribute("creditCard", creditCard);
		model.addAttribute("user", user);

		return "addNewCC";
	}

	@PostMapping("/save")
	public String saveNewCard(@ModelAttribute("creditCard") CreditCard creditCard, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		creditCard.setUserId(user.getUserId());
		creditCardService.saveCreditCard(creditCard);

		model.addAttribute("user", user);

		return "redirect:/cards/list";
	}

	@GetMapping(value = "/edit")
	public String cardEdit(@RequestParam(value = "cardId") Long cardId, Model model, Principal principal) {

		CreditCard creditCard = creditCardService.findCCById(cardId);
		model.addAttribute("creditCard", creditCard);

		return "addNewCC";
	}

	@GetMapping(value = "/view")
	public String cardView(@RequestParam(value = "cardId") Long cardId, Model model, Principal principal) {

		CreditCard creditCard = creditCardService.findCCById(cardId);
		String cc = creditCard.getCardNumber();
		String finalCC = null;
		if (cc.length() >= 16) {
			String s1 = cc.substring(0, 4);
			String s2 = cc.substring(17, 19);

			finalCC = s1 + "-****-****-**" + s2;
		} else if (cc.length()<= 15) {
			String s1 = cc.substring(0, 4);
			String s2 = cc.substring(11, 4);
			finalCC = s1 + "-****-****-" + s2;
		}
		String s1 = creditCard.getCvv().substring(0, 1);
		creditCard.setCvv(s1+"**");
		creditCard.setCardNumber(finalCC);
		model.addAttribute("creditCard", creditCard);
		return "ccDetails";
	}
}
