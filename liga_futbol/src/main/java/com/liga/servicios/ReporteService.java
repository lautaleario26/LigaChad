package com.liga.servicios;

import com.liga.modelo.*;
import java.util.*;

public class ReporteService {
    private LigaService liga;
    public ReporteService(LigaService liga) { this.liga = liga; }

    public String reporteGeneral() {
        int totalGoles = liga.getEquipos().stream().mapToInt(Equipo::getGolesTotales).sum();
        Jugador goleador = liga.getGoleador();
        Equipo masGoles = liga.rankingEquiposPorGoles().stream().findFirst().orElse(null);

        return "--- Reporte General Liga Chad ---\n" +
               "Total de goles: " + totalGoles + "\n" +
               "Goleador: " + (goleador!=null?goleador.getNombre()+" ("+goleador.getGoles()+")":"-" ) + "\n" +
               "Equipo más goleador: " + (masGoles!=null?masGoles.getNombre()+" ("+masGoles.getGolesTotales()+")":"-" );
    }

    public String reporteEquipo(Equipo eq) {
        double prom = eq.getPromedioGoles();
        String sinGoles = eq.getJugadores().stream()
                .filter(j -> j.getGoles()==0)
                .map(Jugador::getNombre)
                .reduce("", (a,b)->a.isEmpty()?b:a+", "+b);

        Titular maxMin = eq.getJugadores().stream()
                .filter(j -> j instanceof Titular)
                .map(j -> (Titular) j)
                .max(Comparator.comparingInt(Titular::getMinutosJugados))
                .orElse(null);
        Suplente masUsado = eq.getJugadores().stream()
                .filter(j -> j instanceof Suplente)
                .map(j -> (Suplente) j)
                .max(Comparator.comparingInt(Suplente::getPartidosIngresados))
                .orElse(null);

        return "--- Reporte equipo: " + eq.getNombre() + " ---\n" +
               "Prom. de goles del equipo: " + String.format("%.2f", prom) + "\n" +
               "Jugadores sin goles: " + (sinGoles.isEmpty()?"Ninguno":sinGoles) + "\n" +
               "Titular con más minutos: " + (maxMin!=null?maxMin.getNombre():"-") + "\n" +
               "Suplente más usado: " + (masUsado!=null?masUsado.getNombre():"-");
    }
}
