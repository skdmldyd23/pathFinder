package com.douzone.bit.pathfinder.model.network.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.douzone.bit.pathfinder.model.entity.AreaTb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class AdminBranchRequest {

  private Long branchIndex;
  
  @NotEmpty
  @NotNull(message = "@@@@@@@@@Name cannot be null")
  @NotBlank(message = "@@@@@@@@Name cannot be null")
  private String branchName;

  @NotEmpty
  @NotBlank(message = "OWNER ERROR")
  private String branchOwner;

  private Integer branchValue;

  private String branchAddr;

  private String branchDaddr;
  
  private String branchPhone;

  private Double branchLat;

  private Double branchLng;
  
  private Long areaIndex;
  
}
