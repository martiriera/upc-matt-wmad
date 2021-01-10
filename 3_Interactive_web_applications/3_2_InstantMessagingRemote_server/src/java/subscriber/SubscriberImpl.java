package subscriber;

import com.google.gson.Gson;
import util.Message;
import util.Subscription_close;
import java.io.IOException;
import javax.websocket.Session;

/**
 *
 * @author juanluis
 */

// We use a WebSocket session to asynchronously send messages to the client.
public class SubscriberImpl implements Subscriber {
    
    public Session session;

    public SubscriberImpl(Session session) {
        this.session = session;
    }

    @Override
    public void onMessage(Message message) {
        try {
            // Send through session the message in JSON format
            session.getBasicRemote().sendText(new Gson().toJson(message));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClose(Subscription_close subs_close) {
        try {
            // Send through session the subs_close event in JSON format
            session.getBasicRemote().sendText(new Gson().toJson(subs_close));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
