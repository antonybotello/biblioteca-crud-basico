package com.usta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AgregarController {

    @FXML
    private TextField nombreTxt;

    @FXML
    private TextField autorTxt;

    @FXML
    private ComboBox<String> generoCBX;

    @FXML
    private TextField isbnTxt;
    

    public void ventana(String nombre){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito!");
        alert.setHeaderText("El libro "+nombre+" se ha agregado correctamente");
        alert.setContentText("Puedes verificar en la sección de listar.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
    }

    public void initialize(){
       ObservableList<String> generosOL = FXCollections.observableArrayList(
            "Comedia", "Drama", "Terror", "Acción", "Ciencia Ficción", 
            "Fantasía", "Aventura", "Romance", "Misterio", "Thriller", 
            "Documental", "Animación", "Biografía", "Histórico", 
            "Musical", "Guerra", "Western", "Noir", "Deportivo"
        );
        generoCBX.setItems(generosOL);
    }

    @FXML
    private void switchToListar() throws IOException {
        App.setRoot("listar");
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

    @FXML
    public void agregarLibro() {
        String nombre = nombreTxt.getText();
        String autor = autorTxt.getText();
        String genero = generoCBX.getValue();
        String isbn = isbnTxt.getText();

        Libro nuevoLibro = new Libro(nombre, autor, genero, isbn);
        
        
        String directoryPath = "src\\main\\resources\\com\\usta\\data";
        String filePath = directoryPath + File.separator + "libros.txt";

        
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(nuevoLibro.toString());
            writer.newLine();
            ventana(nombre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
