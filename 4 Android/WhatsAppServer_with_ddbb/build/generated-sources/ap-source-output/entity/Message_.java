package entity;

import entity.UserInfo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-01-08T12:28:21")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, Date> date;
    public static volatile SingularAttribute<Message, UserInfo> userSender;
    public static volatile SingularAttribute<Message, Integer> id;
    public static volatile SingularAttribute<Message, UserInfo> userReceiver;
    public static volatile SingularAttribute<Message, String> content;

}