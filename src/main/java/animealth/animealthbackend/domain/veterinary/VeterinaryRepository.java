package animealth.animealthbackend.domain.veterinary;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinaryRepository extends JpaRepository<Veterinary, Long> {

    Veterinary save(Veterinary veterinary);
    Veterinary findByVeterinaryId(Long veterinaryId);
    List<Veterinary> findByVeterinaryName(String veterinaryName);
    List<Veterinary> findAll();
}
