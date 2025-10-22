package task.devices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.devices.entities.Device;

@Repository
public interface DevicesRepository extends JpaRepository<Device, Long>, DevicesCustomRepository {
}
