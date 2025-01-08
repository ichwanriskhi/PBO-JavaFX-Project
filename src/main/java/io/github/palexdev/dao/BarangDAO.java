package io.github.palexdev.dao;

import io.github.palexdev.DatabaseConnection;
import io.github.palexdev.model.Barang;
import io.github.palexdev.model.JenisBarang;
import io.github.palexdev.model.Kategori;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {
    public static List<Barang> getAllBarang() {
        List<Barang> barangList = new ArrayList<>();
        String query = "SELECT barang.*, kategori.id_kategori, kategori.nama_kategori " +
                       "FROM barang " +
                       "JOIN kategori ON barang.id_kategori = kategori.id_kategori;";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Kategori kategori = new Kategori(
                    resultSet.getString("id_kategori"),
                    resultSet.getString("nama_kategori"),
                    0
                );

                Barang barang = new Barang(
                    resultSet.getInt("id_barang"),
                    resultSet.getString("nama_barang"),
                    JenisBarang.valueOf(resultSet.getString("jenis")),
                    resultSet.getString("deskripsi"),
                    resultSet.getString("nama_penjual"),
                    resultSet.getInt("harga_awal"),
                    resultSet.getString("tgl_masuk"),
                    kategori
                );

                barangList.add(barang);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching barang data: " + e.getMessage());
        }

        return barangList;
    }

    public static boolean createBarang(Barang barang) {
        String query = "INSERT INTO barang (nama_barang, jenis, id_kategori, deskripsi, nama_penjual, harga_awal, tgl_masuk) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, barang.getNamaBarang());
            stmt.setString(2, barang.getJenis().toString());
            stmt.setString(3, barang.getKategori().getIdKategori());
            stmt.setString(4, barang.getDeskripsi());
            stmt.setString(5, barang.getNamaPenjual());
            stmt.setInt(6, barang.getHargaAwal());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error inserting barang: " + e.getMessage());
            return false;
        }
    }
}
