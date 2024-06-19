package animealth.animealthbackend.domain.pet;

import animealth.animealthbackend.api.pet.dto.PetDto.*;
import animealth.animealthbackend.api.pet.dto.UpdatePetResponseDTO;
import animealth.animealthbackend.domain.BaseEntity;
import animealth.animealthbackend.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@DynamicUpdate
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseEntity {
    @Id
    @Column(name = "PET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    private User owner;

    @Column(name = "PET_NAME")
    private String name;

    @Column(name = "PET_AGE")
    private int age;

    @Column(name = "PET_CATEGORY")
    @Enumerated(EnumType.STRING)
    private PetCategory category;

    @Column(name = "PET_WEIGHT")
    private int weight;

    @Column(name = "PET_NEUTER")
    private boolean neutered;

    @Column(name = "PET_GENDER")
    @Enumerated(EnumType.STRING)
    private PetGender gender;

    @Column(name = "PET_IMAGE_URL")
    private String imageUrl;

    @Builder
    public Pet(
            User owner,
            String name,
            int age,
            PetCategory category,
            int weight,
            boolean neutered,
            PetGender gender,
            String imageUrl
    ) {
        this.owner = owner;
        this.name = name;
        this.age = age;
        this.category = category;
        this.weight = weight;
        this.neutered = neutered;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public static final Pet of(
            User owner,
            String name,
            int age,
            PetCategory category,
            int weight,
            boolean neutered,
            PetGender gender,
            String imageUrl
    ){
        return Pet.builder()
                .owner(owner)
                .name(name)
                .age(age)
                .category(category)
                .weight(weight)
                .neutered(neutered)
                .gender(gender)
                .imageUrl(imageUrl)
                .build();
    }

    public void updatePetInfo(UpdatePetResponseDTO request) {
        this.name = request.getName();
        this.age = request.getAge();
        this.category = request.getCategory();
        this.weight = request.getWeight();
        this.neutered = request.isNeutered();
        this.gender = request.getGender();
        this.imageUrl = request.getImageUrl();
    }
}
