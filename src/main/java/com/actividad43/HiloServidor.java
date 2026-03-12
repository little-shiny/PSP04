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
        String estado; //variable de control que cambia en función del estado

        try {
            BufferedReader lector = new BufferedReader(new InputStreamReader(miCliente.getInputStream()));
            PrintWriter escritor = new PrintWriter(miCliente.getOutputStream(), true);

            login(lector, escritor, miCliente);

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

    public void login(BufferedReader bf, PrintWriter pw, Socket cliente) {
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
        }else{
            pw.println("FAIL");
        }
    }
}

