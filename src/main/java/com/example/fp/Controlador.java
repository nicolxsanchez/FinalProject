package com.example.fp;

import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logico.Grafo;

public class Controlador {

    @FXML private BorderPane borderPane;
    @FXML private AnchorPane anchorPane;
    private Grafo grafo = new Grafo();

    @FXML
    private void agregarParada(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarParada.fxml"));
            Parent root = loader.load();
            AgregaParadas controller = loader.getController();
            controller.setGrafo(grafo);
            borderPane.setCenter(root);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de agregar parada.");
            e.printStackTrace();
        }
    }

    @FXML
    private void agregarRuta(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarRuta.fxml"));
            Parent root = loader.load();

            AgregaRutas controller = loader.getController();
            controller.setGrafo(grafo);

            borderPane.setCenter(root);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de agregar ruta.");
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarParada(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarParada.fxml"));
            Parent root = loader.load();

            EliminaParadas controller = loader.getController();
            controller.setGrafo(grafo);

            borderPane.setCenter(root);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de eliminar paradas.");
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarRuta(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarRuta.fxml"));
            Parent root = loader.load();

            EliminaRutas controller = loader.getController();
            controller.setGrafo(grafo);

            borderPane.setCenter(root);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de eliminar rutas.");
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarParada(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarParada.fxml"));
            Parent root = loader.load();

            ModificaParadas controller = loader.getController();
            controller.setGrafo(grafo);

            borderPane.setCenter(root);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de modificar paradas.");
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarRuta(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarRuta.fxml"));
            Parent root = loader.load();

            ModificaRutas controller = loader.getController();
            controller.setGrafo(grafo);

            borderPane.setCenter(root);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de modificar rutas.");
            e.printStackTrace();
        }
    }

    @FXML
    private void calcRutaMasCorta(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CalcularRutaMasCorta.fxml"));
            Parent root = loader.load();

            CalcRutaMasCorta controller = loader.getController();
            controller.setGrafo(grafo);

            borderPane.setCenter(root);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de calcular ruta mas corta.");
            e.printStackTrace();
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

