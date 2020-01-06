package com.douzone.bit.pathfinder.db;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.douzone.bit.pathfinder.PathfinderApplicationTests;
import com.douzone.bit.pathfinder.model.entity.AreaTb;
import com.douzone.bit.pathfinder.repository.AreaRepository;

public class AreaRepositoryTest extends PathfinderApplicationTests {

	@Autowired
	private AreaRepository areaRepository;
	
	public void create() {
		AreaTb area;
		
		String areaValue[] = {"경기도", "강원도", "충청남도", "충청북도",
				"경상북도", "경상남도", "전라남도", "전라북도"};
		
		for (int i = 0; i < areaValue.length; i++) {
			area = new AreaTb();
			area.setAreaName(areaValue[i]);
			
			areaRepository.save(area);
		}
	}
	
	@Test
	public void read() {
		List<AreaTb> area = areaRepository.findAll();
		
		area.forEach(result -> {
			System.out.println(result.getAreaName());
		});
	}
}
