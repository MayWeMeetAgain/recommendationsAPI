package com.annieryannel.recommendationsapp.filters;

import com.annieryannel.recommendationsapp.models.Review;
import com.annieryannel.recommendationsapp.repositories.ReviewRepository;
import com.annieryannel.recommendationsapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component("reviewSecurity")
@RequiredArgsConstructor
public class ReviewSecurity {

    final ReviewRepository reviewRepository;

    public boolean isAuthor(Authentication authentication, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NoSuchElementException("Review with id " + reviewId + " does not found"));
        String name = authentication.getName();
        String authorName = review.getAuthor().getUsername();
        return name.equals(authorName);
    }
}