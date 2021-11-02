package helpers.factories;

import helpers.Device;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CapabilitiesFactory {
    private static final Logger logger = LoggerFactory.getLogger(CapabilitiesFactory.class);

    public static Capabilities getCapabilities(Device device, String app) {
        DesiredCapabilities capabilities = new DesiredCapabilities(device.getCapabilities());
        if (!app.startsWith("bs")) {
            Path pathApp = Paths.get(app);
            app = pathApp.toAbsolutePath().toString();
        }
        capabilities.setCapability("app", app);
        capabilities.setCapability("autoGrantPermissions", "true");
        return capabilities;
    }
}
