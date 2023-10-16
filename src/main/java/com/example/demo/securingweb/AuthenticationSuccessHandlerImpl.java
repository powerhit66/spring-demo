package com.example.demo.securingweb;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/*
 * セッションを設定するクラス
 * */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Autowired HttpSession session; //autowiring session

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
		// ログインしている場合、TOP画面にリダイレクトする、それ以外の場合はログイン画面に遷移する
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String loginType;
		String username;
		
		if (OAuth2AuthenticationToken.class.isInstance(auth)) {
			loginType = "oauth";
			String displayName = ((OidcUser) auth.getPrincipal()).getFullName();
			username = auth.getName();
			session.setAttribute("display_name", displayName);
			
		} else {
			loginType = "form";
			username = auth.getName();
		}
		
		session.setAttribute("loginType", loginType);
		session.setAttribute("username", username);
		
        response.sendRedirect(request.getContextPath() + "/");
    }
}
