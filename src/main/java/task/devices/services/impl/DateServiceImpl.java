package task.devices.services.impl;

import org.springframework.stereotype.Service;
import task.devices.services.DateService;

import java.time.LocalDateTime;

@Service
public class DateServiceImpl implements DateService {
    @Override
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
