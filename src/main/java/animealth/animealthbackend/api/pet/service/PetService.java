package animealth.animealthbackend.api.pet.service;

import animealth.animealthbackend.api.pet.dto.CreatePetDto.*;
import animealth.animealthbackend.api.pet.repository.PetRepository;
import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Transactional
    public Pet registerPet(Long userId, CreatePetRequestDTO request) {
        User owner = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User Not Found!")
        );

        return petRepository.save(
                Pet.of(
                        owner,
                        request.getName(),
                        request.getAge(),
                        request.getCategory(),
                        request.getWeight(),
                        request.isNeutered(),
                        request.getGender(),
                        request.getImageUrl()
                )
        );

    }


}
