package com.gunr.bookreviewcolumn.image;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageSeeder implements CommandLineRunner{

	private final ImageRepository imageRepository;
	
	@Override
	public void run(String... args) {
		if (imageRepository.count() == 0) {
			imageRepository.save(new Image("/images/profile1.png"));
			imageRepository.save(new Image("/images/profile2.png"));
			imageRepository.save(new Image("/images/profile3.png"));
			imageRepository.save(new Image("/images/profile4.png"));
			imageRepository.save(new Image("/images/profile5.png"));
		}
				
	}

}
