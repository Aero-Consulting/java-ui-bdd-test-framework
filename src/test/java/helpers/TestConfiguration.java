package helpers;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public final class TestConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(TestConfiguration.class);
    @Expose
    private static volatile TestConfiguration instance;
    private HashMap<String, Hub> hubs;

    private TestConfiguration() {
    }

    public static TestConfiguration getInstance() {
        TestConfiguration result = instance;
        if (result != null) {
            return result;
        }
        synchronized (TestConfiguration.class) {
            if (instance == null) {
                instance = new TestConfiguration();
                instance.readConfiguration();
            }
            return instance;
        }
    }

    private static Hub getHub(String hub) {
        TestConfiguration configuration = getInstance();
        if (configuration.hubs.containsKey(hub)) {
            return configuration.hubs.get(hub);
        } else {
            logger.error("Hub: {} not found", hub);
            throw new RuntimeException(String.format("Hub:%s not found", hub));
        }
    }

    public static Device getDefaultDevice() {
        TestConfiguration configuration = getInstance();
        Hub defaultHub = configuration.hubs.get("default");
        Device defaultDevice = defaultHub.devices.values().iterator().next();
        return defaultDevice;
    }

    public static Device getDeviceForHub(String hubName, String deviceName) {
        Hub hub = getHub(hubName);
        HashMap<String, Device> devices = hub.devices;
        if (devices.containsKey(deviceName)) {
            return devices.get(deviceName);
        } else {
            logger.error("Device: {} not found for hub: {}", deviceName, hubName);
            throw new RuntimeException(String.format("Device: %s not found for hub: %s", deviceName, hubName));
        }
    }

    public static URL getHubUrl(String hubName) {
        Hub hub = getHub(hubName);
        try {
            return new URL(hub.url.replace("{Username}", System.getenv("bsUser")).replace("{AccessKey}", System.getenv("bsKey")));
        } catch (MalformedURLException e) {
            logger.error("Check URL for hub:{}", hub);
            throw new RuntimeException(e.getCause());
        }
    }

    private void readConfiguration() {
        Gson gson = new Gson();
        JsonReader reader = null;
        String fileName = System.getProperty("testconfig", "testconfig.json");
        try {
            reader = new JsonReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        instance = gson.fromJson(reader, TestConfiguration.class);
    }

    private class Hub {
        private String url;
        private HashMap<String, Device> devices;
    }
}
