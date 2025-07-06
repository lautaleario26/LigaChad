package com.liga;

import com.liga.servicios.LigaService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LigaService ligaService = new LigaService();
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENÚ LIGA CHAD ===");
            System.out.println("1. Crear equipo");
            System.out.println("2. Registrar partido");
            System.out.println("3. Mostrar jugadores");
            System.out.println("4. Ver goleador");
            System.out.println("5. Ranking de equipos");
            System.out.println("6. Transferir jugador");
            System.out.println("7. Titular con más minutos");
            System.out.println("8. Suplentes que nunca ingresaron");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> ligaService.crearEquipoInteractivo(sc);
                case 2 -> ligaService.registrarPartidoInteractivo(sc);
                case 3 -> ligaService.mostrarJugadores();
                case 4 -> {
                    var goleador = ligaService.getGoleador();
                    if (goleador != null) {
                        System.out.println("Goleador: " + goleador.getNombre() + " con " + goleador.getGoles() + " goles.");
                    } else {
                        System.out.println("No hay goles registrados aún.");
                    }
                }
                case 5 -> {
                    System.out.println("Ranking de equipos:");
                    ligaService.rankingEquiposPorGoles().forEach(e -> 
                        System.out.println(e.getNombre() + " - Goles: " + e.getGolesTotales()));
                }
                case 6 -> ligaService.transferirJugadorInteractivo(sc);
                case 7 -> {
                    var t = ligaService.titularMasMinutos();
                    if (t != null) {
                        System.out.println("Titular con más minutos: " + t.getNombre() + " (" + t.getMinutosJugados() + " minutos)");
                    } else {
                        System.out.println("No hay titulares registrados aún.");
                    }
                }
                case 8 -> {
                    System.out.println("Suplentes que nunca ingresaron:");
                    ligaService.suplentesNuncaIngresados().forEach(s ->
                        System.out.println(s.getNombre()));
                }
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}

