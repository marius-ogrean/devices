package task.devices.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.devices.dtos.DeviceDto;

import java.util.List;

@RestController
@RequestMapping("devices")
public class DevicesController {
    @GetMapping
    public List<DeviceDto> getAllDevices() {
        return null;
    }
}
