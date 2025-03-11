package com.example.fp;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import logico.Grafo;
import logico.Parada;
import logico.Ruta;

public class EliminaRutas {

    private Grafo grafo = new Grafo();

    @FXML
    private ComboBox<Ruta> cbRutas;

    @FXML
    private Button btnEliminar;


    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
        actualizarComboBox();
    }

    private void actualizarComboBox() {
        cbRutas.getItems().setAll(grafo.getRutas());
    }

    @FXML
    private void eliminarRuta() {
        Ruta rutaSeleccionada = cbRutas.getValue();

        if (rutaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una parada para eliminar.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar");
        confirmacion.setHeaderText("¿Está seguro de que desea eliminar la ruta?");

        ButtonType resultado = confirmacion.showAndWait().orElse(ButtonType.CANCEL);

        if (resultado == ButtonType.OK) {
            if (grafo.eliminarRuta(rutaSeleccionada)) {
                cbRutas.getItems().remove(rutaSeleccionada);
                mostrarAlerta("Éxito", "Parada Eliminada Correctamente.");
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la parada.");
            }
        }

    }
    private void mostrarAlerta (String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


}