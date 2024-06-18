package es.ufv.dis.final2022.yls;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LeerFicheroJSON {
    public static ArrayList<Productos> LeerFicheroProductos() throws IOException {
        // Obtiene el archivo JSON
        InputStream inputStream = LeerFicheroJSON.class.getClassLoader().getResourceAsStream("productos/Datos.json");

        // Copia el archivo JSON a un File
        File productos = new File("productos/Datos.json");
        FileUtils.copyInputStreamToFile(inputStream, productos);

        // Inicializa Gson
        Gson gson = new Gson();

        // Lee el JSON desde el archivo y lo convierte a un ArrayList de NationalDataFile
        JsonReader reader = new JsonReader(new FileReader(productos));
        Type ProductListType = new TypeToken<ArrayList<Productos>>() {}.getType();
        ArrayList<Productos> products = gson.fromJson(reader, ProductListType);

        return products;
    }
}
