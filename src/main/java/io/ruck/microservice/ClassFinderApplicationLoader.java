package io.ruck.microservice;

import io.ruck.classfinder.ClassFinder;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.ws.rs.core.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Loads Application implementations found via io.ruck:classfinder
 * 
 * @author ruckc
 */
public class ClassFinderApplicationLoader implements ApplicationLoader {

    private final Logger LOG = LogManager.getLogger();

    @Override
    public Collection<Application> loadApplications() {
        ClassFinder applications = ClassFinder.getInstance("javax.ws.rs.core.Application");
        return applications.getClasses()
                .stream()
                .map((Class<?> cls) -> newInstance(cast(cls)))
                .collect(Collectors.toList());
    }
    
    @SuppressWarnings("unchecked")
    private Class<? extends Application> cast(Class<?> cls) {
        return (Class<? extends Application>) cls;
    }

    private Application newInstance(Class<? extends Application> cls) {
        try {
            return cls.getConstructor().newInstance();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            return null;
        }
    }
}
