package com.gunr.bookreviewcolumn.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

	Image findByImg(String string);
}
