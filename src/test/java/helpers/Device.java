package helpers;

import java.util.HashMap;

public class Device {
    private String driver;
    private HashMap<String, String> capabilities;

    public String getDriver() {
        return driver;
    }

    public HashMap<String, String> getCapabilities() {
        return capabilities;
    }
}
