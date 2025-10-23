package task.devices.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.devices.converters.DeviceConverter;
import task.devices.entities.Device;
import task.devices.entities.DeviceState;
import task.devices.exceptions.DeleteDeviceInUseException;
import task.devices.models.DeviceModel;
import task.devices.repositories.DevicesRepository;
import task.devices.services.DateService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.*;

public class DevicesServiceTest {

    @Test
    public void createDeviceTest() {
        var devicesRepositoryMock = mock(DevicesRepository.class);
        var dateServiceMock = mock(DateService.class);

        var now = LocalDateTime.of(2025, 10, 23, 0, 0);

        when(dateServiceMock.getCurrentDateTime()).thenReturn(now);

        AtomicReference<Device> savedDevice = new AtomicReference<>();

        doAnswer(invocationOnMock -> {
            savedDevice.set(invocationOnMock.getArgument(0));
            savedDevice.get().setId(15L);
            return savedDevice.get();
        }).when(devicesRepositoryMock).save(any(Device.class));

        var devicesService = new DevicesServiceImpl(devicesRepositoryMock, dateServiceMock, null);

        var name = "name1";
        var brand = "brand1";
        var state = DeviceState.AVAILABLE;

        var deviceModel = DeviceModel.builder()
                .name(name)
                .brand(brand)
                .state(state)
                .build();

        var result = devicesService.createDevice(deviceModel);

        Assertions.assertEquals(15L, result);
        Assertions.assertEquals(savedDevice.get().getName(), name);
        Assertions.assertEquals(savedDevice.get().getBrand(), brand);
        Assertions.assertEquals(savedDevice.get().getState(), state);
        Assertions.assertEquals(savedDevice.get().getCreationTime(), now);
    }

    @Test
    public void getAllDevicesTest() {
        var devicesRepositoryMock = mock(DevicesRepository.class);
        var deviceConverter = new DeviceConverter();

        when(devicesRepositoryMock.findByBrandAndState(any(), any()))
                .thenReturn(Arrays.asList(Device.builder()
                                .id(1L)
                                .name("name1")
                                .build(),
                        Device.builder()
                                .id(2L)
                                .name("name2")
                                .build()));

        var devicesService = new DevicesServiceImpl(devicesRepositoryMock, null, deviceConverter);

        var result = devicesService.getAllDevices("brand", null);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().anyMatch(d -> d.getId().equals(1L)));
        Assertions.assertTrue(result.stream().anyMatch(d -> d.getId().equals(2L)));
    }

    @Test
    public void deleteDeviceBadIdTest() {
        var devicesRepositoryMock = mock(DevicesRepository.class);

        when(devicesRepositoryMock.findById(any())).thenReturn(Optional.empty());

        var devicesService = new DevicesServiceImpl(devicesRepositoryMock, null, null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> devicesService.deleteDevice(1L));
    }

    @Test
    public void deleteInUseDeviceTest() {
        var devicesRepositoryMock = mock(DevicesRepository.class);

        when(devicesRepositoryMock.findById(any())).thenReturn(Optional.of(Device.builder()
                .state(DeviceState.INUSE)
                .build()));

        var devicesService = new DevicesServiceImpl(devicesRepositoryMock, null, null);

        Assertions.assertThrows(DeleteDeviceInUseException.class, () -> devicesService.deleteDevice(1L));
    }

    @Test
    public void deleteDeviceSuccessTest() {
        var devicesRepositoryMock = mock(DevicesRepository.class);

        var id = 23L;

        when(devicesRepositoryMock.findById(any())).thenReturn(Optional.of(Device.builder()
                .id(id)
                .state(DeviceState.AVAILABLE)
                .build()));

        AtomicReference<Device> deletedDevice = new AtomicReference<>();

        doAnswer(invocationOnMock -> {
            deletedDevice.set(invocationOnMock.getArgument(0));
            return null;
        }).when(devicesRepositoryMock).delete(any(Device.class));

        var devicesService = new DevicesServiceImpl(devicesRepositoryMock, null, null);

        devicesService.deleteDevice(id);

        Assertions.assertEquals(id, deletedDevice.get().getId());
    }
}
