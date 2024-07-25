package animealth.animealthbackend.domain.veterinary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeterinaryHospitalRepository extends JpaRepository<VeterinaryHospital, Long> {

    VeterinaryHospital save(VeterinaryHospital veterinary);
    Optional<VeterinaryHospital> findByVeterinaryId(Long veterinaryId);
    List<VeterinaryHospital> findByVeterinaryName(String veterinaryName);
    List<VeterinaryHospital> findAll();
}
