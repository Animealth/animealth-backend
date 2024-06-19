package animealth.animealthbackend.domain.medical_history;

import animealth.animealthbackend.dummy.DummyMedicalHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicalHistoryTest {

    private MedicalHistory medicalHistory;

    @BeforeEach
    void setUp() {
        medicalHistory = DummyMedicalHistory.dummy();
    }

    @Test
    void getMedicalId() {
        assertEquals(1L, medicalHistory.getMedicalId());
    }

    @Test
    void getMedicalContent() {
        assertEquals("Test medical content", medicalHistory.getMedicalContent());
    }

    @Test
    void getMedicalDate() {
        assertNotNull(medicalHistory.getMedicalDate());
    }

    @Test
    void getMedicalType() {
        assertEquals("Test medical type", medicalHistory.getVaccineType());
    }

    @Test
    void getPet() {
        assertNull(medicalHistory.getPet()); // 테스트를 위해 임시로 null로 설정했으므로 null을 예상합니다.
    }

    @Test
    void getUser() {
        assertNull(medicalHistory.getUser()); // 테스트를 위해 임시로 null로 설정했으므로 null을 예상합니다.
    }

    @Test
    void getVeterinary() {
        assertNull(medicalHistory.getVeterinary()); // 테스트를 위해 임시로 null로 설정했으므로 null을 예상합니다.
    }

    @Test
    void builder() {
        // MedicalHistory.builder()가 null이 아니고 새로운 MedicalHistory 객체를 생성하는지 확인합니다.
        assertNotNull(MedicalHistory.builder().build());
    }
}