package animealth.animealthbackend.api.veterinary.service;

import animealth.animealthbackend.api.common.exception.NotFoundVeterinaryException;
import animealth.animealthbackend.api.veterinary.dto.*;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.CreateVeterinaryRequestDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.UpdateVeterinaryRequestDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.VeterinaryResponseDTO;
import animealth.animealthbackend.domain.veterinary.VeterinaryHospital;
import animealth.animealthbackend.domain.veterinary.VeterinaryHospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
public class VeterinaryHospitalServiceImpl implements VeterinaryHospitalService {

    private final VeterinaryHospitalRepository veterinaryHospitalRepository;

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
    public VeterinaryDTO.CreateVeterinaryDTO createVeterinary(CreateVeterinaryRequestDTO requestDTO) {
        VeterinaryHospital veterinary = VeterinaryHospital.createVeterinary(requestDTO);
        VeterinaryHospital save = veterinaryHospitalRepository.save(veterinary);
        return VeterinaryDTO.CreateVeterinaryDTO.fromEntity(save);
    }

    /**
     * Updates an existing Veterinary entity based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 기존 Veterinary 엔티티를 업데이트합니다.
     *
     * @param veterinaryId the ID fromEntity the Veterinary entity to update 업데이트할 Veterinary 엔티티의 ID
     * @param requestDTO the data transfer object containing the updated information 업데이트된 정보가 포함된 데이터 전송 객체
     * @return the updated Veterinary entity 업데이트된 Veterinary 엔티티
     */
    @Transactional
    @Override
    public VeterinaryDTO.UpdateVeterinaryDTO updateVeterinary(Long veterinaryId, UpdateVeterinaryRequestDTO requestDTO) {
        VeterinaryHospital veterinary = veterinaryHospitalRepository.findByVeterinaryId(veterinaryId)
                .orElseThrow(() -> new NotFoundVeterinaryException("veterinary not found"));
        VeterinaryHospital updateVeterinary = veterinary.updateVeterinary(requestDTO);
        return VeterinaryDTO.UpdateVeterinaryDTO.fromEntity(updateVeterinary);
    }

    /**
     * Deletes an existing Veterinary entity based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 기존 Veterinary 엔티티를 삭제합니다.
     *
     * @param veterinaryId the ID fromEntity the Veterinary entity to delete 삭제할 Veterinary 엔티티의 ID
     * @return the deleted Veterinary entity 삭제된 Veterinary 엔티티
     */
    @Transactional
    @Override
    public VeterinaryDTO.DeleteVeterinaryDTO deleteVeterinary(Long veterinaryId) {
        VeterinaryHospital veterinaryHospital = veterinaryHospitalRepository.findByVeterinaryId(veterinaryId)
                .orElseThrow(() -> new NotFoundVeterinaryException("veterinary not found"));
        VeterinaryHospital deleteVeterinary = veterinaryHospital.deleteVeterinary();
        return VeterinaryDTO.DeleteVeterinaryDTO.fromEntity(deleteVeterinary);
    }

    /**
     * Retrieves a Veterinary entity by its ID.
     *
     * ID를 기준으로 Veterinary 엔티티를 검색합니다.
     * @autor yeom hwiju
     * @param veterinaryId the ID fromEntity the Veterinary entity to retrieve 검색할 Veterinary 엔티티의 ID
     * @return the Veterinary entity with the specified ID 지정된 ID를 가진 Veterinary 엔티티
     */
    @Override
    public VeterinaryResponseDTO findByVeterinaryId(Long veterinaryId) {
        VeterinaryHospital veterinary = veterinaryHospitalRepository.findByVeterinaryId(veterinaryId)
                .orElseThrow(() -> new NotFoundVeterinaryException("veterinary not found"));
        return VeterinaryResponseDTO.fromEntity(veterinary);
    }

