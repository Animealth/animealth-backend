package animealth.animealthbackend.api.pet.repository;

import animealth.animealthbackend.domain.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
