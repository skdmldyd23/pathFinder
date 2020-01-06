package com.douzone.bit.pathfinder.model.network.request;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserRequest {

  private Long userIndex;

  @NotEmpty
  @NotBlank(message = "아이디를 입력해주세요.")
  @Length(min = 3, max = 15)
  private String userId;

  private String userPw;

  @NotEmpty
  @NotBlank(message = "이름을 입력해주세요.")
  @Length(min = 2, max = 10)
  private String userName;

  @NotEmpty
  @NotBlank(message = "직책을 선택해주세요.")
  private String userPosition;

  @NotEmpty
  @NotBlank
  @Email(message = "잘못된 이메일 주소입니다.")
  private String userEmail;

  @NotEmpty
  @NotBlank(message = "핸드폰 번호를 입력해주세요.")
  private String userPhone;

  private LocalDateTime userCreated;

  @NotNull(message = "권한을 선택해주세요.")
  private Boolean userAuth;

  private Long branchIndex;
}