package com.liga.servicios;

import java.util.*;
import com.liga.modelo.*;

public class LigaService {
    private  List<Equipo>   equipos  = new ArrayList<>();
    private  List<Partido> partidos = new ArrayList<>();

    public void registrarEquipo(Equipo e) { 
        equipos.add(e); 
    }   
    public void registrarPartido(Partido p) {
        partidos.add(p);
        p.getLocal().sumarPartido();
        p.getVisitante().sumarPartido();
    }

    public void mostrarJugadores() {
        equipos.forEach(eq -> {
            System.out.println("Equipo: " + eq.getNombre());
            eq.getJugadores().forEach(j ->
                System.out.printf("  - %s (%s) | Goles: %d\n", j.getNombre(), j.getTipo(), j.getGoles()));
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
                .filter(j -> j instanceof Suplente s && s.getPartidosIngresados()==0)
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
        if  (origen.getJugadores().contains(j)) {
            origen.eliminarJugador(j);
            destino.agregarJugador(j);
        }   else  {
            throw new IllegalArgumentException("El jugador no pertenece al equipo de origen");
        }
    }


    


    public List<Equipo> getEquipos()   { 
        return equipos; 
    }
    public List<Partido> getPartidos() { 
        return partidos; 
    }
}
