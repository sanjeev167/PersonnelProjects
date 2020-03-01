/**
 * 
 */
package com.admin.pub.local_en.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.pvt.sec.rbac.entity.UserReg;
import com.admin.pvt.sec.rbac.repo.AppAdminUserRepository;

/**
 * @author Sanjeev
 *
 */

@Service
public class CustomAdministrativeUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AppAdminUserRepository appAdminUserRepository;	

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) {
		
		UserReg userReg = appAdminUserRepository.findByEmail(email).get();
		CustomAdministrativeUserDetailsImpl customAdministrativeUserDetailsImpl = new CustomAdministrativeUserDetailsImpl();
		UserBuilder builder = null;
		if (userReg != null) {
			builder = org.springframework.security.core.userdetails.User.withUsername(email);
			builder.password(new BCryptPasswordEncoder().encode(userReg.getPassword()));
			builder.authorities(customAdministrativeUserDetailsImpl.getAuthorities(userReg));			
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		return builder.build();
	}

	

}