package com.douzone.bit.pathfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.douzone.bit.pathfinder.model.entity.AreaTb;

@Repository
public interface AreaRepository extends JpaRepository<AreaTb, Long> {

  @Query(value = "select area_index, area_name from area_tb", nativeQuery = true)
  public List<Object> findAreaName();

}
