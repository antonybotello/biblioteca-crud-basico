package com.usta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditarController {
    private String filePath = "src/main/resources/com/usta/data/libros.txt";

    public ObservableList<Libro> libroOL = FXCollections.observableArrayList();
    public List<Libro> libroList = new ArrayList<>();

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
    @FXML
    private TextField nombreTxt;

    @FXML
    private TextField autorTxt;

    @FXML
    private ComboBox<String> generoCBX;

    @FXML
    private TextField isbnTxt;

    private Libro libroAEditar;

    public void ventana(String nombre) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito!");
        alert.setHeaderText("El libro " + nombre + " se ha agregado correctamente");
        alert.setContentText("Puedes verificar en la sección de listar.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
        libroOL.setAll(libroList);
        libroTable.setItems(libroOL);
    }

    public void initialize() {
        ObservableList<String> generosOL = FXCollections.observableArrayList(
                "Comedia", "Drama", "Terror", "Acción", "Ciencia Ficción",
                "Fantasía", "Aventura", "Romance", "Misterio", "Thriller",
                "Documental", "Animación", "Biografía", "Histórico",
                "Musical", "Guerra", "Western", "Noir", "Deportivo");
        generoCBX.setItems(generosOL);
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
        String filePath = directoryPath + File.separator + "libros.txt";
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
    private void switchToListar() throws IOException {
        App.setRoot("listar");
    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void switchToAgregar() throws IOException {
        App.setRoot("agregar");
    }

    @FXML
    private void switchToEliminar() throws IOException {
        App.setRoot("eliminar");
    }

    @FXML
    private void switchToEditar() throws IOException {
        App.setRoot("editar");
    }

    public void setLibroAEditar() {
        this.libroAEditar = libroTable.getSelectionModel().getSelectedItem();
        cargarDatosLibro();
    }

    private void cargarDatosLibro() {
        if (libroAEditar != null) {
            nombreTxt.setText(libroAEditar.getNombre());
            autorTxt.setText(libroAEditar.getAutor());
            generoCBX.setValue(libroAEditar.getGenero());
            isbnTxt.setText(libroAEditar.getIsbn());
        }
    }

    @FXML
    public void editarLibro() throws IOException {
        if (libroAEditar != null) {
            // Actualizar los datos del libro con los valores de los campos
            libroAEditar.setNombre(nombreTxt.getText());
            libroAEditar.setAutor(autorTxt.getText());
            libroAEditar.setGenero(generoCBX.getValue());
            libroAEditar.setIsbn(isbnTxt.getText());

            // Guardar los cambios en el archivo
            actualizarArchivo();

        }
        switchToEditar();
    }

    private void actualizarArchivo() {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4 && parts[3].equals(libroAEditar.getIsbn())) {
                    writer.write(libroAEditar.getNombre() + "|" + libroAEditar.getAutor() + "|" +
                            libroAEditar.getGenero() + "|" + libroAEditar.getIsbn());
                    writer.newLine();
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        // Reemplazar el archivo original con el temporal
        File file = new File(filePath);
        File tempFile = new File(filePath + ".tmp");
        if (file.delete()) {
            // Intentar renombrar el archivo temporal
            if (tempFile.renameTo(file)) {
                // Mostrar ventana de éxito
                ventana("La edición del libro " + libroAEditar.getNombre() + " se ha guardado correctamente.");
            } else {
                System.out.println("No se pudo renombrar el archivo temporal.");
            }
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }
        if (tempFile.renameTo(file)) {
            // Mostrar ventana de éxito
            ventana("La edición del libro " + libroAEditar.getNombre() + " se ha guardado correctamente.");
        } else {
            System.out.println("No se pudo reemplazar el archivo original con el temporal.");
        }
    }
}
