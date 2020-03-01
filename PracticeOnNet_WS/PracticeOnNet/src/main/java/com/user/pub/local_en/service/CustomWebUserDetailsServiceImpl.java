/**
 * 
 */
package com.user.pub.local_en.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.pub.local_en.entity.WebUser;
import com.user.pub.local_en.repo.WebUserRepository;

/**
 * @author Sanjeev
 *
 */

@Service
public class CustomWebUserDetailsServiceImpl implements UserDetailsService {

	    @Autowired
	    private WebUserRepository webUserRepository;
	    
	    @Transactional
	    @Override
	    public UserDetails loadUserByUsername(String email) {		    	
	    	UserBuilder builder = null;
	    	try {	      
	       WebUser webUser = webUserRepository.findByEmail(email).get();     
	        CustomWebUserDetailsImpl customUserDetailsImpl=new CustomWebUserDetailsImpl();	      
	        System.out.println("Sanjeev = "+new BCryptPasswordEncoder().encode(webUser.getPassword()));
	        if (webUser != null) {	        	
	          builder = org.springframework.security.core.userdetails.User.withUsername(email);
	          builder.password(new BCryptPasswordEncoder().encode(webUser.getPassword()));          
	          builder.authorities(customUserDetailsImpl.getAuthorities(webUser));	        
	        }
	    	}catch(Exception ex) {ex.printStackTrace();}
	        return builder.build();
	        
	    }

	


}