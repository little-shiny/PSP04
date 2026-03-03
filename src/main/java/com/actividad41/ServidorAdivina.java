package com.actividad41;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ServidorAdivina {
    public static void main(String[] args) {
        try {
            // Abrimos el puerto 2000 para que el cliente se conecte
            ServerSocket socketServidor = new ServerSocket(2000);
            System.out.println("Esperando a que alguien se conecte para jugar...");

            Socket miCliente = socketServidor.accept();

            // Generamos el número secreto aquí para que no cambie durante la partida
            int secreto = new Random().nextInt(101);
            System.out.println("El número que tienen que adivinar es: " + secreto);

            // Canales para leer y escribir datos
            DataInputStream flujoEntrada = new DataInputStream(miCliente.getInputStream());
            DataOutputStream flujoSalida = new DataOutputStream(miCliente.getOutputStream());

            int numeroRecibido = -1;
            final int MAX_INTENTOS = 5;
            int intentos = 0;
            boolean adivinado = false;

            // El bucle sigue hasta que el cliente acierte o se cumplan los intentos
            while (!adivinado && intentos < MAX_INTENTOS) {
                numeroRecibido = flujoEntrada.readInt();
                intentos ++;


                if (numeroRecibido < secreto) {
                    flujoSalida.writeUTF("Te has quedado corto, el número es mayor. LLevas " + intentos + " Intentos");
                } else if (numeroRecibido > secreto) {
                    flujoSalida.writeUTF("Te has pasado, el número es menor. LLevas "+ intentos + " Intentos");

                } else {
                    flujoSalida.writeUTF("Ese era el número!!!! Número encontrado en "+ intentos + " intentos");
                    adivinado = true;
                }
            }

            // Solo se envía si NO adivinó
            if (!adivinado) {
                flujoSalida.writeUTF("Has llegado al máximo de intentos. El número era: " + secreto);
            }

            // Cerramos al terminar
            miCliente.close();
            socketServidor.close();

        } catch (Exception e) {
            System.out.println("Ha habido un fallo en el servidor: " + e.getMessage());
        }
    }
}