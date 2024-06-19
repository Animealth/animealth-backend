package animealth.animealthbackend.api.vaccine;

public interface VaccinationAlarmService {

    // 광견병 주사(1년 주기) 알람을 보내는 로직을 작성합니다.
    void sendRabiesVaccineAlert(Long userId);

    // 구충제 (3개월 주기) 알람을 보내는 로직을 작성합니다.
    void sendDewormerAlert(Long userId);

    // 내외부 구충제 (1개월 주기) 알람을 보내는 로직을 작성합니다
    void sendInternalExternalDewormerAlert(Long userId);

    // 강아지 시절의(애기때) 1차 접종 알람을 보내는 로직을 작성합니다.
    void sendPuppyFirstVaccinationAlert(Long userId);

    // 강아지 시절의(애기때) 2차 접종 알람을 보내는 로직을 작성합니다.
    void sendPuppySecondVaccinationAlert(Long userId);

}
