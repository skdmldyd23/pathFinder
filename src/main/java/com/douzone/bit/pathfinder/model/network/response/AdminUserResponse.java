package com.douzone.bit.pathfinder.model.network.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class AdminUserResponse {

  private Long userIndex;

  private String userId;

  private String userPw;

  private String userName;

  private String userPosition;

  private String userEmail;

  private String userPhone;

  private LocalDateTime userCreated;

  private Boolean userAuth;

  private Long branchIndex;

  private String branchName;

  private Long areaIndex;
}