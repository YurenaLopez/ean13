package es.ufv.dis.final2022.yls;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ProductosController {

    // Métodos GET para leer datos de la API
    @GetMapping("/productos")
    public ArrayList<Productos> productos() throws IOException {

        return  LeerFicheroJSON.LeerFicheroProductos();
    }

    @PostMapping("/productos")
    public ArrayList<Productos> createProductos(@RequestBody Productos nuevoDato) throws IOException {
        ArrayList<Productos> datos = LeerFicheroJSON.LeerFicheroProductos();

        // Añadimos los datos nuevos al fichero existente
        datos.add(nuevoDato);

        // Convertimos la lista actualizada a JSON manteniendo el formato
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(datos);

        // Escribimos el JSON actualizado en el archivo
        File file = new File("productos/Datos.json");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(json); // Escribir el JSON actualizado
        fileWriter.flush();
        fileWriter.close();

        return datos;
    }
}
