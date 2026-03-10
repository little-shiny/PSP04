package com.actividad41;

import java.net.*;

public class ServidorAdivina {
    public static void main(String[] args) {
        try (ServerSocket socketServidor = new ServerSocket(2000)){
            System.out.println("Esperando a que alguien se conecte para jugar...");

            while(true){ //El servidor nunca se apaga
                Socket miCliente = socketServidor.accept(); // Espera clientes
                System.out.println("Se ha conectado un cliente");
                System.out.println("Cliente conectado desde: " + miCliente.getInetAddress());
                new HiloAdivina(miCliente).start(); // cada cliente está en un hilo diferente
            }
        } catch (Exception e) {
            System.out.println("Ha habido un fallo en el servidor: " + e.getMessage());
        }
    }
}