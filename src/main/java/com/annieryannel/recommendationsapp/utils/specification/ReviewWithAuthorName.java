package com.annieryannel.recommendationsapp.utils.specification;

import com.annieryannel.recommendationsapp.models.Review;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class ReviewWithAuthorName implements Specification<Review> {

    private String authorName;

    @Override
    public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (authorName == null)
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        return criteriaBuilder.equal(root.get("author").get("username"), authorName);
    }
}
