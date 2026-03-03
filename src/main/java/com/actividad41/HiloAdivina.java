package com.actividad41;
import java.io.*;
import java.net.*;
import java.util.Random;
public class HiloAdivina extends Thread {

    private Socket miCliente; // Socket Cliente asociado al hilo

    // Constructor
    public HiloAdivina(Socket miCliente){
        this.miCliente = miCliente;
    }

    public Socket getMiCliente() {
        public void run(){

            try{
                // Creamos el número secreto diferente para cada cliente
                int secreto = new Random().nextInt(101); // Entre 1 y 100
                System.out.println("Número secreto para este cliente: " + secreto);

                DataInputStream flujoEntrada = new DataInputStream(miCliente.getInputStream());
                DataOutputStream flujoSalida = new DataOutputStream(miCliente.getOutputStream());

                int numeroRecibido;
                int intentos = 0;
                final int MAX_INTENTOS = 5; // Determinamos el numero maximo de intentos para adivinar el numero
                boolean adivinado = false;

                while(!adivinado && intentos < MAX_INTENTOS){ // Mientras no se haya acertado y aun no llegue a los
                    // intentos maximos

                    numeroRecibido = flujoEntrada.readInt(); // Lee el numero que escribe el cliente
                    intentos++; // Se suma un intento

                    // Comprobamos si acierta el número

                    if (numeroRecibido < secreto){
                        flujoSalida.writeUTF("Te has quedado corto, el número es mayor. Llevas " + intentos +
                                "intentos");
                    } else if (numeroRecibido > secreto){
                        flujoSalida.writeUTF("Te has pasado, el número es menor. Llevas " + intentos +
                                "intentos");
                    } else{
                        flujoSalida.writeUTF("Ese era el número! Lo has encontrado en " + intentos + " intentos");
                    }
                }

                if (!adivinado){ // si no consigue advinarlo y el if anterior se ha consumido
                    flujoSalida.writeUTF("Ya no te quedan más intentos. El número era: " + secreto);
                }

                miCliente.close(); // Cerramos el hilo de ese cliente

            } catch (Exception e){ // Si hay error en la conexión
                System.out.println("Error en el hilo cliente: "+ e.getMessage());
            }
        }
    }
}
