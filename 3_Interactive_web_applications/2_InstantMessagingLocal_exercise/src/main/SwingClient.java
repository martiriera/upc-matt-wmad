package main;

import util.Message;
import util.Subscription_check;
import util.Topic;
import subscriber.SubscriberImpl;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Set;
import publisher.Publisher;
import publisher.PublisherImpl;
import subscriber.Subscriber;
import topicmanager.TopicManager;
import util.Subscription_check.Result;
import util.Subscription_close;
import util.Subscription_close.Cause;
import util.Topic_check;

public class SwingClient {

    TopicManager topicManager;
    public Map<Topic, Subscriber> my_subscriptions;
    Publisher publisher;
    Topic publisherTopic;

    JFrame frame;
    JTextArea topic_list_TextArea;
    public JTextArea messages_TextArea;
    public JTextArea my_subscriptions_TextArea;
    JTextArea publisher_TextArea;
    JTextField argument_TextField;

    public SwingClient(TopicManager topicManager) {
        this.topicManager = topicManager;
        my_subscriptions = new HashMap<Topic, Subscriber>();
        publisher = null;
        publisherTopic = null;
    }

    public void createAndShowGUI() {

        frame = new JFrame("Publisher/Subscriber demo");
        frame.setSize(300, 300);
        frame.addWindowListener(new CloseWindowHandler());

        topic_list_TextArea = new JTextArea(5, 10);
        messages_TextArea = new JTextArea(10, 20);
        my_subscriptions_TextArea = new JTextArea(5, 10);
        publisher_TextArea = new JTextArea(1, 10);
        argument_TextField = new JTextField(20);

        JButton show_topics_button = new JButton("show Topics");
        JButton new_publisher_button = new JButton("new Publisher");
        JButton new_subscriber_button = new JButton("new Subscriber");
        JButton to_unsubscribe_button = new JButton("to unsubscribe");
        JButton to_post_an_event_button = new JButton("post an event");
        JButton to_close_the_app = new JButton("close app.");

        show_topics_button.addActionListener(new showTopicsHandler());
        new_publisher_button.addActionListener(new newPublisherHandler());
        new_subscriber_button.addActionListener(new newSubscriberHandler());
        to_unsubscribe_button.addActionListener(new UnsubscribeHandler());
        to_post_an_event_button.addActionListener(new postEventHandler());
        to_close_the_app.addActionListener(new CloseAppHandler());

        JPanel buttonsPannel = new JPanel(new FlowLayout());
        buttonsPannel.add(show_topics_button);
        buttonsPannel.add(new_publisher_button);
        buttonsPannel.add(new_subscriber_button);
        buttonsPannel.add(to_unsubscribe_button);
        buttonsPannel.add(to_post_an_event_button);
        buttonsPannel.add(to_close_the_app);

        JPanel argumentP = new JPanel(new FlowLayout());
        argumentP.add(new JLabel("Write content to set a new_publisher / new_subscriber / unsubscribe / post_event:"));
        argumentP.add(argument_TextField);

        JPanel topicsP = new JPanel();
        topicsP.setLayout(new BoxLayout(topicsP, BoxLayout.PAGE_AXIS));
        topicsP.add(new JLabel("Topics:"));
        topicsP.add(topic_list_TextArea);
        topicsP.add(new JScrollPane(topic_list_TextArea));
        topicsP.add(new JLabel("My Subscriptions:"));
        topicsP.add(my_subscriptions_TextArea);
        topicsP.add(new JScrollPane(my_subscriptions_TextArea));
        topicsP.add(new JLabel("I'm Publisher of topic:"));
        topicsP.add(publisher_TextArea);
        topicsP.add(new JScrollPane(publisher_TextArea));

        JPanel messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.PAGE_AXIS));
        messagesPanel.add(new JLabel("Messages:"));
        messagesPanel.add(messages_TextArea);
        messagesPanel.add(new JScrollPane(messages_TextArea));

        Container mainPanel = frame.getContentPane();
        mainPanel.add(buttonsPannel, BorderLayout.PAGE_START);
        mainPanel.add(messagesPanel, BorderLayout.CENTER);
        mainPanel.add(argumentP, BorderLayout.PAGE_END);
        mainPanel.add(topicsP, BorderLayout.LINE_START);

        frame.pack();
        frame.setVisible(true);
    }

    class showTopicsHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            topic_list_TextArea.setText("");
            List<Topic> topics = topicManager.topics();
            for (Topic topic : topics) {
                topic_list_TextArea.append(topic.name + "\n");
            }
        }
    }

    class newPublisherHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String topicName = argument_TextField.getText();
            // Si ja hi ha un publisher eliminar-lo 
            if (publisher != null) {
                topicManager.removePublisherFromTopic(publisherTopic);
            }
            // Crear un nou topic a partir del nom proporcionat 
            publisherTopic = new Topic(topicName);

            List<Topic> topics = topicManager.topics();
            if (topics.contains(publisherTopic)) {
                messages_TextArea.append("topic " + topicName + " already exists\n");
                argument_TextField.setText("");
            } else {
                // Afegir topic al topicMap<Topic, Publisher> 
                publisher = topicManager.addPublisherToTopic(publisherTopic);

                messages_TextArea.append("added as publisher of topic " + topicName + "\n");
                publisher_TextArea.setText(topicName);
                argument_TextField.setText("");
            }
        }
    }

        class newSubscriberHandler implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                String topicToSubscribeName = argument_TextField.getText();
                // Crear topic "temporal" amb el contingut que arriba de usuari
                Topic topicToSubscribe = new Topic(topicToSubscribeName);
                // Crear subscriber
                Subscriber subscriber = new SubscriberImpl(SwingClient.this);

                Subscription_check subscriptionCheck = topicManager.subscribe(topicToSubscribe, subscriber);
                if (subscriptionCheck.result == Result.OKAY) {
                    messages_TextArea.append("added as subscriber of topic " + topicToSubscribeName + "\n");
                    // Printar la llista actualitzada de subscripcions
                    my_subscriptions.put(topicToSubscribe, subscriber);
                    Set topicSet = my_subscriptions.keySet();
                    Iterator<Topic> it = topicSet.iterator();
                    String my_subs_string = "";
                    while (it.hasNext()) {
                        Topic nextTopic = it.next();
                        my_subs_string = my_subs_string + nextTopic.name + "\n";
                    }
                    my_subscriptions_TextArea.setText(my_subs_string);
                } else if (subscriptionCheck.result == Result.NO_TOPIC) {
                    // Eliminar el topic "temporal"
                    topicToSubscribe = null;
                    messages_TextArea.append("requested topic does not exist\n");
                }
                argument_TextField.setText("");
            }

        }

        class UnsubscribeHandler implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                String topicToUnsuscribeName = argument_TextField.getText();
                Topic topicToUnsubscribe = new Topic(topicToUnsuscribeName);
                Subscriber subscriber = my_subscriptions.get(topicToUnsubscribe);
                Subscription_check subscriptionCheck = topicManager.unsubscribe(topicToUnsubscribe, subscriber);
                if (null != subscriptionCheck.result) {
                    switch (subscriptionCheck.result) {
                        case OKAY:
                            my_subscriptions.remove(topicToUnsubscribe);
                            subscriber.onClose(new Subscription_close(topicToUnsubscribe, Cause.SUBSCRIBER));

                            // Printar de nou la llista
                            Set topicSet = my_subscriptions.keySet();
                            Iterator<Topic> it = topicSet.iterator();
                            String my_subs_string = "";
                            while (it.hasNext()) {
                                Topic nextTopic = it.next();
                                my_subs_string = my_subs_string + nextTopic.name + "\n";
                            }
                            my_subscriptions_TextArea.setText(my_subs_string);
                            break;

                        case NO_TOPIC:
                            messages_TextArea.append("requested topic does not exist\n");
                            break;
                        case NO_SUBSCRIPTION:
                            messages_TextArea.append("not subscribed to requested topic\n");
                            break;
                        default:
                            break;
                    }
                }
                argument_TextField.setText("");
            }
        }

        class postEventHandler implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                String messageContent = argument_TextField.getText();
                Message message = new Message(publisherTopic, messageContent);
                publisher.publish(message);
                argument_TextField.setText("");
            }
        }

        class CloseAppHandler implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                System.out.println("all users closed");
                System.exit(0);
            }
        }

        class CloseWindowHandler implements WindowListener {

            public void windowDeactivated(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {

                //...
                System.out.println("one user closed");
            }
        }
    }
