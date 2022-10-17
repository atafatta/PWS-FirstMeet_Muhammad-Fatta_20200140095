/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author STRIX
 */
@Entity
@Table(name = "transaksii")
@NamedQueries({
    @NamedQuery(name = "Transaksii.findAll", query = "SELECT t FROM Transaksii t"),
    @NamedQuery(name = "Transaksii.findByIdTransaksi", query = "SELECT t FROM Transaksii t WHERE t.idTransaksi = :idTransaksi"),
    @NamedQuery(name = "Transaksii.findByIdPelanggan", query = "SELECT t FROM Transaksii t WHERE t.idPelanggan = :idPelanggan"),
    @NamedQuery(name = "Transaksii.findByTglPesan", query = "SELECT t FROM Transaksii t WHERE t.tglPesan = :tglPesan"),
    @NamedQuery(name = "Transaksii.findByTotalPesanan", query = "SELECT t FROM Transaksii t WHERE t.totalPesanan = :totalPesanan")})
public class Transaksii implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Transaksi")
    private String idTransaksi;
    @Basic(optional = false)
    @Column(name = "Id_Pelanggan")
    private String idPelanggan;
    @Basic(optional = false)
    @Column(name = "Tgl_Pesan")
    @Temporal(TemporalType.DATE)
    private Date tglPesan;
    @Basic(optional = false)
    @Column(name = "Total_Pesanan")
    private int totalPesanan;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idTransaksi")
    private DetailTransaksi detailTransaksi;

    public Transaksii() {
    }

    public Transaksii(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Transaksii(String idTransaksi, String idPelanggan, Date tglPesan, int totalPesanan) {
        this.idTransaksi = idTransaksi;
        this.idPelanggan = idPelanggan;
        this.tglPesan = tglPesan;
        this.totalPesanan = totalPesanan;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public Date getTglPesan() {
        return tglPesan;
    }

    public void setTglPesan(Date tglPesan) {
        this.tglPesan = tglPesan;
    }

    public int getTotalPesanan() {
        return totalPesanan;
    }

    public void setTotalPesanan(int totalPesanan) {
        this.totalPesanan = totalPesanan;
    }

    public DetailTransaksi getDetailTransaksi() {
        return detailTransaksi;
    }

    public void setDetailTransaksi(DetailTransaksi detailTransaksi) {
        this.detailTransaksi = detailTransaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaksi != null ? idTransaksi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaksii)) {
            return false;
        }
        Transaksii other = (Transaksii) object;
        if ((this.idTransaksi == null && other.idTransaksi != null) || (this.idTransaksi != null && !this.idTransaksi.equals(other.idTransaksi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta.Transaksii[ idTransaksi=" + idTransaksi + " ]";
    }
    
}
