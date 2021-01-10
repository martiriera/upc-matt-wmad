package publisher;

import util.Subscription_close;
import util.Message;
import util.Topic;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import subscriber.Subscriber;
import subscriber.SubscriberImpl;
import javax.websocket.Session;

public class PublisherImpl implements Publisher {

    private List<Subscriber> subscriberSet;
    private int numPublishers;
    private Topic topic;

    public List<Subscriber> getSubscriberSet() {
        return subscriberSet;
    }

    public PublisherImpl(Topic topic) {
        subscriberSet = new ArrayList<Subscriber>();
        numPublishers = 1;
        this.topic = topic;
    }

    @Override
    public void incPublishers() {
        numPublishers = numPublishers + 1;
//    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int decPublishers() {
        numPublishers = numPublishers - 1;
        if (numPublishers == 0) {
            detachAllSubscribers();
        }
        return numPublishers;
//    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void attachSubscriber(Subscriber subscriber) {
        subscriberSet.add(subscriber);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean detachSubscriber(Subscriber subscriber) {
        return subscriberSet.remove(subscriber);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void detachAllSubscribers() {
        if (numPublishers == 0) {
            Iterator<Subscriber> it = subscriberSet.iterator();
            while (it.hasNext()) {
                it.next().onClose(new Subscription_close(topic, Subscription_close.Cause.PUBLISHER));
            }
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void publish(Message message) {
        Iterator<Subscriber> it = subscriberSet.iterator();
        while (it.hasNext()) {
            it.next().onMessage(message);
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Subscriber subscriber(Session session) {
        // For every subscriber on the subsSet
        for (Subscriber subscriber : subscriberSet) {
            // Convert to subscriberImpl
            SubscriberImpl subscriberImpl = (SubscriberImpl) subscriber;
            // Check if session matches the given one
            if (subscriberImpl.session == session) {
                return subscriber;
            }
        }
        return null;
    }
}
