package io.ruck.microservice;

import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.Application;

/**
 *
 * @author ruckc
 */
public class StaticApplicationLoader implements ApplicationLoader {
    private final List<Application> apps;
    
    public StaticApplicationLoader(List<Application> apps) {
        this.apps = apps;
    }

    @Override
    public Collection<Application> loadApplications() {
        return apps;
    }
    
}
