package com.annieryannel.recommendationsapp.mappers;


import com.annieryannel.recommendationsapp.DTO.ReviewDto;
import com.annieryannel.recommendationsapp.models.Review;
import com.annieryannel.recommendationsapp.models.User;
import com.annieryannel.recommendationsapp.repositories.UserRepository;
import com.annieryannel.recommendationsapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ReviewMapper {

    final ModelMapper modelMapper;

    final UserMapper userMapper;

    final UserService userService;


    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Review.class, ReviewDto.class)
                .addMappings(m -> m.skip(ReviewDto::setLikes))
                .addMappings(m -> m.skip(ReviewDto::setLiked))
                .addMappings(m -> m.skip(ReviewDto::setAuthor))
                .addMappings(m -> m.skip(ReviewDto::setReadOnlyMode))
                .addMappings(m -> m.skip(ReviewDto::setRated))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(ReviewDto.class, Review.class)
                .addMappings(m -> m.skip(Review::setLikes))
                .addMappings(m -> m.skip(Review::setAuthor))
                .addMappings(m -> m.skip(Review::setUsersRating))
                .addMappings(m -> m.skip(Review::setPicture))
                .setPostConverter(toEntityConverter());
    }

    public ReviewDto toDto(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    public Review toEntity(ReviewDto dto) {
        return modelMapper.map(dto, Review.class);
    }

    public Converter<Review, ReviewDto> toDtoConverter() {
        return context -> {
            Review source = context.getSource();
            ReviewDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public Converter<ReviewDto, Review> toEntityConverter() {
        return context -> {
            ReviewDto source = context.getSource();
            Review destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Review source, ReviewDto destination) {
        destination.setLikes(source.getLikes().size());
        destination.setAuthor(userMapper.toDto(source.getAuthor()));
        mapCurrentUserFields(source, destination);
    }

    private void mapCurrentUserFields(Review source, ReviewDto destination) {
        try {
            User currentUser = getCurrentUser();
            boolean isPermit = source.isAuthor(currentUser) || currentUser.isAdmin();
            setCurrentUserFields(destination, source.isLiked(currentUser), !isPermit, source.isRated(currentUser));
        } catch (Exception e) {
            setCurrentUserFields(destination, false, true, false);
        }
    }

    private void setCurrentUserFields(ReviewDto destination, boolean liked, boolean readOnlyMode, boolean rated) {
        destination.setLiked(liked);
        destination.setReadOnlyMode(readOnlyMode);
        destination.setRated(rated);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = ((UserDetails) auth.getPrincipal()).getUsername();
        return (User)userService.loadUserByUsername(name);
    }

    private void mapSpecificFields(ReviewDto source, Review destination) {
        destination.setAuthor(getCurrentUser());
        destination.setUsersRating(0f);
        destination.setPicture("https://ucarecdn.com/7fbb2115-c08e-4cf5-a3e5-84f5c93aead2/");
    }

    public Review setDtoToEntity(ReviewDto reviewDto, Review review) {
        setUpdates(reviewDto, review);
        return review;
    }

    private void setUpdates(ReviewDto source, Review destination) {
        destination.setText(source.getText());
        destination.setTitle(source.getTitle());
        destination.setCategory(source.getCategory());
        destination.setAuthorRating(source.getAuthorRating());
    }

    public Page<ReviewDto> pageToDtos(Page<Review> reviews) {
        return reviews.map(this::toDto);
    }
}
