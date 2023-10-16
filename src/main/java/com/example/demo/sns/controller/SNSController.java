package com.example.demo.sns.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.sns.entity.BbsEntity;
import com.example.demo.sns.entity.BbsRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@ComponentScan
public class SNSController{
	
	@Autowired
	BbsRepository bbsRepository;
	
	@Autowired
	HttpSession session;
	
	ZoneId z = ZoneId.of( "Asia/Tokyo" ) ;  
	ZonedDateTime zdt = ZonedDateTime.now(z);
	
	@GetMapping("/bbs")
	public String bbs_get(Model model, Pageable pageable) {
		List<BbsEntity> bbsList = bbsRepository.findAll();
		
		Page<BbsEntity> pages = bbsRepository.findAll(pageable);
		
		model.addAttribute("username", (String)session.getAttribute("username"));
		model.addAttribute("bbsList", bbsList);
		model.addAttribute("pages", pages);
		
		return "sns/bbs.jsp";
	}
	
	
	@PostMapping("/bbs")
	public String bbs_post(Model model, @ModelAttribute BbsEntity bbsEntity) throws Exception { // ModelAttributeを宣言することでHtmlのFormを取得する
		String username = (String)session.getAttribute("username");
		Integer maxId = bbsRepository.findMaxId();
		maxId = maxId == null ? 1: maxId+1;
		bbsEntity.setCreated_time(zdt);
		bbsEntity.setUpdated_time(zdt);
		bbsEntity.setId(maxId);
		bbsEntity.setUsername(username);
		
		
		try {
			bbsRepository.save(bbsEntity);	
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return "redirect:bbs";
	}
	
	@PostMapping("/bbs_editForm")
	public String bbs_editForm(HttpServletRequest request, Model model, @ModelAttribute BbsEntity bbsEntity) throws Exception {
		String username = bbsEntity.getUsername();
		Integer id = bbsEntity.getId();
		bbsEntity = bbsRepository.findByCompositePrimaryKey(id, username);
		
		String mode = (String) request.getParameter("submit");
		
		// 返信の場合、内容に＞＞を付ける
		if(mode.equals("reply")) {
			model.addAttribute("reply", bbsEntity.getContent()+ ">>" + bbsEntity.getId());
		}
		
		System.out.println("id: " +  id + ", username: " + username + " ,mode: " + mode);
		model.addAttribute("bbsEntity", bbsEntity);
		model.addAttribute("mode", mode);
		
		return "sns/bbsEdit.jsp";
	}
	
	@PostMapping("/bbs_edit")
	public String bbs_edit(HttpServletRequest request, Model model, @ModelAttribute BbsEntity bbsEntity) throws Exception {
		String username = (String)session.getAttribute("username");
		String mode = (String) request.getParameter("submit");
	
		model.addAttribute("bbsEntity", bbsEntity);
		model.addAttribute("mode", mode);
		
		return "sns/bbsEdit.jsp";
	}
	
	@PostMapping("/bbs_delete")
	public String bbs_delete(Model model, @ModelAttribute BbsEntity bbsEntity) throws Exception {
		try {
			bbsRepository.delete(bbsEntity);	
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return "redirect:bbs";
	}
	
	@PostMapping("/bbs_modify")
	public String bbs_modify(Model model, @ModelAttribute BbsEntity bbsEntity) throws Exception {
		try {
			bbsEntity.setUpdated_time(zdt);
			bbsRepository.save(bbsEntity);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return "redirect:bbs";
	}
	
	@PostMapping("/bbs_reply")
	public String bbs_reply(Model model, @ModelAttribute BbsEntity bbsEntity, @RequestParam String reply) throws Exception {
		String username = (String)session.getAttribute("username");
		Integer maxId = bbsRepository.findMaxId();
		maxId = maxId == null ? 1: maxId+1;
		bbsEntity.setCreated_time(zdt);
		bbsEntity.setUpdated_time(zdt);
		bbsEntity.setId(maxId);
		bbsEntity.setUsername(username);
		bbsEntity.setContent(reply + "\n" + bbsEntity.getContent());
		
		
		try {
			bbsRepository.save(bbsEntity);	
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return "redirect:bbs";
	}
	
}