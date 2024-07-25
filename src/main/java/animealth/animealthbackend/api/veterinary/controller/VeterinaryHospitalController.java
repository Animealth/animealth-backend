package animealth.animealthbackend.api.veterinary.controller;


import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.CreateVeterinaryDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.CreateVeterinaryRequestDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.VeterinaryResponseDTO;
import animealth.animealthbackend.api.veterinary.service.VeterinaryHospitalService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * 병원 생성 및 관리 및 뷰와 관련된 요청을 처리하는 컨트롤러입니다.
 * 모든 메서드는 병원 생성 기능과 관련된 요청을 처리합니다.
 *
 * @author hwiju yeom
 */
@Controller
@RequestMapping("veterinary-hospitals")
@RequiredArgsConstructor
public class VeterinaryHospitalController {

    private final VeterinaryHospitalService veterinaryHospitalService;

    /**
     * 병원 생성 폼으로 이동에 사용하는 메서드입니다.
     * 이 메서드는 GET 요청을 처리하여 병원 생성 폼 페이지를 반환합니다.
     *
     * @return 병원 생성 폼 페이지의 뷰 이름
     * @see VeterinaryHospitalController
     * @since 1.0
     */
    @GetMapping("/create")
    public String createForm(){
        return "veterinary-hospital/create-veterinary-hospital";
    }


    /**
     * 병원 생성에 사용하는 메서드입니다.
     * 이 메서드는 POST 요청을 처리하여 병원 정보를 생성하고, 생성된 병원 정보를 반환합니다.
     *
     * @param requestDTO 병원 생성에 필요한 요청 데이터
     * @return 생성된 병원 정보가 포함된 {@link CreateVeterinaryDTO} 객체
     * @see CreateVeterinaryDTO
     * @see CreateVeterinaryRequestDTO
     * @since 1.0
     */
    @PostMapping("/create")
    public CreateVeterinaryDTO createVeterinaryHospital(@RequestBody CreateVeterinaryRequestDTO requestDTO) {
        return veterinaryHospitalService.createVeterinary(requestDTO);
    }

    /**
     * 모든 병원 정보를 조회하여 화면에 표시하는 메서드입니다.
     * 이 메서드는 GET 요청을 처리하여 {@link Model} 객체에 병원 정보를 추가하고, 병원 목록을 보여주는 뷰를 반환합니다.
     *
     * @param model {@link Model} 객체로, 병원 정보를 화면에 전달하는 데 사용됩니다.
     * @return 병원 목록을 표시하는 뷰의 경로인 {@code "/veterinary-hospital/veterinary-hospitals-list"} 문자열을 반환합니다.
     * @see VeterinaryHospitalService
     * @see VeterinaryResponseDTO
     * @since 1.0
     */
    @GetMapping("/veterinary-hospitals-list")
    public String getAllVeterinaryHospitals(Model model) {
        model.addAttribute("veterinaryList", veterinaryHospitalService.findAll());
        return "/veterinary-hospital/veterinary-hospitals-list";
    }


}
