package com.actividad43;

import java.io.*;
import java.net.Socket;

public class HiloServidor extends Thread {
    private final Socket miCliente;

    // Constructor
    HiloServidor(Socket miCliente) {
        this.miCliente = miCliente;
    }

    public void run() {
        final int MAX_INTENTOS = 3;


        try {
            BufferedReader lector = new BufferedReader(new InputStreamReader(miCliente.getInputStream()));
            PrintWriter escritor = new PrintWriter(miCliente.getOutputStream(), true);

            //login
            boolean autenticado = false;
            int intentos = 0;

            while (!autenticado && intentos < MAX_INTENTOS) {
                autenticado = login(lector, escritor, miCliente);
                intentos++;
            }

            //Si el login es correcto, comienza el menú
            if (autenticado) {
                menu(lector, escritor);
            }

        } catch (IOException e) {
            System.out.println("El cliente se ha desconectado.");
        } catch (Exception e) {
            System.out.println("Error en el hilo: " + e.getMessage());
        } finally {
            try {
                miCliente.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el socket");
            }
        }
    }

    //Función que controla el login del usuario
    public boolean login(BufferedReader bf, PrintWriter pw, Socket cliente) {
        final String USER = "admin";
        final String PASS = "1234";

        String client_user;
        String client_pass;

        try {
            client_user = bf.readLine();
            client_pass = bf.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (client_user.equals(USER) && client_pass.equals(PASS)) {
            System.out.println("Autenticación correcta para el cliente " + cliente.getInetAddress());
            pw.println("OK");
            return true;
        } else {
            pw.println("FAIL");
            return false;
        }
    }

    // Método que controla el menú principal.
    private void menu(BufferedReader lector, PrintWriter escritor) throws IOException {
        String opcion;

        while (true) {//Siempre encendido
            opcion = lector.readLine(); //Lee lo que le envia el cliente

            switch (opcion) {
                case "1":
                    listaDirectorio(escritor);
                    break;
                case "2":
                    String nomArchivo = lector.readLine(); //Leemos lo que dice el cliente
                    muestraFichero(nomArchivo, escritor);
                    break;
                case "3":
                    // salir
                    return;
            }

        }

    }

    //Método para la opción 1 del menú
    public void listaDirectorio(PrintWriter pw) {
        try {
            File directorio = new File(".");
            String[] lista = directorio.list(); //Llena la lista con lo que hay en la carpeta

            if (lista == null) {
                pw.println("El directorio está vacío.");
                pw.println();
                pw.println("FIN");
            } else {
                for (String elemento : lista) {
                    pw.println(elemento);
                }
                pw.println();
                pw.println("FIN");
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    //Método que muestra el contenido de un fichero (Se reutiliza del ejercicio anterior)
    public void muestraFichero(String nombre, PrintWriter escritor) {
        File f = new File(nombre);

        if (f.exists() && f.isFile()) {
            // Si el archivo está, lo leemos y enviamos línea a línea
            try {
                BufferedReader leerArchivo = new BufferedReader(new FileReader(f));
                String linea;

                while ((linea = leerArchivo.readLine()) != null) {
                    escritor.println(linea);
                }
                escritor.println();
                escritor.println("Lectura del archivo terminada");
                escritor.println();
                leerArchivo.close();
                escritor.println("FIN"); //Enviamos que termina

            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        } else {
            // Si no existe, mandamos el mensaje de error directamente
            escritor.println("ERROR: No se encuentra ese archivo en el servidor.");
            escritor.println("FIN");
        }
    }
}

