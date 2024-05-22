package com.usta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EliminarController {
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

    private Libro libroAEliminar;

    public void initialize() {
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
        generoCol.setCellValueFactory(new PropertyValueFactory<>("genero"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        cargarLibros();

        libroOL.setAll(libroList);
        libroTable.setItems(libroOL);

    }

    public void setLibroAEditar() {
        this.libroAEliminar = libroTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void ventanaEliminar() throws IOException {
        setLibroAEditar();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar el libro " + libroAEliminar.getNombre() + "?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Aquí puedes incluir la lógica para eliminar el libro si el usuario confirma
            // Por ejemplo, puedes llamar a un método para eliminar el libro de la lista y
            // actualizar la tabla
            eliminarLibro();
        }
    }
    public void ventanaExito(String nombre) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito!");
        alert.setHeaderText(nombre);
        alert.setContentText("Puedes verificar en la sección de listar.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
        libroOL.setAll(libroList);
        libroTable.setItems(libroOL);
    }

    public void eliminarLibro() throws IOException {
        // Archivo original y archivo temporal para escribir los cambios
       
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Dividir la línea en partes
                String[] parts = line.split("\\|");
                // Si el ISBN de la línea coincide con el ISBN del libro a eliminar, no escribas
                // esa línea en el archivo temporal
                if (parts.length == 4 && parts[3].equals(libroAEliminar.getIsbn())) {
                    continue;
                }
                // Escribe la línea en el archivo temporal si no coincide con el ISBN del libro
                // a eliminar
                writer.write(line);
                writer.newLine();
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
                ventanaExito("La eliminación del libro " + libroAEliminar.getNombre() + " se ha realizado correctamente.");
                switchToEliminar();
            } else {
                System.out.println("No se pudo renombrar el archivo temporal.");
            }
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }
      
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

    @FXML
    private void switchToListar() throws IOException {
        App.setRoot("eliminar");
    }

}
