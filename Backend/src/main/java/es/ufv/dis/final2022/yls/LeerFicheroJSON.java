package es.ufv.dis.final2022.yls;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LeerFicheroJSON {
    public static ArrayList<Productos> LeerFicheroProductos() throws IOException {
        // Obtiene el archivo JSON desde la ruta relativa al directorio del proyecto
        File productos = new File("productos/Datos.json");

        // Verifica si el archivo existe
        if (!productos.exists()) {
            throw new IOException("El archivo productos/Datos.json no se encuentra.");
        }

        // Inicializa Gson
        Gson gson = new Gson();

        // Lee el JSON desde el archivo y lo convierte a un ArrayList de Productos
        JsonReader reader = new JsonReader(new FileReader(productos));
        Type ProductListType = new TypeToken<ArrayList<Productos>>() {}.getType();
        ArrayList<Productos> products = gson.fromJson(reader, ProductListType);

        return products;
    }
}
