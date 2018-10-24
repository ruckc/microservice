package io.ruck.microservice;

import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import io.undertow.Undertow.Builder;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 *
 * @author ruckc
 */
public class Server {

    private static final Logger LOG = LogManager.getLogger();
    private final ApplicationLoader loader;
    private final UndertowJaxrsServer server;

    public Server(ApplicationLoader loader) {
        SLF4JBridgeHandler.install();
        server = new UndertowJaxrsServer();
        this.loader = loader;
    }

    public void start() {
        start(8080, "0.0.0.0");
    }

    public void start(int port, String host) {
        start(Undertow.builder().addHttpListener(port, host));
    }

    public void start(Builder b) {
        loader.loadApplications()
                .stream()
                .forEach(this::deploy);
        server.start(b);
    }

    private void deploy(Application app) {
        LOG.info("Deploying application: " + app + " path: " + app.getClass().getAnnotation(ApplicationPath.class).value());
        server.deploy(app);
    }

    public void stop() {
        server.stop();
    }
}
