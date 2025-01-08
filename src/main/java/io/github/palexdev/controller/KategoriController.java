package io.github.palexdev.controller;

import io.github.palexdev.dao.KategoriDAO;
import io.github.palexdev.model.Kategori;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class KategoriController {
    @FXML
    private TableView<Kategori> tableKategori;
    @FXML
    private TableColumn<Kategori, String> colIdKategori;
    @FXML
    private TableColumn<Kategori, String> colNamaKategori;
    @FXML
    private TableColumn<Kategori, Integer> colJumlahBarang;

    @FXML
    public void initialize() {
        colIdKategori.setCellValueFactory(new PropertyValueFactory<>("idKategori"));
        colNamaKategori.setCellValueFactory(new PropertyValueFactory<>("namaKategori"));
        colJumlahBarang.setCellValueFactory(new PropertyValueFactory<>("jumlahBarang"));

        ObservableList<Kategori> kategoriList = FXCollections.observableArrayList(KategoriDAO.getAllKategori());
        tableKategori.setItems(kategoriList);
    }

    @FXML
    private void handleDataBarangView(ActionEvent event) {
        loadView(event, "databarang.fxml", "Data Barang");
    }

    @FXML
    private void handleEditBarangView(ActionEvent event) {
        loadView(event, "editbarang.fxml", "Edit Barang");
    }

    @FXML
    private void handleBerandaView(ActionEvent event) {
        loadView(event, "beranda.fxml", "Beranda");
    }

    @FXML
    private void handleLelangView(ActionEvent event) {
        loadView(event, "lelang.fxml", "Lelang");
    }

    @FXML
    private void handleKategoriView(ActionEvent event) {
        loadView(event, "kategori.fxml", "Kategori");
    }

    private void loadView(ActionEvent event, String fxmlFile, String title) {
        try {
            // Sesuaikan path ke file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/github/palexdev/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Mendapatkan stage saat ini
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set scene baru
            stage.setTitle(title);
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Gagal memuat file FXML: " + e.getMessage());
        }
    }
}
