package co.silbersoft.openlab.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.silbersoft.openlab.models.Authority;
import co.silbersoft.openlab.models.Permission;
import co.silbersoft.openlab.models.WebUser;
import co.silbersoft.openlab.service.AccountService;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class ActiveDirectoryUserDetailsService implements UserDetailsService {

	final private String rolePrefix = "ROLE_";

	@Override
	@Transactional(readOnly = false)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Set<Authority> authorities = new HashSet<Authority>();

		Set<GrantedAuthority> finalAuthorities = new HashSet<GrantedAuthority>();

		WebUser webUser = webUserService.getUserByUsername(username);
		Authority authority = webUserService.findAuthorityByName("domain_user");
		authorities.add(authority);
		if (webUser == null) {
			// Create the username in the database
			webUser = new WebUser();
			webUser.setCn(username);
			webUser.setAuthorities(authorities);
			webUser.setEnabled(true);
			webUserService.createWebUser(webUser);
		}

		Set<Authority> aSet = webUser.getAuthorities();		

		for (Authority auth : aSet) {
			String role = auth.getAuthority();
			role = role.toUpperCase();
			finalAuthorities.add(new SimpleGrantedAuthority(rolePrefix + role));						
		}
		
		return new User(username, "", webUser.getEnabled(), true, true, true, finalAuthorities);
	}

	@Autowired
	AccountService webUserService;

}
