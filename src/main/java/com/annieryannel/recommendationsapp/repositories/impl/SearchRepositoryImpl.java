package com.annieryannel.recommendationsapp.repositories.impl;

import com.annieryannel.recommendationsapp.models.Review;
import com.annieryannel.recommendationsapp.repositories.SearchRepository;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SearchRepositoryImpl implements SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Review> search(String terms, Pageable pageable) {
        SearchResult<Review> result = Search.session(entityManager).search(Review.class)
                .where(f -> f.match()
                        .fields("title", "text")
                        .matching(terms)).fetch((int) pageable.getOffset(), pageable.getPageSize());

        return new PageImpl<>(result.hits(), pageable, result.total().hitCount());
    }

}

