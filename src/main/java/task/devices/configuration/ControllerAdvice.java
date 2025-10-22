package task.devices.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import task.devices.exceptions.DeleteDeviceInUseException;
import task.devices.exceptions.InvalidPatchFieldException;
import task.devices.exceptions.UpdateWhileInUseException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleBadIdException() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid id");
    }

    @ExceptionHandler(DeleteDeviceInUseException.class)
    public ResponseEntity handleDeviceInUseException() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Cannot delete device which is in use");
    }

    @ExceptionHandler(InvalidPatchFieldException.class)
    public ResponseEntity handleInvalidPatchFieldException(InvalidPatchFieldException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid field name; valid names: " + ex.getMessage());
    }

    @ExceptionHandler(UpdateWhileInUseException.class)
    public ResponseEntity handleUpdateWhileInUseException() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Cannot update name or brand for device which is in use");
    }
}
