package com.gunr.bookreviewcolumn.openai;

import java.util.List;

import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AINicknameService {

    private final OpenAiService openAiService;

    public String generateNickname() {
        ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(
                        new ChatMessage("user", "최근 5년간 인기있었던 소설 속에 등장한 실제 등장인물 이름 1개 출력해줘."
                        						+ "작품 속 실제 등장인물 이름 그대로 사용해줘."
                        						+ "한 줄로만, 작품명 없이 이름만 한국어로 출력해줘")
                )	)
                .maxTokens(50)
                .temperature(0.9)
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(chatRequest);
        
        String response = result.getChoices().get(0).getMessage().getContent().trim();
        String nickname = response.split("\n")[0].trim();
        // 특수문자 , 숫자 제거
        nickname = nickname.replaceAll("[^가-힣a-zA-Z]", "");
        // 10자 이내 
        if (nickname.length() > 6) {
            nickname = nickname.substring(0, 6); 
        } else if (nickname.length() < 2) {
            nickname = "가온누리"; // 2자 이하면 기본 닉
        }
        
        return nickname;
    }
}

