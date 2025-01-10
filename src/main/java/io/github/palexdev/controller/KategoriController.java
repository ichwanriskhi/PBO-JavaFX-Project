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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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
    private TableColumn<Kategori, String> colAksi;

    @FXML
    private TextField id_kategori;
    @FXML
    private TextField nama_kategori;

    @FXML
    public void initialize() {
        colIdKategori.setCellValueFactory(new PropertyValueFactory<>("idKategori"));
        colNamaKategori.setCellValueFactory(new PropertyValueFactory<>("namaKategori"));
        colJumlahBarang.setCellValueFactory(new PropertyValueFactory<>("jumlahBarang"));

        colAksi.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("Edit");
            private final Button btnHapus = new Button("Hapus");
            private final HBox actionButtons = new HBox(5, btnEdit, btnHapus);

            {
                btnEdit.setOnAction(event -> {
                    Kategori kategori = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/github/palexdev/editkategori.fxml"));
                        Parent root = loader.load();

                        // Kirim data kategori ke controller edit
                        EditKategoriController controller = loader.getController();
                        controller.setKategoriData(kategori);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Edit Kategori");
                        stage.show();
                    } catch (IOException e) {
                        System.err.println("Error loading edit kategori view: " + e.getMessage());
                    }
                });

                btnHapus.setOnAction(event -> {
                    Kategori kategori = getTableView().getItems().get(getIndex());
                    boolean confirm = showConfirmation("Apakah Anda yakin ingin menghapus kategori ini?");
                    if (confirm) {
                        if (KategoriDAO.deleteKategori(kategori.getIdKategori())) {
                            getTableView().getItems().remove(kategori);
                            showAlert("Sukses", "Kategori berhasil dihapus!", Alert.AlertType.INFORMATION);
                        } else {
                            showAlert("Error", "Kategori tidak dapat dihapus!", Alert.AlertType.ERROR);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionButtons);
                }
            }
        });

        ObservableList<Kategori> kategoriList = FXCollections.observableArrayList(KategoriDAO.getAllKategori());
        tableKategori.setItems(kategoriList);
    }

    @FXML
    private void handleSimpanKategori(ActionEvent event) {
        String idKategori = id_kategori.getText();
        String namaKategori = nama_kategori.getText();

        if (idKategori.isEmpty() || namaKategori.isEmpty()) {
            showAlert("Error", "Semua data harus diisi!", Alert.AlertType.ERROR);
            return;
        }

        try {
            Kategori kategori = new Kategori(idKategori, namaKategori, 0);
            boolean success = KategoriDAO.createKategori(kategori);

            if (success) {
                showAlert("Sukses", "Kategori berhasil disimpan!", Alert.AlertType.INFORMATION);
                clearForm();
            } else {
                showAlert("Error", "Gagal menyimpan kategori!", Alert.AlertType.ERROR);
            }
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Terjadi kesalahan saat menyimpan kategori!", Alert.AlertType.ERROR);
        }
    }

    private boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setContentText(message);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/github/palexdev/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Gagal memuat file FXML: " + e.getMessage());
        }
    }
}
