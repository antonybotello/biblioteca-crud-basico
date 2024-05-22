package com.usta;

import java.io.IOException;
import javafx.fxml.FXML;

public class MenuController {


    @FXML
    private void switchToAgregar() throws IOException {
        App.setRoot("agregar");
    }
    @FXML
    private void switchToListar() throws IOException {
        App.setRoot("listar");
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
