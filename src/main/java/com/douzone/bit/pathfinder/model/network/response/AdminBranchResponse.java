package com.douzone.bit.pathfinder.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminBranchResponse {

  private Long branchIndex;

  private String branchName;

  private String branchOwner;

  private Integer branchValue;

  private String branchAddr;

  private String branchDaddr;

  private String branchPhone;

  private Double branchLat;

  private Double branchLng;
  
  private String area;
  
  private Long areaIndex;
}
