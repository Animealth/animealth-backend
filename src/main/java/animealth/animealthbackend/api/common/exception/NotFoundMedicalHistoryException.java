package animealth.animealthbackend.api.common.exception;

public class NotFoundMedicalHistoryException extends RuntimeException {
    public NotFoundMedicalHistoryException(String message) {
        super(message);
    }
}
