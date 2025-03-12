package com.example.fp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logico.Grafo;
import logico.Parada;
import logico.Ruta;

import java.util.*;

public class CalcRutaMasCorta {

    private Grafo grafo = new Grafo();

    @FXML private ComboBox<Parada> cbOrigen;
    @FXML private ComboBox<Parada> cbDestino;
    @FXML private ComboBox<String> cbCriterio;
    @FXML private TextArea txtResultado;
    @FXML private ListView<String> lvRutasRegistradas;


    @FXML
    public void initialize() {
        cbCriterio.getItems().addAll("Distancia", "Tiempo");

        cbOrigen.setOnAction(event -> verificarRutasDisponibles());
        cbDestino.setOnAction(event -> verificarRutasDisponibles());
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
        cargarParadas();
    }

    //llena los comboboxes de origen y destino con las paradas que existen
    private void cargarParadas() {
        cbOrigen.getItems().setAll(grafo.getParadas());
        cbDestino.getItems().setAll(grafo.getParadas());
    }

    //convierte una lista de paradas en un texto con los nombres separados por "->"
    private String obtenerRutaString(List<Parada> ruta) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < ruta.size(); i++) {
            string.append(ruta.get(i).getNombre());
            if (i < ruta.size() - 1) {
                string.append(" -> ");
            }
        }
        return string.toString();
    }

    //busca todas las rutas posibles entre dos paradas
    private void buscarRutas(Parada origen, Parada destino) {

        Stack<List<Parada>> pila = new Stack<>();
        List<Parada> caminoInicial = new ArrayList<>();
        caminoInicial.add(origen);
        pila.push(caminoInicial);

        while (!pila.isEmpty()) {
            List<Parada> caminoActual = pila.pop();
            Parada actual = caminoActual.get(caminoActual.size() - 1);

            if (actual.equals(destino)) {
                String rutaString = obtenerRutaString(caminoActual);
                lvRutasRegistradas.getItems().add(rutaString);
            } else {
                List<Ruta> rutasVecinas = grafo.getAdyacencias().get(actual);
                if (rutasVecinas != null) {
                    for (Ruta ruta : rutasVecinas) {
                        Parada vecino = ruta.getDestino();
                        if (!caminoActual.contains(vecino)) {
                            List<Parada> nuevoCamino = new ArrayList<>(caminoActual);
                            nuevoCamino.add(vecino);
                            pila.push(nuevoCamino);
                        }
                    }
                }
            }
        }
    }

    // verifica si hay rutas disponibles entre el origen y destino seleccionado
    private void verificarRutasDisponibles() {
        Parada origenSeleccionada = cbOrigen.getValue();
        Parada destinoSeleccionado = cbDestino.getValue();

        if (origenSeleccionada != null && destinoSeleccionado != null) {
            lvRutasRegistradas.getItems().clear();
            buscarRutas(origenSeleccionada, destinoSeleccionado);

            if (lvRutasRegistradas.getItems().isEmpty()) {
                mostrarAdvertencia("No se encontraron rutas entre el origen y el destino seleccionados.");
            }
        }
    }

        ///calcula la ruta entre el origen y destino seleccionados según el criterio y muestra resultado
        @FXML
        public void calcularRuta () {
            Parada origen = cbOrigen.getValue();
            Parada destino = cbDestino.getValue();
            String criterio = cbCriterio.getValue();

            if (origen == null || destino == null || criterio == null) {
                txtResultado.setText("Por favor, seleccione todas las opciones.");
                return;
            }

            String resultado = dijkstra(origen, destino, criterio);
            if (resultado == null) {
                txtResultado.setText("No se encontró ruta.");
            } else {
                txtResultado.setText(resultado);
            }
        }

    //calcula la ruta más corta entre dos paradas usando el algoritmo de Dijkstra
    private String dijkstra(Parada origen, Parada destino, String criterio) {
        Map<Parada, Integer> distancia = new HashMap<>();
        Map<Parada, Parada> paradaAnterior = new HashMap<>();

        for (Parada parada : grafo.getParadas()) {
            distancia.put(parada, Integer.MAX_VALUE);
            paradaAnterior.put(parada, null);
        }
        distancia.put(origen, 0);
        PriorityQueue<Parada> cola = new PriorityQueue<>(Comparator.comparingInt(distancia::get));
        cola.add(origen);

        while (!cola.isEmpty()) {
            Parada actual = cola.poll();
            if (actual.equals(destino)) {
                break;
            }

            List<Ruta> rutasAdj = grafo.getAdyacencias().get(actual);
            if (rutasAdj != null) {
                for (Ruta ruta : rutasAdj) {
                    Parada vecino = ruta.getDestino();

                    int peso = (criterio.equals("Distancia")) ? ruta.getDistancia() : ruta.getTiempo();
                    int nuevaDist = distancia.get(actual) + peso;
                    if (nuevaDist < distancia.get(vecino)) {
                        distancia.put(vecino, nuevaDist);
                        paradaAnterior.put(vecino, actual);
                        cola.add(vecino);
                    }
                }
            }
        }

        if (distancia.get(destino) == Integer.MAX_VALUE) {
            return null;
        }

        List<Parada> camino = new ArrayList<>();
        for (Parada paradaActual = destino; paradaActual != null; paradaActual = paradaAnterior.get(paradaActual)) {
            camino.add(paradaActual);
        }
        Collections.reverse(camino);
        String rutaString = obtenerRutaString(camino);
        int costoTotal = distancia.get(destino);
        return rutaString + "\nCosto total en " + criterio.toLowerCase() + ": " + costoTotal;
    }


    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    }



