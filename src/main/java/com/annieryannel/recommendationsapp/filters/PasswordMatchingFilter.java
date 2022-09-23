package com.annieryannel.recommendationsapp.filters;

import com.annieryannel.recommendationsapp.models.Review;
import com.annieryannel.recommendationsapp.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component("passwordMatchingFilter")
@RequiredArgsConstructor
public class PasswordMatchingFilter {

    final ReviewRepository reviewRepository;

//    public boolean passwordMatchs(String password, String passwordConfirmation) {
//        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NoSuchElementException("Review with id " + reviewId + " does not found"));
//        String name = authentication.getName();
//        String authorName = review.getAuthor().getUsername();
//        return name.equals(authorName);
//    }
}
