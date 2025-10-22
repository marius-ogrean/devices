package task.devices.exceptions;

public class InvalidPatchFieldException extends RuntimeException {
    public InvalidPatchFieldException(String validFields) {
        super(validFields);
    }
}
