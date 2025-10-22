package task.devices.repositories;

import task.devices.entities.Device;
import task.devices.entities.DeviceState;

import java.util.List;

public interface DevicesCustomRepository {
    List<Device> findByBrandAndState(String brand, DeviceState state);
}
