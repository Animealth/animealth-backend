package animealth.animealthbackend.api.veterinary.service;

import animealth.animealthbackend.api.veterinary.dto.*;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.CreateVeterinaryDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.DeleteVeterinaryDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.UpdateVeterinaryRequestDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.VeterinaryResponseDTO;

import java.util.List;

public interface VeterinaryService {

    CreateVeterinaryDTO createVeterinary(VeterinaryDTO.CreateVeterinaryRequestDTO requestDTO);

    VeterinaryDTO.UpdateVeterinaryDTO updateVeterinary(Long veterinaryId, UpdateVeterinaryRequestDTO requestDTO);

    DeleteVeterinaryDTO deleteVeterinary(Long veterinaryId);

    VeterinaryResponseDTO findByVeterinaryId(Long veterinaryId);

    List<VeterinaryResponseDTO> findByVeterinaryName(String veterinaryName);
    List<VeterinaryResponseDTO> findAll();

}
