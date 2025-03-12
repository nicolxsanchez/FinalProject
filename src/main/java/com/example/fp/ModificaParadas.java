package com.example.fp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import logico.Grafo;
import logico.Parada;

public class ModificaParadas {


    private Grafo grafo = new Grafo();

    @FXML private ComboBox<Parada> cbParadas;
    @FXML private TextField txtNombre;
    @FXML private TextField txtLatitud;
    @FXML private TextField txtLongitud;

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
        actualizarComboBox();
    }

    private void actualizarComboBox() {
        cbParadas.getItems().setAll(grafo.getParadas());
    }


    @FXML
    private void cargarDatosParada() {
        Parada paradaSeleccionada = cbParadas.getValue();
        if (paradaSeleccionada == null) {
            return;
        }
        txtNombre.setText(paradaSeleccionada.getNombre());
        txtLatitud.setText(String.valueOf(paradaSeleccionada.getLatitud()));
        txtLongitud.setText(String.valueOf(paradaSeleccionada.getLongitud()));
    }



    @FXML private void modificarParada(){
        if (txtNombre.getText().isEmpty() || txtLatitud.getText().isEmpty() || txtLongitud.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Debes llenar todos los campos .");
            return;
        }

        Parada paradaSeleccionada = cbParadas.getValue();
        if (paradaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una parada para modificar.");
            return;
        }

        for (Parada parada : grafo.getParadas()) {
            if (!parada.equals(paradaSeleccionada) && parada.getNombre().equalsIgnoreCase(txtNombre.getText())) {
                mostrarAlerta("Error", "Ya existe una parada con ese nombre .");
                return;
            }
        }

        if (!validarNumero(txtLatitud.getText()) || !validarNumero(txtLongitud.getText())) {
            mostrarAlerta("Datos inválidos", "Latitud y longitud deben ser números válidos .");
            return;
        }

        double latitud = Double.parseDouble(txtLatitud.getText());
        double longitud = Double.parseDouble(txtLongitud.getText());

        for (Parada parada : grafo.getParadas()) {
            if (!parada.equals(paradaSeleccionada) && parada.getLatitud() == latitud && parada.getLongitud() == longitud) {
                mostrarAlerta("Error", "Ya existe una parada con esa latitud y longitud.");
                return;
            }
        }

        if (paradaSeleccionada != null) {
            grafo.modificarParada(paradaSeleccionada, txtNombre.getText(), latitud, longitud);
            mostrarAlerta("Éxito", "Parada modificada correctamente.");
            actualizarComboBox();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
    }

    private boolean validarNumero(String valor) {
        return valor.matches("-?\\d+(\\.\\d+)?");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
