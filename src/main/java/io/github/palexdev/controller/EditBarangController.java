package io.github.palexdev.controller;

import java.io.IOException;

import io.github.palexdev.dao.BarangDAO;
import io.github.palexdev.model.Barang;
import io.github.palexdev.model.JenisBarang;
import io.github.palexdev.model.Kategori;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;


public class EditBarangController {

    @FXML
    private TextField nama_barang;
    @FXML
    private ChoiceBox<JenisBarang> jenis;
    @FXML
    private ChoiceBox<Kategori> id_kategori;
    @FXML
    private TextArea deskripsi;
    @FXML
    private TextField nama_penjual;
    @FXML
    private TextField harga_awal;

    private Barang barang;

    public void setBarangData(Barang barang) {
        this.barang = barang;

        // Isi form dengan data barang
        nama_barang.setText(barang.getNamaBarang());
        jenis.setValue(barang.getJenis());
        id_kategori.setValue(barang.getKategori());
        deskripsi.setText(barang.getDeskripsi());
        nama_penjual.setText(barang.getNamaPenjual());
        harga_awal.setText(String.valueOf(barang.getHargaAwal()));
    }

    @FXML
    private void handleSimpanPerubahan() {
        try {
            // Ambil data dari form
            barang.setNamaBarang(nama_barang.getText());
            barang.setJenis(jenis.getValue());
            barang.setKategori(id_kategori.getValue());
            barang.setDeskripsi(deskripsi.getText());
            barang.setNamaPenjual(nama_penjual.getText());
            barang.setHargaAwal(Integer.parseInt(harga_awal.getText()));

            // Simpan perubahan ke database
            boolean success = BarangDAO.updateBarang(barang);

            if (success) {
                showAlert("Sukses", "Data berhasil diperbarui!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Gagal memperbarui data!", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Harga harus berupa angka!", Alert.AlertType.ERROR);
        } catch (Exception e) {
            System.err.println("Error updating barang: " + e.getMessage());
            showAlert("Error", "Terjadi kesalahan!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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
