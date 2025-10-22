package task.devices.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task.devices.dtos.DeviceDto;
import task.devices.models.DeviceModel;
import task.devices.services.DevicesService;

import java.util.List;

@RestController
@RequestMapping("devices")
@AllArgsConstructor
public class DevicesController {

    private DevicesService devicesService;

    @GetMapping
    public List<DeviceDto> getAllDevices() {
        return null;
    }

    @PostMapping
    public Long createDevice(@RequestBody DeviceDto device) {
        var deviceModel = DeviceModel.builder()
                .name(device.getName())
                .brand(device.getBrand())
                .state(device.getState())
                .build();

        var id = devicesService.createDevice(deviceModel);

        return id;
    }
}
