package com.douzone.bit.pathfinder.model.network.request;

import java.util.List;

import com.douzone.bit.pathfinder.model.Marker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteSortRequest {

  private Long carIndex;

  private List<Marker> markerList;
}