package com.douzone.bit.pathfinder.model.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.douzone.bit.pathfinder.model.entity.BranchTb;
import com.douzone.bit.pathfinder.model.entity.UserTb;
import com.douzone.bit.pathfinder.model.entity.UserTb.UserTbBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class SignDTO implements UserDetails {

	private static final long serialVersionUID = 7409745273729947063L;
	
	private Long userIndex;
	private String userFullName;
	private String password;
	private String username;
	private String userEmail;
	private String userPhone;
	private String userPosition;
	private String userBranch;
	private String userArea;
	private Collection<GrantedAuthority> authorities;
	
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
}