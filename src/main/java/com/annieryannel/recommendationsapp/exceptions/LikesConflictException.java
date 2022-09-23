package com.annieryannel.recommendationsapp.exceptions;


import com.annieryannel.recommendationsapp.service.ReviewService;

public class LikesConflictException extends RuntimeException {

    public LikesConflictException(Long reviewId, String username) {
        super("Data conflict while handling like for Review " + reviewId + " from User " + username);
    }
}
