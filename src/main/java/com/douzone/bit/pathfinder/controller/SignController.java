package com.douzone.bit.pathfinder.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.bit.pathfinder.model.dto.SignDTO;
import com.douzone.bit.pathfinder.model.network.request.SignRequest;
import com.douzone.bit.pathfinder.util.JwtUtil;

@RestController
@RequestMapping("/")
public class SignController {

	/*
	 * 로그인한 유저 정보 가져오기 SecurityContextHolder.getContext()
	 * .getAuthentication().getPrincipal()
	 */

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@GetMapping({ "", "/", "/login" })
	public ModelAndView loginPage() {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("/defaultpage/login");

		return mv;
	}

	@PostMapping("/authenticate.do")
	public ModelAndView createAuthenticationToken(SignRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mv = new ModelAndView();

		try {
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getId(), request.getPwd()));

			SecurityContext securityContext = SecurityContextHolder.getContext();

			securityContext.setAuthentication(auth);
			String token = jwtTokenUtil.generateToken((SignDTO) auth.getPrincipal());

			Cookie cookie = new Cookie("token", token);

			cookie.setMaxAge(6 * (60 * 60));
			
			response.addCookie(cookie);

			mv.setViewName("redirect:/home");

		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect userId or password", e);
		}

		return mv;
	}

}