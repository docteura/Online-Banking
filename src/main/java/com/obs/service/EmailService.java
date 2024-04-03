package com.obs.service;

public interface EmailService {

	String verifyAccount(String email, String otp);

	String regenerateOtp(String email);

}
