package com.douzone.bit.pathfinder.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class AreaTb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long areaIndex;
	private String areaName;

	@OneToMany(mappedBy = "area")
	private List<BranchTb> branchList;

	@OneToMany(mappedBy = "carArea")
	private List<CarTb> carList;
}
