package animealth.animealthbackend.dummy;

import animealth.animealthbackend.domain.medical_history.MedicalHistory;
import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.veterinary.Veterinary;

import java.time.LocalDateTime;

public class DummyMedicalHistory {

    public static MedicalHistory createDummyMedicalHistory(Pet pet, User user, Veterinary veterinary) {
        return MedicalHistory.builder()
                .medicalContent("Dummy medical content")
                .medicalDate(LocalDateTime.now())
                .medicalType("Dummy medical type")
                .pet(pet)
                .user(user)
                .veterinary(veterinary)
                .build();
    }
    public static MedicalHistory dummy() {
        return MedicalHistory.builder()
                        .medicalId(1L)
                        .medicalContent("Test medical content")
                        .medicalDate(LocalDateTime.now())
                        .medicalType("Test medical type")
                        .pet(null) // 테스트를 위해 임시로 null로 설정
                        .user(null) // 테스트를 위해 임시로 null로 설정
                        .veterinary(null) // 테스트를 위해 임시로 null로 설정
                        .build();
    }


}
