package io.github.palexdev.controller;

import io.github.palexdev.dao.LelangDAO;
import io.github.palexdev.model.Lelang;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        colHargaAkhir.setCellValueFactory(new PropertyValueFactory<>("hargaAkhir"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Lelang> lelangList = FXCollections.observableArrayList(LelangDAO.getAllLelang());
        tableLelang.setItems(lelangList);
    }
}
