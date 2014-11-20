package cn.gerodan.webREST;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class RESTServicesList extends Application {
    private static Set<Object> services = new HashSet<Object>();

    public RESTServicesList() {
    	services.add(new OrdersService());
        services.add(new HBaseFSService());
    }

    @Override
    public Set<Object> getSingletons() {
        return services;
    }

    public static Set<Object> getServices() {
        return services;
    }
}
