package com.douzone.bit.pathfinder.db;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.douzone.bit.pathfinder.PathfinderApplicationTests;
import com.douzone.bit.pathfinder.model.entity.mongodb.HistoryTb;
import com.douzone.bit.pathfinder.repository.mongodb.HistoryRepository;
import com.douzone.bit.pathfinder.repository.mongodb.RoutesRepository;
import com.douzone.bit.pathfinder.service.HistoryService;

public class MongoRepositoryTest extends PathfinderApplicationTests {

	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	private RoutesRepository routesRepository;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private MongoTemplate mongo;

	// 다 들고오기
	// ------------------------------------------------------------------------------------------

	@Test
	public void printTest() {
		Query q = new Query();

		q.addCriteria(Criteria.where("arrivedate").gte(Calendar.getInstance().getTime()));

		List<HistoryTb> a = mongo.find(q, HistoryTb.class, "history");
	}
}
