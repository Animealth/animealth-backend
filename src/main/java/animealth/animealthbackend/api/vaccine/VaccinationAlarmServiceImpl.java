package animealth.animealthbackend.api.vaccine;

import animealth.animealthbackend.api.pet.repository.PetRepository;
import animealth.animealthbackend.domain.medical_history.MedicalHistory;
import animealth.animealthbackend.domain.medical_history.MedicalHistoryRepository;
import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.vaccine.VaccineType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class VaccinationAlarmServiceImpl implements VaccinationAlarmService{


    private final PetRepository petRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    List<Pet> petInfo =null;
    LocalDate currentDate = LocalDate.now();
    List<MedicalHistory> petMedicalHistories = new ArrayList<>();
    /*
    * 1. 주인 아이디 값으로 해당 동물 정보 죄다 불러오기
    * 2. 동물 정보로 해당 백신 기록 찾아서 1년 지났으면 알람 ㄱㄱ
    * (중복 처리는? )
    * 알람 보내야 하는 동물이 여러마리이면? -> return List<Pet> 으로 해야하나?
    * */
    @Scheduled(cron = "0 0 0 * * * *")
    @Override
    public boolean sendRabiesVaccineAlarm(Long userId) {

        petInfo= extractedOwner(userId);

        for (Pet pet : petInfo) {
            List<MedicalHistory> petMedicalHistory = medicalHistoryRepository.findByPetId(pet.getId());
            petMedicalHistories.add(petMedicalHistory);

        }
         = medicalHistoryRepository.findByPetId(petId);


        if (isVaccineExpired(byPet, VaccineType.RABIES_VACCINE)) return true;
        return false;
    }




    @Override
    @Scheduled(cron = "0 0 0 * * * *")
    public boolean sendDewormerAlarm(Long userId) {
        byOwner= extractedOwner(userId);


    }

    @Scheduled(cron = "0 0 0 * * * *")
    @Override
    public boolean sendInternalExternalDewormerAlarm(Long userId) {
        byOwner= extractedOwner(userId);

    }

    @Scheduled(cron = "0 0 0 * * * *")
    @Override
    public boolean sendPuppyFirstVaccinationAlarm(Long userId) {
        byOwner= extractedOwner(userId);

    }

    @Scheduled(cron = "0 0 0 * * * *")
    @Override
    public boolean sendPuppySecondVaccinationAlarm(Long userId) {
        byOwner= extractedOwner(userId);

    }
    private List<Pet> extractedOwner(Long userId) {
        return petRepository.findByOwner(userId);
    }

    private boolean isVaccineExpired(List<MedicalHistory> byPet, VaccineType vaccineType) {
        for (MedicalHistory medicalHistory : byPet) {
            if (medicalHistory.getVaccineType().getNo() == vaccineType.getNo()) {
                LocalDate vaccineDate = medicalHistory.getMedicalDate().toLocalDate();
                if (vaccineDate.isBefore(currentDate.minusYears(1))) {
                    return true;
                }
            }
        }
        return false;

    }
}
