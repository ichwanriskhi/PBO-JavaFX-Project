package io.github.palexdev.dao;

import io.github.palexdev.DatabaseConnection;
import io.github.palexdev.model.Kategori;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {
    public static List<Kategori> getAllKategori() {
        List<Kategori> kategoriList = new ArrayList<>();

        // Query untuk mengambil data kategori dan jumlah barang
        String query = "SELECT k.id_kategori, k.nama_kategori, COUNT(b.id_barang) AS jumlah_barang " +
                   "FROM kategori k " +
                   "LEFT JOIN barang b ON k.id_kategori = b.id_kategori " +
                   "GROUP BY k.id_kategori, k.nama_kategori";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterasi hasil query
            while (resultSet.next()) {
                // Membuat objek Kategori dengan data dari database
                Kategori kategori = new Kategori(
                    resultSet.getString("id_kategori"),
                    resultSet.getString("nama_kategori"),
                    resultSet.getInt("jumlah_barang") // Tambahan jumlah barang
                );

                // Menambahkan kategori ke list
                kategoriList.add(kategori);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching kategori data: " + e.getMessage());
        }

        return kategoriList;
    }
}
