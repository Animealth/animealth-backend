package animealth.animealthbackend.domain.veterinary;

import animealth.animealthbackend.dummy.DummyVeterinary;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VeterinaryRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VeterinaryRepository veterinaryRepository;

    private Veterinary veterinary;
    private final Long ID = 1L;
    private final String VETERNARY_NAME = "Animal Care Clinic";

    @BeforeEach
    void setUp() {
        veterinary = DummyVeterinary.dummy();
    }


    @DisplayName("병원 저장 테스트")
    @Test
    void save() {
        //given
        entityManager.persist(veterinary);

        //when
        Veterinary save = veterinaryRepository.save(veterinary);

        //then
        assertThat(save).isEqualTo(veterinary);
    }

    @DisplayName("병원 id값으로 해당 병원만 찾아오는 테스트")
    @Test
    void findByVeterinaryId() {
        //given
        entityManager.persist(veterinary);

        //when
        Optional<Veterinary> veterinary1 = veterinaryRepository.findByVeterinaryId(ID);

        //then
        assertThat(veterinary1.isEmpty()).isFalse();

    }

    @DisplayName("병원 이름으로 모든 병원 리스트 찾아오는 테스트")
    @Test
    void findByVeterinaryName() {
        //given
        entityManager.persist(veterinary);

        //when
        List<Veterinary> veterinaryList = veterinaryRepository.findByVeterinaryName(VETERNARY_NAME);

        //then
        assertThat(veterinaryList.get(0)).isEqualTo(veterinary);


    }

    @DisplayName("병원 정보 전부 찾는 테스트")
    @Test
    void findAll() {
        //given
        entityManager.persist(veterinary);

        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        //then
        assertThat(veterinaryList).hasSize(1);

    }
}