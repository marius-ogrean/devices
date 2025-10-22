package task.devices.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import task.devices.entities.Device;
import task.devices.models.DeviceModel;
import task.devices.repositories.DevicesRepository;
import task.devices.services.DateService;
import task.devices.services.DevicesService;

@Service
@AllArgsConstructor
public class DevicesServiceImpl implements DevicesService {

    private DevicesRepository devicesRepository;
    private DateService dateService;

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
}
