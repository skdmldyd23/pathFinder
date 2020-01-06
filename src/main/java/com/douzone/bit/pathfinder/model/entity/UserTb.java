package com.douzone.bit.pathfinder.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;

import com.douzone.bit.pathfinder.model.entity.BranchTb;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@ToString(exclude = { "branch" })
@EntityListeners(AuditingEntityListener.class)
@Transactional
@Builder
@Accessors(chain = true)
public class UserTb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userIndex;

	private String userId;

	private String userPw;

	private String userName;

	private String userPosition;

	private String userEmail;

	private String userPhone;

	@CreatedDate
	private LocalDateTime userCreated;

	private Boolean userAuth;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branchIndex")
	private BranchTb branch;
}
