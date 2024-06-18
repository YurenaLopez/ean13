package es.ufv.dis.final2022.yls;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class PDFManager {
        public static void GenerarPDF(Productos productoNuevo, String nombreArchivo) {
                try {
                        Document doc = new Document(PageSize.A4, 50, 50, 100, 72);
                        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("productos/" + nombreArchivo + ".pdf"));
                        doc.open();

                        // Crear y añadir los párrafos con la información del producto
                        String nombre = "Nombre: " + productoNuevo.getNombre();
                        String categoria = "Categoría: " + productoNuevo.getCategoria();
                        String precio = "Precio: " + productoNuevo.getPrecio();
                        String ean13 = "EAN13: " + productoNuevo.getEan13();

                        Paragraph pNombre = new Paragraph(nombre);
                        pNombre.setAlignment(Element.ALIGN_JUSTIFIED);
                        doc.add(pNombre);

                        Paragraph pCategoria = new Paragraph(categoria);
                        pCategoria.setAlignment(Element.ALIGN_JUSTIFIED);
                        doc.add(pCategoria);

                        Paragraph pPrecio = new Paragraph(precio);
                        pPrecio.setAlignment(Element.ALIGN_JUSTIFIED);
                        doc.add(pPrecio);

                        Paragraph pEan13 = new Paragraph(ean13);
                        pEan13.setAlignment(Element.ALIGN_JUSTIFIED);
                        doc.add(pEan13);

                        doc.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
