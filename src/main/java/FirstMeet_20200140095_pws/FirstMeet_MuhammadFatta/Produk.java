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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author STRIX
 */
@Entity
@Table(name = "produk")
@NamedQueries({
    @NamedQuery(name = "Produk.findAll", query = "SELECT p FROM Produk p"),
    @NamedQuery(name = "Produk.findByIdProduk", query = "SELECT p FROM Produk p WHERE p.idProduk = :idProduk"),
    @NamedQuery(name = "Produk.findByNamaKue", query = "SELECT p FROM Produk p WHERE p.namaKue = :namaKue"),
    @NamedQuery(name = "Produk.findByHarga", query = "SELECT p FROM Produk p WHERE p.harga = :harga"),
    @NamedQuery(name = "Produk.findByStok", query = "SELECT p FROM Produk p WHERE p.stok = :stok")})
public class Produk implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Produk")
    private String idProduk;
    @Basic(optional = false)
    @Column(name = "Nama_Kue")
    private String namaKue;
    @Basic(optional = false)
    @Column(name = "Harga")
    private int harga;
    @Basic(optional = false)
    @Column(name = "Stok")
    private int stok;

    public Produk() {
    }

    public Produk(String idProduk) {
        this.idProduk = idProduk;
    }

    public Produk(String idProduk, String namaKue, int harga, int stok) {
        this.idProduk = idProduk;
        this.namaKue = namaKue;
        this.harga = harga;
        this.stok = stok;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getNamaKue() {
        return namaKue;
    }

    public void setNamaKue(String namaKue) {
        this.namaKue = namaKue;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProduk != null ? idProduk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produk)) {
            return false;
        }
        Produk other = (Produk) object;
        if ((this.idProduk == null && other.idProduk != null) || (this.idProduk != null && !this.idProduk.equals(other.idProduk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta.Produk[ idProduk=" + idProduk + " ]";
    }
    
}
