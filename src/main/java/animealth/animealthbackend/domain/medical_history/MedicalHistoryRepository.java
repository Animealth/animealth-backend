package animealth.animealthbackend.domain.medical_history;

import animealth.animealthbackend.domain.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    List<MedicalHistory> findByPetId(Long pet);

}
