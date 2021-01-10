package webSocketService;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.SQLException;
import javax.inject.Inject;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import subscriber.Subscriber;
import subscriber.SubscriberImpl;
import util.Subscription_request;
import publisher.Publisher;
import util.Global;

@ServerEndpoint("/ws")
public class WebSocketServer {

    @Inject
    Global global;

    @OnMessage
    public void onMessage(String json, Session session)
            throws IOException, SQLException {

        System.out.println("onMessage - Subscription_request: " + json);

        // Get the JSON subscription request and convert it to Java object 
        Subscription_request s_req = new Gson().fromJson(json, Subscription_request.class);

        // Subcription is ADD
        if (s_req.type == Subscription_request.Type.ADD) {
            // Get the publisher that corresponds to the topic contained on the subscription request
            Publisher publisher = global.getTopicManager().publisher(s_req.topic);
            // Protect from not getting the publisher
            if (publisher != null) {
                // Get the subscriber matching this session
                Subscriber subscriber = publisher.subscriber(session);
                if (subscriber == null) {
                    // If no subscriber on the session create a new one and subscribe to the topic
                    subscriber = new SubscriberImpl(session);
                    global.getTopicManager().subscribe(s_req.topic, subscriber);
                    System.out.println("New subscription to topic " + s_req.topic.name + "\n");
                }
            }
            // Subscription is to REMOVE
        } else if (s_req.type == Subscription_request.Type.REMOVE) {
            // Get the publisher that corresponds to the topic contained on the subscription request
            Publisher publisher = global.getTopicManager().publisher(s_req.topic);
            if (publisher != null) {
                // Get the subscriber matching this session
                Subscriber subscriber = publisher.subscriber(session);
                if (subscriber != null) {
                    // Unsuscribe that subscriber from the topic
                    global.getTopicManager().unsubscribe(s_req.topic, subscriber);
                    System.out.println("Unsuscribed from " + s_req.topic.name + "\n");
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("new session: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("closed session: " + session.getId());
    }

}
