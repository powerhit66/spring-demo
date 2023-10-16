package com.example.demo.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity // Spring Security用のアノテーション
public class WebSecurityConfig {

	@Autowired
	AuthenticationSuccessHandlerImpl auth;
	
	@Bean 
	// 使用するサーブレットの種類が多数存在する場合に使用する
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector)
    {
        return new MvcRequestMatcher.Builder(introspector);
    }
	
	@Bean // 戻り値をコンテナに保存するために必要なアノテーション
	SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers(mvc.pattern("/home")
						, mvc.pattern("/css/**") //cssの場合、static/がルートとなるため/cssと書く
						, mvc.pattern("/create")
						, mvc.pattern("/js/**")
						, mvc.pattern("/img/**")
						).permitAll() // create、/home、js、imgとcssフォルダは認証なし
				.anyRequest().authenticated() // それ以外のページはすべて認証が必要
			
			// フォームのログイン定義
			).formLogin((form) -> form
				.loginPage("/login") //　自作のログインページを利用する場合に書く。書かない場合はデフォルトのページに
				//.successForwardUrl("/") //ログイン成功した時にリダイレクトするハンドラ（Controller）
				.successHandler(auth) // ログイン成功後の処理ハンドラを呼ぶ
				//.defaultSuccessUrl("/") // ハンドラがいらず、html/jspを呼ぶだけの場合はこの定義
				.permitAll()
				
			// oauth2（SNSのログイン）定義、デフォルトを利用する場合はCustomizer.withDefaults()を引数に
			).oauth2Login(loginConfigurer
				-> loginConfigurer.loginPage("/login") // ログインページ：/login、ただ実際のログインはhttp://localhost:8080/oauth2/authorization/google
				.successHandler(auth) // ログイン成功後の処理ハンドラを呼ぶ
				//.defaultSuccessUrl("/userinfo") // ログイン成功の場合、/userinfoに遷移する
				.permitAll()
			)
			.logout((logout) -> logout.permitAll()); // ログアウト後ログイン画面に遷移できる設定

		return http.build();
	}
	
	/*
	 メモリーにユーザ情報を書き込む時の書き方
	 * 
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("demo")
				.password("demo")
				//.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}*/
	
	 // パスワードの暗号化に使用するクラス、必須。
	 @Bean
	 PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}