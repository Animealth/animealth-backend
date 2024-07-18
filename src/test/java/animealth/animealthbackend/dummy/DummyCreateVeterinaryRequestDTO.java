package animealth.animealthbackend.dummy;

import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO;

public class DummyCreateVeterinaryRequestDTO {
    public static VeterinaryDTO.CreateVeterinaryRequestDTO dummy() {
        return VeterinaryDTO.CreateVeterinaryRequestDTO.builder()
                .veterinaryName("Animal Care Clinic")
                .location("123 Main St")
                .openTime("09:00 AM")
                .closeTime("06:00 PM")
                .closedDay("Sunday")
                .avgRating("4.5")
                .contact("123-456-7890")
                .build();
    }
}
