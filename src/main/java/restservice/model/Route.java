/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restservice.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMore
 */
@Entity
@Table(name = "ROUTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r"),
    @NamedQuery(name = "Route.findByTicketID", query = "SELECT r FROM Route r WHERE r.ticketID = :ticketID"),
    @NamedQuery(name = "Route.findByPath", query = "SELECT r FROM Route r WHERE r.path = :path"),
    @NamedQuery(name = "Route.findByDate", query = "SELECT r FROM Route r WHERE r.date = :date")})
public class Route implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ticketID")
    private Integer ticketID;
    @Size(max = 45)
    @Column(name = "path")
    private String path;
    @Size(max = 45)
    @Column(name = "date")
    private String date;

    public Route() {
    }

    public Route(Integer ticketID) {
        this.ticketID = ticketID;
    }

    public Integer getTicketID() {
        return ticketID;
    }

    public void setTicketID(Integer ticketID) {
        this.ticketID = ticketID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketID != null ? ticketID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Route)) {
            return false;
        }
        Route other = (Route) object;
        if ((this.ticketID == null && other.ticketID != null) || (this.ticketID != null && !this.ticketID.equals(other.ticketID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restservice.model.Route[ ticketID=" + ticketID + " ]";
    }
    
}
