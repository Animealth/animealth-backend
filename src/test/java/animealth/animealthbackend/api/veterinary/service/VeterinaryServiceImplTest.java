package animealth.animealthbackend.api.veterinary.service;

import animealth.animealthbackend.api.veterinary.dto.*;
import animealth.animealthbackend.domain.veterinary.Veterinary;
import animealth.animealthbackend.domain.veterinary.VeterinaryRepository;
import animealth.animealthbackend.dummy.DummyCreateVeterinaryRequestDTO;
import animealth.animealthbackend.dummy.DummyUpdateVeterinaryRequestDTO;
import animealth.animealthbackend.dummy.DummyVeterinary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class VeterinaryServiceImplTest {

    @InjectMocks
    private VeterinaryServiceImpl veterinaryService;


    @Mock
    private VeterinaryRepository veterinaryRepository;

    private Veterinary veterinary;
    private UpdateVeterinaryRequestDTO updateVeterinaryRequestDTO;
    private CreateVeterinaryRequestDTO createVeterinaryRequestDTO;
    private CreateVeterinaryDTO createVeterinaryDTO;
    private UpdateVeterinaryDTO updateVeterinaryDTO;
    private DeleteVeterinaryDTO deleteVeterinaryDTO;
    private VeterinaryResponseDTO veterinaryResponseDTO;

    private final Long VETERINARY_ID = 1L;

    @BeforeEach
    void setUp() {
        veterinary = DummyVeterinary.dummy();
        updateVeterinaryRequestDTO = DummyUpdateVeterinaryRequestDTO.dummy();
        createVeterinaryRequestDTO = DummyCreateVeterinaryRequestDTO.dummy();

    }

    @DisplayName("병원 생성 테스트")
    @Test
    void createVeterinary() {

        Veterinary savedVeterinary = Veterinary.builder().build(); // 임의로 생성한 Mock 객체

        // when 구문에서 반환값 설정
        when(veterinaryRepository.save(any(Veterinary.class))).thenReturn(savedVeterinary);

        // 실제 테스트 코드에서 save 메서드 호출 후 반환값 사용
        Veterinary result = veterinaryRepository.save(veterinary);

        // 검증
        assertEquals(savedVeterinary, result);
    }

    @DisplayName("병원 수정 테스트")
    @Test
    void updateVeterinary() {

        // Create a mock or concrete instance of Veterinary
        Veterinary savedVeterinary = Veterinary.builder().build();

        // Stub the repository method to return Optional of savedVeterinary
        when(veterinaryRepository.findByVeterinaryId(anyLong()))
                .thenReturn(Optional.of(savedVeterinary));

        // Call the service method
        veterinaryService.updateVeterinary(anyLong(), updateVeterinaryRequestDTO);

        // Verify that the repository's save method was called with the correct object
        Mockito.verify(veterinaryRepository).findByVeterinaryId(anyLong());

    }

    @DisplayName("병원 삭제 테스트")
    @Test
    void deleteVeterinary() {
        // Create a mock or concrete instance of Veterinary
        Veterinary savedVeterinary = Veterinary.builder().build();

        // Stub the repository method to return Optional of savedVeterinary
        when(veterinaryRepository.findByVeterinaryId(anyLong()))
                .thenReturn(Optional.of(savedVeterinary));

        // Call the service method
        veterinaryService.deleteVeterinary(anyLong());

        // Verify that the repository's save method was called with the correct object
        Mockito.verify(veterinaryRepository).findByVeterinaryId(anyLong());
    }

    @DisplayName("병원 id로 찾는 테스트")
    @Test
    void findByVeterinaryId() {
        // Create a mock or concrete instance of Veterinary
        Veterinary veterinary1 = Veterinary.builder().build();

        // Stub the repository method to return Optional of savedVeterinary
        when(veterinaryRepository.findByVeterinaryId(anyLong()))
                .thenReturn(Optional.of(veterinary1));

        veterinaryService.findByVeterinaryId(anyLong());
        Mockito.verify(veterinaryRepository).findByVeterinaryId(anyLong());


    }

    @DisplayName("병원 이름으로 찾는 테스트")
    @Test
    void findByVeterinaryName() {
        //given
        Veterinary veterinary1 = Veterinary.builder().build();

        //when
        when(veterinaryRepository.findByVeterinaryName(anyString()))
                .thenReturn(List.of(veterinary1));

        veterinaryService.findByVeterinaryName(anyString());

        //then
        Mockito.verify(veterinaryRepository).findByVeterinaryName(anyString());
    }

    @DisplayName("병원 전부 찾는 테스트")
    @Test
    void findAll() {
        //given
        Veterinary veterinary1 = Veterinary.builder().build();

        //when
        when(veterinaryRepository.findAll())
                .thenReturn(List.of(veterinary1));

        veterinaryService.findAll();

        //then
        Mockito.verify(veterinaryRepository).findAll();
    }
}