package lentaimpl.activator;


import lentaimpl.LentaImpl;
import intersource.INews;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Registers INews interface as a service with implementation for source "Lenta" by the BundleActivator.
 */
public class Activator implements BundleActivator {
    ServiceRegistration registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        registration = bundleContext.registerService(INews.class.getName(), new LentaImpl(), null);

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
