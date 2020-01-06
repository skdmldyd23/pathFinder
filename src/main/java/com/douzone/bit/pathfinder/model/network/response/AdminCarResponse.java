package com.douzone.bit.pathfinder.model.network.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class AdminCarResponse {

	private Long carIndex;
	private Double carName;
	private Double carFuel;
	private String carNumber;
	private LocalDate carBuy;
	private String carArea;

}
