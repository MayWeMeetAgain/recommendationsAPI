package com.annieryannel.recommendationsapp.adapters;

import com.annieryannel.recommendationsapp.models.User;
import com.annieryannel.recommendationsapp.service.ReviewService;
import com.annieryannel.recommendationsapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EvaluationAdapter {

    private final ReviewService reviewService;
    private final UserService userService;

    public Integer like(Long reviewId, String username) {
        return reviewService.like(reviewId, getUser(username));
    }

    public Integer unlike(Long reviewId, String username) {
        return reviewService.unlike(reviewId, getUser(username));
    }

    public Float rate(Long reviewId, String username, Integer rate) {
        return reviewService.rate(reviewId, getUser(username), rate);
    }

    private User getUser(String username) {
        return (User)userService.loadUserByUsername(username);
    }
}
