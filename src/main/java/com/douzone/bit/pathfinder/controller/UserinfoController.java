package com.douzone.bit.pathfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.request.AdminUserRequest;
import com.douzone.bit.pathfinder.model.network.response.AdminUserResponse;
import com.douzone.bit.pathfinder.service.UserinfoService;

@RestController
@RequestMapping("/userinfo")
public class UserinfoController {

	@Autowired
	UserinfoService userinfoService;

	@GetMapping({ "", "/" })
	public ModelAndView userInfo() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/userinfo");

		return mv;
	}
	
	@PutMapping("")
	public Header<AdminUserResponse> userUpdate(@RequestBody AdminUserRequest request, Errors errors) {

		if (errors.hasErrors()) {
			return Header.ERROR(errors);
		}

		return userinfoService.update(request);
	}
}