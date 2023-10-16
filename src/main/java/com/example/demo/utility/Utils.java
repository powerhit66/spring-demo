package com.example.demo.utility;

import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpSession;

public class Utils {

	public void setSession(HttpSession session, String loginType) {
		session.setAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
		session.setAttribute("loginType", loginType);
	}
}
