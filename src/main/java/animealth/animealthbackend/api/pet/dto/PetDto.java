package animealth.animealthbackend.api.pet.dto;

import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.pet.PetCategory;
import animealth.animealthbackend.domain.pet.PetGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PetDto {

    @Getter
    @AllArgsConstructor // test 작성을 위함
    public static class PetRequestDTO {
        private String name;
        private int age;
        private PetCategory category;
        private int weight;
        private boolean isNeutered;
        private PetGender gender;
        private String imageUrl;
    }

    @Getter
    public static class PetResponseDTO {
        private Long petId;
        private String name;
        private int age;
        private PetCategory category;
        private int weight;
        private boolean neutered;
        private PetGender gender;
        private String imageUrl;

        @Builder
        public PetResponseDTO(Long petId, String name, int age, PetCategory category, int weight, boolean neutered, PetGender gender, String imageUrl) {
            this.petId = petId;
            this.name = name;
            this.age = age;
            this.category = category;
            this.weight = weight;
            this.neutered = neutered;
            this.gender = gender;
            this.imageUrl = imageUrl;
        }

        public static PetResponseDTO from(Pet petEntity) {
            return PetResponseDTO.builder()
                    .petId(petEntity.getId())
                    .name(petEntity.getName())
                    .age(petEntity.getAge())
                    .category(petEntity.getCategory())
                    .weight(petEntity.getWeight())
                    .neutered(petEntity.isNeutered())
                    .gender(petEntity.getGender())
                    .imageUrl(petEntity.getImageUrl())
                    .build();
        }
    }

}
