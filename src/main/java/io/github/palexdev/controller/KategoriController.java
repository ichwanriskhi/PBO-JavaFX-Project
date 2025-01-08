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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField id_kategori;
    @FXML
    private TextField nama_kategori;

    @FXML
    private void handleSimpanKategori(ActionEvent event) {
        String idKategori = id_kategori.getText();
        String namaKategori = nama_kategori.getText();

        // Validasi input
        if (idKategori.isEmpty() || namaKategori.isEmpty()) {
            showAlert("Error", "Semua data harus diisi!", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Simpan kategori ke database
            Kategori kategori = new Kategori(idKategori, namaKategori, 0); // 0 karena kategori baru
            boolean success = KategoriDAO.createKategori(kategori);

            if (success) {
                showAlert("Sukses", "Kategori berhasil disimpan!", Alert.AlertType.INFORMATION);
                clearForm(); // Bersihkan form setelah sukses
            } else {
                showAlert("Error", "Gagal menyimpan kategori!", Alert.AlertType.ERROR);
            }
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Terjadi kesalahan saat menyimpan kategori!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        id_kategori.clear();
        nama_kategori.clear();
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
