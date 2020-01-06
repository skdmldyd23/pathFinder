package com.douzone.bit.pathfinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MongoRoutesDTO {

	private Double rdist;
	private Double rtime;
	private String rdep;
	private String rarvl;
	private Integer rfee;
}
