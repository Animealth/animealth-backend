package animealth.animealthbackend.api.pet.controller;

import animealth.animealthbackend.api.common.controller.BaseController;
import animealth.animealthbackend.api.pet.dto.PetDto.*;
import animealth.animealthbackend.api.pet.dto.UpdatePetResponseDTO;
import animealth.animealthbackend.api.pet.service.PetService;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/pet")
public class PetController extends BaseController {

    private final PetService petService;

    /**
     * 애완동물 등록
     */
    @PostMapping(value = "/register")
    public String registerPet(HttpSession session, @RequestBody PetRequestDTO dto, Model model) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        PetResponseDTO petResponseDTO = PetResponseDTO.from(petService.registerPet(principal.getId(), dto));
        model.addAttribute("pet", petResponseDTO);
        return "pet/register";
    }

    /**
     * 내 애완동물 리스트 조회
     */
    @GetMapping
    public String findPets(HttpSession session, Model model) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        List<PetResponseDTO> petDTOList = petService.findPets(principal.getId());
        model.addAttribute("petList", petDTOList);
        return "pet/list";
    }

    /**
     * 애완동물 상세 조회
     */
    @GetMapping(value = "/{petId}")
    public String getPetById(@PathVariable(value = "petId") Long petId, Model model) {
        PetResponseDTO petResponseDTO = petService.getPetById(petId);
        model.addAttribute("pet", petResponseDTO);
        return "pet/detail";
    }

    /**
     * 애완동물 업데이트
     */
    @PatchMapping(value = "/update")
    public String updatePet(@RequestBody UpdatePetResponseDTO dto, Model model) {
        PetResponseDTO petResponseDTO = petService.update(dto);
        model.addAttribute("pet", petResponseDTO);
        return "pet/update";
    }

    /**
     * 애완동물 삭제
     */
    @DeleteMapping(value = "/delete/{petId}")
    public String deletePet(@PathVariable(value = "petId") Long petId, Model model) {
        petService.deletePetById(petId);
        return "redirect:/api/pet"; // 삭제 후 애완동물 목록으로 리디렉션
    }
}