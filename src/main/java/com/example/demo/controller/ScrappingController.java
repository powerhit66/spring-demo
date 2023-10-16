package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Controller("/scrap")
public class ScrappingController {
	private String ELIN = "https://store.steampowered.com/app/2135150/Elin/";
	private String STEAM_RELEASE_DATE = "div.date";
	private String STEAM_TO_CART = "div.btn_addtocart";
	
	@RequestMapping("/scrap")
	// ゲームの発売日を参照する。また、購入できるかもチェックする
	public String getScrapList() throws IOException {
		Document target = Jsoup.connect(ELIN).get();
		String date = target.select(STEAM_RELEASE_DATE).text();
		String toCart = target.select(STEAM_TO_CART).text();
		
		System.out.println(date);
		System.out.println(toCart.equals(""));
		
		return "top";
	}
}
