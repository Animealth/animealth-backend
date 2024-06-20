package animealth.animealthbackend.dummy;

import animealth.animealthbackend.domain.veterinary.Veterinary;

/*
* 테스트에 사용할 동물 병원 더미 클래스입니다.
* */
public class DummyVeterinary {


    /*
     * 테스트에 사용할 동물 병원 더미 메서드 입니다.
     * */
    public static Veterinary dummy(){
        return Veterinary.builder()
                .veterinaryId(1L)
                .veterinaryName("Animal Care Clinic")
                .location("1234 Elm Street")
                .openTime("08:00 AM")
                .closeTime("06:00 PM")
                .closedDay("Sunday")
                .avgRating("4.5")
                .contact("123-456-7890")
                .build();
    }
}
