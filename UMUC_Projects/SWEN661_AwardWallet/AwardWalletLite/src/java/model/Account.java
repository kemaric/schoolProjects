/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "ACCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByProgramName", query = "SELECT a FROM Account a WHERE a.programName = :programName"),
    @NamedQuery(name = "Account.findByUrl", query = "SELECT a FROM Account a WHERE a.url = :url"),
    @NamedQuery(name = "Account.findByAccount", query = "SELECT a FROM Account a WHERE a.account = :account"),
    @NamedQuery(name = "Account.findByBalance", query = "SELECT a FROM Account a WHERE a.balance = :balance"),
    @NamedQuery(name = "Account.findByExpiration", query = "SELECT a FROM Account a WHERE a.expiration = :expiration"),
    @NamedQuery(name = "Account.findByGoal", query = "SELECT a FROM Account a WHERE a.goal = :goal")})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PROGRAM_NAME")
    private String programName;
    @Size(max = 200)
    @Column(name = "URL")
    private String url;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ACCOUNT")
    private String account;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BALANCE")
    private long balance;
    @Lob
    @Column(name = "COMMENT")
    private String comment;
    @Column(name = "EXPIRATION")
    @Temporal(TemporalType.DATE)
    private Date expiration;
    @Column(name = "GOAL")
    private BigInteger goal;
    @JoinColumn(name = "TYPE", referencedColumnName = "TYPE")
    @ManyToOne(optional = false)
    private AwardType type;

    public Account() {
    }

    public Account(String programName) {
        this.programName = programName;
    }

    public Account(String programName, String account, long balance) {
        this.programName = programName;
        this.account = account;
        this.balance = balance;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public BigInteger getGoal() {
        return goal;
    }

    public void setGoal(BigInteger goal) {
        this.goal = goal;
    }

    public AwardType getType() {
        return type;
    }

    public void setType(AwardType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programName != null ? programName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.programName == null && other.programName != null) || (this.programName != null && !this.programName.equals(other.programName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Account[ programName=" + programName + " ]";
    }
    
}
