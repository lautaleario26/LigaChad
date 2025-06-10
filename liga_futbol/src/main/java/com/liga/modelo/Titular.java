package com.liga.modelo;

public class Titular extends Jugador {
    private int MinutosJugados;

    public Titular(String nombre, int edad){
        super(nombre, edad);
        this.MinutosJugados = 0;
    }

    @Override
    public String getTipo(){
        return "Titular";
    }

    public void sumarMinutos(int minutos){
        MinutosJugados += minutos;
    }

    public int getminutosJugados(){
        return MinutosJugados;
    }
    
}
