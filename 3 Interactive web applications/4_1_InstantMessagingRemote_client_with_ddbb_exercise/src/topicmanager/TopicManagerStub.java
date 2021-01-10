package topicmanager;

import apiREST.apiREST_Message;
import apiREST.apiREST_Publisher;
import apiREST.apiREST_Subscriber;
import apiREST.apiREST_Topic;
import entity.Message;
import util.Subscription_check;
import entity.Topic;
import util.Topic_check;
import entity.User;
import publisher.Publisher;
import java.util.List;
import publisher.PublisherStub;
import subscriber.Subscriber;
import webSocketService.WebSocketClient;

public class TopicManagerStub implements TopicManager {

    public User user;

    public TopicManagerStub(User user) {
        WebSocketClient.newInstance();
        this.user = user;
    }

    public void close() {
        WebSocketClient.close();
    }

    @Override
    public Publisher addPublisherToTopic(Topic topic) {
//      - Need to use both entity.Publisher and PublisherStub
//      - use apiRest (Publisher)
        PublisherStub publisherStub = new PublisherStub(topic);
        entity.Publisher entityPublisher = new entity.Publisher();
        entityPublisher.setTopic(topic);
        entityPublisher.setUser(user);
        apiREST_Publisher.createPublisher(entityPublisher);
        return publisherStub;
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
//        - Use entity.Publisher
//        - Use apiRest
        entity.Publisher entityPublisher = new entity.Publisher();
        entityPublisher.setTopic(topic);
        entityPublisher.setUser(user);
        apiREST_Publisher.deletePublisher(entityPublisher);
    }

    @Override
    public Topic_check isTopic(Topic topic) {
//        - Use apiRest
        return apiREST_Topic.isTopic(topic);
    }

    @Override
    public List<Topic> topics() {
        //        - Use apiRest
        return apiREST_Topic.allTopics();
    }

    @Override
    public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
        /* Hints:
        - Use entity.Subscriber and Subscriber
        - Use apiRest
        - Update WebSocketClient
         */
        Subscription_check sc = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
        Topic_check tc = this.isTopic(topic);
        if (tc.isOpen == true) {
            entity.Subscriber entitySubscriber = new entity.Subscriber();
            entitySubscriber.setTopic(topic);
            entitySubscriber.setUser(user);

            apiREST_Subscriber.createSubscriber(entitySubscriber);
            WebSocketClient.addSubscriber(topic, subscriber);
            sc = new Subscription_check(topic, Subscription_check.Result.OKAY);
        }
        return sc;
    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        /* Hints:
        - Use entity.Subscriber and Subscriber
        - Use apiRest
        - Update WebSocketClient
         */
        Subscription_check sc = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
        Topic_check tc = this.isTopic(topic);
        if (tc.isOpen) {
            entity.Subscriber entitySubscriber = new entity.Subscriber();
            entitySubscriber.setTopic(topic);
            entitySubscriber.setUser(user);
            WebSocketClient.removeSubscriber(topic);
            sc = apiREST_Subscriber.deleteSubscriber(entitySubscriber);
        }
        return sc;
    }

    @Override
    public Publisher publisherOf() {
        /* Hints:
        - Use entity.Publisher and PublisherStub
        - Use apiRest
         */
        entity.Publisher entityPublisher = apiREST_Publisher.PublisherOf(user);
        PublisherStub publisherStub = null;
        if(entityPublisher != null){
            publisherStub = new PublisherStub(entityPublisher.getTopic());
        }
        return publisherStub;
    }

    @Override
    public List<entity.Subscriber> mySubscriptions() {
        /* Hints:
         - Use apiRest
         */
        return apiREST_Subscriber.mySubscriptions(user);
    }

    @Override
    public List<Message> messagesFrom(Topic topic) {
        /* Hints:
         - Use apiRest
         */
        return apiREST_Message.messagesFromTopic(topic);
    }

}
