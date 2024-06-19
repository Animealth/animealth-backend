package animealth.animealthbackend.domain.vaccine;

public enum VaccineType {
    RABIES_VACCINE(1, "Rabies Vaccine", "광견병 주사"),
    DEWORMER(2, "Dewormer", "구충제"),
    INTERNAL_EXTERNAL_DEWORMER(3, "Internal and External Dewormer", "내외부 구충제"),
    PUPPY_FIRST_VACCINATION(4, "Puppy's First Vaccination", "강아지 1차 접종"),
    PUPPY_SECOND_VACCINATION(5, "Puppy's Second Vaccination", "강아지 2차 접종");

    private final int no;
    private final String englishDescription;
    private final String koreanDescription;

    VaccineType(int no, String englishDescription, String koreanDescription) {
        this.no = no;
        this.englishDescription = englishDescription;
        this.koreanDescription = koreanDescription;
    }

    public int getNo() {
        return no;
    }

    public String getEnglishDescription() {
        return englishDescription;
    }

    public String getKoreanDescription() {
        return koreanDescription;
    }
}
