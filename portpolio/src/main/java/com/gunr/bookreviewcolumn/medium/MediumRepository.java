package com.gunr.bookreviewcolumn.medium;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MediumRepository extends JpaRepository<Medium, Long> {
	@Query("select m from Medium m where m.name = :name")
	Optional<Medium> findByName(@Param("name") String name);
}
