package com.liga.util;

import com.liga.modelo.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {
    public static void exportarJugadores(String nombreArchivo, List<Jugador> jugadores) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("Es titular,Nombre,Edad,Goles\n");
            for (Jugador j : jugadores) {
                boolean esTitular = j instanceof Titular;
                writer.write(esTitular + "," + j.getNombre() + "," + j.getEdad() + "," + j.getGoles() + "\n");
            }
            System.out.println("Exportación exitosa a " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error exportando archivo: " + e.getMessage());
        }
    }
}

