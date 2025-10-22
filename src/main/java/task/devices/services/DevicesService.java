package task.devices.services;

import task.devices.models.DeviceModel;

import java.util.List;

public interface DevicesService {
    Long createDevice(DeviceModel device);
    List<DeviceModel> getAllDevices();
    DeviceModel getDevice(Long id);
}
