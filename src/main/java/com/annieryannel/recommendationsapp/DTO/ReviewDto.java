package com.annieryannel.recommendationsapp.DTO;

import com.annieryannel.recommendationsapp.utils.Category;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    private UserDto author;

    @NotNull
    private Integer authorRating;

    @NotNull
    private Category category;

    private String picture;

    private Float usersRating;

    private boolean isRated;

    private Integer likes;

    private boolean liked;

    private boolean isReadOnlyMode;
}
