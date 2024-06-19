package animealth.animealthbackend.api.pet.controller;

import animealth.animealthbackend.api.common.controller.BaseController;
import animealth.animealthbackend.api.common.dto.ResponseDTO;
import animealth.animealthbackend.api.pet.dto.CreatePetDto.*;
import animealth.animealthbackend.api.pet.service.PetService;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/pet")
public class PetController extends BaseController {

    private final PetService petService;

    @PostMapping(value = "/register")
    public ResponseDTO<CreatePetResponseDTO> registerPet(HttpSession session, @RequestBody CreatePetRequestDTO dto) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        return ResponseDTO.ok(CreatePetResponseDTO.from(petService.registerPet(principal.getId(), dto)));
    }


}
