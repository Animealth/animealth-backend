package animealth.animealthbackend.domain.veterinary;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * 동물 병원 엔티티입니다.
 * 이때 따로 중복 관련 작업은 진행하지 않아 추후 수정할 수 있습니다.
 * */
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "veterinaries")
@Entity
public class veterinary {
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
}
