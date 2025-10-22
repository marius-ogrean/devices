package task.devices.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import task.devices.converters.DeviceConverter;
import task.devices.entities.Device;
import task.devices.models.DeviceModel;
import task.devices.repositories.DevicesRepository;
import task.devices.services.DateService;
import task.devices.services.DevicesService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DevicesServiceImpl implements DevicesService {

    private DevicesRepository devicesRepository;
    private DateService dateService;
    private DeviceConverter deviceConverter;

    @Override
    public Long createDevice(DeviceModel device) {
        var newDevice = Device.builder()
                .name(device.getName())
                .brand(device.getBrand())
                .state(device.getState())
                .creationTime(dateService.getCurrentDateTime())
                .build();

        newDevice = devicesRepository.save(newDevice);

        return newDevice.getId();
    }

    @Override
    public List<DeviceModel> getAllDevices() {
        var devices = devicesRepository.findAll();

        return devices.stream()
                .map(d -> deviceConverter.convertToModel(d))
                .collect(Collectors.toList());
    }

    @Override
    public DeviceModel getDevice(Long id) {
        var device = devicesRepository.findById(id);

        return device.map(d -> deviceConverter.convertToModel(d)).orElse(null);
    }
}
