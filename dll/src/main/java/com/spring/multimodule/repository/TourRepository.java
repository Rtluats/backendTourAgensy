package com.spring.multimodule.repository;

import com.spring.multimodule.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
	List<Tour> findByTitleContainsOrderByTitle(String title);
}
