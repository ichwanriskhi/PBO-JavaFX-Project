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
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
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

        // Mengambil semua data lelang yang sudah ada dari database
        List<Lelang> lelangData = LelangDAO.getAllLelang(); // Dapatkan data lelang dari database

        // Membuat set untuk menyimpan ID barang yang sudah ada di lelang
        Set<Integer> barangSudahAdaDiLelang = new HashSet<>();
        for (Lelang lelang : lelangData) {
            barangSudahAdaDiLelang.add(lelang.getBarang().getIdBarang());
        }

        // Mengambil daftar barang dari database
        List<Barang> barangData = BarangDAO.getAllBarang(); // Misalnya ada method getAllBarang di BarangDAO

        // ObservableList untuk ChoiceBox
        ObservableList<String> barangList = FXCollections.observableArrayList();

        // Mengisi ChoiceBox dengan nama barang yang belum ada di tabel lelang
        for (Barang barang : barangData) {
            if (!barangSudahAdaDiLelang.contains(barang.getIdBarang())) {
                barangList.add(barang.getNamaBarang());
            }
        }

        // Menetapkan barangList ke ChoiceBox
        choiceBoxBarang.setItems(barangList);
        choiceBoxBarang.getSelectionModel().selectFirst(); // Memilih barang pertama secara default

        // Mengisi TableView dengan data lelang yang ada
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

    // Dalam metode handleSimpanLelang atau kode Anda yang sesuai
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
