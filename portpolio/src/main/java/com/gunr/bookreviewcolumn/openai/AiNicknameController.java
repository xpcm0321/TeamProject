package com.gunr.bookreviewcolumn.openai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AiNicknameController {

	 private final AINicknameService aiNicknameService;

	    @GetMapping("/nickname/generate")
	    public ResponseEntity<String> generateNickname() {
	        String nickname = aiNicknameService.generateNickname();
	        return ResponseEntity.ok(nickname);
	    }
}
