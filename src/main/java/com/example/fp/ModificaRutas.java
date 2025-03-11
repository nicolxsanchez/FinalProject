package com.example.fp;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import logico.Grafo;
import logico.Parada;
import logico.Ruta;

import java.util.List;

public class ModificaRutas {

    private Grafo grafo = new Grafo();

    @FXML private ComboBox<Ruta> cbRutas;
    @FXML private ComboBox<String> cbOrigen;
    @FXML private ComboBox<String> cbDestino;
    @FXML private TextField txtDistancia;
    @FXML private TextField txtTiempo;
    @FXML private TextField txtCosto;


    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
        actualizarComboBox();
        llenarParadas();
    }

    private void actualizarComboBox() {
        cbRutas.getItems().setAll(grafo.getRutas());
    }

    @FXML
    private void cargarDatosRuta() {
        Ruta rutaSeleccionada = cbRutas.getValue();
        if (rutaSeleccionada == null) {
            return;
        }

        txtTiempo.setText(String.valueOf(rutaSeleccionada.getTiempo()));
        txtDistancia.setText(String.valueOf(rutaSeleccionada.getDistancia()));
        txtCosto.setText(String.valueOf(rutaSeleccionada.getCosto()));
        cbOrigen.setValue(rutaSeleccionada.getOrigen().getNombre());
        cbDestino.setValue(rutaSeleccionada.getDestino().getNombre());
    }

    @FXML private void modificarRuta(){

        Ruta rutaSeleccionada = cbRutas.getValue();
        if (rutaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una ruta para modificar.");
            return;
        }

        if (txtCosto.getText().isEmpty()  || txtDistancia.getText().isEmpty() ||
                txtTiempo.getText().isEmpty() || cbOrigen.getValue() == null || cbDestino.getValue() == null) {
            mostrarAlerta("Campos vacíos", "Debes llenar todos los campos. ");
            return;
        }

        if (!validarNumero(txtTiempo.getText()) || !validarNumero(txtDistancia.getText())) {
            mostrarAlerta("Datos inválidos", "Tiempo y distancia deben ser números enteros válidos. ");
            return;
        }

        if (!validarDecNumero(txtCosto.getText())) {
            mostrarAlerta("Datos inválidos", "Costo debe ser un número válido, con o sin decimales. ");
            return;
        }

        Parada origen = obtnParadaSeleccionada(cbOrigen);
        Parada destino = obtnParadaSeleccionada(cbDestino);

        int tiempo = Integer.parseInt(txtTiempo.getText());
        int distancia = Integer.parseInt(txtDistancia.getText());
        double costo = Double.parseDouble(txtCosto.getText());

        if (origen.equals(destino)) {
            mostrarAlerta("Error de selección", "El origen y el destino no pueden ser la misma parada.");
            return;
        }

        if (existeRuta(origen, destino, rutaSeleccionada)) {
            mostrarAlerta("Ruta duplicada", "Ya existe la ruta: " + origen.getNombre() + " -> " + destino.getNombre());
            return;
        }



        if (rutaSeleccionada != null) {
            rutaSeleccionada.setCosto(costo);
            rutaSeleccionada.setDistancia(distancia);
            rutaSeleccionada.setTiempo(tiempo);
            rutaSeleccionada.setOrigen(origen);
            rutaSeleccionada.setDestino(destino);
            mostrarAlerta("Éxito", "Ruta modificada correctamente.");
            actualizarComboBox();
            limpiarCampos();
        }
    }


    @FXML
    private void llenarParadas() {
        List<Parada> paradas = grafo.getParadas();
        System.out.println("Número de paradas: " + paradas.size());

        List<Ruta> rutas = grafo.getRutas();
        System.out.println("Número de rutas: " + rutas.size());

        for (Parada parada : paradas) {
            cbOrigen.getItems().add(parada.getNombre());
            cbDestino.getItems().add(parada.getNombre());
        }
    }

    private boolean existeRuta(Parada origen, Parada destino, Ruta rutaSeleccionada) {
        List<Ruta> rutas = grafo.getRutas();
        for (Ruta ruta : rutas) {
            if (!ruta.equals(rutaSeleccionada) && ruta.getOrigen().equals(origen) && ruta.getDestino().equals(destino)) {
                return true;
            }
        }
        return false;
    }

    private Parada obtnParadaSeleccionada(ComboBox<String> comboBox) {
        String seleccionado = comboBox.getSelectionModel().getSelectedItem();
        List<Parada> paradas = grafo.getParadas();

        for (Parada parada : paradas) {
            if (parada.getNombre().equals(seleccionado)) {
                return parada;
            }
        }

        return null;
    }


    private void limpiarCampos() {
        txtDistancia.setText("");
        txtCosto.setText("");
        txtTiempo.setText("");
        cbDestino.setValue(null);
        cbOrigen.setValue(null);
        cbDestino.getSelectionModel().clearSelection();
        cbOrigen.getSelectionModel().clearSelection();

        cbDestino.setPromptText("<Seleccionar destino>");
        cbOrigen.setPromptText("<Seleccionar origen>");
    }

    private boolean validarDecNumero(String valor) {
        return valor.matches("\\d+(\\.\\d+)?");
    }

    private boolean validarNumero(String valor) {
        return valor.matches("\\d+");
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
