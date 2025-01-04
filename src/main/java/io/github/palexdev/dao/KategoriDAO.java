package io.github.palexdev.dao;

import io.github.palexdev.DatabaseConnection;
import io.github.palexdev.model.Kategori;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {
    public static List<Kategori> getAllKategori() {
        List<Kategori> kategoriList = new ArrayList<>();
        String query = "SELECT * FROM kategori";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Kategori kategori = new Kategori(
                    resultSet.getString("id_kategori"),
                    resultSet.getString("nama_kategori")
                );

                kategoriList.add(kategori);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching kategori data: " + e.getMessage());
        }

        return kategoriList;
    }
}
