package com.liga.modelo;

import java.util.*;

public class Partido {
    private  Equipo local;
    private  Equipo visitante;
    private  Map<Jugador,Integer> golesPorJugador = new HashMap<>();

    public Partido(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;
    }

    
    public void registrarGol(Jugador autor) {
        golesPorJugador.merge(autor, 1, Integer::sum);
        Equipo eq = autor.getTipo().equals("Titular") || autor.getTipo().equals("Suplente") ?
                     (local.getJugadores().contains(autor) ? local : visitante) : null;
        if (eq == null) throw new IllegalArgumentException("Jugador no pertenece al partido");
        eq.registrarGol(autor);
    }

    public Map<Jugador,Integer> getGolesPorJugador() { return Collections.unmodifiableMap(golesPorJugador); }

    public Equipo getLocal()     { return local; }
    public Equipo getVisitante() { return visitante; }
}
