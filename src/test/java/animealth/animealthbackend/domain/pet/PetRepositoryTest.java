package animealth.animealthbackend.domain.pet;

import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    EntityManager em;

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

    @DisplayName("주인의 애완동물 리스트를 찾는 테스트")
    @Test
    void findPets(){
        //when
        List<Pet> byOwner = petRepository.findByOwner(owner);

        //then
        assertThat(byOwner.get(0)).isEqualTo(pet);

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
