package com.douzone.bit.pathfinder.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoutesRequest {
	
	private String rarvl;
	private String rdep;
	private String rdist;
	private int rfee;
	private String rtime;
}
