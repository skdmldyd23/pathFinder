package com.douzone.bit.pathfinder.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.douzone.bit.pathfinder.model.entity.AreaTb;
import com.douzone.bit.pathfinder.model.entity.BranchTb;
import com.douzone.bit.pathfinder.model.entity.UserTb;

@Repository
public interface BranchRepository extends JpaRepository<BranchTb, Long> {

	public Boolean existsByBranchName(String branchName);
	
	public List<BranchTb> findByArea(AreaTb area);

	@Query(value ="SELECT count(*) FROM branch_tb", nativeQuery = true)
	public Long findAllBranchCount();
	
	@Query(value = "select branch_index, branch_name from branch_tb", nativeQuery = true)
	public List<Object> findBranchName();

	// 지점이름검색
	public List<BranchTb> findByBranchNameLike(String branchName);

	// 지점주소검색
	public List<BranchTb> findByBranchAddrLike(String branchAddr);

	@Query(value = "select b.branchIndex, b.branchName from BranchTb b where area = ?1")
	public List<Object> findValueByArea(AreaTb area);

	public Page<BranchTb> findByBranchAddrLike(String branchAddr, Pageable pageable);

	public Page<BranchTb> findByBranchNameLike(String branchName, Pageable pageable);

	public Page<BranchTb> findByArea(AreaTb area, Pageable pageable);
	//어드레스 또는 이름에 포함된 문자열 &
	// + areaIndex가 내가 보내는 값이랑 일치
	// aeraIndex -> areaTb -> areaIndex areaName
	// branchTb List -> areaIndex
	// List <= 
	//%Name%AndareaIndex
	//여쪽붙터 해야함

	public Page<BranchTb> findByAreaAndBranchNameLike(AreaTb area, String branchName, Pageable pageable);

	public Page<BranchTb> findByAreaAndBranchAddrLike(AreaTb area, String branchAddr, Pageable pageable);

	
	//public Page<BranchTb> findByBranchAddrLike(List<BranchTb> branchs1, String branchAddr, Pageable pageable);

	//public Page<BranchTb> findByBranchNameInAndBranchNameLike(List<BranchTb> branchs1, String branchName, Pageable pageable);



}
