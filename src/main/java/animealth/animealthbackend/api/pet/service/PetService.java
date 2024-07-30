package animealth.animealthbackend.api.pet.service;

import animealth.animealthbackend.api.pet.dto.PetDto.*;
import animealth.animealthbackend.api.pet.dto.UpdatePetResponseDTO;
import animealth.animealthbackend.domain.pet.Pet;

import java.util.List;

public interface PetService {

    /**
     * 애완동물 등록
     */
    Pet registerPet(Long userId, PetRequestDTO request);

    /**
     * 내 애완동물 리스트 조회
     */
    List<PetResponseDTO> findPets(Long userId);

    /**
     * 애완동물 상세 조회
     */
    PetResponseDTO getPetById(Long petId);

    /**
     * 애완동물 업데이트
     */
    PetResponseDTO update(UpdatePetResponseDTO request);

    /**
     * 애완동물 삭제
     */
    void deletePetById(Long petId);
}