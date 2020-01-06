package com.douzone.bit.pathfinder.model.network.response;

import javax.servlet.http.Cookie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
public class SignResponse {
	
	private String token;
	
	private Cookie cookie;
	
	public SignResponse(Cookie cookie) {
		this.cookie = cookie;
	}
}
