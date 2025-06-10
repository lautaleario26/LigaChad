package com.liga.servicios;

import com.liga.modelo.*;

public class PartidoService {
    private LigaService liga;
    public PartidoService(LigaService liga) { 
        this.liga = liga; 
    }

    public Partido crearPartido(Equipo local, Equipo visitante) {
        Partido p = new Partido(local, visitante);
        liga.registrarPartido(p);
        return p;
    }
}
