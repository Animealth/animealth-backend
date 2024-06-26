package animealth.animealthbackend.api.veterinary.service;

import animealth.animealthbackend.api.common.exception.NotFoundVeterinaryException;
import animealth.animealthbackend.api.veterinary.dto.*;
import animealth.animealthbackend.domain.veterinary.Veterinary;
import animealth.animealthbackend.domain.veterinary.VeterinaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * VeterinaryService is responsible for handling operations related to Veterinary entities.
 * It provides methods to create, update, delete, and retrieve Veterinary objects.
 *
 * VeterinaryService는 Veterinary 엔티티와 관련된 작업을 처리하는 역할을 합니다.
 * Veterinary 객체를 생성, 업데이트, 삭제 및 검색하는 메서드를 제공합니다.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class VeterinaryServiceImpl implements VeterinaryService{

    private final VeterinaryRepository veterinaryRepository;

    /**
     * Creates a new Veterinary entity based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 새로운 Veterinary 엔티티를 생성합니다.
     *
     * @param requestDTO the data transfer object containing the information needed to create a Veterinary entity
     *                   Veterinary 엔티티 생성을 위한 정보가 포함된 데이터 전송 객체
     * @return a new Veterinary entity 새로운 Veterinary 엔티티
     */
    @Transactional
    @Override
    public CreateVeterinaryDTO createVeterinary(CreateVeterinaryRequestDTO requestDTO) {
        Veterinary veterinary = Veterinary.createVeterinary(requestDTO);
        Veterinary save = veterinaryRepository.save(veterinary);
        return CreateVeterinaryDTO.fromEntity(save);
    }

    /**
     * Updates an existing Veterinary entity based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 기존 Veterinary 엔티티를 업데이트합니다.
     *
     * @param veterinaryId the ID of the Veterinary entity to update 업데이트할 Veterinary 엔티티의 ID
     * @param requestDTO the data transfer object containing the updated information 업데이트된 정보가 포함된 데이터 전송 객체
     * @return the updated Veterinary entity 업데이트된 Veterinary 엔티티
     */
    @Transactional
    @Override
    public UpdateVeterinaryDTO updateVeterinary(Long veterinaryId, UpdateVeterinaryRequestDTO requestDTO) {
        Veterinary veterinary = veterinaryRepository.findByVeterinaryId(veterinaryId)
                .orElseThrow(() -> new NotFoundVeterinaryException("veterinary not found"));
        Veterinary updateVeterinary = veterinary.updateVeterinary(requestDTO);
        return UpdateVeterinaryDTO.fromEntity(updateVeterinary);
    }

    /**
     * Deletes an existing Veterinary entity based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 기존 Veterinary 엔티티를 삭제합니다.
     *
     * @param veterinaryId the ID of the Veterinary entity to delete 삭제할 Veterinary 엔티티의 ID
     * @return the deleted Veterinary entity 삭제된 Veterinary 엔티티
     */
    @Transactional
    @Override
    public DeleteVeterinaryDTO deleteVeterinary(Long veterinaryId) {
        Veterinary veterinary = veterinaryRepository.findByVeterinaryId(veterinaryId)
                .orElseThrow(() -> new NotFoundVeterinaryException("veterinary not found"));
        Veterinary deleteVeterinary = veterinary.deleteVeterinary();
        return DeleteVeterinaryDTO.fromEntity(deleteVeterinary);
    }

    /**
     * Retrieves a Veterinary entity by its ID.
     *
     * ID를 기준으로 Veterinary 엔티티를 검색합니다.
     * @autor yeom hwiju
     * @param veterinaryId the ID of the Veterinary entity to retrieve 검색할 Veterinary 엔티티의 ID
     * @return the Veterinary entity with the specified ID 지정된 ID를 가진 Veterinary 엔티티
     */
    @Override
    public VeterinaryResponseDTO findByVeterinaryId(Long veterinaryId) {
        Veterinary veterinary = veterinaryRepository.findByVeterinaryId(veterinaryId)
                .orElseThrow(() -> new NotFoundVeterinaryException("veterinary not found"));
        return VeterinaryResponseDTO.fromEntity(veterinary);
    }

    /**
     * Retrieves a list of Veterinary entities by their name.
     *
     * 이름을 기준으로 Veterinary 엔티티 목록을 검색합니다.
     *
     * @autor yeom hwiju
     * @param veterinaryName the name of the Veterinary entities to retrieve 검색할 Veterinary 엔티티의 이름
     * @return a list of Veterinary entities with the specified name 지정된 이름을 가진 Veterinary 엔티티 목록
     */
    @Override
    public List<VeterinaryResponseDTO> findByVeterinaryName(String veterinaryName) {
        List<Veterinary> veterinaryList = veterinaryRepository.findByVeterinaryName(veterinaryName);
        return veterinaryList.isEmpty() ? extractList(veterinaryList) : new ArrayList<>();
    }

    /**
     * Retrieves a list of all Veterinary entities.
     *
     * 모든 Veterinary 엔티티 목록을 검색합니다.
     *
     * @autor yeom hwiju
     * @return a list of all Veterinary entities 모든 Veterinary 엔티티 목록
     */
    @Override
    public List<VeterinaryResponseDTO> findAll() {
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        return veterinaryList.isEmpty() ? extractList(veterinaryList) : new ArrayList<>();
    }

    /**
     * Converts a list of Veterinary entities to a list of VeterinaryResponseDTOs.
     *
     * Veterinary 엔티티 목록을 VeterinaryResponseDTO 목록으로 변환합니다.
     *
     * @autor yeom hwiju
     * @param veterinaryList the list of Veterinary entities to convert 변환할 Veterinary 엔티티 목록
     * @return a list of VeterinaryResponseDTOs VeterinaryResponseDTO 목록
     */
    private static List<VeterinaryResponseDTO> extractList(List<Veterinary> veterinaryList) {
        List<VeterinaryResponseDTO> list = new ArrayList<>();
        for (Veterinary veterinary : veterinaryList) {
            list.add(VeterinaryResponseDTO.fromEntity(veterinary));
        }
        return list;
    }
}
