# Tarea PSP04 - Trabajo concurrente en servidor/cliente
## Índice

## Actividad 4.1
Se pide modificar el ejercicio 3.1 (PSP03) para trabajar de forma concurrente con varios clientes.

La interpretación que se hace del enunciado implica:
- El servidor no se bloquea cuando un cliente se conecta
- Crea un hilo por cada cliente
- Puede atender a diferentes clientes a la vez

En el ejercicio 3.1, el funcionamiento era sencillo, el cliente se conectaba al servidor y iba mandando números del 1 al 100 que debían coincidir con el elegido por el servidor.

Actualmente (reutilizando el código del ejericio PSP03) el ServidorAdivina.java:
```java
// Acepta un cliente y se cierra
Socket miCliente = socketServidor.accept();

```

Modificaremos el main de manera que varios clientes estén de forma simultánea y que cada cliente tenga un número 
secreto mediante la creación de un HiloAdivina.java que es el que maneja esa concurrencia.