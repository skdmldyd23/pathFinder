package com.douzone.bit.pathfinder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.bit.pathfinder.model.entity.AreaTb;
import com.douzone.bit.pathfinder.model.entity.BranchTb;
import com.douzone.bit.pathfinder.model.entity.UserTb;
import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.Pagination;
import com.douzone.bit.pathfinder.model.network.response.AdminUserResponse;
import com.douzone.bit.pathfinder.model.network.response.HierarchyResponse;
import com.douzone.bit.pathfinder.repository.AreaRepository;
import com.douzone.bit.pathfinder.repository.BranchRepository;
import com.douzone.bit.pathfinder.repository.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service
@Transactional
public class HierarchyService {

	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private BranchRepository branchRepository;

	public Header<HierarchyResponse> readCompany() {
		Map<String, Boolean> state = new HashMap<String, Boolean>();
		state.put("opened", true);
		state.put("selected", true);

		HierarchyResponse company = HierarchyResponse.builder().id("company:1").text("더존 공장").state(state)
				.children(readArea()).build();

		return Header.OK(company);
	}

	public List<HierarchyResponse> readArea() {

		List<AreaTb> areas = areaRepository.findAll();

		List<HierarchyResponse> areaList = areas.stream().map(area -> areaResponse(area)).collect(Collectors.toList());

		return areaList;
	}

	public List<HierarchyResponse> readBranch(Long areaIndex, String parent) {
		List<BranchTb> branchs = branchRepository.findByArea(areaRepository.getOne(areaIndex));

		List<HierarchyResponse> branchList = branchs.stream().map(branch -> branchResponse(branch))
				.collect(Collectors.toList());

		return branchList;
	}

	private HierarchyResponse areaResponse(AreaTb area) {
		String childParent = "area:" + area.getAreaIndex();

		HierarchyResponse treeResponse = HierarchyResponse.builder().id("area:" + area.getAreaIndex())
				.text(area.getAreaName()).children(readBranch(area.getAreaIndex(), childParent)).build();

		return treeResponse;
	}

	private HierarchyResponse branchResponse(BranchTb branch) {
		HierarchyResponse treeResponse = HierarchyResponse.builder().id("branch:" + branch.getBranchIndex())
				.text(branch.getBranchName()).build();
		return treeResponse;
	}
}