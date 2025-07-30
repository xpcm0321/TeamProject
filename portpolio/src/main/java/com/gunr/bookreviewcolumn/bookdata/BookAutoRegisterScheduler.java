package com.gunr.bookreviewcolumn.bookdata;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookAutoRegisterScheduler {

    @Autowired
    private NaverBooks naverBooks;

    @Autowired
    private BookdataService service;

    private final ObjectMapper objectMapper = new ObjectMapper();  // Jackson 라이브러리

    @Scheduled(fixedRate = 60000)
    public void registerFromNaverApi() {
        try {
            // 1. 네이버 API에서 JSON 문자열 가져오기
            String json = naverBooks.getApi(1, "야구");  // 빈 문자열로 보내면 "book"으로 처리

            // 2. JSON → Map<String, Object> 파싱
            Map<String, Object> result = objectMapper.readValue(
                    json,
                    new TypeReference<Map<String, Object>>() {}
            );

            // 3. items 배열 파싱
            List<Map<String, Object>> items = (List<Map<String, Object>>) result.get("items");

            // 4. 각 아이템마다 DTO 생성 후 중복 체크 + 저장
            for (Map<String, Object> item : items) {
                // ISBN이 없으면 건너뛰기
                String isbn = Optional.ofNullable(item.get("isbn"))
                                      .map(Object::toString)
                                      .filter(s -> !s.isBlank())  // 빈 문자열도 거르기
                                      .orElse(null);

                String link = Optional.ofNullable(item.get("link"))
                                      .map(Object::toString)
                                      .filter(s -> !s.isBlank())
                                      .orElse(null);

                // 로그로 ISBN 확인
                log.info("처리 중: isbn={}, link={}", isbn, link);

                if (isbn == null || link == null) {
                    // 필수 값이 없으면 건너뛰기
                    log.warn("스킵됨 (필수값 누락): isbn={}, link={}", isbn, link);
                    continue;
                }

                // DTO 만들기
                BookdataDto dto = new BookdataDto();
                dto.setTitle(Optional.ofNullable(item.get("title")).map(Object::toString).orElse("제목 없음"));
                dto.setAuthor(Optional.ofNullable(item.get("author")).map(Object::toString).orElse("작자 미상"));
                dto.setPublisher(Optional.ofNullable(item.get("publisher")).map(Object::toString).orElse("출판사 없음"));
                dto.setPubdate(Optional.ofNullable(item.get("pubdate")).map(Object::toString).orElse(""));
                dto.setImage(Optional.ofNullable(item.get("image")).map(Object::toString).orElse(""));
                dto.setLink(link);
                dto.setIsbn(isbn);

                // 이미 DB에 있으면 기존 객체, 없으면 저장 후 반환
                Bookdata saved = service.saveOrFind(dto);

                // 저장된 결과 로그 출력
                if (saved.getId() != null) {
                    log.info("책 등록 완료: {}", saved.getTitle());
                } else {
                    log.info("기존 책 데이터 조회: {}", saved.getTitle());
                }
            }

        } catch (Exception e) {
            log.error("등록 실패 원인: " + e.getMessage(), e);
        }
    }
}
