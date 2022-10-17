/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author STRIX
 */
@Entity
@Table(name = "kasir")
@NamedQueries({
    @NamedQuery(name = "Kasir.findAll", query = "SELECT k FROM Kasir k"),
    @NamedQuery(name = "Kasir.findByIdKasir", query = "SELECT k FROM Kasir k WHERE k.idKasir = :idKasir"),
    @NamedQuery(name = "Kasir.findByNamaKasir", query = "SELECT k FROM Kasir k WHERE k.namaKasir = :namaKasir")})
public class Kasir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Kasir")
    private String idKasir;
    @Basic(optional = false)
    @Column(name = "Nama_Kasir")
    private String namaKasir;
    @JoinColumn(name = "Order_Id", referencedColumnName = "Order_Id")
    @OneToOne(optional = false)
    private Orderr orderId;

    public Kasir() {
    }

    public Kasir(String idKasir) {
        this.idKasir = idKasir;
    }

    public Kasir(String idKasir, String namaKasir) {
        this.idKasir = idKasir;
        this.namaKasir = namaKasir;
    }

    public String getIdKasir() {
        return idKasir;
    }

    public void setIdKasir(String idKasir) {
        this.idKasir = idKasir;
    }

    public String getNamaKasir() {
        return namaKasir;
    }

    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    public Orderr getOrderId() {
        return orderId;
    }

    public void setOrderId(Orderr orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKasir != null ? idKasir.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kasir)) {
            return false;
        }
        Kasir other = (Kasir) object;
        if ((this.idKasir == null && other.idKasir != null) || (this.idKasir != null && !this.idKasir.equals(other.idKasir))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta.Kasir[ idKasir=" + idKasir + " ]";
    }
    
}
