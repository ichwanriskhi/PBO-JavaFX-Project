package io.github.palexdev.model;

public class Kategori {
    private String idKategori;
    private String namaKategori;
    private int jumlahBarang;

    // Constructor
    public Kategori(String idKategori, String namaKategori, int jumlahBarang) {
        // Validasi idKategori untuk huruf kapital
        if (!idKategori.matches("[A-Z]+")) {
            throw new IllegalArgumentException("ID Kategori harus berupa huruf kapital tanpa spasi");
        }
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.jumlahBarang = jumlahBarang;
    }

    // Getter untuk ID Kategori
    public String getIdKategori() {
        return idKategori;
    }

    // Getter untuk Nama Kategori
    public String getNamaKategori() {
        return namaKategori;
    }

    // Getter untuk Jumlah Barang
    public int getJumlahBarang() {
        return jumlahBarang;
    }

    // Getter tambahan untuk memastikan format (opsional)
    public String getFormattedIdKategori() {
        return idKategori;
    }

    // toString untuk debugging
    @Override
    public String toString() {
        return namaKategori;
    }
}
