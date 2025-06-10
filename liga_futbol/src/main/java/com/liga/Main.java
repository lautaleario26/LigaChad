package com.liga;

import com.liga.modelo.*;
import com.liga.servicios.*;
import com.liga.util.CSVExporter;

import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static LigaService liga = new LigaService();
    private static ReporteService rep = new ReporteService(liga);

    public static void main(String[] args) {
        int op;
        do {
            menu();
            op = leerInt("Opción: ");
            switch (op) {
                case 1 -> crearEquipo();
                case 2 -> registrarPartido();
                case 3 -> liga.mostrarJugadores();
                case 4 -> System.out.println("Goleador: " +
                        Optional.ofNullable(liga.getGoleador()).map(j->j.getNombre()+" ("+j.getGoles()+")").orElse("-"));
                case 5 -> mostrarRankingEquipos();
                case 6 -> transferirJugador();
                case 7 -> mostrarSuplentesNuncaIngresados();
                case 8 -> mostrarTitularMasMinutos();
                case 9 -> exportarCSVEquipo();
                case 10 -> System.out.println(rep.reporteGeneral());
                case 11 -> reporteEquipo();
            }
        } while (op != 0);
        sc.close();
    }

    private static void menu() {
        System.out.println("\n=== LIGA CHAD ===");
        System.out.println("1. Crear equipo e incorporar jugadores");
        System.out.println("2. Registrar partido y asignar goles");
        System.out.println("3. Mostrar todos los jugadores");
        System.out.println("4. Mostrar goleador de la liga");
        System.out.println("5. Ranking de equipos por goles");
        System.out.println("6. Transferir jugador");
        System.out.println("7. Suplentes que nunca ingresaron");
        System.out.println("8. Titular con más minutos");
        System.out.println("9. Exportar jugadores de un equipo a CSV");
        System.out.println("10. Reporte general de liga");
        System.out.println("11. Reporte de equipo");
        System.out.println("0. Salir");
    }

    // ---- Opciones del menú ----
    private static void crearEquipo() {
        String nombreEq = leerString("Nombre del equipo: ");
        Equipo eq = new Equipo(nombreEq);
        int cant = leerInt("¿Cuántos jugadores? ");
        for (int i = 0; i < cant; i++) {
            String nom = leerString("Nombre jugador: ");
            int edad = leerInt("Edad: ");
            boolean esTitular = leerString("¿Es titular? (s/n): ").equalsIgnoreCase("s");
            Jugador j = esTitular ? new Titular(nom, edad) : new Suplente(nom, edad);
            eq.agregarJugador(j);
        }
        liga.registrarEquipo(eq);
    }

    private static void registrarPartido() {
        if (liga.getEquipos().size() < 2) {
            System.out.println("Necesitás al menos dos equipos registrados.");
            return;
        }
        Equipo local = seleccionarEquipo("Equipo local");
        Equipo visitante = seleccionarEquipo("Equipo visitante");
        if (local == visitante) { System.out.println("No podés jugar contra el mismo equipo"); return; }
        Partido p = new Partido(local, visitante);
        liga.registrarPartido(p);

        String masGoles;
        do {
            Jugador j = seleccionarJugador("Jugador que anotó", local, visitante);
            p.registrarGol(j);
            masGoles = leerString("¿Agregar otro gol? (s/n): ");
        } while (masGoles.equalsIgnoreCase("s"));
    }

    private static void mostrarRankingEquipos() {
        int pos = 1;
        System.out.println("\n-- Ranking de goles --");
        for (Equipo e : liga.rankingEquiposPorGoles()) {
            System.out.printf("%d) %s - %d goles\n", pos++, e.getNombre(), e.getGolesTotales());
        }
    }

    private static void transferirJugador() {
        Equipo origen = seleccionarEquipo("Equipo origen");
        Jugador j = seleccionarJugador("Jugador a transferir", origen);
        Equipo destino = seleccionarEquipo("Equipo destino");
        liga.transferirJugador(j, origen, destino);
        System.out.println("Transferencia realizada.");
    }

    private static void mostrarSuplentesNuncaIngresados() {
        System.out.println("Suplentes que nunca ingresaron:");
        liga.suplentesNuncaIngresados().forEach(s ->
                System.out.println(" - " + s.getNombre() + " (" + s.getEdad() + " años)"));
    }

    private static void mostrarTitularMasMinutos() {
        Titular t = liga.titularMasMinutos();
        System.out.println(t==null?"No hay titulares registrados":
                "Titular con más minutos: " + t.getNombre() + " (" + t.getMinutosJugados() + " min)");
    }

    private static void exportarCSVEquipo() {
        Equipo eq = seleccionarEquipo("Equipo a exportar");
        CSVExporter.exportarJugadores(eq.getNombre()+".csv", eq.getJugadores());
    }

    private static void reporteEquipo() {
        Equipo eq = seleccionarEquipo("Equipo");
        System.out.println(rep.reporteEquipo(eq));
    }

    // ---- Helpers de lectura ----
    private static String leerString(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
    private static int leerInt(String msg) {
        System.out.print(msg);
        return Integer.parseInt(sc.nextLine());
    }

    // ---- Helpers de selección ----
    private static Equipo seleccionarEquipo(String prompt) {
        System.out.println(prompt + ":");
        for (int i = 0; i < liga.getEquipos().size(); i++)
            System.out.println((i+1) + ") " + liga.getEquipos().get(i).getNombre());
        int idx = leerInt("Elegí (número): ") - 1;
        return liga.getEquipos().get(idx);
    }
    private static Jugador seleccionarJugador(String prompt, Equipo... equipos) {
        List<Jugador> todos = new ArrayList<>();
        for (Equipo e : equipos) todos.addAll(e.getJugadores());
        System.out.println(prompt + ":");
        for (int i = 0; i < todos.size(); i++)
            System.out.println((i+1) + ") " + todos.get(i).getNombre() + " - " + todos.get(i).getTipo());
        int idx = leerInt("Elegí (número): ") - 1;
        return todos.get(idx);
    }
}

