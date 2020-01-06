package com.douzone.bit.pathfinder.repository.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.douzone.bit.pathfinder.model.entity.mongodb.HistoryTb;
import java.time.LocalDateTime;
import java.util.List;
import java.lang.Long;

public interface HistoryRepository extends MongoRepository<HistoryTb, String> {

	HistoryTb findById(ObjectId id);

	Object findByRoutes(ObjectId routes);

	// 검색 페이지
	List<HistoryTb> findByUsernameLike(String username, Pageable pageable);

	// 검색 페이지
	@Query(value = "{'username' : ?0}", sort = "{'regdate': 1}")
	List<HistoryTb> findAllByUsername(String username);

	// findTop5ByIngByOrderByArrivedateDesc
	// 전체 갯수
	@Query(value = "{}", count = true)
	Long findAllCount();

	// 오늘 배송할 총갯수
	@Query(value = "{$and :[{'arrivedate' : {'$gte' : ?0} },{'arrivedate' :{'$lte' : ?1 } }]}", count = true)
	Integer findAllByTotalToday(LocalDateTime LocalTime1, LocalDateTime LocalTime2);

	// 오늘거배송된 갯수
	@Query(value = "{$and : [{$and :[{'arrivedate' : {'$gte' : ?0} },{'arrivedate' :{'$lte' : ?1 } }]}, "
			+ "{'arrivedate' : {'$lte' : ?2} }]}", count = true)
	Integer findAllByDoingToday(LocalDateTime LocalTime1, LocalDateTime LocalfindAllByIngTime2, LocalDateTime LocalTime3);

	// 현재날짜가 출발일 도착일 사이(오늘의 운송)
	@Query("{$and :[{'arrivedate' : {'$lte' : ?0} }, {'arrivedate' : {'$gte' : ?1 } } ]}")
	List<HistoryTb> findTodayList(Pageable pageable, LocalDateTime todayEndTime, LocalDateTime todayStratTime);

	// 전체 검색
	@Query("{'arrivedate' : { '$lt' : ?0 }}")
	Page<HistoryTb> findAllByPp(Pageable pageable, LocalDateTime LocalTime);

	@Query("{$and :[ {arrivedate : {'$gte' : ?0} }, { 'dlvrdate' :{'$lte' : ?0 }}]}")
	Page<HistoryTb> findAllByIng(Pageable pageable, LocalDateTime LocalTime);

	@Query("{$and :[ {arrivedate : {'$gte' : ?0} }, { 'dlvrdate' :{'$lte' : ?0 }}]}")
	List<HistoryTb> findAllByIngList(Pageable pageable, LocalDateTime LocalTime);

	@Query("{'dlvrdate' : { '$gt' : ?0 }}")
	Page<HistoryTb> findAllByWill(Pageable pageable, LocalDateTime LocalTime);

	// 전체 검색 & 날짜로 검색
	@Query("{$and : [{'arrivedate' : { '$lt' : ?0 }}, {'dlvrdate' : { '$gte' : ?1 }}, {'dlvrdate' : { '$lte' : ?2 }}]}")
	Page<HistoryTb> findAllByPpAndDate(Pageable pageable, LocalDateTime LocalTime, LocalDateTime keyword,
			LocalDateTime endDate);

	@Query("{$and :[ {arrivedate : { '$gte' : ?0} }, { 'dlvrdate' : {'$lte' : ?0 } }, { 'dlvrdate' : {'$gte' : ?1 }}, {'dlvrdate' : { '$lte' : ?2 }}]}")
	Page<HistoryTb> findAllByIngAndDate(Pageable pageable, LocalDateTime LocalTime, LocalDateTime keyword,
			LocalDateTime endDate);

	@Query("{$and : [{'dlvrdate' : { '$gt' : ?0 }}, { 'dlvrdate' : {'$gte' : ?1 }}, {'dlvrdate' : { '$lte' : ?2 }}]}")
	Page<HistoryTb> findAllByWillAndDate(Pageable pageable, LocalDateTime LocalTime, LocalDateTime keyword,
			LocalDateTime endDate);

