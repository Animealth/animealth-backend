package animealth.animealthbackend.api.medical_history.service;

import animealth.animealthbackend.api.common.exception.NotFoundMedicalHistoryException;
import animealth.animealthbackend.api.medical_history.dto.MedicalHistoryDTO;
import animealth.animealthbackend.api.medical_history.dto.MedicalHistoryDTO.MedicalHistoryResponseDTO;
import animealth.animealthbackend.domain.medical_history.MedicalHistory;
import animealth.animealthbackend.domain.medical_history.MedicalHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MedicalHistoryResponseDTO> findMedicalHistoryByPetId(Long petId) {
        List<MedicalHistory> list = medicalHistoryRepository.findByPetId(petId);

        if (list==null && list.isEmpty()){
            throw new NotFoundMedicalHistoryException("Medical history not found for the given pet ID");
        }

        return MedicalHistoryResponseDTO.makeDtoListFromEntityList(list);
    }


}
