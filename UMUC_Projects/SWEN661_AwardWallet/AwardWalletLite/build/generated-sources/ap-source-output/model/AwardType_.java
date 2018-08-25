package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Account;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-01T11:52:02")
@StaticMetamodel(AwardType.class)
public class AwardType_ { 

    public static volatile SingularAttribute<AwardType, String> type;
    public static volatile CollectionAttribute<AwardType, Account> accountCollection;

}