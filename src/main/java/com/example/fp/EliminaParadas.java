package com.example.fp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logico.Grafo;
import logico.Parada;

public class EliminaParadas {

    private Grafo grafo = new Grafo();

    @FXML
    private ComboBox<Parada> cbParadas;

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
        actualizarComboBox();
    }

    private void actualizarComboBox() {
        cbParadas.getItems().setAll(grafo.getParadas());
    }

    @FXML
    private void eliminarParada() {
        Parada paradaSeleccionada = cbParadas.getValue();

        if (paradaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una parada para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar");
        confirmacion.setHeaderText("¿Está seguro de que desea eliminar la parada?");

        ButtonType resultado = confirmacion.showAndWait().orElse(ButtonType.CANCEL);

        if (resultado == ButtonType.OK) {
            if (grafo.eliminarParada(paradaSeleccionada)) {
                cbParadas.getItems().remove(paradaSeleccionada);
                mostrarAlerta("Éxito", "Parada Eliminada Correctamente.");
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la parada.");
            }
        }
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}