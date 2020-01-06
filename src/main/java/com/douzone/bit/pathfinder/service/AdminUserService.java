package com.douzone.bit.pathfinder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.bit.pathfinder.model.entity.BranchTb;
import com.douzone.bit.pathfinder.model.entity.UserTb;
import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.Pagination;
import com.douzone.bit.pathfinder.model.network.request.AdminUserRequest;
import com.douzone.bit.pathfinder.model.network.response.AdminUserResponse;
import com.douzone.bit.pathfinder.repository.AreaRepository;
import com.douzone.bit.pathfinder.repository.BranchRepository;
import com.douzone.bit.pathfinder.repository.UserRepository;

@Service
@Transactional
public class AdminUserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BranchRepository branchRepository;

  @Autowired
  private AreaRepository areaRepository;

  public Header<AdminUserResponse> read(Long id) {
    Optional<UserTb> optional = userRepository.findById(id);

    return optional.map(user -> response(user)).map(Header::OK).orElseGet(() -> Header.ERROR("데이터 없음"));
  }

  // 유저 등록 서비스
  public Header<AdminUserResponse> create(AdminUserRequest request) {

	BCryptPasswordEncoder brc = new BCryptPasswordEncoder();
	String brcPassword = brc.encode("12345");
	
    UserTb user = UserTb.builder().userId(request.getUserId()).userPw(brcPassword).userName(request.getUserName())
        .userEmail(request.getUserEmail()).userPhone(request.getUserPhone()).userCreated(LocalDateTime.now())
        .userAuth(request.getUserAuth()).userPosition(request.getUserPosition())
        .branch(branchRepository.getOne(request.getBranchIndex())).build();

    if (idCheck(user.getUserId())) {
      Header.ERROR("중복된 ID");
    } 

    UserTb newUser = userRepository.save(user);
    return Header.OK(response(newUser));
  }

  // 지점 이름 불러오기
  // TODO 지점 service 쪽으로 뺴야함.
  public Header<List<Object>> readBranchName(Long id) {

    // 이부분 나중에 수정 요망
    return Header.OK(branchRepository.findValueByArea((areaRepository.getOne(id))));
  }

  // 지점 이름 불러오기
  // TODO 지역 service 쪽으로 뺴야함.
  public Header<List<Object>> readAreaName() {

    return Header.OK(areaRepository.findAreaName());
  }

  public Boolean idCheck(String userId) {

    return !(userRepository.existsByuserId(userId));
  }

  // 유저 리스트
  public Header<List<AdminUserResponse>> list(String id, Pageable pageable, String searchType, String keyword) {
	
	String treeId[] = id.split(":");
    String nodeType = treeId[0];
    Long nodeIndex = Long.parseLong(treeId[1]);

    Page<UserTb> users = mappingUser(nodeType, nodeIndex, searchType, keyword, pageable);
    List<AdminUserResponse> userResponseList = users.stream().map(user -> response(user)).collect(Collectors.toList());
    if (users.getTotalElements() == 0) {
		return Header.ERROR("조회 결과가 없습니다.");
	}
    Pagination pagination = Pagination.builder().totalPages(users.getTotalPages())
        .totalElements(users.getTotalElements()).currentPage(users.getNumber())
        .currentElements(users.getNumberOfElements()).nodeType(nodeType).nodeIndex(nodeIndex).build();

    return Header.OK(userResponseList, pagination);
  }

  public Header<List<AdminUserResponse>> search(String id, String type, String value, Pageable pageable) {

    String treeId[] = id.split(":");
    String nodeType = treeId[0];
    Long nodeIndex = Long.parseLong(treeId[1]);

    Page<UserTb> users = mappingUser(nodeType, nodeIndex, type, value, pageable);

    List<AdminUserResponse> userResponseList = users.stream().map(user -> response(user)).collect(Collectors.toList());
	
    if (users.getTotalElements() == 0) {
		return Header.ERROR("조회 결과가 없습니다.");
	}
    
    Pagination pagination = Pagination.builder().totalPages(users.getTotalPages())
        .totalElements(users.getTotalElements()).currentPage(users.getNumber())
        .currentElements(users.getNumberOfElements()).nodeType(nodeType).nodeIndex(nodeIndex).build();

    return Header.OK(userResponseList, pagination);
  }

  // 유저 수정
  public Header<AdminUserResponse> update(AdminUserRequest request) {
    Optional<UserTb> optional = userRepository.findById(request.getUserIndex());

    return optional.map(user -> {

      user.setUserName(request.getUserName()).setUserEmail(request.getUserEmail()).setUserPhone(request.getUserPhone())
          .setUserPosition(request.getUserPosition()).setUserAuth(request.getUserAuth())
          .setBranch(branchRepository.getOne(request.getBranchIndex()));

      return user;
    }).map(user -> userRepository.save(user)).map(user -> response(user)).map(Header::OK)
        .orElseGet(() -> Header.ERROR("데이터 없음"));
  }

  // 유저 비밀번호 초기화
  public Header<AdminUserResponse> updatePw(Long id) {

	BCryptPasswordEncoder brc = new BCryptPasswordEncoder();
	String brcPassword = brc.encode("12345");
	  
    Optional<UserTb> optional = userRepository.findById(id);

    return optional.map(user -> {
      user.setUserPw(brcPassword);
      return user;
    }).map(updatedUser -> userRepository.save(updatedUser)).map(updatedUser -> response(updatedUser)).map(Header::OK)
        .orElseGet(() -> Header.ERROR("데이터 없음"));
  }

  // 유저 삭제
  public Header delete(Long id) {

    Optional<UserTb> optional = userRepository.findById(id);

    return optional.map(user -> {
      userRepository.delete(user);
      return Header.OK();
    }).orElseGet(() -> Header.ERROR("데이터 없음"));
  }

  // 검색 Type별 분기
  public Page<UserTb> mappingUser(String nodeType, Long nodeIndex, String type, String value, Pageable pageable) {

    Page<UserTb> users = null;

    String searchValue = "%" + value + "%";

    switch (nodeType) {
    case "company":
      if (type.equals("name")) {
        users = userRepository.findByUserNameLike(searchValue, pageable);
      } else if (type.equals("position")) {
        users = userRepository.findByUserPositionLike(searchValue, pageable);
      } else {
        users = userRepository.findAll(pageable);
      }

      break;

    case "area":
      List<BranchTb> branchs = branchRepository.findByArea(areaRepository.getOne(nodeIndex));

      if (type.equals("name")) {
        users = userRepository.findByBranchInAndUserNameLike(branchs, searchValue, pageable);
      } else if (type.equals("position")) {
        users = userRepository.findByBranchInAndUserPositionLike(branchs, searchValue, pageable);
      } else {
        users = userRepository.findByBranchIn(branchs, pageable);
      }

      break;

    case "branch":
      if (type.equals("name")) {
        users = userRepository.findByBranchAndUserNameLike(branchRepository.getOne(nodeIndex), searchValue, pageable);
      } else if (type.equals("position")) {
        users = userRepository.findByBranchAndUserPositionLike(branchRepository.getOne(nodeIndex), searchValue,
            pageable);
      } else {
        users = userRepository.findByBranch(branchRepository.getOne(nodeIndex), pageable);
      }

      break;
    }

    return users;
  }

  // Response 데이터 파싱
  private AdminUserResponse response(UserTb user) {

    AdminUserResponse adminUserResponse = AdminUserResponse.builder().userIndex(user.getUserIndex())
        .userId(user.getUserId()).userName(user.getUserName()).userEmail(user.getUserEmail())
        .userPhone(user.getUserPhone()).branchIndex(user.getBranch().getBranchIndex())
        .branchName(user.getBranch().getBranchName()).areaIndex(user.getBranch().getArea().getAreaIndex())
        .userPosition(user.getUserPosition()).userAuth(user.getUserAuth()).build();

    return adminUserResponse;
  }
}