package com.douzone.bit.pathfinder.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.bit.pathfinder.model.entity.CarTb;
import com.douzone.bit.pathfinder.model.entity.BranchTb;

import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.Pagination;
import com.douzone.bit.pathfinder.model.network.request.AdminCarRequest;
import com.douzone.bit.pathfinder.model.network.response.AdminCarResponse;
import com.douzone.bit.pathfinder.repository.AreaRepository;
import com.douzone.bit.pathfinder.repository.BranchRepository;
import com.douzone.bit.pathfinder.repository.CarRepository;

@Service
@Transactional
public class AdminCarService {

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private BranchRepository branchRepository;

	// branch read
	public Optional<CarTb> read(Long id) {

		return carRepository.findById(id);
	}

	// car create
	public Header<AdminCarResponse> create(AdminCarRequest request) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(request.getCarBuy(), format);

		CarTb car = CarTb.builder().carName(request.getCarName()).carFuel(request.getCarFuel())
				.carNumber(request.getCarNumber()).carBuy(dateTime).carArea(areaRepository.getOne(request.getCarArea()))
				.build();

		CarTb newCar = carRepository.save(car);

		return Header.OK(response(newCar));

	}

	// car page
	public Header<List<AdminCarResponse>> listpage(Pageable pageable) {
		Page<CarTb> cars = carRepository.findAll(pageable);

		List<AdminCarResponse> carResponseList = cars.stream().map(car -> response(car)).collect(Collectors.toList());

		Pagination pagination = Pagination.builder().totalPages(cars.getTotalPages()).totalElements(cars.getTotalElements())
				.currentPage(cars.getNumber()).currentElements(cars.getNumberOfElements()).build();
		return Header.OK(carResponseList, pagination);
	}

	// branch delete
	public Header delete(Long id) {
		return carRepository.findById(id).map(car -> {
			carRepository.delete(car);
			return Header.OK();
		}).orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	private AdminCarResponse response(CarTb car) {
		AdminCarResponse adminCarResponse = AdminCarResponse.builder().carIndex(car.getCarIndex()).carName(car.getCarName())
				.carFuel(car.getCarFuel()).carNumber(car.getCarNumber()).carBuy(car.getCarBuy())
				.carArea(car.getCarArea().getAreaName()).build();
		return adminCarResponse;
	}

	// public Header<HierarchyResponse> readCompany() {
	// Map<String, Boolean> state = new HashMap<String, Boolean>();
	//
	// state.put("opened", true);
	// state.put("selected", true);
	// HierarchyResponse company =
	// HierarchyResponse.builder().id("company:1").text("더존 공장").state(state)
	// .children(readArea()).build();
	//
	// return Header.OK(company);
	// }
	//
	// // 지점 조직도
	// public List<HierarchyResponse> readArea() {
	//
	// List<AreaTb> areas = areaRepository.findAll();
	// List<HierarchyResponse> areaList = areas.stream().map(area ->
	// areaOnlyResponse(area)).collect(Collectors.toList());
	// return areaList;
	// }
	//
	// // jsTree response
	// private HierarchyResponse areaOnlyResponse(AreaTb area) {
	// HierarchyResponse treeResponse = HierarchyResponse.builder().id("area:" +
	// area.getAreaIndex())
	// .text(area.getAreaName()).build();
	//
	// return treeResponse;
	// }
	// 차량 중복 검사
	public Boolean carCheck(String carNumber) {
		return !(carRepository.existsByCarNumber(carNumber));
	}

	// 지점 검색
	public Header<List<AdminCarResponse>> search(Pageable pageable, String searchType, String keyword, String selectedArea) {

		String treeId[] = selectedArea.split(":");
		String nodeType = treeId[0];
		Long nodeIndex = Long.parseLong(treeId[1]);
		
		Page<CarTb> cars = null;
		List<AdminCarResponse> carResponseList = null;
		
		if(nodeType.equals("company")) {
			switch (searchType) {

			case "carNumber":
				cars = carRepository.findByCarNumberLike("%" + keyword + "%", pageable);
				carResponseList = cars.stream().map(car -> response(car)).collect(Collectors.toList());
				break;
				
			case "carName":
				cars = carRepository.findByCarName(Double.parseDouble(keyword), pageable);
				carResponseList = cars.stream().map(car -> response(car)).collect(Collectors.toList());
				break;
				
			case "branch":
				List<CarTb> carList = carRepository.findBycarArea(areaRepository.getOne(Long.parseLong(keyword)));
				carResponseList = carList.stream().map(car -> response(car)).collect(Collectors.toList());

				return Header.OK(carResponseList);
			
			default:
				break;
			}
		}
		else {
		      switch (searchType) {
		      
		    case "area":
					cars = carRepository.findBycarArea(areaRepository.getOne(Long.parseLong(keyword)), pageable);
					carResponseList = cars.stream().map(car -> response(car)).collect(Collectors.toList());
					break;
					
		  	case "carNumber":
		  		
				cars = carRepository.findByCarAreaAndCarNumberLike(areaRepository.getOne(nodeIndex),"%" + keyword + "%", pageable);
		  		
				carResponseList = cars.stream().map(car -> response(car)).collect(Collectors.toList());
				break;
				
			case "carName":
				cars = carRepository.findByCarAreaAndCarName(areaRepository.getOne(nodeIndex), Double.parseDouble(keyword) , pageable);
				
				carResponseList = cars.stream().map(car -> response(car)).collect(Collectors.toList());
				break;
				
			default:
				break;
		      }
		}
		
	    if (cars.getTotalElements() == 0) {
			return Header.ERROR("조회 결과가 없습니다.");
		}
		Pagination pagination = Pagination.builder().totalPages(cars.getTotalPages()).totalElements(cars.getTotalElements())
				.currentPage(cars.getNumber()).currentElements(cars.getNumberOfElements()).build();

		return Header.OK(carResponseList, pagination);

	}

}