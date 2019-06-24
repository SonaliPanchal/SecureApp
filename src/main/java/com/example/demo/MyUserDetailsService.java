package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired //it is intiating this class 
	private UserRepository repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = repo.findByUsername(userName);
	
		System.out.println("user----------->"+user);
		if(user==null)
			
		throw new UsernameNotFoundException("User Not Found 404");
		
		return new UserPrinciple(user);
	}

}
