package hydro.pi.bridge.susbcription.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import hydro.pi.bridge.susbcription.abstracts.Listener;
import hydro.pi.bridge.susbcription.client.SubscriptionClient;

/**
 * Subscription Listeners class that will register a listener on an active
 * socket connection.
 * 
 * @author Sam Butler
 * @since Septemeber 2, 2022
 */
public class SubscriptionListeners {

    private List<Listener<?>> listeners;

    private SubscriptionClient client;

    /**
     * Default constructor requires an active client connection. If the connection
     * is not active then it will throw an exception.
     * 
     * @param client The client to register listeners on.
     */
    public SubscriptionListeners(SubscriptionClient client) {
        Assert.notNull(client.getSession(), "Client requires an active session!");
        this.client = client;
        listeners = new ArrayList<>();
    }

    /**
     * Will register the listener of the passed in type. It will add the listener to
     * the running list to keep a log of the active listeners.
     * 
     * @param <T> The generic type of the listener.
     * @param l   The listener to register.
     */
    public <T> void register(Listener<T> l) {
        Assert.notNull(l, "Listener can not be null");

        listeners.add(l);
        client.listen(l.url(), l.resultType()).subscribe(res -> l.run(res));
    }
}
