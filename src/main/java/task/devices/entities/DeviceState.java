package task.devices.entities;

public enum DeviceState {
    AVAILABLE("AV"), INUSE("US"), INACTIVE("IN");

    private final String code;

    DeviceState(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
