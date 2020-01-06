package com.douzone.bit.pathfinder.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.bit.pathfinder.model.entity.AreaTb;
import com.douzone.bit.pathfinder.model.entity.BranchTb;
import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.response.HierarchyResponse;
import com.douzone.bit.pathfinder.repository.AreaRepository;
import com.douzone.bit.pathfinder.repository.BranchRepository;

@Service
@Transactional
public class TreeService {

  @Autowired
  AreaRepository areaRepository;

  @Autowired
  BranchRepository branchRepository;

  public Header<HierarchyResponse> readCompany() {
	  HierarchyResponse company = HierarchyResponse.builder()
    .id("company:1")
    .text("더존마트")
    .children(readArea())
    .build();

    return Header.OK(company);
  }
  
  public List<HierarchyResponse> readArea() {

    List<AreaTb> areas = areaRepository.findAll();

    List<HierarchyResponse> areaList = areas.stream().map(area -> areaResponse(area)).collect(Collectors.toList());

    return areaList;
  }

  public List<HierarchyResponse> readBranch(Long areaIndex) {

    List<BranchTb> branchs = branchRepository.findByArea(areaRepository.getOne(areaIndex));

    List<HierarchyResponse> branchList = branchs.stream().map(branch -> branchResponse(branch)).collect(Collectors.toList());

    return branchList;
  }

  // Response 데이터 파싱
  private HierarchyResponse areaResponse(AreaTb area) {

	  HierarchyResponse treeResponse = HierarchyResponse.builder().id("area:" + area.getAreaIndex()).text(area.getAreaName())
        .children(readBranch(area.getAreaIndex())).build();

    return treeResponse;
  }

  private HierarchyResponse branchResponse(BranchTb branch) {

	  HierarchyResponse treeResponse = HierarchyResponse.builder().id("branch:" + branch.getBranchIndex())
        .text(branch.getBranchName()).build();

    return treeResponse;
  }

}