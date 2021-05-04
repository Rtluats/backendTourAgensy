package com.spring.multimodule.repository;

import com.spring.multimodule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long> {
	List<Comment> findAllByPriceListIdOrderByLocalDateTime(Long priceListId);
}
