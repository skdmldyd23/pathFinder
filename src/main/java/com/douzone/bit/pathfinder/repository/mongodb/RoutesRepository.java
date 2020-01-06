package com.douzone.bit.pathfinder.repository.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.douzone.bit.pathfinder.model.entity.mongodb.RoutesTb;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;

public interface RoutesRepository extends MongoRepository<RoutesTb, String> {
	// Object findByHindex(ObjectId hindex);
	RoutesTb findById(ObjectId id);
	// void deleteByHindex(ObjectId hindex);
}
