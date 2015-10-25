package io.ruck.microservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ruckc
 */
public class ServiceClassLoader {

    @SuppressWarnings("unchecked")
    public static <T> List<Class<? extends T>> load(Class<T> cls) {
        try {
            List<Class<? extends T>> list = new ArrayList<>();
            Enumeration<URL> urls = ClassLoader.getSystemResources(String.format("META-INF/services/%s", cls.getName()));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        try {
                            Class<T> impl = (Class<T>) Class.forName(line);
                            list.add(impl);
                        } catch (ClassNotFoundException ex) {
                            LoggerFactory.getLogger(ServiceClassLoader.class).warn("Can't load '" + line + "' as an " + cls.getName(), ex);
                        }
                    }
                }
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
