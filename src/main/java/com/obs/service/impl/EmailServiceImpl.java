package com.obs.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obs.dto.LoginDto;
import com.obs.dto.RegisterDto;
import com.obs.entity.User;
import com.obs.repository.UserRepository;
import com.obs.service.EmailService;
import com.obs.util.EmailUtil;
import com.obs.util.OtpUtil;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private OtpUtil otpUtil;
	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private UserRepository userRepository;

	public String register(RegisterDto registerDto) {
		String otp = otpUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp please try again");
		}
		User user = new User();
		user.setFirstName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setPassword(registerDto.getPassword());
		user.setOtp(otp);
		user.setOtpGeneratedTime(LocalDateTime.now());
		userRepository.save(user);
		return "User registration successful";
	}

	public String verifyAccount(String email, String otp) {
		User user = userRepository.findByEmail(email);
		if (user.getOtp().equals(otp)
				&& Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
			user.setEnabled(true);
			userRepository.save(user);
			return "OTP verified you can login";
		}
		return "Please regenerate otp and try again";
	}

	public String regenerateOtp(String email) {
		User user = userRepository.findByEmail(email);
		String otp = otpUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(email, otp);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp please try again");
		}
		user.setOtp(otp);
		user.setOtpGeneratedTime(LocalDateTime.now());
		userRepository.save(user);
		return "Email sent... please verify account within 1 minute";
	}

	public String login(LoginDto loginDto) {
		User user = userRepository.findByEmail(loginDto.getEmail());
		if (!loginDto.getPassword().equals(user.getPassword())) {
			return "Password is incorrect";
		} else if (!user.isEnabled()) {
			return "your account is not verified";
		}
		return "Login successful";
	}
}
