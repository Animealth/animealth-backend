package animealth.animealthbackend.api.veterinary.dto;

import animealth.animealthbackend.domain.veterinary.Veterinary;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateVeterinaryDTO {

    private Long veterinaryId;
    private String veterinaryName;
    private String location;
    private String openTime;
    private String closeTime;
    private String closedDay;
    private String avgRating;
    private String contact;

    public static CreateVeterinaryDTO fromEntity(Veterinary veterinary) {
        return CreateVeterinaryDTO.builder()
                .veterinaryId(veterinary.getVeterinaryId())
                .veterinaryName(veterinary.getVeterinaryName())
                .location(veterinary.getLocation())
                .openTime(veterinary.getOpenTime())
                .closeTime(veterinary.getCloseTime())
                .closedDay(veterinary.getClosedDay())
                .avgRating(veterinary.getAvgRating())
                .contact(veterinary.getContact())
                .build();
    }
}
