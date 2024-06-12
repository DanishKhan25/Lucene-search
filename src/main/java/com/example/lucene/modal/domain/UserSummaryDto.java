package com.example.lucene.modal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSummaryDto {
    private String uid;
    private String displayName;
    private Double ratingAsBuyer;
    private Double ratingAsTraveler;
    private String profilePhotoUrl;
    private Boolean isOnline;
    private Boolean isUserVerified;
    private String email;
}
