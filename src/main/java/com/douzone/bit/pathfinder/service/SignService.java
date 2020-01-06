package com.douzone.bit.pathfinder.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.bit.pathfinder.model.dto.SignDTO;
import com.douzone.bit.pathfinder.model.entity.UserTb;
import com.douzone.bit.pathfinder.repository.UserRepository;

@Service
@Transactional
public class SignService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserTb user = userRepository.findByUserId(userId);

		if (user == null) {
			return null;
		}

		SignDTO signInfo = SignDTO.builder().userIndex(user.getUserIndex()).username(user.getUserId())
				.password(user.getUserPw()).userFullName(user.getUserName()).userEmail(user.getUserEmail())
				.userPhone(user.getUserPhone()).userPosition(user.getUserPosition())
				.userBranch(user.getBranch().getBranchName()).userArea(user.getBranch().getArea().getAreaName())
				.authorities(setAuthorites(user.getUserAuth())).accountNonExpired(true).accountNonLocked(true)
				.credentialsNonExpired(true).enabled(true).build();

		return signInfo;
	}

	public Collection<GrantedAuthority> setAuthorites(boolean authority) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		String auth = "";

		auth = (authority) ? "ROLE_ADMIN" : "ROLE_USER";

		authorities.add(new SimpleGrantedAuthority(auth));

		return authorities;
	}
}