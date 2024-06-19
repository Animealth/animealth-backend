package animealth.animealthbackend.api.pet.repository;

import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByOwner(User owner);

}
