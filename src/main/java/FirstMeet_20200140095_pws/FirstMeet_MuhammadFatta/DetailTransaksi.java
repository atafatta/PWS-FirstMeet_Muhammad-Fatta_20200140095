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
@Table(name = "detail_transaksi")
@NamedQueries({
    @NamedQuery(name = "DetailTransaksi.findAll", query = "SELECT d FROM DetailTransaksi d"),
    @NamedQuery(name = "DetailTransaksi.findByIdDetail", query = "SELECT d FROM DetailTransaksi d WHERE d.idDetail = :idDetail"),
    @NamedQuery(name = "DetailTransaksi.findByIdProduk", query = "SELECT d FROM DetailTransaksi d WHERE d.idProduk = :idProduk"),
    @NamedQuery(name = "DetailTransaksi.findByJumlah", query = "SELECT d FROM DetailTransaksi d WHERE d.jumlah = :jumlah"),
    @NamedQuery(name = "DetailTransaksi.findByHarga", query = "SELECT d FROM DetailTransaksi d WHERE d.harga = :harga")})
public class DetailTransaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Detail")
    private String idDetail;
    @Basic(optional = false)
    @Column(name = "Id_Produk")
    private String idProduk;
    @Basic(optional = false)
    @Column(name = "Jumlah")
    private int jumlah;
    @Basic(optional = false)
    @Column(name = "Harga")
    private int harga;
    @JoinColumn(name = "Id_Transaksi", referencedColumnName = "Id_Transaksi")
    @OneToOne(optional = false)
    private Transaksii idTransaksi;

    public DetailTransaksi() {
    }

    public DetailTransaksi(String idDetail) {
        this.idDetail = idDetail;
    }

    public DetailTransaksi(String idDetail, String idProduk, int jumlah, int harga) {
        this.idDetail = idDetail;
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(String idDetail) {
        this.idDetail = idDetail;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public Transaksii getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Transaksii idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetail != null ? idDetail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetailTransaksi)) {
            return false;
        }
        DetailTransaksi other = (DetailTransaksi) object;
        if ((this.idDetail == null && other.idDetail != null) || (this.idDetail != null && !this.idDetail.equals(other.idDetail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta.DetailTransaksi[ idDetail=" + idDetail + " ]";
    }
    
}
