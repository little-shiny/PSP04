package com.actividad43;

import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * Estados posibles de la variable de control:
 * LOGIN -> pedir datos para iniciar sesion
 * MENU -> inicio de sesion correcto, seleccion de opciones
 */

public class ClienteFicheros {
    public static void main(String[] args) {
        try {
            Socket conexion = new Socket("localhost", 1500);

            String estado = "LOGIN"; //variable de control
            final int MAX_INTENTOS = 3;
            int intentos = 1;
            boolean esCorrecto = false;


            Scanner sc = new Scanner(System.in);
            PrintWriter enviar = new PrintWriter(conexion.getOutputStream(), true);
            BufferedReader recibir = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

            // Login
            while (!esCorrecto && intentos <= MAX_INTENTOS) {
                System.out.println("Introduce tu usuario:");
                String client_user = sc.nextLine();
                System.out.println("Introduce tu contraseña:");
                String client_pass = sc.nextLine();

                //Se envía al servidor
                enviar.println(client_user);
                enviar.println(client_pass);

                if (Objects.equals(recibir.readLine(), "OK")) {
                    estado = "MENU";
                    esCorrecto = true;
                } else {
                    System.out.println("Login incorrecto. Te quedan " + (MAX_INTENTOS - intentos) + " intentos");
                    intentos++;
                }
            }
            if (esCorrecto) {
                System.out.println("Login correcto");
            } else {
                System.out.println("Intentos máximos alcanzados. Se bloquea el sistema");
            }

            //Menu
            if (esCorrecto) {
                while (estado.equals("MENU")) {
                    System.out.println("-------------------------------------------");
                    System.out.println("                 MENU                      ");
                    System.out.println("-------------------------------------------");
                    System.out.println("[1] Ver contenido del directorio actual");
                    System.out.println("[2] Mostrar contenido de un archivo");
                    System.out.println("[3] Salir");
                    System.out.println();
                    System.out.println("Escoja una opción: ");

                    int opcion = Integer.parseInt(sc.nextLine()); //Se hace parseInt porque nextIOnt no consume el
                    // enter y entonces al hacer sc.nextLine para leer el nombre del archivo en el caso 2 se
                    // devolvería vacio el string

                    String linea;

                    switch (opcion) {
                        case 1: //Muestra archivos del directorio
                            enviar.println(1);
                            //Enviamos al servidor la opción 1 para que revise los archivos
                            //leemos hasta "FIN"
                            while (!(linea = recibir.readLine()).equals("FIN")) {
                                System.out.println(linea);
                            }
                            break;

                        case 2: // mostrar contenido de un archivo
                            enviar.println(2);
                            System.out.println("Escriba el nombre del archivo que desea leer");
                            String nombre = sc.nextLine();
                            enviar.println(nombre); // Se envía al servidor el nombre del archivo

                            while (!(linea = recibir.readLine()).equals("FIN")) {
                                System.out.println(linea);
                            }
                            break;

                        case 3: // salir del programa
                            enviar.println(3);
                            estado = "SALIR";
                            break;

                        default:
                            System.out.println("Opción no válida");
                    }
                }
            }
            conexion.close();
            System.out.println("--- Conexión cerrada ---");

        } catch (Exception e) {
            System.out.println("Error al intentar encontrar el archivo.");
        }
    }
}