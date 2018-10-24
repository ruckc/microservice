package io.ruck.microservice;

import java.util.Collection;
import javax.ws.rs.core.Application;

/**
 *
 * @author ruckc
 */
public interface ApplicationLoader {
    Collection<Application> loadApplications();
}