    /**
     * Retrieves a list fromEntity Veterinary entities by their name.
     *
     * 이름을 기준으로 Veterinary 엔티티 목록을 검색합니다.
     *
     * @autor yeom hwiju
     * @param veterinaryName the name fromEntity the Veterinary entities to retrieve 검색할 Veterinary 엔티티의 이름
     * @return a list fromEntity Veterinary entities with the specified name 지정된 이름을 가진 Veterinary 엔티티 목록
     */
    @Override
    public List<VeterinaryResponseDTO> findByVeterinaryName(String veterinaryName) {
        List<VeterinaryHospital> veterinaryList = veterinaryHospitalRepository.findByVeterinaryName(veterinaryName);
        return veterinaryList.isEmpty() ?new ArrayList<>(): extractList(veterinaryList);

    }

    /**
     * Retrieves a list fromEntity all Veterinary entities.
     * <p>
     * 모든 Veterinary 엔티티 목록을 검색합니다.
     *
     * @return a list fromEntity all Veterinary entities 모든 Veterinary 엔티티 목록
     * @autor yeom hwiju
     */
    @Override
    public List<VeterinaryResponseDTO> findAll() {
        List<VeterinaryHospital> veterinaryList = veterinaryHospitalRepository.findAll();
        return veterinaryList.isEmpty() ? new ArrayList<>(): extractList(veterinaryList);
    }


    @Override
    public List<VeterinaryHospital> loadVeterinariesFromCSV(String csvFilePath) {
        List<VeterinaryHospital> veterinaries = new ArrayList<>();
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            // Skip the header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(cvsSplitBy);

                CreateVeterinaryRequestDTO dto = new CreateVeterinaryRequestDTO();
                dto.setVeterinaryName(columns[20]); // 사업장명
                dto.setLocation(columns[19]); // 도로명전체주소 or 18 for 소재지전체주소
                dto.setOpenTime(columns[5]); // 인허가일자
                dto.setCloseTime(columns[11]); // 폐업일자
                dto.setClosedDay(columns[12] + " to " + columns[13]); // 휴업시작일자 to 휴업종료일자
                dto.setContact(columns[16]); // 소재지전화

                VeterinaryHospital veterinary = VeterinaryHospital.createVeterinary(dto);
                veterinaries.add(veterinary);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return veterinaries;
    }

    @Override
    public List<Veterinary> loadVeterinariesFromCSV(String csvFilePath) {
        List<Veterinary> veterinaries = new ArrayList<>();
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            // Skip the header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(cvsSplitBy);

                CreateVeterinaryRequestDTO dto = new CreateVeterinaryRequestDTO();
                dto.setVeterinaryName(columns[20]); // 사업장명
                dto.setLocation(columns[19]); // 도로명전체주소 or 18 for 소재지전체주소
                dto.setOpenTime(columns[5]); // 인허가일자
                dto.setCloseTime(columns[11]); // 폐업일자
                dto.setClosedDay(columns[12] + " to " + columns[13]); // 휴업시작일자 to 휴업종료일자
                dto.setContact(columns[16]); // 소재지전화

                Veterinary veterinary = Veterinary.createVeterinary(dto);
                veterinaries.add(veterinary);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return veterinaries;    }

    /**
     * Converts a list fromEntity Veterinary entities to a list fromEntity VeterinaryResponseDTOs.
     *
     * Veterinary 엔티티 목록을 VeterinaryResponseDTO 목록으로 변환합니다.
     *
     * @autor yeom hwiju
     * @param veterinaryList the list fromEntity Veterinary entities to convert 변환할 Veterinary 엔티티 목록
     * @return a list fromEntity VeterinaryResponseDTOs VeterinaryResponseDTO 목록
     */
    private static List<VeterinaryResponseDTO> extractList(List<VeterinaryHospital> veterinaryList) {
        List<VeterinaryResponseDTO> list = new ArrayList<>();
        for (VeterinaryHospital veterinary : veterinaryList) {
            list.add(VeterinaryResponseDTO.fromEntity(veterinary));
        }
        return list;
    }
}
