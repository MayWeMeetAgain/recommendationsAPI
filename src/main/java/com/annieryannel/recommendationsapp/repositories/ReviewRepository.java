package com.annieryannel.recommendationsapp.repositories;

import com.annieryannel.recommendationsapp.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, SearchRepository {

    List<Review> findAllByAuthorId(Long authorId);
    Page<Review> findAll(Specification specification, Pageable pageable);
    List<Review> findAllByOrderByIdDesc();

}
