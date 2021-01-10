package demo.impl;

import demo.spec.Message;
import demo.spec.MessageWall;
import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MessageWall_and_RemoteLogin_Impl implements RemoteLogin, MessageWall {

    private List<Message> messages = new ArrayList<Message>();
    private Hashtable<String, String> users = new Hashtable<String, String>();

    public MessageWall_and_RemoteLogin_Impl() {
        users.put("john", "1234");
        users.put("fred", "4321");
    }
    
    // Taula buida i posar-los al fer connect

    @Override
    public UserAccess connect(String usr, String passwd) {
        if (users.containsKey(usr) && users.get(usr).equals(passwd)) {
            return new UserAccess_Impl(this, usr);
        } else {
            return null;
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void put(String user, String msg) {
        Message_Impl newMsg = new Message_Impl();
        newMsg.setOwner(user);
        newMsg.setContent(msg);

        messages.add(newMsg);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String user, int index) {
        Message msgToRemove = messages.get(index);
        if (user.equals(msgToRemove.getOwner())) {
            return messages.remove(msgToRemove);
        } else {
            return false;
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Message getLast() {
        if (messages.isEmpty()) {
            return null;
        } else {
            return messages.get(this.getNumber() - 1);
        }

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumber() {
        if (messages.isEmpty()) {
            return 0;
        } else {
            return messages.size();
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Message> getAllMessages() {
        return messages;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
