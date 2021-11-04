package com.mirosario.MiRosario.servicios;

import com.mirosario.MiRosario.enums.Zona;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ZonaServicio {

    public List<Zona> listarZonas(){
       
        List<Zona> zonas = new ArrayList<>();
       
        zonas.add(Zona.CENTRO);
        zonas.add(Zona.NORESTE);
        zonas.add(Zona.NOROESTE);
        zonas.add(Zona.NORTE);
        zonas.add(Zona.SUR);
        zonas.add(Zona.SUDOESTE);
        zonas.add(Zona.SUDOESTE);
        
        return zonas;
    }
}
