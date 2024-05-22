package com.usta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListarController {

    public ObservableList<Libro> libroOL = FXCollections.observableArrayList();
    public List<Libro> libroList= new ArrayList<>();

    @FXML
    private TableView<Libro> libroTable;
    @FXML
    private TableColumn<Libro, String> nombreCol;
    @FXML
    private TableColumn<Libro, String> autorCol;
    @FXML
    private TableColumn<Libro, String> generoCol;
    @FXML
    private TableColumn<Libro, String> isbnCol;

    public void initialize(){
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
        generoCol.setCellValueFactory(new PropertyValueFactory<>("genero"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        Libro nuevoLibro= new Libro("Programación Básica","Antony Botello","Terror","1q2w3e4r");
        libroList.add( nuevoLibro);
        libroOL.setAll(libroList);
        libroTable.setItems(libroOL);
         

    }

    @FXML
    private void switchToAgregar() throws IOException {
        App.setRoot("agregar");
    }
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    @FXML
    private void switchToEditar() throws IOException {
        App.setRoot("editar");
    }
    @FXML
    private void switchToEliminar() throws IOException {
        App.setRoot("eliminar");
    }
}
