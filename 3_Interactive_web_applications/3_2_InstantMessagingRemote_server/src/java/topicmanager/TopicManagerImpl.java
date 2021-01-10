package topicmanager;

import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import publisher.Publisher;
import publisher.PublisherImpl;
import subscriber.Subscriber;

public class TopicManagerImpl implements TopicManager {

  private Map<Topic, Publisher> topicMap;

  public TopicManagerImpl() {
    topicMap = new HashMap<Topic, Publisher>();
  }

  @Override
    public Publisher addPublisherToTopic(Topic topic) {
        Publisher publisher = new PublisherImpl(topic);
        if (topicMap.containsKey(topic)) {
            publisher = topicMap.get(topic);
        } else {
            topicMap.put(topic, publisher);
        }
        return publisher;
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
        if (topicMap.containsKey(topic)) {
            Publisher publisher = topicMap.get(topic);
            if (publisher.decPublishers() == 0) {
                topicMap.remove(topic);
            }
        }
    }

    @Override
    public Topic_check isTopic(Topic topic) {
        Topic_check tc = new Topic_check(topic, this.topicMap.containsKey(topic));
        return tc;
    }

    @Override
    public List<Topic> topics() {
        List<Topic> topics = new ArrayList<Topic>();
        topics.addAll(topicMap.keySet());
        return topics;
    }

    @Override
    public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
        // MIRAR SI TOPIC EXISTEIX, SI ES QUE SI FER ATTACH DEL SUBSCRIBER
        Subscription_check sc = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
        Topic_check tc = this.isTopic(topic);
        if (tc.isOpen == true) {
            Publisher publisher = topicMap.get(topic);
            publisher.attachSubscriber(subscriber);
            sc = new Subscription_check(topic, Subscription_check.Result.OKAY);
        }
        return sc;
    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        // MIRAR SI TOPIC EXISTEIX I SI HI HA SUBSCRIPCIÓ
        Subscription_check sc = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
        Topic_check tc = this.isTopic(topic);
        if (tc.isOpen) {
            Publisher publisher = topicMap.get(topic);
            List<Subscriber> subscriberSet = publisher.getSubscriberSet();
            if (!subscriberSet.contains(subscriber)) {
                sc = new Subscription_check(topic, Subscription_check.Result.NO_SUBSCRIPTION);
            } else {
                publisher.detachSubscriber(subscriber);
                sc = new Subscription_check(topic, Subscription_check.Result.OKAY);
            }
        }
        return sc;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
  // Get the publisher of the given topic
  @Override
  public Publisher publisher(Topic topic){
    return topicMap.get(topic);
  }
  
}
