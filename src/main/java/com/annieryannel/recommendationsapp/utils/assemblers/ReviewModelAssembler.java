package com.annieryannel.recommendationsapp.utils.assemblers;

import com.annieryannel.recommendationsapp.DTO.ReviewDto;
import com.annieryannel.recommendationsapp.controllers.ReviewController;
import com.annieryannel.recommendationsapp.models.Review;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReviewModelAssembler implements RepresentationModelAssembler<ReviewDto, EntityModel<ReviewDto>> {

    @Override
    public EntityModel<ReviewDto> toModel(ReviewDto review) {

        return EntityModel.of(review,
                linkTo(methodOn(ReviewController.class).getReviewById(review.getId(), "read"))
                        .withSelfRel());
    }
}
