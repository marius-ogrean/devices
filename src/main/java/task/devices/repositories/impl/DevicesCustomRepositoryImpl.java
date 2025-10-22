package task.devices.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import task.devices.entities.Device;
import task.devices.entities.DeviceState;
import task.devices.repositories.DevicesCustomRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class DevicesCustomRepositoryImpl implements DevicesCustomRepository {
    private EntityManager entityManager;

    @Override
    public List<Device> findByBrandAndState(String brand, DeviceState state) {
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
        return query.getResultList();
    }
}
