/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kemar
 */
@Entity
@Table(name = "HOTEL_RESERVATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HotelReservations.findAll", query = "SELECT h FROM HotelReservations h"),
    @NamedQuery(name = "HotelReservations.findByName", query = "SELECT h FROM HotelReservations h WHERE h.hotelReservationsPK.name = :name"),
    @NamedQuery(name = "HotelReservations.findByCheckIn", query = "SELECT h FROM HotelReservations h WHERE h.hotelReservationsPK.checkIn = :checkIn"),
    @NamedQuery(name = "HotelReservations.findByCheckOut", query = "SELECT h FROM HotelReservations h WHERE h.checkOut = :checkOut"),
    @NamedQuery(name = "HotelReservations.findByConfirmation", query = "SELECT h FROM HotelReservations h WHERE h.confirmation = :confirmation"),
    @NamedQuery(name = "HotelReservations.findByAddress", query = "SELECT h FROM HotelReservations h WHERE h.address = :address"),
    @NamedQuery(name = "HotelReservations.findByPhone", query = "SELECT h FROM HotelReservations h WHERE h.phone = :phone")})
public class HotelReservations implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HotelReservationsPK hotelReservationsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHECK_OUT")
    @Temporal(TemporalType.DATE)
    private Date checkOut;
    @Size(max = 50)
    @Column(name = "CONFIRMATION")
    private String confirmation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "ADDRESS")
    private String address;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 25)
    @Column(name = "PHONE")
    private String phone;
    @Lob
    @Column(name = "NOTES")
    private String notes;

    public HotelReservations() {
    }

    public HotelReservations(HotelReservationsPK hotelReservationsPK) {
        this.hotelReservationsPK = hotelReservationsPK;
    }

    public HotelReservations(HotelReservationsPK hotelReservationsPK, Date checkOut, String address) {
        this.hotelReservationsPK = hotelReservationsPK;
        this.checkOut = checkOut;
        this.address = address;
    }

    public HotelReservations(String name, Date checkIn) {
        this.hotelReservationsPK = new HotelReservationsPK(name, checkIn);
    }

    public HotelReservationsPK getHotelReservationsPK() {
        return hotelReservationsPK;
    }

    public void setHotelReservationsPK(HotelReservationsPK hotelReservationsPK) {
        this.hotelReservationsPK = hotelReservationsPK;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hotelReservationsPK != null ? hotelReservationsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HotelReservations)) {
            return false;
        }
        HotelReservations other = (HotelReservations) object;
        if ((this.hotelReservationsPK == null && other.hotelReservationsPK != null) || (this.hotelReservationsPK != null && !this.hotelReservationsPK.equals(other.hotelReservationsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.HotelReservations[ hotelReservationsPK=" + hotelReservationsPK + " ]";
    }
    
}
