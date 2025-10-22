package task.devices.entities;

public enum DeviceState {
    AVAILABLE("A"), INUSE("U"), INACTIVE("I");

    private final String code;

    DeviceState(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
