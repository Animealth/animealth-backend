package animealth.animealthbackend.api.medical_history.controller;

import animealth.animealthbackend.api.medical_history.dto.MedicalHistoryDTO;
import animealth.animealthbackend.api.medical_history.service.MedicalHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/medical-history")
@RequiredArgsConstructor
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;


    /**
     * 주어진 반려동물의 ID로 의료 기록을 조회하고 이를 모델에 추가하여 뷰에 전달합니다.
     *
     * @param petId  의료 기록을 조회할 반려동물의 ID
     * @param model  조회된 의료 기록을 추가할 모델 객체
     * @return       의료 기록 목록을 보여주는 뷰의 이름
     */
    @GetMapping("/{petId}")
    public String getMedicalHistoryByPetId(@PathVariable Long petId, Model model) {

        List<MedicalHistoryDTO.MedicalHistoryResponseDTO> responseDTOS = medicalHistoryService.findMedicalHistoryByPetId(petId);

        model.addAttribute("medicalHistories",responseDTOS);

        return "medical-history/medical-history-list";
    }
}
