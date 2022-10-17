/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author STRIX
 */
@Entity
@Table(name = "orderr")
@NamedQueries({
    @NamedQuery(name = "Orderr.findAll", query = "SELECT o FROM Orderr o"),
    @NamedQuery(name = "Orderr.findByOrderId", query = "SELECT o FROM Orderr o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "Orderr.findByOrdervalue", query = "SELECT o FROM Orderr o WHERE o.ordervalue = :ordervalue")})
public class Orderr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Order_Id")
    private String orderId;
    @Basic(optional = false)
    @Column(name = "Order_value")
    private String ordervalue;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Kasir kasir;

    public Orderr() {
    }

    public Orderr(String orderId) {
        this.orderId = orderId;
    }

    public Orderr(String orderId, String ordervalue) {
        this.orderId = orderId;
        this.ordervalue = ordervalue;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrdervalue() {
        return ordervalue;
    }

    public void setOrdervalue(String ordervalue) {
        this.ordervalue = ordervalue;
    }

    public Kasir getKasir() {
        return kasir;
    }

    public void setKasir(Kasir kasir) {
        this.kasir = kasir;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderr)) {
            return false;
        }
        Orderr other = (Orderr) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta.Orderr[ orderId=" + orderId + " ]";
    }
    
}
