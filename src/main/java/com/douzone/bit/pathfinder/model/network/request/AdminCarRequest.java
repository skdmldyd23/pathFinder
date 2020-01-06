package com.douzone.bit.pathfinder.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class AdminCarRequest {

	private Long carArea;
	private Double carName;
	private Double carFuel;
	private String carNumber;
	
//	@DateTimeFormat(pattern="yyyy-MM-dd")
//	private LocalDateTime carBuy;
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//	private LocalDateTime carBuy;
	
	private String carBuy; 
	
}

