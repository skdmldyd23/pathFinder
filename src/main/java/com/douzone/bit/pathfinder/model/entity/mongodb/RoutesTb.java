package com.douzone.bit.pathfinder.model.entity.mongodb;

import java.util.List;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.douzone.bit.pathfinder.model.dto.MongoRoutesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "routes")
public class RoutesTb {

	@Id
	private ObjectId id;

	private List<MongoRoutesDTO> detail;
}
