package io.github.palexdev.dao;

import io.github.palexdev.DatabaseConnection;
import io.github.palexdev.model.Barang;
import io.github.palexdev.model.Kategori;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {
    public static List<Barang> getAllBarang() {
        List<Barang> barangList = new ArrayList<>();
        String query = "SELECT barang.*, kategori.* " +
               "FROM barang " +
               "JOIN kategori ON barang.id_kategori = kategori.id_kategori;";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Kategori kategori = new Kategori(
                    resultSet.getString("id_kategori"),
                    resultSet.getString("nama_kategori")
                );

                Barang barang = new Barang(
                    resultSet.getInt("id_barang"),
                    resultSet.getString("nama_barang"),
                    resultSet.getString("jenis"),
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
}
