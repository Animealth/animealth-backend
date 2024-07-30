package animealth.animealthbackend.api.veterinary.dto;

import animealth.animealthbackend.domain.veterinary.Veterinary;
import lombok.*;

public class VeterinaryDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateVeterinaryDTO {
        private Long veterinaryId;
        private String veterinaryName;
        private String location;
        private String openTime;
        private String closeTime;
        private String closedDay;
        private String avgRating;
        private String contact;

        public static CreateVeterinaryDTO fromEntity(Veterinary veterinary) {
            return CreateVeterinaryDTO.builder()
                    .veterinaryId(veterinary.getVeterinaryId())
                    .veterinaryName(veterinary.getVeterinaryName())
                    .location(veterinary.getLocation())
                    .openTime(veterinary.getOpenTime())
                    .closeTime(veterinary.getCloseTime())
                    .closedDay(veterinary.getClosedDay())
                    .avgRating(veterinary.getAvgRating())
                    .contact(veterinary.getContact())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateVeterinaryRequestDTO {
        private String veterinaryName;
        private String location;
        private String openTime;
        private String closeTime;
        private String closedDay;
        private String avgRating;
        private String contact;

        public static CreateVeterinaryRequestDTO fromEntity(Veterinary veterinary) {
            return CreateVeterinaryRequestDTO.builder()
                    .veterinaryName(veterinary.getVeterinaryName())
                    .location(veterinary.getLocation())
                    .openTime(veterinary.getOpenTime())
                    .closeTime(veterinary.getCloseTime())
                    .closedDay(veterinary.getClosedDay())
                    .avgRating(veterinary.getAvgRating())
                    .contact(veterinary.getContact())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteVeterinaryDTO {
        private Long veterinaryId;
        private String veterinaryName;
        private String location;
        private String openTime;
        private String closeTime;
        private String closedDay;
        private String avgRating;
        private String contact;

        public static DeleteVeterinaryDTO fromEntity(Veterinary veterinary) {
            return DeleteVeterinaryDTO.builder()
                    .veterinaryId(veterinary.getVeterinaryId())
                    .veterinaryName(veterinary.getVeterinaryName())
                    .location(veterinary.getLocation())
                    .openTime(veterinary.getOpenTime())
                    .closeTime(veterinary.getCloseTime())
                    .closedDay(veterinary.getClosedDay())
                    .avgRating(veterinary.getAvgRating())
                    .contact(veterinary.getContact())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateVeterinaryDTO {
        private Long veterinaryId;
        private String veterinaryName;
        private String location;
        private String openTime;
        private String closeTime;
        private String closedDay;
        private String avgRating;
        private String contact;

        public static UpdateVeterinaryDTO fromEntity(Veterinary veterinary) {
            return UpdateVeterinaryDTO.builder()
                    .veterinaryId(veterinary.getVeterinaryId())
                    .veterinaryName(veterinary.getVeterinaryName())
                    .location(veterinary.getLocation())
                    .openTime(veterinary.getOpenTime())
                    .closeTime(veterinary.getCloseTime())
                    .closedDay(veterinary.getClosedDay())
                    .avgRating(veterinary.getAvgRating())
                    .contact(veterinary.getContact())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VeterinaryResponseDTO {
        private Long veterinaryId;
        private String veterinaryName;
        private String location;
        private String openTime;
        private String closeTime;
        private String closedDay;
        private String avgRating;
        private String contact;

        public static VeterinaryResponseDTO fromEntity(Veterinary veterinary) {
            return VeterinaryResponseDTO.builder()
                    .veterinaryId(veterinary.getVeterinaryId())
                    .veterinaryName(veterinary.getVeterinaryName())
                    .location(veterinary.getLocation())
                    .openTime(veterinary.getOpenTime())
                    .closeTime(veterinary.getCloseTime())
                    .closedDay(veterinary.getClosedDay())
                    .avgRating(veterinary.getAvgRating())
                    .contact(veterinary.getContact())
                    .build();
        }
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class UpdateVeterinaryRequestDTO {

        private String veterinaryName;
        private String location;
        private String openTime;
        private String closeTime;
        private String closedDay;
        private String avgRating;
        private String contact;



    }
}
