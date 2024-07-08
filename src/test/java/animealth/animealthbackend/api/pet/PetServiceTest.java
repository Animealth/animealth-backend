package animealth.animealthbackend.api.pet;

import animealth.animealthbackend.api.pet.service.PetService;
import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.pet.PetCategory;
import animealth.animealthbackend.domain.pet.PetGender;
import animealth.animealthbackend.domain.pet.PetRepository;
import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {
    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    @Mock
    private UserRepository userRepository;

    private User owner;
    private Pet pet;

    @BeforeEach
    void setUp(){
        owner = createOwner(
                "조명주",
                "test@test.com",
                "010-1234-5678",
                "닉");
        userRepository.save(owner);

        pet = createPet(
                owner,
                "초코",
                6,
                PetCategory.DOG,
                3,
                true,
                PetGender.MALE,
                "www.naver.com");
        petRepository.save(pet);

    }



    private User createOwner(
            String name,
            String email,
            String phone,
            String nickname){
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
            String imageUrl
    ){
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
