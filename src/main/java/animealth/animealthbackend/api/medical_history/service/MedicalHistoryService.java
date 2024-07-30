package animealth.animealthbackend.api.medical_history.service;

import animealth.animealthbackend.api.medical_history.dto.MedicalHistoryDTO;
import animealth.animealthbackend.api.medical_history.dto.MedicalHistoryDTO.MedicalHistoryResponseDTO;
import animealth.animealthbackend.domain.pet.Pet;

import java.util.List;

/**
 * MedicalHistoryService는 의료 기록과 관련된 서비스 인터페이스입니다.
 *
 * <p>이 인터페이스는 특정 반려동물에 대한 의료 기록을 검색하는 기능을 제공합니다.</p>
 *
 * @author hwiju yeom
 * @version 1.0
 */
public interface MedicalHistoryService {

    /**
     * 주어진 반려동물(Pet)의 의료 기록 목록을 반환합니다.
     *
     * @param petId  의료 기록을 검색할 반려동물 객체
     * @return     주어진 반려동물에 대한 MedicalHistoryResponseDTO 객체의 리스트
     */
    List<MedicalHistoryResponseDTO> findMedicalHistoryByPetId(Long petId);
}