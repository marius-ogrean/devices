package task.devices.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import task.devices.entities.DeviceState;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class DeviceModel {
    private Long id;
    private String name;
    private String brand;
    private DeviceState state;
    private LocalDateTime creationTime;
}
