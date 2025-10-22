package task.devices.services;

import task.devices.entities.DeviceState;
import task.devices.models.DeviceModel;

import java.util.List;

public interface DevicesService {
    Long createDevice(DeviceModel device);
    List<DeviceModel> getAllDevices(String brand, DeviceState state);
    DeviceModel getDevice(Long id);
    void deleteDevice(Long id);
    void updateDevice(Long id, DeviceModel deviceModel);
}
