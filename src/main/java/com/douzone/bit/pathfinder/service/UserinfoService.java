package com.douzone.bit.pathfinder.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.bit.pathfinder.model.entity.UserTb;
import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.request.AdminUserRequest;
import com.douzone.bit.pathfinder.model.network.response.AdminUserResponse;
import com.douzone.bit.pathfinder.repository.UserRepository;

@Service
@Transactional
public class UserinfoService {

	@Autowired
	private UserRepository userRepository;

	// 유저 수정
	public Header<AdminUserResponse> update(AdminUserRequest request) {
		BCryptPasswordEncoder brc = new BCryptPasswordEncoder();
		String brcPassword = brc.encode(request.getUserPw());
		
		Optional<UserTb> optional = userRepository.findById(request.getUserIndex());
		return optional.map(user -> {

			user.setUserEmail(request.getUserEmail()).setUserPhone(request.getUserPhone()).setUserPw(brcPassword);

			return user;
		}).map(user -> userRepository.save(user)).map(user -> response(user)).map(Header::OK)
				.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	private AdminUserResponse response(UserTb user) {

		AdminUserResponse adminUserResponse = AdminUserResponse.builder().userIndex(user.getUserIndex())
				.userId(user.getUserId()).userName(user.getUserName()).userEmail(user.getUserEmail()).userPw(user.getUserPw())
				.userPhone(user.getUserPhone()).branchIndex(user.getBranch().getBranchIndex())
				.branchName(user.getBranch().getBranchName()).areaIndex(user.getBranch().getArea().getAreaIndex())
				.userPosition(user.getUserPosition()).userAuth(user.getUserAuth()).build();

		return adminUserResponse;
	}

}