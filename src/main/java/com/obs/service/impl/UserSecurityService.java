package com.obs.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.obs.entity.User;
import com.obs.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {

	/** The application logger */
	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

	@Autowired
	private UserRepository userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (null == user) {
			LOG.warn("Username {} not found", username);
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		return user;
	}
}
