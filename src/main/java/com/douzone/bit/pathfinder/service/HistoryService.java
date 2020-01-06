package com.douzone.bit.pathfinder.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.douzone.bit.pathfinder.model.dto.MongoRoutesDTO;
import com.douzone.bit.pathfinder.model.entity.mongodb.HistoryTb;
import com.douzone.bit.pathfinder.model.entity.mongodb.RoutesTb;
import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.Pagination;
import com.douzone.bit.pathfinder.model.network.response.HistoryResponse;
import com.douzone.bit.pathfinder.model.network.response.HistoryRoutesResponse;
import com.douzone.bit.pathfinder.repository.CarRepository;
import com.douzone.bit.pathfinder.repository.mongodb.HistoryRepository;
import com.douzone.bit.pathfinder.repository.mongodb.RoutesRepository;
import com.douzone.bit.pathfinder.util.S3Uploader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class HistoryService {
	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	private RoutesRepository routesRepository;

	@Autowired
	private CarRepository carRepository;
	
	private final S3Uploader s3Uploader;

	public Header<List<HistoryResponse>> readHistory(int page, String id, boolean myhistory, String keyword) {
		Pageable pageable;
		Page<HistoryTb> historys = null;
		String userName = null;
		LocalDateTime date = null;
		LocalDateTime endDate = null;
		
		if (myhistory) {
			userName = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		
		if (keyword != null && keyword == "") {
			keyword = null;
		}

		try {
			if (keyword != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				LocalDate localDate = LocalDate.parse(keyword, formatter);

				date = localDate.atTime(0, 0);
				endDate = localDate.atTime(23, 59);					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		LocalDateTime currentDate = LocalDateTime.now();

		switch (id) {
		case "will":
			pageable = PageRequest.of(page, 10, Sort.by("regdate").descending());
			if (keyword == null) {
				historys = (myhistory) ? historyRepository.findAllByWillAndUsername(pageable, currentDate, userName)
						: historyRepository.findAllByWill(pageable, currentDate);
			} else {
				historys = (myhistory)
						? historyRepository.findAllByWillAndUsernameAndDate(pageable, currentDate, userName, date, endDate)
						: historyRepository.findAllByWillAndDate(pageable, currentDate, date, endDate);
			}

			break;
		case "ing":
			pageable = PageRequest.of(page, 10, Sort.by("dlvrdate").descending());
			if (keyword == null) {
				historys = (myhistory) ? historyRepository.findAllByIngAndUsername(pageable, currentDate, userName)
						: historyRepository.findAllByIng(pageable, currentDate);
			} else {
				historys = (myhistory) ? historyRepository.findAllByIngAndUsernameAndDate(pageable, currentDate, userName, date, endDate)
						: historyRepository.findAllByIngAndDate(pageable, currentDate, date, endDate);
			}

			break;
		case "pp":
			pageable = PageRequest.of(page, 10, Sort.by("dlvrdate").descending());
			if (keyword == null) {
				historys = (myhistory) ? historyRepository.findAllByPpAndUsername(pageable, currentDate, userName)
						: historyRepository.findAllByPp(pageable, currentDate);
			} else {
				historys = (myhistory) ? historyRepository.findAllByPpAndUsernameAndDate(pageable, currentDate, userName, date, endDate)
						: historyRepository.findAllByPpAndDate(pageable, currentDate, date, endDate);
			}

			break;
		}

		if (historys.getTotalElements() == 0) {
			return Header.ERROR("조회 결과가 없습니다.");
		}

		List<HistoryResponse> historyList = historys.stream().map(history -> historyResponse(history))
				.collect(Collectors.toList());

		if (historyList == null) {
			return Header.ERROR("에러가 발생하였습니다.");
		}

		Pagination pagination = Pagination.builder().totalPages(historys.getTotalPages())
				.totalElements(historys.getTotalElements()).currentPage(historys.getNumber())
				.currentElements(historys.getNumberOfElements()).build();

		return Header.OK(historyList, pagination);
	}

	// TODO 이부분 코드 문제있음.
	public Header<List<HistoryResponse>> readRecentlyHistoryUseHome() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String username = securityContext.getAuthentication().getName();

		Pageable pageable = PageRequest.of(0, 5, Sort.by("regdate").descending());
		List<HistoryTb> historys = historyRepository.findByUsername(username, pageable);
		
		if (historys.isEmpty()) {
			return Header.ERROR("조회 결과가 없습니다.");
		}
		List<HistoryResponse> historyList = historys.stream().map(history -> historyResponseUseHome(history))
				.collect(Collectors.toList());

		return Header.OK(historyList);
	}

	public Header<List<HistoryResponse>> readTodayHistoryUseHome() {
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime todayStartDate = LocalDateTime.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth(),0 ,0 ,0);
		LocalDateTime todayEndDate = LocalDateTime.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()+1 ,0 ,0 ,0);
		Pageable pageable = PageRequest.of(0, 5, Sort.by("regdate").descending());
		List<HistoryTb> historys = historyRepository.findTodayList(pageable, todayEndDate, todayStartDate);
		//List<HistoryTb> historys = historyRepository.findAllByIngList(pageable, currentDate);
		if (historys.isEmpty()) {
			return Header.ERROR("조회 결과가 없습니다.");
		}

		List<HistoryResponse> historyList = historys.stream().map(history -> historyResponseUseHome(history))
				.collect(Collectors.toList());
		
		return Header.OK(historyList);
	}

	public Header<List<HistoryRoutesResponse>> readRoutes(ObjectId id) {

		RoutesTb routesTb = routesRepository.findById(id);
		
		List<HistoryRoutesResponse> historyRoutesResponseList = routesTb.getDetail()
				.stream().map(route -> routesResponse(route)).collect(Collectors.toList());

		return Header.OK(historyRoutesResponseList);
	}

	public Header<String> removeRoutes(ObjectId id) {

		HistoryTb history = historyRepository.findById(id);
		historyRepository.deleteById(id.toString());
		routesRepository.deleteById(history.getRoutes().toString());
		
		String[] s3Key = history.getImgSrc().split("https://bit-pathfinder.s3.ap-northeast-2.amazonaws.com/");
		
		s3Uploader.deleteFileFromS3Bucket(s3Key[1]);
		
		return Header.OK();
	}

	private HistoryResponse historyResponse(HistoryTb history) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		String carNumber = carRepository.findByCarIndex(history.getCarIndex()).getCarNumber();

		HistoryResponse response = HistoryResponse.builder().id(history.getId())
				.regdate(history.getRegdate().format(formatter)).username(history.getUsername()).carname(carNumber)
				.dep(history.getDep()).arvl(history.getArvl()).dist(history.getDist()).fee(history.getFee())
				.dlvrdate(history.getDlvrdate().format(formatter))
				.imgSrc(history.getImgSrc()).time(history.getTime())
				.sortType(history.getSortType())
				.arrivedate(history.getArrivedate().format(formatter)).routes(history.getRoutes().toString()).build();

		return response;
	}

	private HistoryResponse historyResponseUseHome(HistoryTb history) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String carNumber = carRepository.findByCarIndex(history.getCarIndex()).getCarNumber();

		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime todayEndDate = LocalDateTime.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()+1 ,0 ,0 ,0);
		
		LocalDateTime start = history.getDlvrdate();// 출발
		LocalDateTime end = history.getArrivedate();// 도착

		int stat = 0;
		if (currentDate.isBefore(start)) {
			stat = 1;
		} else if (currentDate.isAfter(end)) {
			stat = -1;
		}
		if(currentDate.isAfter(start) && todayEndDate.isBefore(end)) {
			stat = 2;
		}
		

		HistoryResponse response = HistoryResponse.builder().id(history.getId())
				.regdate(history.getRegdate().format(formatter)).username(history.getUsername()).carname(carNumber)
				.dep(history.getDep()).arvl(history.getArvl()).dist(history.getDist()).fee(history.getFee())
				.dlvrdate(history.getDlvrdate().format(formatter)).arrivedate(history.getArrivedate().format(formatter))
				.stat(stat).routes(history.getRoutes().toString()).build();

		return response;
	}

	private HistoryRoutesResponse routesResponse(MongoRoutesDTO routes) {
		
		int second = (int) Math.floor(routes.getRtime());
		
		String result = intToTime(second);
		
		HistoryRoutesResponse response = HistoryRoutesResponse.builder()
				.rdist(routes.getRdist()).rtime(result).rdep(routes.getRdep())
				.rarvl(routes.getRarvl()).rfee(routes.getRfee())
				.build();

		return response;
	}

	public Header<List<HistoryTb>> historyAll() {
		return Header.OK(historyRepository.findAll());
	}
	
	public String intToTime(int second) {
		int hours = second / 3600;
		int min = (second - (hours * 3600)) / 60;
		int sec = second - (hours * 3600) - (min * 60);
		
		String sHours = "", sMin = "", sSec = "";
		
		if (hours < 10) {
			sHours = "0" + hours;
		} else {
			sHours = Integer.toString(hours);
		}
		
		if (min < 10) {
			sMin = "0" + min;
		} else {
			sMin = Integer.toString(min);
		}
		
		if (sec < 10) {
			sSec = "0" + sec;
		} else {
			sSec = Integer.toString(sec);
		}
		
		return sHours + ":" + sMin + ":" + sSec;
	}

	public double todayHistoryPercent() {

		// 현재시간
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime todayDate = LocalDate.now().atTime(0, 0);
		LocalDateTime tomorrowDate = todayDate.plusDays(1);

		int denominator = historyRepository.findAllByTotalToday(todayDate, tomorrowDate);
		int molecular = historyRepository.findAllByDoingToday(todayDate, tomorrowDate, currentDate);

		if (denominator == 0)
			return -1;
		double result = Math.round(((double) molecular / (double) denominator) * 1000) / 10.00;
		return result;
	}
}