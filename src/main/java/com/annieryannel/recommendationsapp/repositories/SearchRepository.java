package com.annieryannel.recommendationsapp.repositories;

import com.annieryannel.recommendationsapp.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepository {

    Page<Review> search(String terms, Pageable pageable);

}