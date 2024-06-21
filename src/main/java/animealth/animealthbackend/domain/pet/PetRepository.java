package animealth.animealthbackend.domain.pet;

import animealth.animealthbackend.domain.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByOwner(Long owner);

}
