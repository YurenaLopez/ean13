package org.vaadin.example;

import com.nimbusds.jose.shaded.gson.Gson;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

    private Grid<Productos> grid = new Grid<>();
    private List<Productos> productos = new ArrayList<>();
    private TextField nombreField = new TextField("Nombre");
    private TextField categoriaField = new TextField("Categoria");
    private TextField precioField = new TextField("Precio");
    private TextField ean13Field = new TextField("EAN13");
    private Button addButton = new Button("Añadir");

    public MainView(@Autowired ProductosService service) {
        grid.addColumn(Productos::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Productos::getCategoria).setHeader("Categoria").setAutoWidth(true);
        grid.addColumn(Productos::getPrecio).setHeader("Precio").setAutoWidth(true);
        grid.addColumn(Productos::getEan13).setHeader("EAN13").setAutoWidth(true);

        // Añadir campos de texto y botón al layout
        HorizontalLayout formLayout = new HorizontalLayout(nombreField, categoriaField, precioField, ean13Field, addButton);
        add(formLayout);

        // Añadir el Grid al layout principal
        add(grid);

        // Configurar el botón de añadir
        addButton.addClickListener(e -> addProducto());

        loadProductos();
    }

    private void loadProductos() {
        // Obtener datos del servicio o controlador
        productos = getProductosFromController();
        grid.setItems(productos);
    }

    private List<Productos> getProductosFromController() {
        String url = "http://localhost:8080/productos";

        try {
            // Configurar cliente HTTP
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // Hacer la llamada GET
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Convertir la respuesta JSON a una lista de Productos
                Productos[] productosArray = new Gson().fromJson(response.body(), Productos[].class);
                return new ArrayList<>(Arrays.asList(productosArray));
            } else {
                // En caso de error mostrar mensaje
                System.out.println("Error al obtener datos: " + response.statusCode());
            }
        } catch (Exception e) {
            // Manejar excepción
            e.printStackTrace();
        }

        // En caso de error
        return new ArrayList<>();
    }

    private void addProducto() {
        try {
            // Obtener valores de los campos
            String nombre = nombreField.getValue();
            String categoria = categoriaField.getValue();
            String precio = precioField.getValue();
            String ean13 = ean13Field.getValue();

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty() || categoria.isEmpty() || precio.isEmpty() || ean13.isEmpty()) {
                Notification.show("Por favor, complete todos los campos");
                return;
            }

            Productos nuevoProducto = new Productos(nombre, categoria, precio, ean13);

            // Hacer la llamada POST al servidor
            HttpClient httpClient = HttpClient.newHttpClient();
            Gson gson = new Gson();
            String json = gson.toJson(nuevoProducto);

            System.out.println("Enviando JSON: " + json); // Log para depuración

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/productos"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println("Respuesta del servidor: " + response.body()); // Log para depuración

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                // Actualizar la lista productos y el grid
                productos = getProductosFromController();
                grid.setItems(productos);

                // Limpiar los campos de texto
                nombreField.clear();
                categoriaField.clear();
                precioField.clear();
                ean13Field.clear();
            } else {
                Notification.show("Error al añadir producto: " + response.statusCode());
            }
        } catch (NumberFormatException e) {
            // Manejar excepción de formato de número
            Notification.show("Por favor, introduce un precio válido");
        } catch (Exception e) {
            // Manejar otras excepciones
            e.printStackTrace();
            Notification.show("Error al añadir producto: " + e.getMessage());
        }
    }

}
