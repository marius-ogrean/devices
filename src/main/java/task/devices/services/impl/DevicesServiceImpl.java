package task.devices.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import task.devices.converters.DeviceConverter;
import task.devices.entities.Device;
import task.devices.entities.DeviceState;
import task.devices.models.DeviceModel;
import task.devices.repositories.DevicesRepository;
import task.devices.services.DateService;
import task.devices.services.DevicesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DevicesServiceImpl implements DevicesService {

    private DevicesRepository devicesRepository;
    private DateService dateService;
    private DeviceConverter deviceConverter;

    @PersistenceContext
    private EntityManager entityManager;

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
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Device.class);

        var deviceRoot = criteriaQuery.from(Device.class);

        var predicates = new ArrayList<Predicate>();

        if (brand != null) {
            predicates.add(criteriaBuilder.equal(deviceRoot.get("brand"), brand));
        }

        if (state != null) {
            predicates.add(criteriaBuilder.equal(deviceRoot.get("state"), state));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().stream()
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
        var device = devicesRepository.findById(id);

        if (device.isEmpty()) {
            throw new IllegalArgumentException();
        }

        devicesRepository.delete(device.get());
    }
}
