package com.obs.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.obs.entity.User;
import com.obs.repository.RoleRepository;
import com.obs.repository.UserRepository;
import com.obs.security.UserRole;
import com.obs.service.AccountService;
import com.obs.service.UserService;
import com.obs.util.EmailUtil;
import com.obs.util.OtpUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userDao;

	@Autowired
	private RoleRepository roleDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AccountService accountService;

	@Autowired
	private OtpUtil otpUtil;
	@Autowired
	private EmailUtil emailUtil;

	public void save(User user) {
		userDao.save(user);
	}

	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userDao.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);

			for (UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);

			user.setPrimaryAccount(accountService.createPrimaryAccount());
			user.setSavingsAccount(accountService.createSavingsAccount());

			String otp = otpUtil.generateOtp();

			user.setOtp(otp);
			user.setOtpGeneratedTime(LocalDateTime.now());
			try {
				emailUtil.sendOtpEmail(user.getEmail(), otp);
			} catch (MessagingException e) {
				throw new RuntimeException("Unable to send otp please try again");
			}

			localUser = userDao.save(user);
		}

		return localUser;
	}

	public boolean checkUserExists(String username, String email) {
		if (checkUsernameExists(username) || checkEmailExists(username)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkUsernameExists(String username) {
		if (null != findByUsername(username)) {
			return true;
		}

		return false;
	}

	public boolean checkEmailExists(String email) {
		if (null != findByEmail(email)) {
			return true;
		}

		return false;
	}

	public User saveUser(User user) {
		return userDao.save(user);
	}

	public List<User> findUserList() {
		return userDao.findAll();
	}

	public void enableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(true);
		userDao.save(user);
	}

	public void disableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(false);
		System.out.println(user.isEnabled());
		userDao.save(user);
		System.out.println(username + " is disabled.");
	}
}
