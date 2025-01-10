package io.github.palexdev.controller;

import io.github.palexdev.dao.KategoriDAO;
import io.github.palexdev.model.Kategori;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditKategoriController {

    @FXML
    private TextField id_kategori;
    @FXML
    private TextField nama_kategori;

    private Kategori kategori;

    public void setKategoriData(Kategori kategori) {
        this.kategori = kategori;
        id_kategori.setText(kategori.getIdKategori());
        nama_kategori.setText(kategori.getNamaKategori());
        id_kategori.setDisable(true); // ID kategori tidak dapat diubah
    }

    @FXML
    private void handleSimpan(ActionEvent event) {
        String namaKategoriBaru = nama_kategori.getText();

        if (namaKategoriBaru.isEmpty()) {
            showAlert("Error", "Nama kategori tidak boleh kosong!", Alert.AlertType.ERROR);
            return;
        }

        kategori.setNamaKategori(namaKategoriBaru);

        boolean success = KategoriDAO.updateKategori(kategori);
        if (success) {
            showAlert("Sukses", "Kategori berhasil diperbarui!", Alert.AlertType.INFORMATION);
            closeWindow();
        } else {
            showAlert("Error", "Gagal memperbarui kategori!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleBatal(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) id_kategori.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}