package com.annieryannel.recommendationsapp.adapters;

import com.annieryannel.recommendationsapp.DTO.ReviewDto;
import com.annieryannel.recommendationsapp.mappers.ReviewMapper;
import com.annieryannel.recommendationsapp.models.Review;
import com.annieryannel.recommendationsapp.service.ReviewService;
import com.annieryannel.recommendationsapp.utils.specification.ReviewWithAuthorName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewAdapter {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewDto get(Long reviewId, String mode) {
        return reviewMapper.toDto(reviewService.load(reviewId, mode));
    }

    public ReviewDto create(ReviewDto reviewDto) {
        return reviewMapper.toDto(reviewService.save(reviewMapper.toEntity(reviewDto)));
    }

    public ReviewDto edit(Long reviewId, ReviewDto reviewDto) {
        Review review = reviewService.load(reviewId, "edit");
        return reviewMapper.toDto(reviewService.save(reviewMapper.setDtoToEntity(reviewDto, review)));
    }

    public void delete(Long reviewId) {
        reviewService.delete(reviewId);
    }

    public Page<ReviewDto> get(String authorName, Pageable pageable) {
        Specification<Review> specification = Specification.where(new ReviewWithAuthorName(authorName));
        return reviewMapper.pageToDtos(reviewService.loadAll(specification, pageable));
    }
}
