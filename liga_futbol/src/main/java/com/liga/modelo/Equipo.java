package com.liga.modelo;

import java.util.*;

public class Equipo {
    private String nombre;
    private List<Jugador> plantilla = new ArrayList<>();

    private int golesAnotadosTotales = 0;
    private int partidosJugados = 0;

    public Equipo(String nombre) { 
        this.nombre = nombre; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public List<Jugador> getJugadores() { 
        return Collections.unmodifiableList(plantilla); 
    }

    public void agregarJugador(Jugador j)   { 
        plantilla.add(j); 
    }
    public boolean tieneJugador(Jugador j) { 
        return plantilla.contains(j); 
    }
    public void removerJugador(Jugador j)   { 
        plantilla.remove(j); 
    }

    public void registrarGol(Jugador j) {
        if (!tieneJugador(j)) throw new IllegalArgumentException("El jugador no pertenece al equipo");
        j.anotarGol();
        golesAnotadosTotales++;
    }
    public void sumarPartido() { 
        partidosJugados++; 
    }
    public int  getGolesTotales()   { 
        return golesAnotadosTotales; 
    }
    public int  getPartidosJugados(){ 
        return partidosJugados; 
    }
    public double getPromedioGoles(){ 
        return partidosJugados==0?0:(double)golesAnotadosTotales/partidosJugados; 
    }
}
