package task.devices;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import task.devices.entities.DeviceState;

class DevicesApplicationTests {

	@Test
	void contextLoads() {
		var deviceState = DeviceState.valueOf("INUSE");
	}

}
