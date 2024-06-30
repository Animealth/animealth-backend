package animealth.animealthbackend.api.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateVeterinaryRequestDTO {

    private String veterinaryName;
    private String location;
    private String openTime;
    private String closeTime;
    private String closedDay;
    private String avgRating;
    private String contact;

}
