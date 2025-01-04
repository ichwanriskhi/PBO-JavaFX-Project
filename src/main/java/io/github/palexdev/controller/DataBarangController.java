package io.github.palexdev.controller;

import io.github.palexdev.dao.BarangDAO;
import io.github.palexdev.model.Barang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DataBarangController {
    @FXML
    private TableView<Barang> tableBarang;
    @FXML
    private TableColumn<Barang, Integer> colIdBarang;
    @FXML
    private TableColumn<Barang, String> colNamaBarang;
    @FXML
    private TableColumn<Barang, String> colJenis;
    @FXML
    private TableColumn<Barang, String> colNamaPenjual;

    @FXML
    public void initialize() {
        colIdBarang.setCellValueFactory(new PropertyValueFactory<>("idBarang"));
        colNamaBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        colNamaPenjual.setCellValueFactory(new PropertyValueFactory<>("namaPenjual"));

        ObservableList<Barang> barangList = FXCollections.observableArrayList(BarangDAO.getAllBarang());
        tableBarang.setItems(barangList);
    }
}
