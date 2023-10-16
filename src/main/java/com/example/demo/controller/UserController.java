package com.example.demo.controller;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.UserEntity;
import com.example.demo.user.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Controllerであることを示すアノテーション、リクエストを受けた時に呼び出される
@SpringBootApplication
@Controller("/")
public class UserController {
	
	private final String MODE_NAME = "mode";
	private final String MODE_CREATE = "create";
	private final String MODE_DELETE = "delete";
	private final String MODE_MODIFY = "modify";
	private final String MODE_VIEW = "view";
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	public UserController(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}
	
	@RequestMapping("/profile")
    public String create(Model model) {
        return "profile.jsp";
    }
	
	@RequestMapping("/")
    public String top(Model model) {
		
        return "top";
    }
	
	@RequestMapping(value="/login") // ルーティング情報を提供。パスが/のHTTPリクエストはこちらのメソッドにマッピングすると指示する
	public String loginForm(Model model) {
		
		// ログインしている場合、TOP画面にリダイレクトする、それ以外の場合はログイン画面に遷移する
		Authentication auth = getAuth();
		System.out.println("credentials: " + auth.getDetails());
		
		if (auth.getAuthorities().toString().equals("[ROLE_ANONYMOUS]")) {
	        return "login";
		}
		
        return "redirect:/";
	}
	
	@RequestMapping("/logoutExecute")
	// ログアウト画面から確定ボタンを押下時に実行され、ユーザをログアウトする
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		// 認証情報をクリアする
		new SecurityContextLogoutHandler().logout(request, response, authentication);
	    return "redirect:/login";
	}
	
	@PostMapping("/create")
	public String createUser(@RequestBody String body, Model model, @ModelAttribute UserEntity user) {

		boolean isCreated = userDetailsServiceImpl.createUser(user);
		
		if (isCreated) {
			return "redirect:/";
		}
		
		model.addAttribute(this.MODE_NAME,this.MODE_CREATE);
		model.addAttribute("error", "入力されたユーザ名はすでに存在している");
		return "create";
	}
	
	@GetMapping("/create")
	public String showUserForm(Model model, @ModelAttribute UserEntity user) {
		
		if (getAuth().getAuthorities().toString().equals("[ROLE_ANONYMOUS]")) {
			model.addAttribute(this.MODE_NAME,this.MODE_CREATE);
			return "create";
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/delete")
	public String deleteUser(Model model, @ModelAttribute UserEntity user) {
		boolean isDeleted = userDetailsServiceImpl.deleteUser(user);
		
		if (isDeleted) {
			return "redirect:/logoutExecute";
		}
		
		model.addAttribute(this.MODE_NAME,this.MODE_DELETE);
		model.addAttribute("error","削除失敗");
		model.addAttribute("user", user);
		//model.addAttribute("session", session);
		return "create";
	}

	@GetMapping("/delete")
	public String deleteUserForm(Model model) {
		model.addAttribute(this.MODE_NAME,this.MODE_DELETE);
		UserEntity user = getUserInfo();
		model.addAttribute("user", user);
		//model.addAttribute("session", session);
		return "create";
	}
	
	@PostMapping("/modify")
	public String modifyUser(Model model, @ModelAttribute UserEntity user) {
		boolean isModified = userDetailsServiceImpl.modifyUser(user);
		
		if (isModified) {
			return "redirect:/";
		}
		model.addAttribute(this.MODE_NAME,this.MODE_MODIFY);
		model.addAttribute("error","編集失敗");
		model.addAttribute("user", user);
		//model.addAttribute("session", session);
		return "create";
	}
	
	@GetMapping("/modify")
	public String modifyUserForm(Model model) {
		UserEntity user = getUserInfo();
		model.addAttribute(this.MODE_NAME,this.MODE_MODIFY);
		model.addAttribute("user", user);
		//model.addAttribute("session", session);
		return "create";
	}
	
	@GetMapping("/userDetail")
	public String UserDetailForm(Model model) {
		model.addAttribute(this.MODE_NAME,this.MODE_VIEW);
		UserEntity user = getUserInfo();
		model.addAttribute("user", user);
		return "create";
	}
	
	@GetMapping("/userinfo")
	public String index(@AuthenticationPrincipal OidcUser user, Model model) {
		//model.addAttribute("session", session);
		
	    return "top";
	}
	
	// 認証情報を取得する
	private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
	}
	
	// ユーザ情報を取得
	private UserEntity getUserInfo() {
		return userDetailsServiceImpl.getUserInfo(getAuth().getName()).get(0);
	}
}