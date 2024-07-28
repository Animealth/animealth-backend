package animealth.animealthbackend.api.medical_history.dto;

import animealth.animealthbackend.api.medical_history.dto.MedicalHistoryDTO.MedicalHistoryResponseDTO;
import animealth.animealthbackend.domain.medical_history.MedicalHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class MedicalHistoryDTO {

    /**
     * MedicalHistoryResponseDTO는 의료 기록에 대한 응답 데이터를 나타내는 클래스입니다.
     * 이 클래스는 의료 기록의 ID, 내용, 날짜, 영어 설명, 한국어 설명을 포함합니다.
     *
     * @author 작성자
     * @version 1.0
     */
    @Getter
    public static class MedicalHistoryResponseDTO {

        private Long medicalId;
        private String medicalContent;
        private LocalDateTime medicalDate;
        private String englishDescription;
        private String koreanDescription;

        /**
         * 주어진 인자로 MedicalHistoryResponseDTO 객체를 생성합니다.
         *
         * @param medicalId           의료 기록의 ID
         * @param medicalContent      의료 기록의 내용
         * @param medicalDate         의료 기록의 날짜
         * @param englishDescription  의료 기록에 대한 영어 설명
         * @param koreanDescription   의료 기록에 대한 한국어 설명
         */
        @Builder
        public MedicalHistoryResponseDTO(Long medicalId, String medicalContent, LocalDateTime medicalDate, String englishDescription, String koreanDescription) {
            this.medicalId = medicalId;
            this.medicalContent = medicalContent;
            this.medicalDate = medicalDate;
            this.englishDescription = englishDescription;
            this.koreanDescription = koreanDescription;
        }

        /**
         * 주어진 MedicalHistory 엔티티로부터 MedicalHistoryResponseDTO 객체를 생성합니다.
         *
         * @param entity  MedicalHistory 엔티티
         * @return        생성된 MedicalHistoryResponseDTO 객체
         */
        public static MedicalHistoryResponseDTO from(MedicalHistory entity) {
            return MedicalHistoryResponseDTO.builder()
                    .medicalId(entity.getMedicalId())
                    .medicalContent(entity.getMedicalContent())
                    .medicalDate(entity.getMedicalDate())
                    .englishDescription(entity.getVaccineType().getEnglishDescription())
                    .koreanDescription(entity.getVaccineType().getKoreanDescription())
                    .build();
        }
    }



}
