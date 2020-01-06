package com.douzone.bit.pathfinder.model.network.response;

import java.util.Date;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HistoryResponse {

	private ObjectId id;
	private String regdate;
	private String username;
	private String carname;
	private String imgSrc;
	private String dep;
	private String arvl;
	private Double dist;
	private Integer fee;
	private String dlvrdate;
	private String arrivedate;
	private String routes;
	private String time;
	private int stat;
	private String sortType;
}
