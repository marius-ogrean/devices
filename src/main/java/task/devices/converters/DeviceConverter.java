package task.devices.converters;

import org.springframework.stereotype.Component;
import task.devices.dtos.DeviceDto;
import task.devices.entities.Device;
import task.devices.models.DeviceModel;

import java.time.ZoneId;

@Component
public class DeviceConverter {
    public DeviceModel convertToModel(Device device) {
        return DeviceModel.builder()
                .id(device.getId())
                .name(device.getName())
                .brand(device.getBrand())
                .state(device.getState())
                .creationTime(device.getCreationTime())
                .build();
    }

    public DeviceDto convertToDto(DeviceModel device) {
        return DeviceDto.builder()
                .id(device.getId())
                .name(device.getName())
                .brand(device.getBrand())
                .state(device.getState())
                .creationTime(device.getCreationTime().atZone(ZoneId.systemDefault()))
                .build();
    }
}
