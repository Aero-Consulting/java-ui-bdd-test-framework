package helpers.factories;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

import static helpers.AppiumHelper.isIOS;

public class ObjectProvider {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    @SuppressWarnings("unchecked")
    public static <T> T instance(final Class<T> objectClass, final Object... args) {
        Class<T> instanceClass = objectClass;
        String instanceClassName = objectClass.getName();
        try {
            if (isIOS()) {
                instanceClassName = instanceClassName.replaceFirst(".android.", String.format(".%s.", "ios"))
                        + "IOS";
            }
            instanceClass = (Class<T>) Class.forName(instanceClassName);
        } catch (ClassNotFoundException e) {
            logger.debug("Can not find class {}", instanceClassName);
        }
        try {
            final T instanceObject = ConstructorUtils.invokeConstructor(instanceClass, args);
            if (instanceObject == null) {
                throw new NoSuchMethodException("Can not find matching accessible constructor");
            }
            return instanceObject;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Can not create instance of class [{}] with arguments {}", instanceClassName, args);
            throw new RuntimeException("Can not create instance of class " + instanceClassName);
        }
    }
}
