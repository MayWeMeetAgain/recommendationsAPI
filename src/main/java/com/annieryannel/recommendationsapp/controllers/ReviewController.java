package com.annieryannel.recommendationsapp.controllers;

import com.annieryannel.recommendationsapp.DTO.ReviewDto;
import com.annieryannel.recommendationsapp.adapters.EvaluationAdapter;
import com.annieryannel.recommendationsapp.adapters.ReviewAdapter;
import com.annieryannel.recommendationsapp.mappers.ReviewMapper;
import com.annieryannel.recommendationsapp.models.Review;
import com.annieryannel.recommendationsapp.utils.Category;
import com.annieryannel.recommendationsapp.utils.assemblers.ReviewModelAssembler;
import com.annieryannel.recommendationsapp.utils.specification.ReviewWithAuthorName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class ReviewController {

    final ReviewAdapter reviewAdapter;
    final EvaluationAdapter evaluationAdapter;
    final ReviewModelAssembler reviewModelAssembler;
    final PagedResourcesAssembler<ReviewDto> pagedResourcesAssembler;


    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<EntityModel<ReviewDto>> getReviewById(@PathVariable("reviewId") Long reviewId, @RequestParam(value = "mode", defaultValue = "read", required = false) String mode) {
//        return reviewAdapter.get(reviewId, mode);
        return ResponseEntity
                .ok()
                .contentType(MediaTypes.HAL_JSON)
                .body(reviewModelAssembler.toModel(reviewAdapter.get(reviewId, mode)));
    }

    @PostMapping("/reviews")
    public ReviewDto createReview(@RequestBody @Valid ReviewDto dto) {
        return reviewAdapter.create(dto);
    }

    @PutMapping("/reviews/{reviewId}")
    public ReviewDto saveEditedReview(@PathVariable("reviewId") Long reviewId, @RequestBody @Valid ReviewDto dto) {
        return reviewAdapter.edit(reviewId, dto);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewAdapter.delete(reviewId);
    }

    @GetMapping("/categories")
    public Category[] getCategories() {
        return Category.values();
    }

    @GetMapping("/reviews")
    public ResponseEntity getReviews(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                                         @RequestParam(value = "authorName", required = false) String authorName) {
        Page<ReviewDto> page = reviewAdapter.get(authorName, pageable);
        return ResponseEntity
                .ok()
                .contentType(MediaTypes.HAL_JSON)
                .body(pagedResourcesAssembler.toModel(page, reviewModelAssembler));
    }

    @PostMapping("/reviews/{reviewId}/rating")
    public Float rateReview(@RequestParam Integer rate, @PathVariable("reviewId") Long reviewId , Authentication authentication) {
        return evaluationAdapter.rate(reviewId, authentication.getName(), rate);
    }

    @PostMapping("/reviews/{reviewId}/likes")
    public Integer likeReview(@PathVariable("reviewId") Long reviewId, Authentication authentication) {
        return evaluationAdapter.like(reviewId, authentication.getName());
    }

    @DeleteMapping("/reviews/{reviewId}/likes")
    public Integer unlikeReview(@PathVariable("reviewId") Long reviewId, Authentication authentication) {
        return evaluationAdapter.unlike(reviewId, authentication.getName());
    }

//    @GetMapping("/{username}/reviews")
//    public List<ReviewDto> GetReviewsByAuthor(@PathVariable("username") String username) {
//        return reviewMapper.ReviewsToDto(reviewService.getByAuthor(username));
//    }


}
