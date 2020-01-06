package com.douzone.bit.pathfinder.model.entity;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@ToString(exclude = { "userList", "area" })
@Builder
@Accessors(chain = true)
public class BranchTb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchIndex;

	private String branchName;

	private String branchOwner;

	private Integer branchValue;

	private String branchAddr;

	private String branchDaddr;

	private String branchPhone;

	private Double branchLat;

	private Double branchLng;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaIndex")
	private AreaTb area;

	@OneToMany(mappedBy = "branch")
	private List<UserTb> userList;
}
