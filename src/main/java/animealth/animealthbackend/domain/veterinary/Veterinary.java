package animealth.animealthbackend.domain.veterinary;

import animealth.animealthbackend.api.veterinary.dto.CreateVeterinaryRequestDTO;
import animealth.animealthbackend.api.veterinary.dto.UpdateVeterinaryRequestDTO;
import jakarta.persistence.*;
import lombok.*;

/**
 *
 * 동물 병원 엔티티입니다.
 * 이때 따로 중복 관련 작업은 진행하지 않아 추후 수정할 수 있습니다.
 * */
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Veterinaries")
@Entity
@Builder
public class Veterinary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long veterinaryId;
    private String veterinaryName;
    private String location;
    private String openTime;
    private String closeTime;
    private String closedDay;
    private String avgRating;
    private String contact;

    public static Veterinary createVeterinary(CreateVeterinaryRequestDTO requestDTO){
        return Veterinary.builder()
                .veterinaryName(requestDTO.getVeterinaryName())
                .location(requestDTO.getLocation())
                .openTime(requestDTO.getOpenTime())
                .closeTime(requestDTO.getCloseTime())
                .closedDay(requestDTO.getClosedDay())
                .avgRating(requestDTO.getAvgRating())
                .contact(requestDTO.getContact())
                .build();
    }

    public Veterinary updateVeterinary(UpdateVeterinaryRequestDTO requestDTO){
        this.veterinaryName = veterinaryName;
        this.location = location;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.closedDay = closedDay;
        this.avgRating = avgRating;
        this.contact = contact;

        return this;
    }

    public Veterinary deleteVeterinary(){
        this.veterinaryName = "deleted veterinary "+this.veterinaryName;

        return this;
    }


}
