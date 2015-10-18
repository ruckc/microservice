package io.ruck.microservice;

import io.undertow.Undertow;
import java.util.ServiceLoader;
import javax.ws.rs.core.Application;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import io.undertow.Undertow.Builder;

/**
 *
 * @author ruckc
 */
public class Server {

    private final UndertowJaxrsServer server;

    public Server() {
        server = new UndertowJaxrsServer();
    }

    public void start() {
        start(8080, "0.0.0.0");
    }

    public void start(int port, String host) {
        start(Undertow.builder().addHttpListener(port, host));
    }

    public void start(Builder b) {
        ServiceLoader.load(Application.class)
                .iterator()
                .forEachRemaining(app -> server.deploy(app));
        server.start(b);
    }

    public void stop() {
        server.stop();
    }
}
