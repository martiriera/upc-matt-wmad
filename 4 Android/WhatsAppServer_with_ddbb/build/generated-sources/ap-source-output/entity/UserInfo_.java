package entity;

import entity.Message;
import entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-01-08T12:28:21")
@StaticMetamodel(UserInfo.class)
public class UserInfo_ { 

    public static volatile SingularAttribute<UserInfo, String> surname;
    public static volatile CollectionAttribute<UserInfo, Message> messageCollection;
    public static volatile CollectionAttribute<UserInfo, Message> messageCollection1;
    public static volatile CollectionAttribute<UserInfo, User> userCollection;
    public static volatile SingularAttribute<UserInfo, String> name;
    public static volatile SingularAttribute<UserInfo, Integer> id;

}