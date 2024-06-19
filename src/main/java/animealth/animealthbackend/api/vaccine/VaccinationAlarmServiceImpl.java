package animealth.animealthbackend.api.vaccine;

import animealth.animealthbackend.api.pet.repository.PetRepository;
import animealth.animealthbackend.domain.medical_history.MedicalHistory;
import animealth.animealthbackend.domain.medical_history.MedicalHistoryRepository;
import animealth.animealthbackend.domain.pet.Pet;
import animealth.animealthbackend.domain.vaccine.VaccineType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VaccinationAlarmServiceImpl implements VaccinationAlarmService{

    private final PetRepository petRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    List<Pet> byOwner =null;
    LocalDate currentDate = LocalDate.now();

    @Override
    public void sendRabiesVaccineAlert(Long petId) {
        List<MedicalHistory> byPet = medicalHistoryRepository.findByPetId(petId);

        for (MedicalHistory medicalHistory : byPet) {
            if (medicalHistory.getVaccineType() == VaccineType.RABIES_VACCINE) {
                LocalDate vaccineDate = medicalHistory.getMedicalDate().toLocalDate();
                if (vaccineDate.isBefore(currentDate.minusYears(1))) {

                }
            }
        }
    }



    @Override
    public void sendDewormerAlert(Long userId) {
        byOwner= extractedOwner(userId);


    }

    @Override
    public void sendInternalExternalDewormerAlert(Long userId) {
        byOwner= extractedOwner(userId);

    }

    @Override
    public void sendPuppyFirstVaccinationAlert(Long userId) {
        byOwner= extractedOwner(userId);

    }

    @Override
    public void sendPuppySecondVaccinationAlert(Long userId) {
        byOwner= extractedOwner(userId);

    }
    private List<Pet> extractedOwner(Long userId) {
        return petRepository.findByOwner(userId);
    }
}
