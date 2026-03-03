package com.actividad41;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteAdivina {
    public static void main(String[] args) {
        try {
            // Nos conectamos al servidor en el puerto 2000
            Socket socketCliente = new Socket("localhost", 2000);

            DataOutputStream enviar = new DataOutputStream(socketCliente.getOutputStream());
            DataInputStream recibir = new DataInputStream(socketCliente.getInputStream());
            Scanner teclado = new Scanner(System.in);

            String feedback = "";

            // Mientras no recibamos la palabra "era", seguimos pidiendo números
            while (true) {

                try{
                    System.out.print("Dime un número del 0 al 100: ");
                    int miNumero = teclado.nextInt();
                    enviar.writeInt(miNumero);

                    feedback = recibir.readUTF();
                    System.out.println("Respuesta del servidor: " + feedback);

                    if(feedback.contains("Ese era el número")){
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("El servidor ha cerrado la conexión");
                    break;
                }

            }

            System.out.println("Has ganado!");
            socketCliente.close();
            teclado.close();

        } catch (Exception e) {
            System.out.println("No puede conectar ¿Está el servidor encendido?");
        }
    }
}