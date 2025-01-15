package io.github.palexdev.controller;

import java.sql.SQLException;
import java.time.LocalDate;

import io.github.palexdev.dao.LelangDAO;
import io.github.palexdev.model.Lelang;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class TutupLelangController {
    @FXML
    private TextField namaPembeliField;
    @FXML
    private TextField hargaAkhirField;

    private Lelang lelang;

    public void setLelang(Lelang lelang) {
        this.lelang = lelang;

        // Isi field dengan data dari lelang
        if (lelang != null) {
            namaPembeliField.setText(lelang.getNamaPembeli());
            hargaAkhirField.setText(String.valueOf(lelang.getHargaAkhir()));
        }
    }

    @FXML
    private void handleSimpan() {
        try {
            if (lelang == null || namaPembeliField == null || hargaAkhirField == null) {
                throw new IllegalStateException("Field atau objek lelang belum diinisialisasi");
            }

            // Simpan perubahan
            Lelang.setNamaPembeli(namaPembeliField.getText());
            try {
                int hargaAkhir = Integer.parseInt(hargaAkhirField.getText());
                Lelang.setHargaAkhir(hargaAkhir);
            } catch (NumberFormatException e) {
                System.err.println("Input harga akhir tidak valid: " + e.getMessage());
                return;
            }
            Lelang.setStatus("Ditutup");
            Lelang.setTglSelesai(LocalDate.now().toString()); // Mengatur tanggal selesai menjadi hari ini

            // Memperbarui data di database
            try {
                LelangDAO.updateLelangStatus(elang);
            } catch (SQLException e) {
                System.err.println("Gagal memperbarui status lelang di database: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}