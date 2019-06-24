package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity //for enabling the web security.
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{

	//it is already implemented class which we have to override 
	/*@Bean   //for getting the userDetailService object.
	@Override
	protected UserDetailsService userDetailsService() 
	{
		//User Details is a inbuild class in spring security 
		List<UserDetails> users = new ArrayList<UserDetails>();
		users.add(User.withDefaultPasswordEncoder().username("sonali").password("1234").roles("USER").build());//user is also inbuild class in which we can getting inmemory data
		//here we are defining our user name and password
		return new InMemoryUserDetailsManager(users);
		
	}*/
	@Autowired
	private UserDetailsService userDetailsServices;
	
	
	@Bean //for getting the AuthenticationProvider Object
	public AuthenticationProvider authProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //this is inbuild class in spring security which talk to database.
		provider.setUserDetailsService(userDetailsServices);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		System.out.println(provider);
		//provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}
	
	

//for 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests().antMatchers("/login").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		.and()
		.logout().invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/logout-sucess").permitAll();
	}
	
	
}
