package task.devices.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task.devices.converters.DeviceConverter;
import task.devices.dtos.DeviceDto;
import task.devices.models.DeviceModel;
import task.devices.services.DevicesService;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("devices")
@AllArgsConstructor
public class DevicesController {

    private DevicesService devicesService;
    private DeviceConverter deviceConverter;

    @GetMapping
    public List<DeviceDto> getAllDevices() {
        return devicesService.getAllDevices().stream()
                .map(d -> deviceConverter.convertToDto(d))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DeviceDto getDevice(@PathVariable Long id) {
        return deviceConverter.convertToDto(devicesService.getDevice(id));
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
