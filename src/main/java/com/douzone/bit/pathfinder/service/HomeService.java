package com.douzone.bit.pathfinder.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.bit.pathfinder.model.entity.BranchTb;
import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.response.AdminBranchResponse;
import com.douzone.bit.pathfinder.repository.AreaRepository;
import com.douzone.bit.pathfinder.repository.BranchRepository;
import com.douzone.bit.pathfinder.repository.UserRepository;
import com.douzone.bit.pathfinder.repository.mongodb.HistoryRepository;

@Service
@Transactional
public class HomeService {

	@Autowired
	HistoryRepository historyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	AreaRepository areaRepository;

	public int[] getTotalCount(boolean myDelivery) {
		int count[] = new int[3];

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime firstDay = LocalDate.now().atTime(0, 0).withDayOfMonth(1);

		if (myDelivery) {
			count[0] = historyRepository.findAllByWillAndUsernameAndDateAndCnt(currentDate, userName);
			count[1] = historyRepository.findAllByIngAndUsernameAndDateAndCnt(currentDate, userName);
			count[2] = historyRepository.findAllByPpAndUsernameAndDateAndCnt(currentDate, userName, firstDay);
		} else {
			count[0] = historyRepository.findAllByWillAndDateAndCnt(currentDate);
			count[1] = historyRepository.findAllByIngAndDateAndCnt(currentDate);
			count[2] = historyRepository.findAllByPpAndDateAndCnt(currentDate, firstDay);
		}

		return count;
	}

	public Long userCount() {
		Long userTotalCount = userRepository.findAllUserCount();
		return userTotalCount;
	}

	public Long branchCount() {
		Long branchTotalCount = branchRepository.findAllBranchCount();
		return branchTotalCount;
	}

	public Long historyAll() {
		Long historyTotalCount = historyRepository.findAllCount();
		return historyTotalCount;
	}

	public Header<List<AdminBranchResponse>> barChart(Long keyword) {

		List<BranchTb> branchs = branchRepository.findByArea(areaRepository.getOne(keyword));
		List<AdminBranchResponse> branchResponseList = branchs.stream().map(branch -> response(branch))
				.collect(Collectors.toList());
		return Header.OK(branchResponseList);
	}

	private AdminBranchResponse response(BranchTb branch) {
		AdminBranchResponse adminBranchResponse = AdminBranchResponse.builder().branchIndex(branch.getBranchIndex())
				.branchName(branch.getBranchName()).branchValue(branch.getBranchValue()).area(branch.getArea().getAreaName())
				.areaIndex(branch.getArea().getAreaIndex()).build();
		return adminBranchResponse;
	}

}