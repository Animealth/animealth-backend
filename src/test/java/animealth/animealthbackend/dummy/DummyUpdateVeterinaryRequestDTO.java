package animealth.animealthbackend.dummy;

import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO;

public class DummyUpdateVeterinaryRequestDTO {

    public static VeterinaryDTO.UpdateVeterinaryRequestDTO dummy() {
        return VeterinaryDTO.UpdateVeterinaryRequestDTO.builder()
                .veterinaryName("Updated Animal Care Clinic")
                .location("456 Main St")
                .openTime("08:00 AM")
                .closeTime("05:00 PM")
                .closedDay("Saturday")
                .avgRating("4.8")
                .contact("987-654-3210")
                .build();

    }
}
