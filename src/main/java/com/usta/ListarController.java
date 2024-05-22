package com.usta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        cargarLibros();

        libroOL.setAll(libroList);
        libroTable.setItems(libroOL);
         

    }
     private void cargarLibros() {
        String directoryPath = "src\\main\\resources\\com\\usta\\data";
        String filePath = directoryPath+ File.separator + "libros.txt";
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 4) {
                        Libro libro = new Libro(parts[0], parts[1], parts[2], parts[3]);
                        libroList.add(libro);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
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
