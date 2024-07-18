package animealth.animealthbackend.api.veterinary.service;

import animealth.animealthbackend.api.veterinary.dto.*;

import java.util.List;

public interface VeterinaryService {

    VeterinaryDTO.CreateVeterinaryDTO createVeterinary(VeterinaryDTO.CreateVeterinaryRequestDTO requestDTO);

    VeterinaryDTO.UpdateVeterinaryDTO updateVeterinary(Long veterinaryId, VeterinaryDTO.UpdateVeterinaryRequestDTO requestDTO);

    VeterinaryDTO.DeleteVeterinaryDTO deleteVeterinary(Long veterinaryId);

    VeterinaryDTO.VeterinaryResponseDTO findByVeterinaryId(Long veterinaryId);

    List<VeterinaryDTO.VeterinaryResponseDTO> findByVeterinaryName(String veterinaryName);
    List<VeterinaryDTO.VeterinaryResponseDTO> findAll();

}
