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

    //borderPane.setCenter(anchorPane);  <- para el boton inicio

    @FXML
    private void agregarParada(MouseEvent event) {
       // cargarVentana("AgregarParada");


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarParada.fxml"));
            Parent root = loader.load();

            // Configurar el controlador con el grafo
            AgregaParadas controller = loader.getController();
            controller.setGrafo(grafo);

            // Mostrar directamente en el BorderPane (sin abrir ventana)
            borderPane.setCenter(root);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de agregar parada.");
            e.printStackTrace();
        }
    }

    @FXML
    private void agregarRuta(MouseEvent event) {
        //cargarVentana("AgregarRuta");

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
        //cargarVentana("EliminarParada");

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
        //cargarVentana("EliminarRuta");

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
       // cargarVentana("ModificarParada");
    }

    @FXML
    private void modificarRuta(MouseEvent event) {
        //cargarVentana("ModificarRuta");
    }

    @FXML
    private void calcRutaMasCorta(MouseEvent event) {

    }

    @FXML
    private void mostrarParadasYRutas(MouseEvent event) {

    }


    private void cargarVentana(String ventana, Grafo grafo) {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource(ventana + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        borderPane.setCenter(root);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}

