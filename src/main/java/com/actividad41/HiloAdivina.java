package com.actividad41;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class HiloAdivina extends Thread {

    private Socket miCliente; // Socket Cliente asociado al hilo

    // Constructor
    public HiloAdivina(Socket miCliente) {
        this.miCliente = miCliente;
    }

    public void run() {

        try {
            // Creamos el número secreto diferente para cada cliente
            int secreto = new Random().nextInt(100) + 1; // Entre 1 y 100
            System.out.println("Hilo ID: "+ Thread.currentThread().threadId()); //Añadimos la ID del hilo para saber que
            // el servidor tiene más de un cliente
            System.out.println("Número secreto para este cliente: " + secreto);

            DataInputStream flujoEntrada = new DataInputStream(miCliente.getInputStream());
            DataOutputStream flujoSalida = new DataOutputStream(miCliente.getOutputStream());

            int numeroRecibido;
            int intentos = 1;
            final int MAX_INTENTOS = 5; // Determinamos el número maximo de intentos para adivinar el numero
            boolean adivinado = false;

            while (!adivinado && intentos < MAX_INTENTOS) { // Mientras no se haya acertado y aun no llegue a los
                // intentos maximos

                numeroRecibido = flujoEntrada.readInt(); // Lee el número que escribe el cliente
                intentos++; // Se suma un intento

                // Comprobamos si acierta el número

                if (numeroRecibido < secreto) {
                    flujoSalida.writeUTF("Te has quedado corto, el número es mayor. Llevas " + intentos +
                            " intentos");
                } else if (numeroRecibido > secreto) {
                    flujoSalida.writeUTF("Te has pasado, el número es menor. Llevas " + intentos +
                            " intentos");
                } else {
                    flujoSalida.writeUTF("Ese era el número! Lo has encontrado en " + intentos + " intentos");
                    adivinado = true; //Faltaba actualizar la variable
                }
            }

            if (!adivinado) { // si no consigue advinarlo y el if anterior se ha consumido
                flujoSalida.writeUTF("Ya no te quedan más intentos. El número era: " + secreto);
            }

        } catch (IOException e){
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
