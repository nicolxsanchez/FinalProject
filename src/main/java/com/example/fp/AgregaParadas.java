package com.example.fp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logico.Parada;
import logico.Grafo;

public class AgregaParadas
{
    private Grafo grafo = new Grafo();

    @FXML private TextField txtNombre;
    @FXML private TextField txtLatitud;
    @FXML private TextField txtLongitud;
    @FXML private Button btnGuardar;

    @FXML
    private void guardarParada() {

        if (txtNombre.getText().isEmpty() || txtLatitud.getText().isEmpty() || txtLongitud.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Debes llenar todos los campos.");
            return;
        }

        for (Parada parada : grafo.getParadas()) {
            if (parada.getNombre().equalsIgnoreCase(txtNombre.getText())) {
                mostrarAlerta("Error", "Ya existe una parada con ese nombre.");
                return;
            }
        }

        if (!validarNumero(txtLatitud.getText()) || !validarNumero(txtLongitud.getText())) {
            mostrarAlerta("Datos inválidos", "Latitud y longitud deben ser números válidos.");
            return;
        }

        double latitud = Double.parseDouble(txtLatitud.getText());
        double longitud = Double.parseDouble(txtLongitud.getText());

        for (Parada parada : grafo.getParadas()) {
            if (parada.getLatitud() == latitud && parada.getLongitud() == longitud) {
                mostrarAlerta("Error", "Ya existe una parada con esa latitud y longitud.");
                return;
            }
        }

        Parada nuevaParada = new Parada(txtNombre.getText(), latitud, longitud);
        grafo.agregarParada(nuevaParada);

        mostrarAlerta("Parada Agregada", "Se agregó la parada: " + txtNombre.getText());
        limpiarCampos();

    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private boolean validarNumero(String valor) {
        return valor.matches("-?\\d+(\\.\\d+)?");
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
    }
}
