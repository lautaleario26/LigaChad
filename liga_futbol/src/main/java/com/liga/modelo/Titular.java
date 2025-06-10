package com.liga.modelo;

public class Titular extends Jugador {
    private int minutosJugados;   // camelCase

    public Titular(String nombre, int edad) {
        super(nombre, edad);
        this.minutosJugados = 0;
    }

    @Override
    public String getTipo() { 
        return "Titular"; 
    }

    public void sumarMinutos(int minutos) { 
        minutosJugados += minutos; 
    }

    public int getMinutosJugados() { 
        return minutosJugados; 
    }
}

