package com.douzone.bit.pathfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.response.HistoryResponse;
import com.douzone.bit.pathfinder.model.network.response.HistoryRoutesResponse;
import com.douzone.bit.pathfinder.service.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {

	@Autowired
	HistoryService historyService;

	@GetMapping({ "", "/" })
	public ModelAndView history(ModelAndView mv, HttpServletRequest request) {

		mv.setViewName("/history");
		return mv;
	}

	@GetMapping("/gethistory.do")
	public Header<List<HistoryResponse>> getHistory(
			@RequestParam("page") int page, 
			@RequestParam("id") String id,
			@RequestParam("myhistory") boolean myhistory, 
			@RequestParam(value = "keyword", required = false) 
			String keyword) {

		return historyService.readHistory(page, id, myhistory, keyword);
	}

	@GetMapping("/getroutes.do")
	public Header<List<HistoryRoutesResponse>> getRoutes(@RequestParam("routesIndex") ObjectId id) {

		return historyService.readRoutes(id);
	}

	@DeleteMapping("/removeroutes.do")
	public Header<String> removeRoutes(@RequestBody ObjectId id) {

		return historyService.removeRoutes(id);
	}
}