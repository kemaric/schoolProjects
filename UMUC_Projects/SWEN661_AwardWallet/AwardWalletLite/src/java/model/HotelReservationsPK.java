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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kemar
 */
@Embeddable
public class HotelReservationsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHECK_IN")
    @Temporal(TemporalType.DATE)
    private Date checkIn;

    public HotelReservationsPK() {
    }

    public HotelReservationsPK(String name, Date checkIn) {
        this.name = name;
        this.checkIn = checkIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        hash += (checkIn != null ? checkIn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HotelReservationsPK)) {
            return false;
        }
        HotelReservationsPK other = (HotelReservationsPK) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        if ((this.checkIn == null && other.checkIn != null) || (this.checkIn != null && !this.checkIn.equals(other.checkIn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.HotelReservationsPK[ name=" + name + ", checkIn=" + checkIn + " ]";
    }
    
}
