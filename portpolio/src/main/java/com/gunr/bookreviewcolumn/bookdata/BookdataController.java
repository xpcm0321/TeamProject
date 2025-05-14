package com.gunr.bookreviewcolumn.bookdata;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gunr.bookreviewcolumn.review.Review;
import com.gunr.bookreviewcolumn.review.ReviewService;

@Controller
//@RestController
//@RequestMapping("/admin/bookdata")
public class BookdataController {
	@Autowired NaverBooks api;
	@Autowired BookdataService bookdataService;
	@Autowired BookdataRepository bookdataRepository;
	@Autowired ReviewService reviewService;

//	private BookdataService bookdataService;
//
//    @Autowired
//    public BookdataController(BookdataService bookdataService) {
//		this.bookdataService = bookdataService;
//    }

//    // API로 데이터를 반환
//    @GetMapping("/getBookData")
//    public ResponseEntity<List<Bookdata>> getBookData() {
//        List<Bookdata> bookData = bookdataService.getBookdataFromDatabase();
//        return ResponseEntity.ok(Bookdata);
//    }
	
	@GetMapping("/naver/books")
	public String booklist() {
//		model.addAttribute(model)
		return "books";
	}
	
	@GetMapping("/board/main")
	public String mainPage(@RequestParam(value = "sort", defaultValue = "recent") String sort, Model model) {
		List<Bookdata> books = bookdataService.findAll();  // 또는 findAll()
		model.addAttribute("bookList", books);
		
		List<Review> reviews = sort.equals("like") ? reviewService.getTop6ByLikes() : reviewService.getTop6RecentReviews();
		model.addAttribute("recentReviews", reviews);
		model.addAttribute("sort", sort);
		
		return "board/main";
	}
	
	@GetMapping("/naver/books/detail")
	public String bookdetail() {
		return "detail";
	}
	
	@GetMapping("/naver/books/import")
	public String bookimport() {
		return "import";
	}
	
	@GetMapping("/naver/books/new")
	public String booknew2() {
		return "bookdata/new";
	}
	
	@GetMapping("/naver/books/new/{title}")
	public String booknew(Model model, @PathVariable("title") String title) {
		return "bookdata/new";
	}
	
	@GetMapping(value = "/naver/booksapi/{search}", produces = "application/json; charset=UTF-8")

	@ResponseBody
	public String booksJson(@PathVariable("search") String search, @PathVariable("start") int start) throws IOException { // 네트워크 통신에서 실패하면 에러가 뜰 수 있음
		System.out.println(search);
		return api.getApi(start, search);
	}
	
	@GetMapping("/book-search")
	public String bookSearchPage() {
		return "bookSearch";
	}
	
	@PostMapping("/book/select")
	@ResponseBody
	public Long selectBook(@RequestBody BookdataDto dto) {
	    Bookdata saved = bookdataService.saveOrFind(dto);
	    return saved.getId();
	}
	
	@GetMapping("/tag-search")
	public String bookSelectPage() {
		return "tagAdd";
	}
	
	////////////////////////////////////////
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<String> saveBook(@RequestBody BookdataDto dto) {
		bookdataService.save(dto);
		return ResponseEntity.ok("등록 완료!");
	}
	
	@GetMapping("/list")
   public String bookList(Model model, @PageableDefault(page = 1, size = 5, sort = "title", direction = Direction.ASC) Pageable pageable) {
      List<Bookdata> books = bookdataRepository.findAll();
      model.addAttribute("bookList", bookdataService.BookdataList(pageable));
      return "admin/adminpage_list";
   }

	@GetMapping("/book-search-popup")
	public String bookSearchPage(Model model) {
	    List<Bookdata> bookList = bookdataService.findAll(); // DB에서 저장된 책들만
	    model.addAttribute("bookList", bookList);
	    return "bookSearch"; // templates/bookSearch.html 로 이동
	}


	
//	String clientId = "ECGnyVl0cYNSGGz2T4yQ"; 		
//	String clientSecret = "ypWbpX4tos";
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