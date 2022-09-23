package com.annieryannel.recommendationsapp.service;

import com.annieryannel.recommendationsapp.exceptions.LikesConflictException;
import com.annieryannel.recommendationsapp.models.Review;
import com.annieryannel.recommendationsapp.models.User;
import com.annieryannel.recommendationsapp.repositories.ReviewRepository;
import com.annieryannel.recommendationsapp.repositories.impl.SearchRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;


@Service
@Transactional
@AllArgsConstructor
public class ReviewService {

    final ReviewRepository reviewRepository;
    final SearchRepositoryImpl searchRepository;


    public Page<Review> loadAll(Specification specification, Pageable pageable) {
        return reviewRepository.findAll(specification, pageable);
    }

    public Review load(Long id, String mode) {
            Review review = reviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("review with id: " + id + " not found"));
            if (mode.equals("read"))
                review.setText(MarkdownService.markdownToHTML(review.getText()));
            return review;
    }

    public Review save(Review review)  {
        return reviewRepository.save(review);
    }

    public Page<Review> search(String text, Pageable pageable) {
        return searchRepository.search(text, pageable);
    }

    public void delete(Long reviewId) {
            reviewRepository.deleteById(reviewId);
    }

    public Integer like(Long reviewId, User user) {
        return likeHandler(load(reviewId, "edit"), user, Set::add);
    }

    public Integer unlike(Long reviewId, User user) {
        return likeHandler(load(reviewId, "edit"), user, Set::remove);
    }

    public Float rate(Long reviewId, User user, Integer rate) {
        Review review = load(reviewId, "edit");
        Set<User> raters = review.getRaters();
        if (!raters.add(user))
            throw new LikesConflictException(review.getId(), user.getUsername());
        return setNewRate(review, user, raters.size(), rate);
    }

    private Integer likeHandler(Review review, User user, ReviewService.Handler handler) {
        if(!handler.handle(review.getLikes(), user))
            throw new LikesConflictException(review.getId(), user.getUsername());
        return reviewRepository.save(review).getLikes().size();
    }

    private Float setNewRate(Review review, User user, Integer ratersAmount, Integer newRate) {
        Float ratingResult = calculateRating(review.getUsersRating(), ratersAmount, newRate);
        review.setUsersRating(ratingResult);
        reviewRepository.save(review);
        return ratingResult;
    }

    private Float calculateRating (Float usersRating, Integer ratersAmount, Integer newRate) {
        return (usersRating * ratersAmount + newRate) / (ratersAmount + 1);
    }

//    public List<Review> getByAuthor(String username) {
//        Long id = userService.getUserByUsername(username).getId();
//        return reviewRepository.findAllByAuthorId(id);
//    }

    public interface Handler {
        boolean handle(Set<User> x, User y);
    }
}

