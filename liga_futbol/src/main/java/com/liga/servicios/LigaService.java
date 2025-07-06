package com.liga.servicios;

import java.util.*;
import com.liga.modelo.*;

public class LigaService {
    private final List<Equipo>  equipos  = new ArrayList<>();
    private final List<Partido> partidos = new ArrayList<>();

    public void registrarEquipo(Equipo e) { equipos.add(e); }
    public void registrarPartido(Partido p) {
        partidos.add(p);
        p.getLocal().sumarPartido();
        p.getVisitante().sumarPartido();
    }

    public void crearEquipoInteractivo(Scanner sc) {
        System.out.print("Nombre del equipo: ");
        String nombre = sc.nextLine();
        Equipo eq = new Equipo(nombre);

        System.out.print("¿Cuántos jugadores? ");
        int cant = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < cant; i++) {
            System.out.print("Nombre jugador: ");
            String nomJ = sc.nextLine();
            System.out.print("Edad: ");
            int edad = Integer.parseInt(sc.nextLine());
            System.out.print("¿Es titular? (s/n): ");
            boolean esTitular = sc.nextLine().equalsIgnoreCase("s");

            Jugador j = esTitular ? new Titular(nomJ, edad) : new Suplente(nomJ, edad);
            eq.agregarJugador(j);
        }
        registrarEquipo(eq);
    }

    public void registrarPartidoInteractivo(Scanner sc) {
        if (equipos.size() < 2) { System.out.println("Se necesitan al menos 2 equipos."); return; }
        // Selección de equipos
        Equipo local = seleccionarEquipo(sc, "local");
        Equipo visitante = seleccionarEquipo(sc, "visitante");
        if (local == visitante) { System.out.println("No puede jugar contra sí mismo."); return; }

        Partido p = new Partido(local, visitante);
        registrarPartido(p);

        // Goles local
        asignarGolesInteractivo(sc, local);
        // Goles visitante
        asignarGolesInteractivo(sc, visitante);
    }

    public void transferirJugadorInteractivo(Scanner sc) {
        if (equipos.size() < 2) { System.out.println("Necesitás al menos dos equipos."); return; }
        Equipo origen  = seleccionarEquipo(sc, "origen");
        Equipo destino = seleccionarEquipo(sc, "destino");
        if (origen == destino) { System.out.println("Origen y destino no pueden ser el mismo equipo."); return; }
        Jugador j = seleccionarJugador(sc, origen);
        transferirJugador(j, origen, destino);
        System.out.println("Transferencia realizada con éxito.");
    }

    public void mostrarJugadores() {
        equipos.forEach(eq -> {
            System.out.println("Equipo: " + eq.getNombre());
            eq.getJugadores().forEach(j ->
                System.out.printf("  - %s (%s) | Goles: %d%n", j.getNombre(), j.getTipo(), j.getGoles()));
        });
    }
    public Jugador getGoleador() {
        return equipos.stream()
                .flatMap(eq -> eq.getJugadores().stream())
                .max(Comparator.comparingInt(Jugador::getGoles))
                .orElse(null);
    }
    public List<Equipo> rankingEquiposPorGoles() {
        return equipos.stream()
                .sorted(Comparator.comparingInt(Equipo::getGolesTotales).reversed())
                .toList();
    }
    public List<Suplente> suplentesNuncaIngresados() {
        return equipos.stream()
                .flatMap(eq -> eq.getJugadores().stream())
                .filter(j -> j instanceof Suplente s && s.getPartidosIngresados() == 0)
                .map(j -> (Suplente) j)
                .toList();
    }
    public Titular titularMasMinutos() {
        return equipos.stream()
                .flatMap(eq -> eq.getJugadores().stream())
                .filter(j -> j instanceof Titular)
                .map(j -> (Titular) j)
                .max(Comparator.comparingInt(Titular::getMinutosJugados))
                .orElse(null);
    }
    public void transferirJugador(Jugador j, Equipo origen, Equipo destino) {
        if (origen.tieneJugador(j)) {
            origen.removerJugador(j);
            destino.agregarJugador(j);
        } else {
            throw new IllegalArgumentException("El jugador no pertenece al equipo de origen");
        }
    }

    private Equipo seleccionarEquipo(Scanner sc, String rol) {
        System.out.println("Seleccione equipo " + rol + ":");
        for (int i = 0; i < equipos.size(); i++)
            System.out.println((i + 1) + ") " + equipos.get(i).getNombre());
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        return equipos.get(idx);
    }
    private Jugador seleccionarJugador(Scanner sc, Equipo equipo) {
        List<Jugador> jugadores = equipo.getJugadores();
        System.out.println("Seleccione jugador:");
        for (int i = 0; i < jugadores.size(); i++)
            System.out.println((i + 1) + ") " + jugadores.get(i).getNombre());
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        return jugadores.get(idx);
    }
    private void asignarGolesInteractivo(Scanner sc, Equipo equipo) {
        System.out.print("¿Cuántos goles anotó " + equipo.getNombre() + "? ");
        int goles = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < goles; i++) {
            Jugador goleador = seleccionarJugador(sc, equipo);
            equipo.registrarGol(goleador);
        }
    }

    public List<Equipo>   getEquipos()  { return equipos; }
    public List<Partido>  getPartidos() { return partidos; }
}
