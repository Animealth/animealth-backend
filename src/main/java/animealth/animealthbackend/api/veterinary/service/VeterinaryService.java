package animealth.animealthbackend.api.veterinary.service;

import animealth.animealthbackend.api.veterinary.dto.*;

import java.util.List;

public interface VeterinaryService {

    CreateVeterinaryDTO createVeterinary(CreateVeterinaryRequestDTO requestDTO);

    UpdateVeterinaryDTO updateVeterinary(Long veterinaryId, UpdateVeterinaryRequestDTO requestDTO);

    DeleteVeterinaryDTO deleteVeterinary(Long veterinaryId);

    VeterinaryResponseDTO findByVeterinaryId(Long veterinaryId);

    List<VeterinaryResponseDTO> findByVeterinaryName(String veterinaryName);
    List<VeterinaryResponseDTO> findAll();

}
