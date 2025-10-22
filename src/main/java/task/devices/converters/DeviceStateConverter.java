package task.devices.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import task.devices.entities.DeviceState;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class DeviceStateConverter implements AttributeConverter<DeviceState, String> {
    @Override
    public String convertToDatabaseColumn(DeviceState deviceState) {
        return deviceState.getCode();
    }

    @Override
    public DeviceState convertToEntityAttribute(String code) {
        return Stream.of(DeviceState.values())
                .filter(s -> s.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
