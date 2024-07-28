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
        PetResponseDTO responseDTO = PetResponseDTO.from(petService.registerPet(principal.getId(), dto));
        model.addAttribute("pet", responseDTO);
        return "redirect:/api/pet"; // 애완동물 리스트 페이지로 리다이렉트
    }

    /**
     * 내 애완동물 리스트 조회
     */
    @GetMapping()
    public String findPets(HttpSession session, Model model) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        List<PetResponseDTO> petList = petService.findPets(principal.getId());
        model.addAttribute("pets", petList);
        return "petListView"; // 애완동물 리스트 뷰
    }

    /**
     * 애완동물 상세 조회
     */
    @GetMapping(value = "/{petId}")
    public String getPetById(@PathVariable(value = "petId") Long petId, Model model) {
        PetResponseDTO petResponseDTO = petService.getPetById(petId);
        model.addAttribute("pet", petResponseDTO);
        return "petDetailView"; // 애완동물 상세 뷰
    }

    /**
     * 애완동물 업데이트
     */
    @PatchMapping(value = "/update")
    public String updatePet(@RequestBody UpdatePetResponseDTO dto, Model model) {
        PetResponseDTO updatedPet = petService.update(dto);
        model.addAttribute("pet", updatedPet);
        return "redirect:/api/pet/" + updatedPet.getPetId(); // 애완동물 상세 페이지로 리다이렉트
    }

    /**
     * 애완동물 삭제
     */
    @DeleteMapping(value = "/delete/{petId}")
    public String deletePet(@PathVariable(value = "petId") Long petId) {
        petService.deletePetById(petId);
        return "redirect:/api/pet"; // 애완동물 리스트 페이지로 리다이렉트
    }
}