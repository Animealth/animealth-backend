package animealth.animealthbackend.api.veterinary.dto;

import animealth.animealthbackend.domain.veterinary.Veterinary;
import lombok.*;

@Getter
@Setter // 뷰에서 해당 작업 진행하면 세터 필요함..
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVeterinaryDTO {

    private Long veterinaryId;
    private String veterinaryName;
    private String location;
    private String openTime;
    private String closeTime;
    private String closedDay;
    private String avgRating;
    private String contact;

    public static UpdateVeterinaryDTO fromEntity(Veterinary veterinary) {
        return UpdateVeterinaryDTO.builder()
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

