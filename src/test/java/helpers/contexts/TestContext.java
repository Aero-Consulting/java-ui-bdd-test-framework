package helpers.contexts;

import org.testng.ITestContext;

public final class TestContext {
    private static final String DEFAULT_HUB = "default";
    private static final String DEFAULT_DEVICE = "default";
    private static final String DEFAULT_APP = "app/demo-android.apk";
    private static final ThreadLocal<TestContext> context = new ThreadLocal<>();

    private final ITestContext testContext;
    private final String hub;
    private final String device;
    private final String app;

    public TestContext(ITestContext testContext, String hub, String device, String app) {
        this.testContext = testContext;
        this.hub = hub;
        this.device = device;
        this.app = app;
    }

    public static void init(ITestContext testContext, String hub, String device, String app) {
        context.set(new TestContext(testContext, hub, device, app));
    }

    public static void initDefault() {
        init(null, DEFAULT_HUB, DEFAULT_DEVICE, DEFAULT_APP);
    }

    public static TestContext get() {
        return context.get();
    }

    public static void remove() {
        context.remove();
    }

    public static ITestContext context() {
        return context.get().getTestContext();
    }

    public static String hub() {
        return context.get().getHub();
    }

    public static String device() {
        return context.get().getDevice();
    }

    public static String app() {
        return context.get().getApp();
    }

    public ITestContext getTestContext() {
        return testContext;
    }

    public String getHub() {
        return hub;
    }

    public String getDevice() {
        return device;
    }

    public String getApp() {
        return app;
    }

    @Override
    public String toString() {
        return "TestContext{" +
                "testContext=" + testContext +
                ", hub='" + hub + '\'' +
                ", device='" + device + '\'' +
                ", app='" + app + '\'' +
                '}';
    }
}
