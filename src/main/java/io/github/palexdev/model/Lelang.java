package io.github.palexdev.model;

public class Lelang {
    private int idLelang;
    private Barang barang;
    private String namaPembeli;
    private Integer hargaAkhir;
    private String tglDibuka;
    private String tglSelesai;
    private String status;

    public Lelang(int idLelang, Barang barang, String namaPembeli, Integer hargaAkhir, String tglDibuka, String tglSelesai, String status) {
        this.idLelang = idLelang;
        this.barang = barang;
        this.namaPembeli = namaPembeli;
        this.hargaAkhir = hargaAkhir;
        this.tglDibuka = tglDibuka;
        this.tglSelesai = tglSelesai;
        this.status = status;
    }

    public int getIdLelang() {
        return idLelang;
    }

    public Barang getBarang() {
        return barang;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public Integer getHargaAkhir() {
        return hargaAkhir;
    }

    public String getTglDibuka() {
        return tglDibuka;
    }

    public String getTglSelesai() {
        return tglSelesai;
    }

    public String getStatus() {
        return status;
    }
}
