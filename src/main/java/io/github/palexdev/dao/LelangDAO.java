package io.github.palexdev.dao;

import io.github.palexdev.DatabaseConnection;
import io.github.palexdev.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LelangDAO {

    public static void insertLelang(Lelang lelang) {
        String query = "INSERT INTO lelang (id_barang, nama_pembeli, harga_akhir, tgl_dibuka, tgl_selesai, status) " +
                "VALUES (?, ?, ?, NOW(), ?, ?)";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setInt(1, lelang.getBarang().getIdBarang());
            statement.setString(2, lelang.getNamaPembeli());
            statement.setInt(3, lelang.getHargaAkhir());
            statement.setString(4, lelang.getTglSelesai());
            statement.setString(5, lelang.getStatus());
    
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting lelang data: " + e.getMessage());
        }
    }    

    public static void updateLelangStatus(Lelang lelang) {
        String query = "UPDATE lelang SET status = ?, tgl_selesai = ?, nama_pembeli = ?, harga_akhir = ? WHERE id_lelang = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setString(1, lelang.getStatus());
            statement.setString(2, lelang.getTglSelesai());
            statement.setString(3, lelang.getNamaPembeli());
            statement.setInt(4, lelang.getHargaAkhir());
            statement.setInt(5, lelang.getIdLelang());
    
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating lelang data: " + e.getMessage());
        }
    }
    

    public static List<Lelang> getAllLelang() {
        List<Lelang> lelangList = new ArrayList<>();
        String query = "SELECT lelang.*, barang.*, kategori.id_kategori, kategori.nama_kategori " +
               "FROM lelang " +
               "JOIN barang ON lelang.id_barang = barang.id_barang " +
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

                Lelang lelang = new Lelang(
                    resultSet.getInt("id_lelang"),
                    barang,
                    resultSet.getString("nama_pembeli"),
                    resultSet.getInt("harga_akhir"),
                    resultSet.getString("tgl_dibuka"),
                    resultSet.getString("tgl_selesai"),
                    resultSet.getString("status")
                );

                lelangList.add(lelang);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching lelang data: " + e.getMessage());
        }

        return lelangList;
    }
}
