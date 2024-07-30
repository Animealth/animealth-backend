package animealth.animealthbackend.domain.veterinary;

import animealth.animealthbackend.dummy.DummyVeterinary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class veterinaryTest {

    private VeterinaryHospital veterinary;

    @BeforeEach
    void setUp() {
        veterinary = DummyVeterinary.dummy();
    }

    @Test
    void getVeterinaryId() {
        assertEquals(1L, veterinary.getVeterinaryId());
    }

    @Test
    void getVeterinaryName() {
        assertEquals("Animal Care Clinic", veterinary.getVeterinaryName());
    }

    @Test
    void getLocation() {
        assertEquals("1234 Elm Street", veterinary.getLocation());
    }

    @Test
    void getOpenTime() {
        assertEquals("08:00 AM", veterinary.getOpenTime());
    }

    @Test
    void getCloseTime() {
        assertEquals("06:00 PM", veterinary.getCloseTime());
    }

    @Test
    void getClosedDay() {
        assertEquals("Sunday", veterinary.getClosedDay());
    }

    @Test
    void getAvgRating() {
        assertEquals("4.5", veterinary.getAvgRating());
    }

    @Test
    void getContact() {
        assertEquals("123-456-7890", veterinary.getContact());
    }
}