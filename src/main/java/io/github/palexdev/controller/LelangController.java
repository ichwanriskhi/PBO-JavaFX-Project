package io.github.palexdev.controller;

import io.github.palexdev.dao.LelangDAO;
import io.github.palexdev.model.Lelang;
import javafx.beans.property.SimpleStringProperty;
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

public class LelangController {
    @FXML
    private TableView<Lelang> tableLelang;
    @FXML
    private TableColumn<Lelang, Integer> colIdLelang;
    @FXML
    private TableColumn<Lelang, String> colNamaBarang;
    @FXML
    private TableColumn<Lelang, String> colNamaPembeli;
    @FXML
    private TableColumn<Lelang, String> colTanggalBuka;
    @FXML
    private TableColumn<Lelang, String> colTanggalAkhir;
    @FXML
    private TableColumn<Lelang, Integer> colHargaAkhir;
    @FXML
    private TableColumn<Lelang, String> colStatus;

    @FXML
    public void initialize() {
        colIdLelang.setCellValueFactory(new PropertyValueFactory<>("idLelang"));
        colNamaBarang.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getBarang().getNamaBarang())
        );
        colNamaPembeli.setCellValueFactory(new PropertyValueFactory<>("namaPembeli"));
        colTanggalBuka.setCellValueFactory(new PropertyValueFactory<>("tglDibuka"));
        colTanggalAkhir.setCellValueFactory(new PropertyValueFactory<>("tglSelesai"));
        colHargaAkhir.setCellValueFactory(new PropertyValueFactory<>("hargaAkhir"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Lelang> lelangList = FXCollections.observableArrayList(LelangDAO.getAllLelang());
        tableLelang.setItems(lelangList);
    }

    // Metode untuk menangani navigasi antar view
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
