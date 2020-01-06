package com.douzone.bit.pathfinder.model.network.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class HierarchyResponse {

  private String id;
  
  private String text;
  
  private Map<String, Boolean> state;

  private List<HierarchyResponse> children;
}
