package task.devices.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task.devices.converters.DeviceConverter;
import task.devices.dtos.DeviceDto;
import task.devices.entities.DeviceState;
import task.devices.services.DevicesService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("devices")
@AllArgsConstructor
public class DevicesController {

    private DevicesService devicesService;
    private DeviceConverter deviceConverter;

    @GetMapping
    public List<DeviceDto> getAllDevices(@RequestParam(required = false) String brand,
                                         @RequestParam(required = false) DeviceState state) {
        return devicesService.getAllDevices(brand, state).stream()
                .map(d -> deviceConverter.convertToDto(d))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DeviceDto getDevice(@PathVariable Long id) {
        var device = devicesService.getDevice(id);

        if (device == null) {
            return null;
        }

        return deviceConverter.convertToDto(device);
    }

    @PostMapping
    public Long createDevice(@RequestBody DeviceDto device) {
        var deviceModel = deviceConverter.convertToModel(device);

        var id = devicesService.createDevice(deviceModel);

        return id;
    }

    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable Long id) {
        devicesService.deleteDevice(id);
    }

    @PutMapping("/{id}")
    public void updateDevice(@PathVariable Long id, @RequestBody DeviceDto device) {
        var deviceModel = deviceConverter.convertToModel(device);

        devicesService.updateDevice(id, deviceModel);
    }
}
