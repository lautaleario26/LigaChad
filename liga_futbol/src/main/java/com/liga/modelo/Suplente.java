package com.liga.modelo;

public class Suplente extends Jugador {
    private int partidosIngresados;

    public Suplente(String nombre, int edad) {
        super(nombre, edad);
        this.partidosIngresados = 0;
    }

    @Override
    public String getTipo() {
        return "Suplente";
    }

    public void ingresarPartido() {
        partidosIngresados++;
    }

    public int getPartidosIngresados() {
        return partidosIngresados;
    }
}
