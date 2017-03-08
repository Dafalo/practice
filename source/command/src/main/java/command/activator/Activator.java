package command.activator;


import command.HelpTracker;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * This class creates a command and registers service by using BundleActivator.
 * Customizes the starting and stopping of a bundle.
 */
public class Activator implements BundleActivator {

    /**
     * Called when this bundle is started so the Framework can perform the bundle-specific activities necessary
     * to start this bundle.     *
     * @param bundleContext The execution context of the bundle being started
     * @throws Exception java.lang.Exception - If this method throws an exception, this bundle is marked as stopped
     * and the Framework will remove this bundle's listeners,
     * unregister all services registered by this bundle, and release all services used by this bundle
     */
    @Override
    public void start(BundleContext bundleContext) throws Exception {

        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("osgi.command.scope", "news");
        hashtable.put("osgi.command.function", "stats");
        HelpTracker tracker = new HelpTracker(bundleContext);
        bundleContext.registerService(HelpTracker.class.getName(), tracker, hashtable);
    }

    /**
     * Called when this bundle is stopped so the Framework can perform the bundle-specific activities necessary to stop the bundle.
     * @param bundleContext The execution context of the bundle being stopped
     * @throws Exception java.lang.Exception - If this method throws an exception, the bundle is still marked as stopped,
     * and the Framework will remove the bundle's listeners, unregister all services registered by the bundle, and release all services used by the bundle
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
