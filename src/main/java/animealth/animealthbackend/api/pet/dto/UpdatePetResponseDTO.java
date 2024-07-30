package animealth.animealthbackend.api.pet.dto;

import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.pet.PetCategory;
import animealth.animealthbackend.domain.pet.PetGender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePetResponseDTO {
    private final Long petId;
    private final String name;
    private final int age;
    private final PetCategory category;
    private final int weight;
    private final boolean neutered;
    private final PetGender gender;
    private final String imageUrl;

    @Builder
    public UpdatePetResponseDTO(
            Long petId,
            String name,
            int age,
            PetCategory category,
            int weight,
            boolean neutered,
            PetGender gender,
            String imageUrl
    ) {
        this.petId = petId;
        this.name = name;
        this.age = age;
        this.category = category;
        this.weight = weight;
        this.neutered = neutered;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public static UpdatePetResponseDTO from(Pet pet) {
        return UpdatePetResponseDTO.builder()
                .petId(pet.getId())
                .name(pet.getName())
                .age(pet.getAge())
                .category(pet.getCategory())
                .weight(pet.getWeight())
                .neutered(pet.isNeutered())
                .gender(pet.getGender())
                .imageUrl(pet.getImageUrl())
                .build();
    }
}
