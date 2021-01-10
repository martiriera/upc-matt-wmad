package webSocketService;

import apiREST.Cons;
import com.google.gson.Gson;
import entity.Message;
import entity.Topic;
import util.Subscription_close;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import subscriber.Subscriber;
import util.Subscription_request;

@ClientEndpoint
public class WebSocketClient {

    static Map<Topic, Subscriber> subscriberMap;
    static Session session;

    public static void newInstance() {
        subscriberMap = new HashMap<Topic, Subscriber>();
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(WebSocketClient.class,
                    URI.create(Cons.SERVER_WEBSOCKET));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addSubscriber(Topic topic, Subscriber subscriber) {
        try {
            // Create a subscription request of type ADD
            Subscription_request subscriptionRequest = new Subscription_request(topic, Subscription_request.Type.ADD);
            // Convert subs. req. Java object to JSON
            String subscriptionRequestJson = new Gson().toJson(subscriptionRequest);
            // Send the JSON to the session to inform the server about the subscription
            session.getBasicRemote().sendText(subscriptionRequestJson);
            System.out.println("addSubscriber: " + subscriptionRequestJson);
            // Put the subscription on the client's subscriberMap too
            subscriberMap.put(topic, subscriber);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void removeSubscriber(Topic topic) {
        try {
            // Create a subscription request of type REMOVE
            Subscription_request subscriptionRequest = new Subscription_request(topic, Subscription_request.Type.REMOVE);
            // Convert subs. req. Java object to JSON
            String unsubscriptionRequestJson = new Gson().toJson(subscriptionRequest);
            // Send the JSON to the session to inform the server about the unsubscription
            session.getBasicRemote().sendText(unsubscriptionRequestJson);
            System.out.println("removeSubscriber: " + unsubscriptionRequestJson);
            System.out.println("ONMESSAGE: " + subscriberMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String json) {

        Gson gson = new Gson();
        Subscription_close subs_close = gson.fromJson(json, Subscription_close.class);

        //ordinary message from topic:
        if (subs_close.cause == null) {
            Message message = gson.fromJson(json, Message.class);
            System.out.println("onMessage: " + message.content);
            for (Map.Entry<Topic, Subscriber> entry : subscriberMap.entrySet()) {
                Topic key = entry.getKey();
                Subscriber value = entry.getValue();
                if (key.equals(subs_close.topic)) {
                    value.onMessage(message);
                }
            }
        } //ending subscription message:
        // TODO: Never entering here?
        else {
            System.out.println("onMessage: " + subs_close.cause);
            Subscriber subscriber = subscriberMap.get(subs_close.topic);
            subscriber.onClose(subs_close);
            System.out.println("ONMESSAGE: " + subscriberMap);
            subscriberMap.remove(subs_close.topic);
        }
    }
}
