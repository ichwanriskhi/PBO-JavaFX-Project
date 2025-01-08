package io.github.palexdev.controller;

import io.github.palexdev.dao.BarangDAO;
import io.github.palexdev.dao.KategoriDAO;
import io.github.palexdev.model.Barang;
import io.github.palexdev.model.Kategori;
import io.github.palexdev.model.JenisBarang;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import java.io.IOException;
import java.util.List;

public class DataBarangController {

    @FXML
    private TableView<Barang> tableBarang;
    @FXML
    private TableColumn<Barang, Integer> colKodeBarang;
    @FXML
    private TableColumn<Barang, String> colNamaBarang;
    @FXML
    private TableColumn<Barang, String> colJenis;
    @FXML
    private TableColumn<Barang, String> colKategori;
    @FXML
    private TableColumn<Barang, String> colPenjual;
    @FXML
    private TableColumn<Barang, Integer> colHarga;
    @FXML
    private TableColumn<Barang, String> colTanggalMasuk;
    @FXML
    private TableColumn<Barang, String> colAksi;


    @FXML
    private TextField nama_barang; // untuk nama barang

    @FXML
    private ChoiceBox<JenisBarang> jenis; // untuk jenis barang

    @FXML
    private ChoiceBox<Kategori> id_kategori; // untuk kategori barang

    @FXML
    private TextArea deskripsi; // untuk deskripsi barang

    @FXML
    private TextField nama_penjual; // untuk nama penjual

    @FXML
    private TextField harga_awal; // untuk harga awal barang

    @FXML
    private void handleSimpan(ActionEvent event) {
        try {
            // Ambil data dari form
            String namaBarang = nama_barang.getText();
            JenisBarang jenisBarang = jenis.getValue();
            Kategori kategori = id_kategori.getValue();
            String deskripsiBarang = deskripsi.getText();
            String namaPenjual = nama_penjual.getText();
            int hargaAwal = Integer.parseInt(harga_awal.getText());

            // Validasi sederhana
            if (namaBarang.isEmpty() || jenisBarang == null || kategori == null || namaPenjual.isEmpty()) {
                showAlert("Error", "Semua data harus diisi!", Alert.AlertType.ERROR);
                return;
            }

            // Simpan data ke database
            Barang barang = new Barang(namaBarang, jenisBarang, deskripsiBarang, namaPenjual, hargaAwal, kategori);
            boolean success = BarangDAO.createBarang(barang);

            if (success) {
                showAlert("Sukses", "Data berhasil disimpan!", Alert.AlertType.INFORMATION);
                clearForm();
            } else {
                showAlert("Error", "Gagal menyimpan data!", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Harga harus berupa angka!", Alert.AlertType.ERROR);
        } catch (Exception e) {
            System.err.println("Error saving barang: " + e.getMessage());
            showAlert("Error", "Terjadi kesalahan!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        nama_barang.clear();
        jenis.setValue(null);
        id_kategori.setValue(null);
        deskripsi.clear();
        nama_penjual.clear();
        harga_awal.clear();
    }

    private boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(null);
        alert.setContentText(message);
    
        // Menunggu respons dari pengguna
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
    

    @FXML
    public void initialize() {
        try {
            // Pastikan properti sesuai dengan atribut di model Barang
            colKodeBarang.setCellValueFactory(new PropertyValueFactory<>("idBarang"));
            colNamaBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
            colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
             // Assuming Barang has a getKategori() method that returns a Kategori object
            colKategori.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getKategori().getNamaKategori()));
            colPenjual.setCellValueFactory(new PropertyValueFactory<>("namaPenjual"));
            colHarga.setCellValueFactory(new PropertyValueFactory<>("hargaAwal"));
            colTanggalMasuk.setCellValueFactory(new PropertyValueFactory<>("tglMasuk"));
            // Tambahkan ke dalam `initialize` method
            colAksi.setCellFactory(new Callback<TableColumn<Barang, String>, TableCell<Barang, String>>() {
                @Override
                public TableCell<Barang, String> call(TableColumn<Barang, String> param) {
                    return new TableCell<>() {
                        private final Button btnEdit = new Button("Edit");
                        private final Button btnHapus = new Button("Hapus");
                        private final HBox actionButtons = new HBox(5, btnEdit, btnHapus);

                        {
                            btnEdit.setOnAction(event -> {
                                Barang barang = getTableView().getItems().get(getIndex());
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/github/palexdev/editbarang.fxml"));
                                    Parent root = loader.load();

                                    // Kirim data barang ke controller edit
                                    EditBarangController controller = loader.getController();
                                    controller.setBarangData(barang);

                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.setTitle("Edit Barang");
                                    stage.show();
                                } catch (IOException e) {
                                    System.err.println("Error loading edit barang view: " + e.getMessage());
                                }
                            });

                            btnHapus.setOnAction(event -> {
                                Barang barang = getTableView().getItems().get(getIndex());
                                boolean confirm = showConfirmation("Apakah Anda yakin ingin menghapus barang ini?");
                                if (confirm) {
                                    if (BarangDAO.deleteBarang(barang.getIdBarang())) {
                                        getTableView().getItems().remove(barang);
                                        showAlert("Sukses", "Barang berhasil dihapus!", Alert.AlertType.INFORMATION);
                                    } else {
                                        showAlert("Error", "Barang tidak bisa dihapus karena sudah terdaftar di tabel lelang!", Alert.AlertType.ERROR);
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
                    };
                }
            });

            // Ambil data dari DAO
            ObservableList<Barang> barangList = FXCollections.observableArrayList(BarangDAO.getAllBarang());
            tableBarang.setItems(barangList);

            // Ambil data kategori dari DAO
            List<Kategori> kategoriList = KategoriDAO.getAllKategori();
            ObservableList<Kategori> kategoriObservableList = FXCollections.observableArrayList(kategoriList);
            
            // Mengisi ChoiceBox dengan kategori
            id_kategori.setItems(kategoriObservableList);

            // Menggunakan StringConverter untuk menampilkan nama kategori
            id_kategori.setConverter(new javafx.util.StringConverter<Kategori>() {
                @Override
                public String toString(Kategori kategori) {
                    return kategori != null ? kategori.getNamaKategori() : "";
                }

                @Override
                public Kategori fromString(String string) {
                    return null; // Tidak digunakan, karena kita hanya perlu menampilkan nama kategori
                }
            });

            // Mengisi ChoiceBox dengan jenis barang (enum JenisBarang)
            jenis.setItems(FXCollections.observableArrayList(JenisBarang.values()));
            
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
        }
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