	// 내 글 검색
	@Query("{$and : [ {'dlvrdate' : { '$gt' : ?0 }}, {'username' : ?1}] }")
	Page<HistoryTb> findAllByWillAndUsername(Pageable pageable, LocalDateTime time, String username);

	@Query("{$and :[ {arrivedate : {'$gte' : ?0} }, { 'dlvrdate' :{'$lte' : ?0 } }, {'username' : ?1} ] }")
	Page<HistoryTb> findAllByIngAndUsername(Pageable pageable, LocalDateTime time, String username);

	@Query("{$and : [ {'arrivedate' : { '$lt' : ?0 }}, {'username' : ?1}]}")
	Page<HistoryTb> findAllByPpAndUsername(Pageable pageable, LocalDateTime LocalTime, String username);

	// 내 글 검색 & 날짜로 검색
	@Query("{$and : [ {'dlvrdate' : { '$gt' : ?0 }}, {'username' : ?1 }, {'dlvrdate' : { '$gte' : ?2 }}, {'dlvrdate' : { '$lte' : ?3 }}] }")
	Page<HistoryTb> findAllByWillAndUsernameAndDate(Pageable pageable, LocalDateTime time, String username,
			LocalDateTime keyword, LocalDateTime endDate);

	@Query("{$and :[ {arrivedate : {'$gte' : ?0} }, { 'dlvrdate' :{'$lte' : ?0 } }, { 'dlvrdate' :{'$gte' : ?2 } }, {'dlvrdate' : { '$lte' : ?3 }}, {'username' : ?1} ] }")
	Page<HistoryTb> findAllByIngAndUsernameAndDate(Pageable pageable, LocalDateTime time, String username,
			LocalDateTime keyword, LocalDateTime endDate);

	@Query("{$and : [ {'arrivedate' : { '$lt' : ?0 }}, {'username' : ?1}, {'dlvrdate' : { '$gte' : ?2 } }, {'dlvrdate' : { '$lte' : ?3 }}]}")
	Page<HistoryTb> findAllByPpAndUsernameAndDate(Pageable pageable, LocalDateTime LocalTime, String username,
			LocalDateTime keyword, LocalDateTime endDate);

	// 내 글 & 카운트
	@Query(value = "{$and : [ {'dlvrdate' : { '$gt' : ?0 }}, {'username' : ?1 }] }", count = true)
	int findAllByWillAndUsernameAndDateAndCnt(LocalDateTime time, String username);

	@Query(value = "{$and :[ {arrivedate : {'$gte' : ?0} }, { 'dlvrdate' :{'$lte' : ?0 } }, {'username' : ?1} ] }", count = true)
	int findAllByIngAndUsernameAndDateAndCnt(LocalDateTime time, String username);

	@Query(value = "{$and : [ {'arrivedate' : { '$lt' : ?0 }}, {'dlvrdate' : { '$gte' : ?2 }}, {'username' : ?1}]}", count = true)
	int findAllByPpAndUsernameAndDateAndCnt(LocalDateTime LocalTime, String username, LocalDateTime thisMonth);

	// 카운트
	@Query(value = "{'dlvrdate' : { '$gt' : ?0 }}", count = true)
	int findAllByWillAndDateAndCnt(LocalDateTime time);

	@Query(value = "{$and :[ {arrivedate : {'$gte' : ?0} }, { 'dlvrdate' :{'$lte' : ?0 } }] }", count = true)
	int findAllByIngAndDateAndCnt(LocalDateTime time);

	@Query(value = "{$and : [ {'arrivedate' : { '$lt' : ?0 }}, {'dlvrdate' : { '$gte' : ?1 }}]}", count = true)
	int findAllByPpAndDateAndCnt(LocalDateTime LocalTime, LocalDateTime thisMonth);

	// 차량 날짜 검색
	@Query("{$and : [ {'carIndex' : ?0}, {'dlvrdate' : { '$gte' : ?1 }}, {'dlvrdate' : { '$lte' : ?2 }}]}")
	List<HistoryTb> findAllByCarnameAndDate(Long carIndex, LocalDateTime start, LocalDateTime end);

	List<HistoryTb> findByUsername(String username, Pageable pageable);
}
