package task.devices.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import task.devices.converters.DeviceConverter;
import task.devices.entities.Device;
import task.devices.entities.DeviceState;
import task.devices.exceptions.DeleteDeviceInUseException;
import task.devices.exceptions.InvalidPatchFieldException;
import task.devices.exceptions.UpdateWhileInUseException;
import task.devices.models.DeviceModel;
import task.devices.models.DeviceUpdateModel;
import task.devices.repositories.DevicesRepository;
import task.devices.services.DateService;
import task.devices.services.DevicesService;

import java.lang.reflect.Field;
import java.util.Arrays;
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
    public List<DeviceModel> getAllDevices(String brand, DeviceState state) {
        var devices = devicesRepository.findByBrandAndState(brand, state);

        return devices.stream()
                .map(d -> deviceConverter.convertToModel(d))
                .collect(Collectors.toList());
    }

    @Override
    public DeviceModel getDevice(Long id) {
        var device = devicesRepository.findById(id);

        if (device.isEmpty()) {
            return null;
        }

        return device.map(d -> deviceConverter.convertToModel(d)).orElse(null);
    }

    @Override
    public void deleteDevice(Long id) {
        var device = devicesRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (device.getState().equals(DeviceState.INUSE)) {
            throw new DeleteDeviceInUseException();
        }

        devicesRepository.delete(device);
    }

    @Override
    public void updateDevice(Long id, DeviceModel deviceModel) {
        var device = devicesRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (device.getState().equals(DeviceState.INUSE)) {
            throw new UpdateWhileInUseException();
        }

        device.setName(deviceModel.getName());
        device.setBrand(deviceModel.getBrand());
        device.setState(deviceModel.getState());

        devicesRepository.save(device);
    }

    @Override
    public void patchDevice(Long id, List<DeviceUpdateModel> updates) {
        var validFieldNames = Arrays.asList("name", "brand", "state");

        var invalidFieldName = updates.stream()
                .filter(u -> !validFieldNames.contains(u.getFieldName()))
                .findFirst();

        if (invalidFieldName.isPresent()) {
            throw new InvalidPatchFieldException(String.join(",", validFieldNames));
        }

        var device = devicesRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (device.getState().equals(DeviceState.INUSE)) {
            var invalidUpdateFieldsWhileInUse = updates.stream()
                    .filter(u -> u.getFieldName().equals("name") || u.getFieldName().equals("brand"))
                    .findFirst();

            if (invalidUpdateFieldsWhileInUse.isPresent()) {
                throw new UpdateWhileInUseException();
            }
        }

        for (DeviceUpdateModel update : updates) {
            Field field;

            try {
                field = device.getClass().getDeclaredField(update.getFieldName());
                field.setAccessible(true);
                field.set(device, getFieldValue(update.getFieldName(), update.getNewValue()));
            } catch (Exception ex) {
                throw new InvalidPatchFieldException(String.join(",", validFieldNames));
            }
        }

        devicesRepository.save(device);
    }

    private Object getFieldValue(String fieldName, String newValue) {
        return switch (fieldName) {
            case "name", "brand" -> newValue;
            case "state" -> DeviceState.valueOf(newValue);
            default -> null;
        };
    }
}
