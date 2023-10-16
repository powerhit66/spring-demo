package com.example.demo.securingweb;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	//　Httpリクエストに対するマッピング情報： /homeの場合はhome.htmlを返す、単純にviewを返す場合はここの定義で十分
	// 処理が必要な場合はcontrollerのrequestMappingで実施する
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/logoutExecute").setViewName("logoutExecute");
		registry.addViewController("/logout").setViewName("logoutConfirm");
	}
	
    @Bean
    // JSP用のresolver定義
    InternalResourceViewResolver jspResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("WEB-INF/views/"); // WEB-INF/views/がルートパスに設定
        //resolver.setSuffix(".jsp"); // Thymeleafと併用するときに拡張子を定義するとうまくいかない
        resolver.setViewNames("*.jsp"); // WEB-INF/views/がルートパスに設定
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(2); //　優先順位=2（Thymeleaf優先）
        return resolver;
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        //ページ単位に表示する件数
        resolver.setFallbackPageable(PageRequest.of(0, 5));
        argumentResolvers.add(resolver);
        //super.addArgumentResolvers(argumentResolvers);
    }
}