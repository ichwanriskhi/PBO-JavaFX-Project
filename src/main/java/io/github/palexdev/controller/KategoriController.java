package io.github.palexdev.controller;

import io.github.palexdev.dao.KategoriDAO;
import io.github.palexdev.model.Kategori;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KategoriController {
    @FXML
    private TableView<Kategori> tableKategori;
    @FXML
    private TableColumn<Kategori, String> colIdKategori;
    @FXML
    private TableColumn<Kategori, String> colNamaKategori;

    @FXML
    public void initialize() {
        colIdKategori.setCellValueFactory(new PropertyValueFactory<>("idKategori"));
        colNamaKategori.setCellValueFactory(new PropertyValueFactory<>("namaKategori"));

        ObservableList<Kategori> kategoriList = FXCollections.observableArrayList(KategoriDAO.getAllKategori());
        tableKategori.setItems(kategoriList);
    }
}
