package io.github.palexdev.dao;

import io.github.palexdev.DatabaseConnection;
import io.github.palexdev.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LelangDAO {
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
                    resultSet.getString("jenis"),
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
