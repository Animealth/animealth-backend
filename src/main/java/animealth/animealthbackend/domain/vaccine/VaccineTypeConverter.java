package animealth.animealthbackend.domain.vaccine;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VaccineTypeConverter implements AttributeConverter<VaccineType, String> {

    @Override
    public String convertToDatabaseColumn(VaccineType attribute) {
        return (attribute != null) ? attribute.name() : null;
    }

    @Override
    public VaccineType convertToEntityAttribute(String dbData) {
        return (dbData != null) ? VaccineType.valueOf(dbData) : null;
    }
}
