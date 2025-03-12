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

    private void cargarParadas() {
        cbOrigen.getItems().setAll(grafo.getParadas());
        cbDestino.getItems().setAll(grafo.getParadas());
    }

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

    private void buscarRutasConPila(Parada origen, Parada destino) {

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

    private void verificarRutasDisponibles() {
        Parada origenSeleccionada = cbOrigen.getValue();
        Parada destinoSeleccionado = cbDestino.getValue();

        if (origenSeleccionada != null && destinoSeleccionado != null) {
            lvRutasRegistradas.getItems().clear();
            buscarRutasConPila(origenSeleccionada, destinoSeleccionado);

            if (lvRutasRegistradas.getItems().isEmpty()) {
                mostrarAdvertencia("No se encontraron rutas entre el origen y el destino seleccionados.");
            }
        }
    }

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

    private String dijkstra(Parada origen, Parada destino, String criterio) {
        Map<Parada, Integer> dist = new HashMap<>();
        Map<Parada, Parada> prev = new HashMap<>();

        for (Parada p : grafo.getParadas()) {
            dist.put(p, Integer.MAX_VALUE);
            prev.put(p, null);
        }
        dist.put(origen, 0);
        PriorityQueue<Parada> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        pq.add(origen);

        while (!pq.isEmpty()) {
            Parada actual = pq.poll();
            if (actual.equals(destino)) {
                break;
            }

            List<Ruta> rutasAdj = grafo.getAdyacencias().get(actual);
            if (rutasAdj != null) {
                for (Ruta ruta : rutasAdj) {
                    Parada vecino = ruta.getDestino();

                    int peso = (criterio.equals("Distancia")) ? ruta.getDistancia() : ruta.getTiempo();
                    int nuevaDist = dist.get(actual) + peso;
                    if (nuevaDist < dist.get(vecino)) {
                        dist.put(vecino, nuevaDist);
                        prev.put(vecino, actual);
                        pq.add(vecino);
                    }
                }
            }
        }

        if (dist.get(destino) == Integer.MAX_VALUE) {
            return null;
        }

        List<Parada> camino = new ArrayList<>();
        for (Parada at = destino; at != null; at = prev.get(at)) {
            camino.add(at);
        }
        Collections.reverse(camino);

        String rutaStr = obtenerRutaString(camino);
        int costoTotal = dist.get(destino);
        return rutaStr + "\nCosto total en " + criterio.toLowerCase() + ": " + costoTotal;
    }

    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    }



