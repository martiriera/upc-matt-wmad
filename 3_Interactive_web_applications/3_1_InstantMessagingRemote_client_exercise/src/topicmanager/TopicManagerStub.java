package topicmanager;

import apiREST.apiREST_TopicManager;
import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.List;
import publisher.Publisher;
import publisher.PublisherStub;
import subscriber.Subscriber;
import webSocketService.WebSocketClient;

public class TopicManagerStub implements TopicManager {

    public String user;

    public TopicManagerStub(String user) {
        WebSocketClient.newInstance();
        this.user = user;
    }

    public void close() {
        WebSocketClient.close();
    }

    @Override
    public Publisher addPublisherToTopic(Topic topic) {
        apiREST_TopicManager.addPublisherToTopic(topic);
        return new PublisherStub(topic);
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
        apiREST_TopicManager.removePublisherFromTopic(topic);
    }

    @Override
    public Topic_check isTopic(Topic topic) {
        return apiREST_TopicManager.isTopic(topic);
    }

    @Override
    public List<Topic> topics() {
        return apiREST_TopicManager.topics();
    }

    @Override
    public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
        Subscription_check sc = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
        Topic_check tc = this.isTopic(topic);
        if (tc.isOpen == true) {
            WebSocketClient.addSubscriber(topic, subscriber);
            sc = new Subscription_check(topic, Subscription_check.Result.OKAY);
        }
        return sc;
    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        Subscription_check sc = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
        Topic_check tc = this.isTopic(topic);
        if (tc.isOpen) {
            WebSocketClient.removeSubscriber(topic);
            sc = new Subscription_check(topic, Subscription_check.Result.OKAY);
        }
        return sc;
    }

}
