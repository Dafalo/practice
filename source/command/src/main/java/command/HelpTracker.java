package command;

import intersource.INews;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import java.util.*;

/**
 * Services registered under the specified class name.
 * This class registered the service and remove it. Realize user interface.
 */

public class HelpTracker extends ServiceTracker implements ServiceTrackerCustomizer {
    public Map<String, INews> newsImps = Collections.synchronizedMap(new HashMap<>());

    public HelpTracker(BundleContext context) {
        super(context, INews.class.getName(), null);
        open();
    }

    /**
     * This method is only called when this ServiceTracker object has been constructed with a null ServiceTrackerCustomizer argument
     * Watch for services and catch them.
     *
     * @param reference Reference to service being added to this ServiceTracker object
     * @return Object of service, which it wants to add
     */
    @Override
    public Object addingService(ServiceReference reference) {
        Object object = context.getService(reference);
        if (object instanceof INews) {
            INews inews = (INews) context.getService(reference);
            newsImps.put(inews.getName(), inews);
        }
        return object;
    }

    /**
     * This method is only called when this ServiceTracker object has been constructed with a null ServiceTrackerCustomizer argument.
     *
     * @param reference Reference to removed service
     * @param service   The service object for the removed service.
     */
    @Override
    public void removedService(ServiceReference reference, Object service) {
        Object object = context.getService(reference);
        if (object instanceof INews) {
            INews inews = (INews) service;
            newsImps.remove(inews.getName());
        }
        super.removedService(reference, service);
    }

    /**
     * User interface.
     */
    private final String LIST_OF_SOURCES = String.format("Enter number of the list%n" +
            "1.Lenta%n" +
            "2.Aif%n" +
            "3.All%n");

    /**
     * Read the parameter to get the requires news.
     */
    public void stats() {
        System.out.print(LIST_OF_SOURCES);

        Scanner in = new Scanner(System.in);
        String param = in.nextLine();

        Set<INews> newsSet = getNewsSet(param);

        process(newsSet);
    }

    /**
     * Show results of user's query.
     *
     * @param newsSet Set implementation of requires source
     */
    private void process(Set<INews> newsSet) {
        newsSet.forEach(news -> {
            System.out.println(news.getName());
            news.getStats();
            System.out.println();
        });
    }

    /**
     * Get the parametr from user and by switch choose needed implementation.
     *
     * @param param one of the meaning from user interface
     * @return Set implementation of requires source
     */
    private Set<INews> getNewsSet(String param) {
        Set<INews> news = new HashSet<>();
        switch (param) {
            case "1":
                news.add(newsImps.get("Lenta"));
                break;
            case "2":
                news.add(newsImps.get("Aif"));
                break;
            case "3":
                news.add(newsImps.get("Lenta"));
                news.add(newsImps.get("Aif"));
                break;
            default:
                System.out.println("Error.Invalid command.");
                break;
        }
        return news;
    }
}

