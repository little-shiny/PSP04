package com.actividad42;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteFicheros {
    public static void main(String[] args) {
        try {
            Socket conexion = new Socket("localhost", 1500);

            Scanner sc = new Scanner(System.in);
            PrintWriter pedir = new PrintWriter(conexion.getOutputStream(), true);
            BufferedReader recibir = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

            System.out.print("Escribe el nombre del fichero: ");
            String nombre = sc.nextLine();

            // Mandamos el nombre al servidor
            pedir.println(nombre);

            System.out.println("--- Respuesta del servidor ---");
            String respuesta;
            // Leemos todo lo que nos mande el servidor hasta que corte la conexión
            while ((respuesta = recibir.readLine()) != null) {
                System.out.println(respuesta);
            }

            conexion.close();
            System.out.println("--- Conexión cerrada ---");

        } catch (Exception e) {
            System.out.println("Error al intentar encontrar el archivo.");
        }
    }
}