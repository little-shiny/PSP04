package com.actividad43;

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorFicheros {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(1500)) {
            System.out.println("Servidor de archivos listo en el puerto 1500...");

            while (true) {
                Socket miCliente = server.accept(); // Espera clientes
                System.out.println("Se ha conectado un cliente");
                System.out.println("Cliente conectado desde: " + miCliente.getInetAddress());
                new HiloServidor(miCliente).start(); // cada cliente está en un hilo diferente
            }

        } catch (Exception e) {
            System.out.println("Ha habido un fallo en el servidor: " + e.getMessage());
        }
    }
}