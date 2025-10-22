package task.devices.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import task.devices.exceptions.DeviceInUseException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleBadIdException() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid id");
    }

    @ExceptionHandler(DeviceInUseException.class)
    public ResponseEntity handleDeviceInUseException() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Device is in use");
    }
}
