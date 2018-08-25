package model;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.AwardType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-01T11:52:02")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, Long> balance;
    public static volatile SingularAttribute<Account, Date> expiration;
    public static volatile SingularAttribute<Account, String> account;
    public static volatile SingularAttribute<Account, String> comment;
    public static volatile SingularAttribute<Account, AwardType> type;
    public static volatile SingularAttribute<Account, String> url;
    public static volatile SingularAttribute<Account, BigInteger> goal;
    public static volatile SingularAttribute<Account, String> programName;

}