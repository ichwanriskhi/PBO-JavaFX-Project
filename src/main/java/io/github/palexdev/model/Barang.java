package io.github.palexdev.model;

public class Barang {
    private int idBarang;
    private String namaBarang;
    private String jenis;
    private String deskripsi;
    private String namaPenjual;
    private int hargaAwal;
    private String tglMasuk;
    private Kategori kategori;

    // Constructor tanpa idBarang (untuk auto increment)
    public Barang(String namaBarang, String jenis, String deskripsi, String namaPenjual, int hargaAwal, Kategori kategori) {
        this.namaBarang = namaBarang;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.namaPenjual = namaPenjual;
        this.hargaAwal = hargaAwal;
        this.kategori = kategori;
    }

    // Constructor dengan idBarang (untuk membaca data dari database)
    public Barang(int idBarang, String namaBarang, String jenis, String deskripsi, String namaPenjual, int hargaAwal, String tglMasuk, Kategori kategori) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.namaPenjual = namaPenjual;
        this.hargaAwal = hargaAwal;
        this.tglMasuk = tglMasuk;
        this.kategori = kategori;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getJenis() {
        return jenis;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getNamaPenjual() {
        return namaPenjual;
    }

    public int getHargaAwal() {
        return hargaAwal;
    }

    public String getTglMasuk() {
        return tglMasuk;
    }

    public Kategori getKategori() {
        return kategori;
    }
}
