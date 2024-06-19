package animealth.animealthbackend.domain.medical_history;

import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.veterinary.Veterinary;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "medical_history")
@Builder
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalId;

    @Column(columnDefinition = "varchar(250)")
    private String medicalContent;

    private LocalDateTime medicalDate;

    @Column(columnDefinition = "varchar(250)")
    private String medicalType;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "veterinary_id")
    private Veterinary veterinary;

}
