package com.actividad42;

import java.io.*;
import java.net.Socket;

public class HiloFicheros extends Thread {
    private final Socket miCliente;

    // Constructor
    HiloFicheros(Socket miCliente) {
        this.miCliente = miCliente;
    }

    public void run() {
        try {
            BufferedReader lector = new BufferedReader(new InputStreamReader(miCliente.getInputStream()));
            PrintWriter escritor = new PrintWriter(miCliente.getOutputStream(), true);

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
                escritor.println("ERROR: No se encuentra ese archivo en el servidor.");
            }
        }catch (IOException e){
            System.out.println("El cliente se ha desconectado.");
        } catch (Exception e) {
            System.out.println("Error en el hilo: " + e.getMessage());
        } finally {
            try{
                miCliente.close();
            }catch(IOException e){
                System.out.println("Error al cerrar el socket");
            }
        }
    }
}

