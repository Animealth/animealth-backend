package animealth.animealthbackend.api.pet.controller;

import animealth.animealthbackend.api.common.controller.BaseController;
import animealth.animealthbackend.api.common.dto.ResponseDTO;
import animealth.animealthbackend.api.pet.dto.PetDto.*;
import animealth.animealthbackend.api.pet.dto.UpdatePetResponseDTO;
import animealth.animealthbackend.api.pet.service.PetService;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/pet")
public class PetController extends BaseController {

    private final PetService petService;

    /**
     * 애완동물 등록
     */
    @PostMapping(value = "/register")
    public ResponseDTO<PetResponseDTO> registerPet(HttpSession session, @RequestBody PetRequestDTO dto) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        return ResponseDTO.ok(PetResponseDTO.from(petService.registerPet(principal.getId(), dto)));
    }

    /**
     * 내 애완동물 리스트 조회
     */
    @GetMapping()
    public ResponseDTO<List<PetResponseDTO>> findPets(HttpSession session) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        return ResponseDTO.ok(petService.findPets(principal.getId()));
    }

    /**
     * 애완동물 상세 조회
     */
    @GetMapping(value = "/{petId}")
    public ResponseDTO<PetResponseDTO> getPetById (@PathVariable(value = "petId") Long petId) {
        return ResponseDTO.ok(petService.getPetById(petId));
    }

    /**
     * 애완동물 업데이트
     */
    @PatchMapping(value = "/update")
    public ResponseDTO<PetResponseDTO> updatePet(@RequestBody UpdatePetResponseDTO dto) {
        return ResponseDTO.ok(petService.update(dto));
    }

    /**
     * 애완동물 삭제
     */
    @DeleteMapping(value = "/delete/{petId}")
    public ResponseDTO<Void> deletePet(@PathVariable(value = "petId") Long petId) {
        petService.deletePetById(petId);
        return ResponseDTO.ok();
    }


}
