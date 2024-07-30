package animealth.animealthbackend.api.pet.service;

import animealth.animealthbackend.api.pet.dto.PetDto.*;
import animealth.animealthbackend.api.pet.dto.UpdatePetResponseDTO;
import animealth.animealthbackend.domain.pet.PetRepository;
import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PetServiceImpl implements PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Override
    public Pet registerPet(Long userId, PetRequestDTO request) {
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

    @Override
    @Transactional(readOnly = true)
    public List<PetResponseDTO> findPets(Long userId) {
        User owner = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User Not Found!")
        );

        List<Pet> petList = petRepository.findByOwner(owner);

        List<PetResponseDTO> petDTOList = petList.stream()
                .map(PetResponseDTO::from)
                .toList();

        return petDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public PetResponseDTO getPetById(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new EntityNotFoundException("Pet Not Found")
        );

        return PetResponseDTO.from(pet);
    }

    @Override
    public PetResponseDTO update(UpdatePetResponseDTO request) {
        Pet petToUpdate = petRepository.findById(request.getPetId()).orElseThrow(
                () -> new EntityNotFoundException("Pet Not Found")
        );

        petToUpdate = petToUpdate.updatePetInfo(request);

        return PetResponseDTO.from(petToUpdate);
    }

    @Override
    public void deletePetById(Long petId) {
        Pet petToDelete = petRepository.findById(petId).orElseThrow(
                () -> new EntityNotFoundException("Pet Not Found")
        );
        petToDelete.deletePet();
    }
}