package com.douzone.bit.pathfinder.model.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "routes")
public class DetailRouteTb {

	private ObjectId id;
	private String rdep;
	private String rarvl;
	private String rdist;
	private Long rtime;
	private Double rfee;
}
