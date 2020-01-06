package com.douzone.bit.pathfinder.model.network.request;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.douzone.bit.pathfinder.model.dto.MongoRoutesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteInsertRequest {

  private Long carIndex;

  private String dep;

  private String arvl;

  private String dlvrdate;

  private String arrivedate;
  
  private String imgSrc;

  private Integer fee;

  private String time;

  private Double dist;
  
  private String sortType;

  private List<MongoRoutesDTO> routes;
}
