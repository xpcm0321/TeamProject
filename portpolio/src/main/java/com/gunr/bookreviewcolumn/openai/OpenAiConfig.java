package com.gunr.bookreviewcolumn.openai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.*;

@Configuration
public class OpenAiConfig {
	
	@Value("${openai.api.key}")
	private String apiKey;
	
    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(apiKey);
    }
}
