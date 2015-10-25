package io.ruck.microservice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.undertow.Undertow;
import java.util.ServiceLoader;
import javax.ws.rs.core.Application;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import io.undertow.Undertow.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ruckc
 */
public class Server {

    private static final Logger LOG = LogManager.getLogger();
    private final UndertowJaxrsServer server;
    private final Injector injector;

    public Server() {
        injector = Guice.createInjector(ServiceLoader.load(Module.class));
        server = new UndertowJaxrsServer();
    }

    public void start() {
        start(8080, "0.0.0.0");
    }

    public void start(int port, String host) {
        start(Undertow
                .builder()
                .addHttpListener(port, host));
    }

    public void start(Builder b) {
        ServiceClassLoader.load(Application.class)
                .iterator()
                .forEachRemaining(app -> {
                    LOG.info("deploying " + app);
                    injector.injectMembers(app);
                    server.deploy(injector.getInstance(app));
                });
        server.start(b);
    }

    public void stop() {
        server.stop();
    }
}
