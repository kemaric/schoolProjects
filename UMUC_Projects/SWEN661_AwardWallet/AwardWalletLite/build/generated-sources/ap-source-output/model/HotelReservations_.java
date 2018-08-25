package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.HotelReservationsPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-01T11:52:02")
@StaticMetamodel(HotelReservations.class)
public class HotelReservations_ { 

    public static volatile SingularAttribute<HotelReservations, String> phone;
    public static volatile SingularAttribute<HotelReservations, Date> checkOut;
    public static volatile SingularAttribute<HotelReservations, String> address;
    public static volatile SingularAttribute<HotelReservations, String> confirmation;
    public static volatile SingularAttribute<HotelReservations, String> notes;
    public static volatile SingularAttribute<HotelReservations, HotelReservationsPK> hotelReservationsPK;

}