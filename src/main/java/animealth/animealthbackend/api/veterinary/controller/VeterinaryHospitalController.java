package animealth.animealthbackend.api.veterinary.controller;


import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.CreateVeterinaryDTO;
import animealth.animealthbackend.api.veterinary.dto.VeterinaryDTO.CreateVeterinaryRequestDTO;
import animealth.animealthbackend.api.veterinary.service.VeterinaryHospitalService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("veterinary-hospital")
@RequiredArgsConstructor
public class VeterinaryHospitalController {

    private final VeterinaryHospitalService veterinaryHospitalService;

    /*
    * 병원 생성 폼으로 이동에 사용하는 메서드 입니다.
    * */
    @GetMapping("/create")
    public String createForm(){
        return "veterinary-hospital/create-veterinary-hospital";
    }


    @PostMapping("/create")
    public CreateVeterinaryDTO createVeterinaryHospital(@RequestBody CreateVeterinaryRequestDTO requestDTO) {
        return veterinaryHospitalService.createVeterinary(requestDTO);
    }

    @GetMapping("/create-success")
    public String createSuccessForm(){
        return "veterinary-hospital/create-success";
    }
}
