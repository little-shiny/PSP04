package com.actividad42;

import java.io.*;
import java.net.*;

public class ServidorFicheros {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1500);
            System.out.println("Servidor de archivos listo en el puerto 1500...");

            // Este servidor atiende a un cliente y se cierra, como pide el ejercicio
            Socket cliente = server.accept();

            BufferedReader lector = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter escritor = new PrintWriter(cliente.getOutputStream(), true);

            // Leemos el nombre del archivo que quiere el cliente
            String nombreArchivo = lector.readLine();
            File f = new File(nombreArchivo);

            if (f.exists() && f.isFile()) {
                // Si el archivo está, lo leemos y enviamos línea a línea
                BufferedReader leerArchivo = new BufferedReader(new FileReader(f));
                String linea;
                while ((linea = leerArchivo.readLine()) != null) {
                    escritor.println(linea);
                }
                leerArchivo.close();
            } else {
                // Si no existe, mandamos el mensaje de error directamente
                escritor.println("ERROR: No se encuenra ese archivo en el servidor.");
            }

            cliente.close();
            server.close();

        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}