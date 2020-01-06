package com.douzone.bit.pathfinder.model.network.response;

import java.util.List;

import org.bson.types.ObjectId;

import com.douzone.bit.pathfinder.model.dto.MongoRoutesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HistoryRoutesResponse {

	private Double rdist;
	private String rtime;
	private String rdep;
	private String rarvl;
	private Integer rfee;

}
