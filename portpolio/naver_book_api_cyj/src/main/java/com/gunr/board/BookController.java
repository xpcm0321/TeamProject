package com.gunr.board;

import java.net.URI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class BookController {
	
	String clientId = "ECGnyVl0cYNSGGz2T4yQ"; 		
	String clientSecret = "ypWbpX4tos";

	@GetMapping("/book/list")
	public String list() {   
        return "/book/list";
	}
	
//	@GetMapping("/book/result")
//	public String result(@RequestParam("bookname") String text, Model model) {
//	    log.info("Received text: {}", text);
//	    model.addAttribute("text", text);
//	}
//	
//	@GetMapping("/api/book")
//    public ResultVO getBooks(@RequestParam(name = "query", defaultValue = "스프링부트") String query) {
//        return WebClientConfig.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/v1/search/book.json")
//                        .queryParam("query", query)
//                        .queryParam("display", 10)
//                        .queryParam("start", 1)
//                        .queryParam("sort", "sim"));
//	}
}