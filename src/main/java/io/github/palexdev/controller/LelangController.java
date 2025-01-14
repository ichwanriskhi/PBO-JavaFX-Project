package io.github.palexdev.controller;

import io.github.palexdev.dao.BarangDAO;
import io.github.palexdev.dao.LelangDAO;
import io.github.palexdev.model.Barang;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import java.util.Set;
import java.io.IOException;

public class LelangController {
    @FXML
    private Button simpanButton;
    @FXML
    private ChoiceBox<String> choiceBoxBarang; // Pilihan barang
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
    private TableColumn<Lelang, Void> colAksi;
    @FXML
    private BorderPane rootLayout;

    @FXML
    public void initialize() {
        // Inisialisasi kolom pada table Lelang
        colIdLelang.setCellValueFactory(new PropertyValueFactory<>("idLelang"));
        colNamaBarang.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getBarang().getNamaBarang())
        );
        colNamaPembeli.setCellValueFactory(new PropertyValueFactory<>("namaPembeli"));
        colTanggalBuka.setCellValueFactory(new PropertyValueFactory<>("tglDibuka"));
        colTanggalAkhir.setCellValueFactory(new PropertyValueFactory<>("tglSelesai"));
        colHargaAkhir.setCellValueFactory(new PropertyValueFactory<>("hargaAkhir"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Menambahkan kolom aksi dengan tombol "Tutup Lelang"
        Callback<TableColumn<Lelang, Void>, TableCell<Lelang, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnTutup = new Button("Tutup Lelang");

            {
                btnTutup.setOnAction(event -> {
                    Lelang lelang = getTableView().getItems().get(getIndex());
                    handleTutupLelang(lelang); // Panggil metode di luar cellFactory
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnTutup);
                    btnTutup.setOnAction(event -> {
                        Lelang lelang = getTableView().getItems().get(getIndex());
                        System.out.println("Tutup Lelang tombol diklik!"); // Tambahkan log
                        handleTutupLelang(lelang);
                    });
                }
            }
        };

        colAksi.setCellFactory(cellFactory);

        // Mengambil semua data lelang yang sudah ada dari database
        List<Lelang> lelangData = LelangDAO.getAllLelang();
        Set<Integer> barangSudahAdaDiLelang = new HashSet<>();

        // Membuat set untuk menyimpan ID barang yang sudah ada di lelang
        for (Lelang lelang : lelangData) {
            barangSudahAdaDiLelang.add(lelang.getBarang().getIdBarang());
        }

        // Mengambil daftar barang dari database
        List<Barang> barangData = BarangDAO.getAllBarang();
        ObservableList<String> barangList = FXCollections.observableArrayList();

        // Mengisi ChoiceBox dengan nama barang yang belum ada di tabel lelang
        for (Barang barang : barangData) {
            if (!barangSudahAdaDiLelang.contains(barang.getIdBarang())) {
                barangList.add(barang.getNamaBarang());
            }
        }

        choiceBoxBarang.setItems(barangList);
        choiceBoxBarang.getSelectionModel().selectFirst(); // Memilih barang pertama secara default

        // Mengisi TableView dengan data lelang yang ada
        ObservableList<Lelang> lelangList = FXCollections.observableArrayList(LelangDAO.getAllLelang());
        tableLelang.setItems(lelangList);
        tableLelang.refresh();
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

    @FXML
    private void handleSimpanLelang(ActionEvent event) {
        // Mengambil barang yang dipilih dari ChoiceBox
        String namaBarang = choiceBoxBarang.getSelectionModel().getSelectedItem();
        Barang selectedBarang = null;

        // Mencari barang yang dipilih berdasarkan nama
        for (Barang barang : BarangDAO.getAllBarang()) {
            if (barang.getNamaBarang().equals(namaBarang)) {
                selectedBarang = barang;
                break;
            }
        }

        if (selectedBarang != null) {
            // Mendapatkan tanggal saat ini
            String tglDibuka = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Membuat objek lelang baru dengan tglSelesai null
            Lelang lelangBaru = new Lelang(
                0, // ID lelang auto increment
                selectedBarang, 
                "",  // Nama pembeli kosong, karena tidak ada inputan
                0,    // Harga akhir kosong
                tglDibuka,  // Tanggal dibuka otomatis
                null,        // Tanggal selesai default null
                "Dibuka"     // Status default
            );

            // Menyimpan lelang baru ke database
            LelangDAO.insertLelang(lelangBaru);
        }
    }

    private void handleTutupLelang(Lelang lelang) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/github/palexdev/tutuplelang.fxml"));
            Parent tutupLelangView = loader.load();

            // Mengakses controller dari FXML
            TutupLelangController controller = loader.getController();
            controller.setLelang(lelang); // Mengirim objek Lelang yang dipilih

            Stage stage = new Stage();
            stage.setTitle("Tutup Lelang");
            stage.setScene(new Scene(tutupLelangView));
            stage.show();
            System.out.println("Tutup Lelang View ditampilkan.");
        } catch (IOException e) {
            System.err.println("Error loading tutup lelang view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadView(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/github/palexdev/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Mendapatkan stage saat ini
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set scene baru
            stage.setTitle(title);
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Gagal memuat file FXML: " + fxmlFile + ", Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}