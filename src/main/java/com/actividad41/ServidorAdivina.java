package com.actividad41;

import java.net.*;

public class ServidorAdivina {
    public static void main(String[] args) {
        try {
            // Abrimos el puerto 2000 para que el cliente se conecte
            ServerSocket socketServidor = new ServerSocket(2000);
            System.out.println("Esperando a que alguien se conecte para jugar...");

            while(true){ //El servidor nunca se apaga
                Socket miCliente = socketServidor.accept(); // Espera clientes
                System.out.println("Se ha conectado un cliente");
                new HiloAdivina(miCliente).start(); // cada cliente está en un hilo diferente
                System.out.println("Cliente conectado desde: " + miCliente.getInetAddress());
            }
        } catch (Exception e) {
            System.out.println("Ha habido un fallo en el servidor: " + e.getMessage());
        }
    }
}