package animealth.animealthbackend.api.pet;

import animealth.animealthbackend.api.pet.dto.PetDto.PetRequestDTO;
import animealth.animealthbackend.api.pet.dto.PetDto.PetResponseDTO;
import animealth.animealthbackend.api.pet.dto.UpdatePetResponseDTO;
import animealth.animealthbackend.api.pet.service.PetServiceImpl;
import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.pet.PetCategory;
import animealth.animealthbackend.domain.pet.PetGender;
import animealth.animealthbackend.domain.pet.PetRepository;
import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PetServiceImplTest {

    @Autowired
    private PetServiceImpl petServiceImpl;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    private User owner;
    private Pet pet;

    @BeforeEach
    void setUp() {
        owner = createOwner(
                "조명주",
                "test@test.com",
                "010-1234-5678",
                "닉네임");
        owner = userRepository.save(owner);

        pet = createPet(
                owner,
                "초코",
                6,
                PetCategory.DOG,
                3,
                true,
                PetGender.MALE,
                "www.naver.com");
        pet = petRepository.save(pet);
    }

    @DisplayName("키우고 있는 애완동물 등록 테스트")
    @Test
    void registerPet() {
        //given
        PetRequestDTO request = new PetRequestDTO(
                "통키",
                5,
                PetCategory.DOG,
                3,
                true,
                PetGender.MALE,
                "www.google.com"
        );

        //when
        Pet result = petServiceImpl.registerPet(owner.getUserId(), request);

        //then
        assertThat(result.getName()).isEqualTo(request.getName());
        assertThat(result.getOwner()).isEqualTo(owner);

    }

    @DisplayName("애완동물 리스트 조회 테스트")
    @Test
    void findPets() {
        //when
        List<PetResponseDTO> result = petServiceImpl.findPets(owner.getUserId());

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getName()).isEqualTo(pet.getName());
    }

    @DisplayName("애완동물 ID로 조회 테스트")
    @Test
    void getPetById() {
        //when
        PetResponseDTO result = petServiceImpl.getPetById(pet.getId());

        //then
        assertThat(result.getName()).isEqualTo(pet.getName());
    }

    @DisplayName("애완동물 업데이트 테스트")
    @Test
    void updatePet() {
        //given
        UpdatePetResponseDTO request = new UpdatePetResponseDTO(
                pet.getId(),
                "통키",
                5,
                PetCategory.CAT,
                4,
                false,
                PetGender.FEMALE,
                "www.newurl.com"
        );

        //when
        PetResponseDTO result = petServiceImpl.update(request);

        //then
        assertThat(result.getName()).isEqualTo(request.getName());
        assertThat(result.getAge()).isEqualTo(request.getAge());
        assertThat(result.getCategory()).isEqualTo(request.getCategory());
        assertThat(result.getWeight()).isEqualTo(request.getWeight());
        assertThat(result.isNeutered()).isEqualTo(request.isNeutered());
        assertThat(result.getGender()).isEqualTo(request.getGender());
        assertThat(result.getImageUrl()).isEqualTo(request.getImageUrl());
    }

    @DisplayName("애완동물 삭제 테스트")
    @Test
    void deletePetById() {
        //when
        petServiceImpl.deletePetById(pet.getId());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            petServiceImpl.getPetById(pet.getId());
        });
    }


    private User createOwner(
            String name,
            String email,
            String phone,
            String nickname) {
        return User.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }

    private Pet createPet(
            User owner,
            String name,
            int age,
            PetCategory category,
            int weight,
            boolean neutered,
            PetGender gender,
            String imageUrl) {
        return Pet.of(
                owner,
                name,
                age,
                category,
                weight,
                neutered,
                gender,
                imageUrl);
    }
}
